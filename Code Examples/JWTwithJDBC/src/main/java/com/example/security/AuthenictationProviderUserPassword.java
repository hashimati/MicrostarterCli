package com.example.security;


import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.micronaut.transaction.annotation.TransactionalEventListener;
import io.reactivex.Flowable;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

public class AuthenictationProviderUserPassword implements AuthenticationProvider {


    private static final Logger log = LoggerFactory.getLogger(AuthenictationProviderUserPassword.class);
    @Inject
    private UserRepository userRepository;

    @Inject
    private ApplicationEventPublisher eventPublisher;

    @Inject
    private StrongPasswordEncryptor strongPasswordEncryptor ;


    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return authenticate(null, authenticationRequest);
    }

    @Transactional
    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> request, AuthenticationRequest<?, ?> authenticationRequest) {

        log.info("Trying to login with :{}", authenticationRequest.getIdentity());

        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setLastTryDate(new Date());
        loginEvent.setStatus(LoginStatus.FAILED);
        loginEvent.setUsername(authenticationRequest.getIdentity().toString());
        loginEvent.setPassword(authenticationRequest.getSecret().toString());


        if(!userRepository.existsByUsername(authenticationRequest.getIdentity().toString())){
            log.error("Couldn't find this User :{}", authenticationRequest.getIdentity());
            Flowable<AuthenticationResponse> result = Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND));
    ;
            return result;
        }
        User user = userRepository.findByUsername(authenticationRequest.getIdentity().toString());

        if(user.isDisabled())
        {
            log.error("This user is disabled :{}", authenticationRequest.getIdentity());

            Flowable<AuthenticationResponse> result = Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.USER_DISABLED));
            loginEvent.setStatus(LoginStatus.FAILED_DISABLED);
            eventPublisher.publishEvent(loginEvent);
            return result;
        }

//        if(user.getExpiration() != null){
//            if(user.getExpiration().toInstant().isBefore(new Date().toInstant()))
//            {
//                log.error("The user is expired :{}", authenticationRequest.getIdentity());
//
//                Flowable<AuthenticationResponse> result = Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_EXPIRED));
//                loginEvent.setStatus(LoginStatus.FAILED_EXPIRED);
//
//                eventPublisher.publishEvent(loginEvent);
//                return result;
//            }
//        }
//        if(user.getPasswordExpiration() != null) {
//            if (user.getPasswordExpiration().toInstant().isBefore(new Date().toInstant())) {
//                log.error("The user password is expired :{}", authenticationRequest.getIdentity());
//
//                Flowable<AuthenticationResponse> result = Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.PASSWORD_EXPIRED));
//                loginEvent.setStatus(LoginStatus.FAILED_PASSWORD_EXPIRED);
//
//                eventPublisher.publishEvent(loginEvent);
//                return result;
//
//            }
//        }
        if(user.isLocked())
        {
            log.error("The user is locked :{}", authenticationRequest.getIdentity());

            Flowable<AuthenticationResponse> result = Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_LOCKED));
            loginEvent.setStatus(LoginStatus.FAILED_LOCKED);

            eventPublisher.publishEvent(loginEvent);

            return result;
        }



        if ( strongPasswordEncryptor.checkPassword(authenticationRequest.getSecret().toString(), user.getPassword())) {
            log.info("Check the user password :{}", authenticationRequest.getIdentity());

            Flowable<AuthenticationResponse> result = Flowable.just(new UserDetails(user.getUsername(),
                    user.getRoles().stream().map(Role::getName).collect(Collectors.toList())));
            System.out.println("These are roles " + user.getRoles());
            loginEvent.setStatus(LoginStatus.SUCCEED);
            eventPublisher.publishEvent(loginEvent);
            return result;
        }else {
            Flowable<AuthenticationResponse> result = Flowable.just(new AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
            loginEvent.setStatus(LoginStatus.FAILED_WRONG_PASSWORD);
            eventPublisher.publishEvent(loginEvent);
            return result;
        }
    }


    @TransactionalEventListener
    public void onLoginEvent(LoginEvent loginEvent)
    {

        userRepository.updateByUsername(loginEvent.getUsername(), loginEvent.getStatus(), loginEvent.getLastTryDate());

    }
}

