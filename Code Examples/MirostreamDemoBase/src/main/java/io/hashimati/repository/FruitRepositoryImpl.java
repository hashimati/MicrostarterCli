package io.hashimati.repository;

import io.hashimati.domains.Fruit;
import io.hashimati.microstream.Data;
import io.micronaut.microstream.RootProvider;
import io.micronaut.microstream.annotations.StoreParams;
import io.micronaut.microstream.annotations.StoreReturn;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.validation.constraints.PastOrPresent;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Singleton
public class FruitRepositoryImpl implements FruitRepository{

    @Inject
    RootProvider<Data> rootProvider;


    @Override
    public Fruit save(Fruit fruit) {
        return save(rootProvider.root().getFruits(), fruit);
    }

    @Override
    public void update(String id, Fruit update) {
        updateFruit(id, update);
    }

    @Override
    public Optional<Fruit> findById(String id) {
        return Optional.ofNullable(rootProvider.root().getFruits().get(id));
    }
    @Override
    public void deleteById(String id) {
        removeFruit(id);
    }

    public Stream<Fruit> findAll()
    {
       return rootProvider.root().getFruits().values().stream();
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
        Fruit fruit = map.get(update.getId());
        if(fruit != null)
        {
            fruit.setName(update.getName());
            fruit.setLetter(update.getLetter());
            map.put(fruit.getName(), fruit);
            return fruit;
        }
            return null;

    }


}
