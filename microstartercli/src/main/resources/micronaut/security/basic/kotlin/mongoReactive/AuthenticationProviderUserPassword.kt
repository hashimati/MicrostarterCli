package ${securityPackage}

import ${securityPackage}.domains.LoginEvent
import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.event.LoginEventPublisher
import ${securityPackage}.repository.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.Instant

@Singleton
class AuthenticationProviderUserPassword(private val userRepository: UserRepository,
                                         private val eventPublisher: LoginEventPublisher,
                                         private val passwordEncoderService: PasswordEncoderService) : AuthenticationProvider {

    override fun authenticate(
        request: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> {
        log.info("Trying to login with :{}", authenticationRequest.identity)
        val loginEvent = LoginEvent()
        loginEvent.lastTryDate = Instant.now()
        loginEvent.status = LoginStatus.FAILED
        loginEvent.username = authenticationRequest.identity.toString()
        loginEvent.password = authenticationRequest.secret.toString()
        if (!userRepository!!.existsByUsername(authenticationRequest.identity.toString())) {
            log.error(
                "Couldn't find this User :{}",
                authenticationRequest.identity
            )
            return Flux.just(AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND))
        }
        val user = userRepository.findByUsername(authenticationRequest.identity.toString()).block()
        if (user.disabled) {
            log.error("This user is disabled :{}", authenticationRequest.identity)
            val result =
                Flux.just<AuthenticationResponse>(AuthenticationFailed(AuthenticationFailureReason.USER_DISABLED))
            loginEvent.status = LoginStatus.FAILED_DISABLED
//            eventPublisher!!.publishEvent(loginEvent)
            return result
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
        if (user.locked) {
            log.error("The user is locked :{}", authenticationRequest.identity)
            val result =
                Flux.just<AuthenticationResponse>(AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_LOCKED))
            loginEvent.status = LoginStatus.FAILED_LOCKED
            eventPublisher!!.publishEvent(loginEvent)
            return result
        }
        return Flux.create { emitter: FluxSink<AuthenticationResponse> ->
            if (passwordEncoderService!!.matches(authenticationRequest.secret.toString(), user.password)) {
                emptyList<Any>()
                emitter.next(
                    AuthenticationResponse.success(
                        authenticationRequest.identity as String,
                        user.roles
                    )
                )
                loginEvent.status = LoginStatus.SUCCEED
                eventPublisher!!.publishEvent(loginEvent)
                log.info(
                    "Username {} logged in successfully",
                    authenticationRequest.identity
                )
                emitter.complete()
            } else {
                log.error(
                    "{} is trying to login with invalid credentials",
                    authenticationRequest.identity
                )
                loginEvent.status = LoginStatus.FAILED_WRONG_PASSWORD
                eventPublisher!!.publishEvent(loginEvent)
                emitter.error(AuthenticationResponse.exception(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH))
            }
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(AuthenticationProviderUserPassword::class.java)
    }
}