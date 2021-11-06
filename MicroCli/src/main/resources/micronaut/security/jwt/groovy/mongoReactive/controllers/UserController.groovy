package ${securityPackage}.controllers


import io.hashimati.security.domains.User
import io.hashimati.security.services.UserService
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Controller("/api/security/users")
@Introspected
class UserController {


    private static Logger logger = LoggerFactory.getLogger(UserController.class)

    @Inject
    private UserService userService

    public UserController(UserService userService) {
        this.userService = userService
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    Mono<User> saveUsers(@Body User user) {
        logger.info("save user {}", user)
        return userService.save(user)
    }

    //activae user
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/activate")
    Mono<User> activateUsers(@Body User user) {
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
    Mono<Void> forgotPassword(@Body String user) {
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
    Mono<User> resetPassword(@Body User user) {
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



}
