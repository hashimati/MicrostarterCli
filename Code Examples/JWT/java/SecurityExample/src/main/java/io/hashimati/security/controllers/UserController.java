package io.hashimati.security.controllers;


import io.hashimati.security.domains.User;
import io.hashimati.security.services.UserService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;

@Controller
@Introspected
public class UserController {


    @Inject
    private UserService userService;

    @Get("/h")
    public User foo()
    {
        User admin = new User(){{
            setUsername("admin");
            setPassword("admin");
            setEmail("Hello@gmail.com");
            setRoles("ADMIN_ROLE");

        }};
        return userService.save(admin);
    }

    @Get("/logout")
    public Mono<String> logout(Authentication authentication, @Header(AUTHORIZATION) String authorization){
        return Mono.just(userService.logout(authentication, authorization));
    }

}
