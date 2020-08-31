package io.hashimati.micronautsecurityjwtgroovy.resources

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.*
import javax.inject.Singleton
import io.hashimati.micronautsecurityjwtgroovy.domains.User
import io.hashimati.micronautsecurityjwtgroovy.services.UserService
import javax.inject.Inject
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/api/user")
class UserController {

    @Inject UserService userService;
    static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Post("/save")
    User save(User user){
        log.info("Saving  User : {}", user);
        //TODO insert your logic here!
        //saving Object
        return userService.save(user)
    }
    @Get("/get")
    User findById(@Parameter("id") long id){

        log.info("Finding User By Id: {}", id);
        return userService.findById(id)
    }

    @Delete("/delete/{id}")
    User deleteById(@PathVariable("id") long id){
        log.info("Deleting User by Id: {}", id);
        return  userService.deleteById(id);
    }
}