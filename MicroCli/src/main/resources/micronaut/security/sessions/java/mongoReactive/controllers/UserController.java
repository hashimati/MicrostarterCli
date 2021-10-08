package ${securityPackage}.controllers;

import ${securityPackage}.domains.User;
import ${securityPackage}.services.UserService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;

@Controller
@Introspected
public class UserController {


    @Inject
    private UserService userService;

    @Get("/h")
    public Flux<User> foo()
    {
        return null;
    }

}
