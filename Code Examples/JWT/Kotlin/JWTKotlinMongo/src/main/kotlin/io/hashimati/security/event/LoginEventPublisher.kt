package io.hashimati.security.event

import io.hashimati.security.domains.LoginEvent
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.scheduling.annotation.Async
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class LoginEventPublisher {

    @Inject
    internal var eventPublisher: ApplicationEventPublisher<LoginEvent>? = null

    fun publishEvent(loginEvent: LoginEvent) {
        log.info("Publishing login event: {}", loginEvent)
        eventPublisher!!.publishEvent(loginEvent )
   }

    companion object {
        private val log = LoggerFactory.getLogger(LoginEventPublisher::class.java)
    }
}