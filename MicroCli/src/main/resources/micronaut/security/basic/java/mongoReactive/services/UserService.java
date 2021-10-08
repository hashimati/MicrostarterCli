package ${securityPackage}.services;


import ${securityPackage}.PasswordEncoderService;
import ${securityPackage}.domains.LoginStatus;
import ${securityPackage}.domains.Roles;
import ${securityPackage}.domains.User;
import ${securityPackage}.repository.UserRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;


@Singleton
public class UserService {

    @Inject
    private UserRepository userRepository;


    @Inject
    private PasswordEncoderService passwordEncoderService;


    public Mono<User> save(User user)
    {
        user.setPassword(passwordEncoderService.encode(user.getPassword()));
        user.setId(user.getUsername());
        return userRepository.save(user);

    }
    public Mono<User> findByUsername(String username)
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
        admin.getRoles().add(Roles.ADMIN);
        admin.setEmail("Hello@gmail.com");
        admin.setLastTimeLogin(new Date());
        admin.setActivationCode("0000");
        admin.setLastTimeTryToLogin(new Date());
        admin.setLastLoginStatus(LoginStatus.SUCCEED);
        System.out.println(admin
        );
        if(!userRepository.existsByUsername(admin.getUsername()))
        save(admin).block();
//        if(!userRepository.existsByUsername(admin.getUsername()))

        System.out.println(findByUsername("admin"));

    }


}
