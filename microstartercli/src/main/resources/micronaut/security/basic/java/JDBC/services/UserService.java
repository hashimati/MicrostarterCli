package ${securityPackage}.services;


import ${securityPackage}.PasswordEncoderService;
import ${securityPackage}.domains.LoginStatus;
import ${securityPackage}.domains.Roles;
import ${securityPackage}.domains.User;
import ${securityPackage}.repository.UserRepository;
import ${securityPackage}.utils.CodeRandomizer;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.Calendar;
import java.time.Instant;


@Singleton
public class UserService {


    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    private UserRepository userRepository;

    @Inject
    private CodeRandomizer codeRandomizer;

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


    public User activateUser(String username, String activationCode) {

        logger.info("Activate User {}", username);
        User user = userRepository.findByUsername(username);
        if (user != null && user.getActivationCode().equals(activationCode))
            user.setActive(true);
        return userRepository.update(user);
    }

    //send forget password email
    public void sendResetPasswordEmail(String username)
    {
        logger.info("Sending reset password email to user: {}",  username);
        String resetPasswordCode = codeRandomizer.getRandomString(6);
        User user = userRepository.findByUsername(username);
        user.setResetPasswordCode(resetPasswordCode);
        userRepository.update(user);
        //TODO: Send email. Override this method in your own implementation.
        logger.info("Reset password code: {}", resetPasswordCode);
    }

    public User resetPassword(String username, String resetCode, String password) {

        logger.info("Reset Password {}", username);
        User user = userRepository.findByUsername(username);
        if (user != null && user.getResetPasswordCode().equals(resetCode))
            user.setPassword(passwordEncoderService.encode(password));
        return userRepository.save(user);
    }


    public User changePassword(String username, String oldPassword, String newPassword) {

        logger.info("Change Password {}", username);
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoderService.matches(oldPassword, user.getPassword()))
            user.setPassword(passwordEncoderService.encode(newPassword));
        return userRepository.save(user);
    }

    @EventListener
    public void init(StartupEvent startupEvent){

        if(!userRepository.existsByUsername("admin")) {
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
        }

        System.out.println(findByUsername("admin"));

    }



    public Flux<User> findAll() {
        return Flux.fromIterable(userRepository.findAll());
    }
}
