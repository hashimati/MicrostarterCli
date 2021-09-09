package io.hashimati;

import io.hashimati.security.domains.Roles;
import io.hashimati.security.domains.User;
import io.hashimati.security.services.UserService;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }


    @Inject
    private UserService userService;

}
