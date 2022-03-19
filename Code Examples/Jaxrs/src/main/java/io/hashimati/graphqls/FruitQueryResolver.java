package io.hashimati.graphqls;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import io.hashimati.domains.Fruit;
import io.hashimati.services.FruitService;
import io.micrometer.core.annotation.Timed;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Singleton
public class FruitQueryResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Inject
    private FruitService fruitService;

    

    @Timed(value = "io.hashimati.graphqls.QueryResolver.save.findById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    public Fruit findFruitById(long id)
    {
        return fruitService.findById(id);
    }

    //@Timed(value = "io.hashimati.graphqls.FruitQueryResolver.findAll", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects")
    public Iterable<Fruit> findAllFruit()
    {
        return fruitService.findAll();
    }

    @Timed(value = "io.hashimati.graphqls.FruitQueryResolver.save", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for saving fruit object")
    public Fruit saveFruit(Fruit fruit){
        return fruitService.save(fruit);
    }

    @Timed(value = "io.hashimati.graphqls.FruitQueryResolver.update", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    public Fruit updateFruit(Fruit fruit)
    {
        return  fruitService.update(fruit);

    }

    @Timed(value = "io.hashimati.graphqls.FruitQueryResolver.deleteById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for deleting a fruit object by id")
    public boolean deleteFruit(long id){
      return fruitService.deleteById(id);
    }


    @Timed(value = "io.hashimati.graphqls.QueryResolver.findByIdName", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by Name")
    public Fruit findFruitByName(String query){
          return fruitService.findByName(query);
    }

    //@Timed(value = "io.hashimati.graphqls.FruitQueryResolver.findAllLetter", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects by Letter")
    public Iterable<Fruit> findAllFruitByLetter(String query){
          return fruitService.findAllByLetter(query);
    }


}

