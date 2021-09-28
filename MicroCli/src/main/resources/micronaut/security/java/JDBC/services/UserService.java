package ${securityPackage}.services;


import ${securityPackage}.PasswordEncoderService;
import ${securityPackage}.domains.LoginStatus;
import ${securityPackage}.domains.Roles;
import ${securityPackage}.domains.User;
import ${securityPackage}.repository.RefreshTokenRepository;
import ${securityPackage}.repository.UserRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.time.Instant;


@Singleton
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private RefreshTokenRepository refreshTokenRepository;

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


        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setActive(true);
        admin.setRoles(Roles.ADMIN);
        admin.setEmail("Hello@gmail.com");
        admin.setLastTimeLogin(Instant.now());
        admin.setActivationCode("0000");
        admin.setLastTimeTryToLogin(Instant.now());
        admin.setLastLoginStatus(LoginStatus.SUCCEED);
        System.out.println(admin
        );
        save(admin);

        System.out.println(findByUsername("admin"));

    }


    public String logout(Authentication authentication, String authorization) {
        try {
            refreshTokenRepository.deleteByUsername(authentication.getName());
            return "SUCCESS";
        }catch (Exception ex){
            return "FAILED";
        }
    }
}
