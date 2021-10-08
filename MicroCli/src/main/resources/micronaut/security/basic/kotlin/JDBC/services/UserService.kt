package ${securityPackage}.services

import ${securityPackage}.PasswordEncoderService
import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.Roles
import ${securityPackage}.domains.User
import ${securityPackage}.repository.UserRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import java.time.Instant


@Singleton
class UserService (private val userRepository: UserRepository?, private val passwordEncoderService: PasswordEncoderService){

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
            "SUCCESS"
        } catch (ex: Exception) {
            "FAILED"
        }
    }
}
