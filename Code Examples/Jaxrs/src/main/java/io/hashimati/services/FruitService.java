package io.hashimati.services;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micrometer.core.annotation.Timed;
import javax.transaction.Transactional;
import io.hashimati.domains.Fruit;
import io.hashimati.repositories.FruitRepository;



@Transactional
@Singleton
public class FruitService {

    private static final Logger log = LoggerFactory.getLogger(FruitService.class);
    @Inject private FruitRepository fruitRepository;

    @Timed(value = "io.hashimati.services.fruitService.save", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for saving fruit object")
    public Fruit save(Fruit fruit ){
        log.info("Saving  Fruit : {}", fruit);
        //TODO insert your logic here!
        //saving Object
        return fruitRepository.save(fruit);

    }

    
    @Timed(value = "io.hashimati.services.fruitService.findById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    public Fruit findById(long id ){
        log.info("Finding Fruit By Id: {}", id);
        return fruitRepository.findById(id).orElse(null);
    }

    @Timed(value = "io.hashimati.services.fruitService.deleteById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for deleting a fruit object by id")
    public boolean deleteById(long id ){
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

    @Timed(value = "io.hashimati.services.fruitService.findAll", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects")
    public Iterable<Fruit> findAll( ) {
        log.info("Find All");
      return  fruitRepository.findAll();
    }

    public boolean existsById(long id )
    {
        log.info("Check if id exists: {}", id);
        return  fruitRepository.existsById(id);

    }

    @Timed(value = "io.hashimati.services.fruitService.update", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    public Fruit update(Fruit fruit )
    {
        log.info("update {}", fruit);
        return fruitRepository.update(fruit);

    }
    
    @Timed(value = "io.hashimati.services.fruitService.findByName", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    public Fruit findByName(String query ){
          log.info("Finding Fruit By Name: {}", query);
          return fruitRepository.findByName(query).orElse(null);
    }


    
    @Timed(value = "io.hashimati.services.fruitService.findAllByLetter", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by Letter")
    public Iterable<Fruit> findAllByLetter(String query ){
          log.info("Finding Fruit By Letter: {}", query);
          return fruitRepository.findAllByLetter(query);
    }



    @Timed(value = "io.hashimati.services.fruitService.updateby.Name", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    public Long updateByName(String query,  String name, String letter )
    {
        log.info("update by {}", query);
        return fruitRepository.updateByName(query, name, letter);
    }

}

