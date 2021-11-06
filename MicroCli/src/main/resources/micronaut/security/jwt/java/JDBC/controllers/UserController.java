package ${securityPackage}.controllers;


import ${securityPackage}.domains.User;
import ${securityPackage}.services.UserService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/security/users")
@Introspected
public class UserController {


    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    public User saveUsers(@Body User user) {
        logger.info("save user {}", user);
        return userService.save(user);
    }

}
