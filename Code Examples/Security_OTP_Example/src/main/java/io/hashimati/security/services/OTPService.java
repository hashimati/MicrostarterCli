package io.hashimati.security.services;

import io.hashimati.security.PasswordEncoderService;
import io.hashimati.security.repository.UserRepository;
import io.hashimati.security.utils.CodeRandomizer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Singleton
public class OTPService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private PasswordEncoderService encoderService;

    private static Logger logger = LoggerFactory.getLogger(OTPService.class);
    public void sendOTP(String username){
        var user = userRepository.findByUsername(username);

        CodeRandomizer codeRandomizer = new CodeRandomizer();
        String code = codeRandomizer.randomOTP(4);
        user.setOneTimePassword(encoderService.encode(code));

        user.setOneTimePasswordExpiry(Instant.now().plus(5, ChronoUnit.MINUTES));
        userRepository.update(user);

        //todo replace this code with your own
        logger.info("The OTP is {}", code);
    }
}
