package io.hashimati.micronautsecurityjwt.services;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Async;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import io.hashimati.micronautsecurityjwt.domains.User;
import io.hashimati.micronautsecurityjwt.repositories.UserRepository;

import java.util.Arrays;

import static io.hashimati.micronautsecurityjwt.security.AuthoritiesConstants.ADMIN;


@Singleton
//@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    @Inject UserRepository userRepository;

    @Inject
    private StrongPasswordEncryptor strongPasswordEncryptor;

    public User save(User user){
        log.info("Saving  User : {}", user);
        //TODO insert your logic here!
        //saving Object
        user.setPassword(strongPasswordEncryptor.encryptPassword(user.getPassword()));
        userRepository.save(user);

        return user;
    }

    public User findById(long id){
        log.info("Finding User By Id: {}", id);
        return userRepository.findById(id).orElse(null);
    }


    public boolean deleteById(long id){
        log.info("Deleting User by Id: {}", id);
        try{
            userRepository.deleteById(id);
            log.info("Deleted User by Id: {}", id);
            return true;
        }
        catch(Exception ex)
        {
            log.info("Failed to delete User by Id: {}", id);
            ex.printStackTrace();
            return false;
        }
    }




    @EventListener
    @Async
    public void loadEvent(final ServiceStartedEvent event)
    {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setActive(true);
        admin.setRoles(Arrays.asList(ADMIN));
        this.save(admin);
    }


}