package io.hashimati.security.services

import io.hashimati.security.PasswordEncoderService
import io.hashimati.security.domains.LoginStatus
import io.hashimati.security.domains.Roles
import io.hashimati.security.domains.User
import io.hashimati.security.repository.RefreshTokenRepository
import io.hashimati.security.repository.UserRepository
import io.hashimati.security.utils.CodeRandomizer
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

import java.time.Instant

@Singleton
class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private RefreshTokenRepository refreshTokenRepository;

    @Inject
    private CodeRandomizer codeRandomizer;


    @Inject
    private PasswordEncoderService passwordEncoderService;


    User save(User user)
    {
        user.setPassword(passwordEncoderService.encode(user.getPassword()));
        return userRepository.save(user);

    }

    User findByUsername(String username)
    {
        return userRepository.findByUsername(username);

    }

    User activateUser(String username, String activationCode)
    {
        logger.info("Activate User {}", username)
        User user = userRepository.findByUsername(username)
        if (user != null && user.getActivationCode().equals(activationCode))
            user.setActive(true)
        return userRepository.update(user)
    }

    void sendResetPasswordEmail(String username)
    {
        logger.info("Sending reset password email to user: {}",  username)
        String resetPasswordCode = codeRandomizer.getRandomString(6)
        User user = userRepository.findByUsername(username)
        user.setResetPasswordCode(resetPasswordCode)
        userRepository.update(user)
        //TODO: Send email. Override this method in your own implementation.
        logger.info("Reset password code: {}", resetPasswordCode)
    }

    Mono<User> resetPassword(String username, String resetPasswordCode, String password)
    {
        logger.info("Reset Password {}", username)
        User user = userRepository.findByUsername(username)
        if (user != null && user.getResetPasswordCode().equals(resetCode))
            user.setPassword(passwordEncoderService.encode(password))
        return userRepository.save(user)
    }

    Mono<User> changePassword(String username, String oldPassword, String password)
    {

        logger.info("Change Password {}", username)
        User user = userRepository.findByUsername(username)
        if (user != null && passwordEncoderService.matches(oldPassword, user.getPassword()))
            user.setPassword(passwordEncoderService.encode(newPassword))
        return userRepository.save(user)
    }

    @EventListener
    void init(StartupEvent startupEvent){
//        Calendar c = Calendar.getInstance();
//        c.setTime(new Date());
//        c.add(Calendar.YEAR, 1000);

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


     String logout(Authentication authentication, String authorization) {
        try {
            refreshTokenRepository.deleteByUsername(authentication.getName());
            return "SUCCESS";
        }catch (Exception ex){
            return "FAILED";
        }
    }
}