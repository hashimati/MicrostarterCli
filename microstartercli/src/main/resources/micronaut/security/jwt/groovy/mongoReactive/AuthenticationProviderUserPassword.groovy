package ${securityPackage}

import ${securityPackage}.PasswordEncoderService
import ${securityPackage}.domains.LoginEvent
import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.User
import ${securityPackage}.repository.RefreshTokenRepository
import ${securityPackage}.repository.UserRepository
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.scheduling.annotation.Async
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux

import java.time.Instant
import java.util.Arrays


@Singleton
public class AuthenticationProviderUserPassword implements HttpRequestAuthenticationProvider<HttpRequest<?>> {


    private static final Logger log = LoggerFactory.getLogger(AuthenticationProviderUserPassword.class)
    @Inject
    private UserRepository userRepository
    @Inject
    private RefreshTokenRepository refreshTokenRepository

    @Inject
    private ApplicationEventPublisher<LoginEvent> eventPublisher

    @Inject
    private PasswordEncoderService passwordEncoderService

    //@Transactional
    @Override
    public @NonNull AuthenticationResponse authenticate(@Nullable HttpRequest<HttpRequest<?>> requestContext, @NonNull AuthenticationRequest<String, String> authenticationRequest) {

        log.info("Trying to login with :{}", authenticationRequest.getIdentity())

        LoginEvent loginEvent = new LoginEvent()
        loginEvent.setLastTryDate(Instant.now())
        loginEvent.setStatus(LoginStatus.FAILED)

        loginEvent.setUsername(authenticationRequest.getIdentity())
        loginEvent.setPassword(authenticationRequest.getSecret())


        if(!userRepository.existsByUsername(authenticationRequest.getIdentity())){
            log.error("Couldn't find this User :{}", authenticationRequest.getIdentity())
            Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND))

            return result.blockFirst()
        }
        User user = userRepository.findByUsername(authenticationRequest.getIdentity())

        loginEvent.setLastTimeLogin(user.getLastTimeLogin())
        if(user.isDisabled())
        {
            log.error("This user is disabled :{}", authenticationRequest.getIdentity())

            Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.USER_DISABLED))
            loginEvent.setStatus(LoginStatus.FAILED_DISABLED)
            eventPublisher.publishEvent(loginEvent)
            return result.blockFirst()
        }

//        if(user.getExpiration() != null)
//            if(user.getExpiration().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
//            {
//                log.error("The user is expired :{}", authenticationRequest.getIdentity())
//
//                Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_EXPIRED))
//                loginEvent.setStatus(LoginStatus.FAILED_EXPIRED)
//
//                eventPublisher.publishEvent(loginEvent)
//                return result
//            }
//        if(user.getPasswordExpiration() != null)
//            if(user.getPasswordExpiration().toInstant().isBefore(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
//            {
//                log.error("The user password is expired :{}", authenticationRequest.getIdentity())
//
//                Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.PASSWORD_EXPIRED))
//                loginEvent.setStatus(LoginStatus.FAILED_PASSWORD_EXPIRED)
//
//                eventPublisher.publishEvent(loginEvent)
//                return result
//
//            }
        if(user.isLocked())
        {
            log.error("The user is locked :{}", authenticationRequest.getIdentity())

            Flux<AuthenticationResponse> result = Flux.just(new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_LOCKED))
            loginEvent.setStatus(LoginStatus.FAILED_LOCKED)

            eventPublisher.publishEvent(loginEvent)

            return result.blockFirst()
        }


        if(passwordEncoderService.matches(authenticationRequest.getSecret(), user.getPassword())){
            refreshTokenRepository.deleteByUsername(authenticationRequest.getIdentity())
            loginEvent.setStatus(LoginStatus.SUCCEED)
            loginEvent.setLastTimeLogin(Instant.now())
            eventPublisher.publishEvent(loginEvent)
            log.info("Username {} logged in successfully", authenticationRequest.getIdentity())
            return AuthenticationResponse.success(authenticationRequest.getIdentity(), Arrays.asList(user.getRoles().split(",")))

        }
        else{
            log.error("{} is trying to login with invalid credentials", authenticationRequest.getIdentity())
            loginEvent.setStatus(LoginStatus.FAILED)
            eventPublisher.publishEvent(loginEvent)
            return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
        }


    }

    @EventListener
    @Async
    void onLoginEvent(LoginEvent loginEvent)
    {
        userRepository.updateByUsername(loginEvent.getUsername(), loginEvent.getStatus(), loginEvent.getLastTryDate(), loginEvent.getLastTimeLogin())
    }
}

