package io.hashimati.services;


import io.hashimati.domains.Fruit;
import io.hashimati.repositories.FruitRepository;
import io.micrometer.core.annotation.Timed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





//@CacheConfig("Fruit")
@Singleton
public class FruitService {

    private static final Logger log = LoggerFactory.getLogger(FruitService.class);
    @Inject private FruitRepository fruitRepository;
    


  //  @CachePut("fruit")
    @Timed(value = "io.hashimati.services.fruitService.save", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for saving fruit object")
    public Fruit save(Fruit fruit ){
        log.info("Saving  Fruit : {}", fruit);
        //TODO insert your logic here!
        //saving Object
        return fruitRepository.save(fruit);

    }


//    @Cacheable
    @Timed(value = "io.hashimati.services.fruitService.findById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    public Fruit findById(String id ){
        log.info("Finding Fruit By Id: {}", id);
        return fruitRepository.findById(id).orElse(null);
    }


//    @Cacheable
    @Timed(value = "io.hashimati.services.fruitService.deleteById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for deleting a fruit object by id")
    public boolean deleteById(String id ){
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

//    @Cacheable
    @Timed(value = "io.hashimati.services.fruitService.findAll", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects")
    public Iterable<Fruit> findAll( ) {
        log.info("Find All");
      return  fruitRepository.findAll();
    }

//    @Cacheable
    public boolean existsById(String id )
    {
        log.info("Check if id exists: {}", id);
        return  fruitRepository.existsById(id);

    }

  //  @CachePut("fruit")
    @Timed(value = "io.hashimati.services.fruitService.update", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    public Fruit update(Fruit fruit )
    {
        log.info("update {}", fruit);
        return fruitRepository.update(fruit);

    }

}

