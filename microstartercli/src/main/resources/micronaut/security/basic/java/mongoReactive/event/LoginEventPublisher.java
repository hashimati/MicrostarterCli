package ${securityPackage}.event;

import ${securityPackage}.domains.LoginEvent;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LoginEventPublisher
{
    private static final Logger log = LoggerFactory.getLogger(LoginEventPublisher.class);
    @Inject
    ApplicationEventPublisher eventPublisher;

    @Async
    public void publishEvent(LoginEvent loginEvent) {
        log.info("Publishing login event: {}", loginEvent);

        eventPublisher.publishEvent(loginEvent);
    }
}
