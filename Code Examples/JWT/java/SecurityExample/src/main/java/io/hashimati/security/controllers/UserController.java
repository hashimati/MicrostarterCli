package io.hashimati.security.controllers;


import io.hashimati.domains.Message;
import io.hashimati.domains.MessageCode;
import io.hashimati.domains.MessageType;
import io.hashimati.security.domains.User;
import io.hashimati.security.services.UserService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import io.reactivex.Single;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Validated
@Controller
@Introspected
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    @Secured(value = SecurityRule.IS_ANONYMOUS)
    @Post("/register/{role}")
    public Mono<Message> registerUser(@Body @Valid User user, @PathVariable("role") String role) throws Exception {
        user.addRole(role);
        logger.info("Saving user {}", user);
        try {
            return Mono.just(new Message<User>(){{
                    setMessage("The user is created Successfully");
                    setCode(MessageCode.POST_SUCCESS_MESSAGE);
                    setData(userService.save(user));
                    setMessageType(MessageType.SUCCESS);
            }});
        } catch (Exception e) {
            return Mono.just(new Message<User>() {{
                setMessage("This is not a valid user");
                setCode(MessageCode.INVALID_USER);
                setData(user);
                setMessageType(MessageType.ERROR);
            }});
        }
    }

    @Get("/findAll")
    public Flux<User> findAll() {
        logger.info("Find All Users");
        return userService.findAll();
    }

    @Get("/logout")
    public Mono<String> logout(Authentication authentication, @Header(AUTHORIZATION) String authorization){
        return Mono.just(userService.logout(authentication, authorization));
    }

}
