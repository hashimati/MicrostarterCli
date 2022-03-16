package micronaut.security.jwt.groovy.mongoReactive.event

{securityPackage}.event

import ${securityPackage}.domains.LoginEvent
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class LoginEventPublisher
{
    private static final Logger log = LoggerFactory.getLogger(LoginEventPublisher.class)
    @Inject
    ApplicationEventPublisher eventPublisher

    @Async
    void publishEvent(LoginEvent loginEvent) {
        log.info("Publishing login event: {}", loginEvent)

        eventPublisher.publishEvent(loginEvent)
    }
}
