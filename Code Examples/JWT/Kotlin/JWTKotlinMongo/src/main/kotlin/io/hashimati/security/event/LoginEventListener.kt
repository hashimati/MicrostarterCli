package io.hashimati.security.event

import io.hashimati.security.domains.LoginEvent
import io.hashimati.security.repository.UserRepository
import io.micronaut.context.event.ApplicationEventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class LoginEventListener(private val userRepository: UserRepository) : ApplicationEventListener<LoginEvent> {

    override fun onApplicationEvent(event: LoginEvent) {
        log.info("Saving login event: {}", event)
        val user = userRepository!!.findByUsername(event.username).block()
        user.lastLoginStatus = event.status
        user.lastTimeTryToLogin = event.lastTimeLogin
        userRepository.update(user).block()
    }

    companion object {
        private val log = LoggerFactory.getLogger(LoginEventListener::class.java)
    }
}