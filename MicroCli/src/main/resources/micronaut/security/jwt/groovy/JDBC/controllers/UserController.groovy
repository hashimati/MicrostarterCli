package ${securityPackage}.controllers


import ${securityPackage}.domains.User
import ${securityPackage}.services.UserService
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

import static io.micronaut.http.HttpHeaders.AUTHORIZATION


@Validated
@Controller("/api/security/users")
@Introspected
class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class)

    @Inject
    private UserService userService

    UserController(UserService userService) {
        this.userService = userService
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    User saveUsers(@Body User user) {
        logger.info("save user {}", user)
        return userService.save(user)
    }

    //activae user
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/activate")
    User activateUsers(@Body User user) {
        /*
        the client should send json object of this structure
        {
            "username": "user",
            "activationCode": "activationCode"
        }
         */
        logger.info("activate user {}", user)
        return userService.activateUser(user.getUsername(), user.getActivationCode())
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/forgot")
    void forgotPassword(@Body String user) {
        /*
        the client should send json object of this structure
        {
            "username": "user"
        }
         */
        logger.info("forgot password {}", user)
        userService.sendResetPasswordEmail(user)

    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/reset")
    User resetPassword(@Body User user) {
        /*
        the client should send json object of this structure
        {
            "username": "user",
            "resetCode": "resetCode"
            "password": "password"
        }
         */
        logger.info("reset user {}", user)
        return userService.resetPassword(user.getUsername(), user.getResetPasswordCode(), user.getPassword())
    }

    @Get("/logout")
    String logout(Authentication authentication, @Header(AUTHORIZATION) String authorization){
        return userService.logout(authentication, authorization)
    }

