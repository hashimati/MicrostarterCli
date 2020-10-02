package com.example.security;


import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@Controller("/api/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    @Inject
    private RoleRepository roleRepository;
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/{role}/save")
    public User save(@PathVariable String role, @Body User user) throws Exception {
        return userService.save(role,  user);

    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/findAll")
    public Iterable<User> findAll()
    {
        return userService.findAll();

    }
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/repo")
    public Iterable<Role> repo()
    {
        return roleRepository.findAll();

    }

}

