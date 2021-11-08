package io.hashimati.security.domains.controllers

import io.hashimati.security.domains.User
import io.hashimati.security.services.UserService
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Controller("/api/security/users")
@Introspected
class UserController(userService: UserService) {
    @Inject
    private val userService: UserService
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    fun saveUsers(@Body user: User): Mono<User> {
        logger.info("save user {}", user)
        return userService.save(user)
    }

    //activae user
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/activate")
    fun activateUsers(@Body user: User): Mono<User?>? {
        /*
        the client should send json object of this structure
        {
            "username": "user",
            "activationCode": "activationCode"
        }
         */
        logger.info("activate user {}", user)
        return userService.activateUser(user.username, user.activationCode)
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/forgot")
    fun forgotPassword(@Body user: String?): Mono<Void> {
        /*
        the client should send json object of this structure
        {
            "username": "user"
        }
         */
        logger.info("forgot password {}", user)
        userService.sendResetPasswordEmail(user)
        return Mono.empty()
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/reset")
    fun resetPassword(@Body user: User): Mono<User?>? {
        /*
        the client should send json object of this structure
        {
            "username": "user",
            "resetCode": "resetCode"
            "password": "password"
        }
         */
        logger.info("reset user {}", user)
        return userService.resetPassword(user.username, user.resetPasswordCode, user.password)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }

    init {
        this.userService = userService
    }
}
