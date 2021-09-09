package io.hashimati.security.services;


import io.hashimati.security.PasswordEncoderService;
import io.hashimati.security.domains.LoginStatus;
import io.hashimati.security.domains.Roles;
import io.hashimati.security.domains.User;
import io.hashimati.security.repository.UserRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Calendar;
import java.util.Date;


@Singleton
public class UserService {

    @Inject
    private UserRepository userRepository;


    @Inject
    private PasswordEncoderService passwordEncoderService;


    public User save(User user)
    {
        user.setPassword(passwordEncoderService.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);

    }

    @EventListener
    public void init(StartupEvent startupEvent){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1000);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setActive(true);
        admin.setRoles(Roles.ADMIN);
        admin.setEmail("Hello@gmail.com");
        admin.setLastTimeLogin(new Date());
        admin.setActivationCode("0000");
        admin.setLastTimeTryToLogin(new Date());
        admin.setLastLoginStatus(LoginStatus.SUCCEED);
        System.out.println(admin
        );
        save(admin);
//        if(!userRepository.existsByUsername(admin.getUsername()))

        System.out.println(findByUsername("admin"));

    }


}
