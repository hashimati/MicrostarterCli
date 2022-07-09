package io.hashimati.repositories;

import io.hashimati.domains.Fruit;
import io.hashimati.microstream.FruitData;
import io.micronaut.microstream.RootProvider;
import io.micronaut.microstream.annotations.StoreParams;
import io.micronaut.microstream.annotations.StoreReturn;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class FruitRepository{

    @Inject
    RootProvider<FruitData> rootProvider;

    
    public Fruit save(Fruit fruit) {
        return save(rootProvider.root().getFruits(), fruit);
    }

    
    public Fruit update(Fruit update) {
        return updateFruit(update.getId(), update);
    }

    
    public Optional<Fruit> findById(String id) {
        return Optional.ofNullable(rootProvider.root().getFruits().get(id));
    }
    
    public void deleteById(String id) {
        removeFruit(id);
    }

    public Iterable<Fruit> findAll()
    {
       return rootProvider.root().getFruits().values();
    }
    @StoreParams("fruits")
    protected void removeFruit(String id) {
        rootProvider.root().getFruits().remove(id);

    }

    @StoreParams("fruits")
    protected Fruit save(HashMap<String, Fruit> map , Fruit fruit)
    {
        fruit.setId(UUID.randomUUID().toString());
        map.putIfAbsent(fruit.getId(), fruit);
        return fruit;
    }

    @StoreReturn
    protected Fruit updateFruit(String id, Fruit update)
    {
        HashMap<String, Fruit> map = rootProvider.root().getFruits();
        Fruit Fruit = map.get(update.getId());
        if(Fruit != null)
        {
            map.put(Fruit.getId(), Fruit);
            return Fruit;
        }
            return null;
    }

    public boolean existsById(String id){
        return rootProvider.root().getFruits().containsKey(id);
    }

}

