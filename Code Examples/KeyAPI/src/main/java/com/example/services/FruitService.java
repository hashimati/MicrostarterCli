package com.example.services;


import jakarta.inject.Inject;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micrometer.core.annotation.Timed;
import javax.transaction.Transactional;
import com.example.domains.Fruit;
import com.example.repositories.FruitRepository;








@Transactional
@Singleton
public class FruitService {

    private static final Logger log = LoggerFactory.getLogger(FruitService.class);
    @Inject private FruitRepository fruitRepository;
    

    @Timed(value = "com.example.services.fruitService.save", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for saving fruit object")
    public Fruit save(Fruit fruit, Principal principle) {
        log.info("Saving  Fruit : {}", fruit);
        //TODO insert your logic here!
        //saving Object
        return fruitRepository.save(fruit);

    }

    
    @Timed(value = "com.example.services.fruitService.findById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    public Fruit findById(long id, Principal principle ){
        log.info("Finding Fruit By Id: {}", id);
        return fruitRepository.findById(id).orElse(null);
    }

    @Timed(value = "com.example.services.fruitService.deleteById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for deleting a fruit object by id")
    public boolean deleteById(long id, Principal principle ){
        log.info("Deleting Fruit by Id: {}", id);
        try{
            fruitRepository.deleteById(id);
            log.info("Deleted Fruit by Id: {}", id);
            return true;
        }
        catch(Exception ex)
        {
            log.info("Failed to delete Fruit by Id: {}", id);
            ex.printStackTrace();
            return false;
        }
    }

    @Timed(value = "com.example.services.fruitService.findAll", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects")
    public Iterable<Fruit> findAll(Principal principle ) {
        log.info("Find All");
      return  fruitRepository.findAll();
    }

    public boolean existsById(long id, Principal principle )
    {
        log.info("Check if id exists: {}", id);
        return  fruitRepository.existsById(id);

    }

    @Timed(value = "com.example.services.fruitService.update", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    public Fruit update(Fruit fruit, Principal principle )
    {
        log.info("update {}", fruit);
        return fruitRepository.update(fruit);

    }

}

