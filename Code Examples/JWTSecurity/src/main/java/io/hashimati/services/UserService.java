package io.hashimati.services;


import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import io.hashimati.domains.User;
import io.hashimati.repositories.UserRepository;

@Introspected
@Singleton
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Inject private UserRepository userRepository;

    public User save(User user){
        log.info("Saving  User : {}", user);
        //TODO insert your logic here!
        //saving Object
        System.out.println("I'm in service");
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

    public Iterable<User> findAll() {
        log.info("Find All");
      return  userRepository.findAll();
    }

    public boolean existsById(Long id)
    {
        log.info("Check if id exists: {}", id);
        return  userRepository.existsById(id);

    }
    public User update(User user)
    {
        log.info("update {}", user);
        return userRepository.update(user);

    }

}

