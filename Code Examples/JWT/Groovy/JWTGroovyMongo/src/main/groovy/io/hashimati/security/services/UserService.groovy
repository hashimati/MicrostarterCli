package io.hashimati.security.services


import io.hashimati.security.PasswordEncoderService
import io.hashimati.security.domains.LoginStatus
import io.hashimati.security.domains.Roles
import io.hashimati.security.domains.User
import io.hashimati.security.repository.UserRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

import java.util.Arrays
import java.util.Calendar
import java.util.Date


@Singleton
 class UserService {

    @Inject
    private UserRepository userRepository


    @Inject
    private PasswordEncoderService passwordEncoderService


     Mono<User> save(User user)
    {
        user.setPassword(passwordEncoderService.encode(user.getPassword()))
        user.setId(user.getUsername())
        return userRepository.save(user)

    }
     Mono<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username)
    }

    @EventListener
     void init(StartupEvent startupEvent){
        Calendar c = Calendar.getInstance()
        c.setTime(new Date())
        c.add(Calendar.YEAR, 1000)

        User admin = new User()
        admin.username = "admin"
        admin.password = "admin"
        admin.setActive(true)
        admin.getRoles().add(Roles.ADMIN)
        admin.setEmail("Hello@gmail.com")
        admin.setLastTimeLogin(new Date())
        admin.setActivationCode("0000")
        admin.setLastTimeTryToLogin(new Date())
        admin.setLastLoginStatus(LoginStatus.SUCCEED)
        System.out.println(admin
        )
        if(!userRepository.existsByUsername(admin.getUsername()))
            save(admin).block()


    }


}
