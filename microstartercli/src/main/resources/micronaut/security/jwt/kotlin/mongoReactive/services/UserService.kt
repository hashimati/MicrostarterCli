package ${securityPackage}.services

import org.slf4j.LoggerFactory


import ${securityPackage}.PasswordEncoderService
import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.Roles
import ${securityPackage}.domains.User
import ${securityPackage}.repository.UserRepository
import ${securityPackage}.utils.CodeRandomizer

import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import java.time.Instant
import java.util.*

@Singleton
class UserService (private val userRepository: UserRepository?, private val passwordEncoderService: PasswordEncoderService, private val codeRandomizer: CodeRandomizer){
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun save(user: User): Mono<User> {
        user.password = passwordEncoderService!!.encode(user.password)
        user.id = user.username
        return userRepository!!.save(user)
    }

    fun findByUsername(username: String?): Mono<User> {
        return userRepository!!.findByUsername(username)
    }

    fun activateUser(username: String?, activationCode: String?): Mono<User?>? {
        logger.info("Activating user: {}", username)
        return userRepository!!.findByUsername(username)
            .filter { user -> user.activationCode.equals(activationCode) }
            .map { user ->
                user.active = (true)
                user.dateUpdated = Instant.now()
                user
            }
            .flatMap(userRepository::update!!)
            .onErrorReturn(null)
    }

    fun sendResetPasswordEmail(username: String?) {
        logger.info("Sending reset password email to user: {}", username)
        val resetPasswordCode = codeRandomizer.getRandomString(6)
        userRepository!!.findByUsername(username).map { user ->
            user.resetPasswordCode = (resetPasswordCode)
            user.dateUpdated = Instant.now()
            user
        }.flatMap(userRepository::update!!)


        //TODO: Send email. Override this method in your own implementation.
        logger.info("Reset password code: {}", resetPasswordCode)
    }

    fun resetPassword(username: String?, resetPasswordCode: String?, password: String?): Mono<User?>? {
        logger.info("Resetting password for user: {}", username)
        return userRepository!!.findByUsername(username).filter { user ->
            user.password.equals(resetPasswordCode)
        }
            .map { user ->
                user.password.equals(passwordEncoderService.encode(password))
                user.resetPasswordCode = null
                user.dateUpdated = Instant.now()
                user
            }
            .flatMap(userRepository::update!!)
            .onErrorReturn(null)
    }

    fun changePassword(username: String?, oldPassword: String?, password: String?): Mono<User?>? {
        logger.info("Changing password for user: {}", username)
        return userRepository!!.findByUsername(username).filter { user ->
            passwordEncoderService.matches(
                oldPassword,
                user.password
            )
        }.map { user ->
            user.password = (passwordEncoderService.encode(password))
            user.dateUpdated = Instant.now()
            user
        }.flatMap(userRepository::update!!)
            .onErrorReturn(null)
    }

    @EventListener
    fun init(startupEvent: StartupEvent?) {
        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.YEAR, 1000)
        val admin = User()
        admin.username = "admin"
        admin.password = "admin"
        admin.active = true
        admin.roles.add(Roles.ADMIN)
        admin.email = "Hello@gmail.com"
        admin.lastTimeLogin = Instant.now()
        admin.activationCode = "0000"
        admin.lastTimeTryToLogin = Instant.now()
        admin.lastLoginStatus = LoginStatus.SUCCEED
        println(
            admin
        )
        if (!userRepository!!.existsByUsername(admin.username)) save(admin).block()
        //        if(!userRepository.existsByUsername(admin.getUsername()))
        println(findByUsername("admin"))
    }
}