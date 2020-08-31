package io.hashimati.micronautsecurityjwt.controllers;


import io.hashimati.micronautsecurityjwt.domains.User;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.*;

import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import io.hashimati.micronautsecurityjwt.services.UserService;


@Controller("/api/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject UserService userService;



    @Post("/save")
    public User save(User user){
        log.info("Saving  User : {}", user);
        //saving Object
        return userService.save(user);
    }


    @Get("/get")
    public User findById(@RequestAttribute("id") long id){
        return userService.findById(id);
    }

    @Delete("/delete/{id}")
    public boolean deleteById(@PathVariable("id") long id){
        log.info("Deleting User by Id: {}", id);
        return  userService.deleteById(id);
    }
}