package ${securityPackage}.event

{securityPackage}.event

import ${securityPackage}.domains.LoginEvent
import ${securityPackage}.domains.User
import ${securityPackage}.repository.UserRepository
import io.micronaut.context.event.ApplicationEventListener
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class LoginEventListener implements ApplicationEventListener<LoginEvent>  {
    private static final Logger log = LoggerFactory.getLogger(LoginEventListener.class)

    @Inject
    private UserRepository userRepository

    @Override
    void onApplicationEvent(LoginEvent event) {
        log.info("Saving login event: {}", event)
        User user =  userRepository.findByUsername(event.getUsername()).block()
        user.setLastLoginStatus(event.getStatus())
        user.setLastTimeTryToLogin(event.getLastTimeLogin())
        userRepository.update(user).block()
    }
}
