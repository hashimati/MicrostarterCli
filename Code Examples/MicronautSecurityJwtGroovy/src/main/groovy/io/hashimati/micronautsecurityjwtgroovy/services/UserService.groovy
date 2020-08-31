package io.hashimati.micronautsecurityjwtgroovy.services

import io.hashimati.micronautsecurityjwtgroovy.security.AuthoritiesConstants
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.scheduling.annotation.Async
import org.jasypt.util.password.StrongPasswordEncryptor

import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import io.hashimati.micronautsecurityjwtgroovy.domains.User
import io.hashimati.micronautsecurityjwtgroovy.repositories.UserRepository

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Transactional
class UserService {
     Logger log = LoggerFactory.getLogger(UserService.class)
    @Inject private UserRepository userRepository

    @Inject
    private StrongPasswordEncryptor strongPasswordEncryptor;
    User save(User user){
        log.info("Saving  User : {}", user)
         //TODO insert your logic here!
         //saving Object

        //user.setPassword(strongPasswordEncryptor.encryptPassword(user.getPassword()))

         return userRepository.save(user)
     }

    User findById(long id){
        log.info("Finding User By Id: {}", id)
        return userRepository.findById(id).orElse(null)
    }

    boolean deleteById(long id){
        try
        {
            userRepository.deleteById(id);
            log.info("Deleting User by Id: {}", id);
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
    void loadEvent(final ServiceStartedEvent event)
    {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setActive(true);
        admin.setRoles(Arrays.asList(AuthoritiesConstants.ADMIN));
        this.save(admin);
    }

}