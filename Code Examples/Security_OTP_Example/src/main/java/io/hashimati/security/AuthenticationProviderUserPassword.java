package io.hashimati.security;


import io.hashimati.security.domains.LoginEvent;
import io.hashimati.security.domains.LoginStatus;
import io.hashimati.security.domains.User;
import io.hashimati.security.repository.RefreshTokenRepository;
import io.hashimati.security.repository.UserRepository;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.micronaut.transaction.annotation.TransactionalEventListener;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;


public class AuthenticationProviderUserPassword implements AuthenticationProvider {


    private final Logger log = LoggerFactory.getLogger(AuthenticationProviderUserPassword.class);
    @Inject
    private UserRepository userRepository;
    @Inject
    private RefreshTokenRepository refreshTokenRepository;

    @Inject
    private ApplicationEventPublisher<LoginEvent> eventPublisher;

    @Inject
    private PasswordEncoderService passwordEncoderService;

    @Transactional
    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> request, AuthenticationRequest<?, ?> authenticationRequest) {

        log.info("Trying to login with :{}", authenticationRequest.getIdentity());

        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setLastTryDate(Instant.now());
        loginEvent.setStatus(LoginStatus.FAILED);
        loginEvent.setUsername(authenticationRequest.getIdentity().toString());
        loginEvent.setPassword(authenticationRequest.getSecret().toString());


        if(!userRepository.existsByUsername(authenticationRequest.getIdentity().toString())){
            log.error("Couldn't find this User :{}", authenticationRequest.getIdentity());
            Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND));;

            return result;
        }
        User user = userRepository.findByUsername(authenticationRequest.getIdentity().toString());

        if(user.isDisabled())
        {
            log.error("This user is disabled :{}", authenticationRequest.getIdentity());

            Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.USER_DISABLED));
            loginEvent.setStatus(LoginStatus.FAILED_DISABLED);
            eventPublisher.publishEvent(loginEvent);
            return result;
        }

//        if(user.getExpiration() != null)
//            if(user.getExpiration().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
//            {
//                log.error("The user is expired :{}", authenticationRequest.getIdentity());
//
//                Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_EXPIRED));
//                loginEvent.setStatus(LoginStatus.FAILED_EXPIRED);
//
//                eventPublisher.publishEvent(loginEvent);
//                return result;
//            }
//        if(user.getPasswordExpiration() != null)
//            if(user.getPasswordExpiration().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
//            {
//                log.error("The user password is expired :{}", authenticationRequest.getIdentity());
//
//                Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.PASSWORD_EXPIRED));
//                loginEvent.setStatus(LoginStatus.FAILED_PASSWORD_EXPIRED);
//
//                eventPublisher.publishEvent(loginEvent);
//                return result;
//
//            }
        if(user.isLocked())
        {
            log.error("The user is locked :{}", authenticationRequest.getIdentity());

            Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_LOCKED));
            loginEvent.setStatus(LoginStatus.FAILED_LOCKED);

            eventPublisher.publishEvent(loginEvent);

            return result;
        }

        return Flux.create(emitter->{
            if(passwordEncoderService.matches(authenticationRequest.getSecret().toString(), user.getOneTimePassword()) && user.getOneTimePasswordExpiry().isAfter(Instant.now())){
                refreshTokenRepository.deleteByUsername(((String) authenticationRequest.getIdentity()).toString());
                Collections.emptyList();
                emitter.next(AuthenticationResponse.success((String)authenticationRequest.getIdentity(), Arrays.asList(user.getRoles().split(","))));
                loginEvent.setStatus(LoginStatus.SUCCEED);
                eventPublisher.publishEvent( loginEvent);
                log.info("Username {} logged in successfully", authenticationRequest.getIdentity());
                emitter.complete();
            }
            else{
                log.error("{} is trying to login with invalid credentials", authenticationRequest.getIdentity());
                emitter.error(AuthenticationResponse.exception(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
            }
        });

    }

    @TransactionalEventListener
    public void onLoginEvent( LoginEvent loginEvent)
    {

        userRepository.updateByUsername(loginEvent.getUsername(), loginEvent.getStatus(), loginEvent.getLastTryDate());

    }
}

