package io.hashimati.security.services

import io.hashimati.security.PasswordEncoderService
import io.hashimati.security.domains.LoginStatus
import io.hashimati.security.domains.Roles
import io.hashimati.security.domains.User
import io.hashimati.security.repository.RefreshTokenRepository
import io.hashimati.security.repository.UserRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import java.time.Instant


@Singleton
class UserService (private val userRepository: UserRepository?, private val passwordEncoderService: PasswordEncoderService,
private val refreshTokenRepository: RefreshTokenRepository){

    fun save(user: User): User {
        user.password = passwordEncoderService.encode(user.password)
        return userRepository!!.save(user)
    }

    fun findByUsername(username: String?): User? {
        return userRepository!!.findByUsername(username)
    }

    @EventListener
    fun init(startupEvent: StartupEvent) {
        val admin = User()
        admin.username = "admin"
        admin.password = "admin"
        admin.active = true
        admin.roles = Roles.ADMIN
        admin.email = "Hello@gmail.com"
        admin.lastTimeLogin = Instant.now()
        admin.activationCode = "0000"
        admin.lastTimeTryToLogin = Instant.now()
        admin.lastLoginStatus = LoginStatus.SUCCEED
        println(
            admin
        )
        save(admin)
        println(findByUsername("admin"))
    }

    fun logout(authentication: Authentication, authorization: String?): String {
        return try {
            refreshTokenRepository.deleteByUsername(authentication.name)
            "SUCCESS"
        } catch (ex: Exception) {
            "FAILED"
        }
    }
}
