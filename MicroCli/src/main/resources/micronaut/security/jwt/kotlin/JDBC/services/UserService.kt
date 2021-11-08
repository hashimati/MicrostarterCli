package ${securityPackage}.services

import ${securityPackage}.PasswordEncoderService
import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.Roles
import ${securityPackage}.domains.User
import ${securityPackage}.repository.RefreshTokenRepository
import ${securityPackage}.repository.UserRepository
import ${securityPackage}.utils.CodeRandomizer
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.time.Instant


@Singleton
class UserService (private val userRepository: UserRepository?, private val passwordEncoderService: PasswordEncoderService, private val refreshTokenRepository: RefreshTokenRepository, private val codeRandomizer: CodeRandomizer)
{
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun save(user: User): User {
        user.password = passwordEncoderService.encode(user.password)
        return userRepository!!.save(user)
    }

    fun findByUsername(username: String?): User? {
        return userRepository!!.findByUsername(username)
    }
    fun activateUser(username: String?, activationCode: String?): User? {
        logger.info("Activate User {}", username)
        val user = userRepository!!.findByUsername(username)
        if (user != null && user.activationCode.equals(activationCode)) user.active = (true)
        return userRepository.update(user)
    }

    //send forget password email
    fun sendResetPasswordEmail(username: String?) {
        logger.info("Sending reset password email to user: {}", username)
        val resetPasswordCode = codeRandomizer.getRandomString(6)
        val user = userRepository!!.findByUsername(username)
        user?.resetPasswordCode = (resetPasswordCode)
        userRepository.update(user)
        //TODO: Send email. Override this method in your own implementation.
        logger.info("Reset password code: {}", resetPasswordCode)
    }

    fun resetPassword(username: String?, resetCode: String?, password: String?): User? {
        logger.info("Reset Password {}", username)
        val user = userRepository!!.findByUsername(username)
        if (user != null && user.resetPasswordCode
                .equals(resetCode)
        ) user.password = (passwordEncoderService.encode(password))
        return userRepository.save(user)
    }


    fun changePassword(username: String?, oldPassword: String?, newPassword: String?): User? {
        logger.info("Change Password {}", username)
        val user = userRepository!!.findByUsername(username)
        if (user != null && passwordEncoderService.matches(oldPassword, user.password)) user.password = (
                passwordEncoderService.encode(newPassword)
                )
        return userRepository.save(user)
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
