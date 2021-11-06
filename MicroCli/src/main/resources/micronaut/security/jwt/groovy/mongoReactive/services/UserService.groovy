package micronaut.security.jwt.groovy.mongoReactive.services

{securityPackage}.services

import ${securityPackage}.PasswordEncoderService
import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.Roles
import ${securityPackage}.domains.User
import ${securityPackage}.repository.UserRepository
import ${securityPackage}.utils.CodeRandomizer
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono




@Singleton
class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserRepository userRepository


    @Inject
    private PasswordEncoderService passwordEncoderService
    @Inject
    private CodeRandomizer codeRandomizer;


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
    Mono<User> activateUser(String username, String activationCode)
    {
        logger.info("Activating user: {}",  username);
        return userRepository.findByUsername(username).filter(
                user -> user.getActivationCode().equals(activationCode)
        )
                .map(user -> {
                    user.setActive(true);
                    user.setDateUpdated(new Date());
                    return user;
                })
                .flatMap(userRepository::update)
                .onErrorReturn(null);
    }

    void sendResetPasswordEmail(String username)
    {
        logger.info("Sending reset password email to user: {}",  username);
        String resetPasswordCode = codeRandomizer.getRandomString(6);
        userRepository.findByUsername(username).map(user -> {
            user.setResetPasswordCode(resetPasswordCode);
            user.setDateUpdated(new Date());
            return user;
        }).flatMap(userRepository::update);


        //TODO: Send email. Override this method in your own implementation.
        logger.info("Reset password code: {}", resetPasswordCode);
    }

    Mono<User> resetPassword(String username, String resetPasswordCode, String password)
    {
        logger.info("Resetting password for user: {}",  username);
        return userRepository.findByUsername(username).filter(
                user -> user.getResetPasswordCode().equals(resetPasswordCode)
        )
                .map(user -> {
                    user.setPassword(passwordEncoderService.encode(password));
                    user.setResetPasswordCode(null);
                    user.setDateUpdated(new Date());
                    return user;
                })
                .flatMap(userRepository::update)
                .onErrorReturn(null);
    }

    Mono<User> changePassword(String username, String oldPassword, String password)
    {
        logger.info("Changing password for user: {}",  username);
        return userRepository.findByUsername(username).filter(user -> passwordEncoderService.matches(oldPassword, user.getPassword())).map(user -> {
            user.setPassword(passwordEncoderService.encode(password));
            user.setDateUpdated(new Date());
            return user;
        }).flatMap(userRepository::update)
                .onErrorReturn(null);
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
