package io.hashimati.security.controllers;


import io.hashimati.security.domains.User;
import io.hashimati.security.services.UserService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Validated
@Controller("/api/security/users")
@Introspected
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    public User saveUsers(@Body User user) {
        logger.info("save user {}", user);
        return userService.save(user);
    }


    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/login")
    public String login(@Body HashMap<String, String> login) {
        logger.info("login user {}", login);
        return userService.firstLogin(login);
    }
    //activae user
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/activate")
    public User activateUsers(@Body User user) {
        /*
        the client should send json object of this structure
        {
            "username": "user",
            "activationCode": "activationCode"
        }
         */
        logger.info("activate user {}", user);
        return userService.activateUser(user.getUsername(), user.getActivationCode());
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/forgot")
    public Mono<Void> forgotPassword(@Body String user) {
        /*
        the client should send json object of this structure
        {
            "username": "user"
        }
         */
        logger.info("forgot password {}", user);
        userService.sendResetPasswordEmail(user);
        return Mono.empty();
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/reset")
    public User resetPassword(@Body User user) {
        /*
        the client should send json object of this structure
        {
            "username": "user",
            "resetCode": "resetCode"
            "password": "password"
        }
         */
        logger.info("reset user {}", user);
        return userService.resetPassword(user.getUsername(), user.getResetPasswordCode(), user.getPassword());
    }

    @Get("/logout")
    public Mono<String> logout(Authentication authentication, @Header(AUTHORIZATION) String authorization){
        return Mono.just(userService.logout(authentication, authorization));
    }

}

