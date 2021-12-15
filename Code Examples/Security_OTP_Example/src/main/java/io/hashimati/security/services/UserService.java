package io.hashimati.security.services;


import io.hashimati.security.PasswordEncoderService;
import io.hashimati.security.domains.LoginEvent;
import io.hashimati.security.domains.LoginStatus;
import io.hashimati.security.domains.Roles;
import io.hashimati.security.domains.User;
import io.hashimati.security.repository.UserRepository;
import io.hashimati.security.repository.RefreshTokenRepository;
import io.hashimati.security.utils.CodeRandomizer;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.transaction.annotation.TransactionalEventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashMap;


@Singleton
public class UserService {


    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Inject
    private UserRepository userRepository;

    @Inject
    private CodeRandomizer codeRandomizer;
    @Inject
    private RefreshTokenRepository refreshTokenRepository;

    @Inject
    private PasswordEncoderService passwordEncoderService;


    public User save(User user)
    {
        user.setPassword(passwordEncoderService.encode(user.getPassword()));
        return userRepository.save(user);

    }

    // update user
    public User update(User user)
    {
        return userRepository.update(user);
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
        return userRepository.update(user);
    }


    public User changePassword(String username, String oldPassword, String newPassword) {

        logger.info("Change Password {}", username);
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoderService.matches(oldPassword, user.getPassword()))
            user.setPassword(passwordEncoderService.encode(newPassword));
        return userRepository.update(user);
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
        admin.setOneTimePassword("");
        admin.setOneTimePasswordExpiry(Instant.now());
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

    public Flux<User> findAll() {
        return Flux.fromIterable(userRepository.findAll());
    }


    @Inject
    private ApplicationEventPublisher<OTPEvent> eventPublisher;

    @Transactional
    public String firstLogin(HashMap<String, String> login) {

        String username = login.get("username");
        if(!userRepository.existsByUsername(username))
            return "The user isn't exist";
        else {
            User user = userRepository.findByUsername(username);
            if(passwordEncoderService.matches(login.get("password"), user.getPassword()))
            {
                OTPEvent otpEvent = new OTPEvent();
                otpEvent.setUsername(user.getUsername());
                eventPublisher.publishEvent(otpEvent);
                return "The OTP is being sent!";
            }
            return "The password is wrong!";
        }
    }

    @Inject
    private OTPService otpService;
    @TransactionalEventListener
    public void onLoginEvent( OTPEvent OTPEvent)
    {
        otpService.sendOTP(OTPEvent.getUsername());
    }
}

