package io.hashimati.controller;


import io.hashimati.domains.Fruit;
import io.hashimati.repository.FruitRepositoryImpl;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class FruitController {
    private final FruitRepositoryImpl fruitRepository;

    public FruitController(FruitRepositoryImpl fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Get("/save")
    public Fruit save()
    {
        return fruitRepository.save(new Fruit(){{
            setLetter("a");
            setName("Apple");
        }});
    }
    @Get("/stream")
    public Iterable<Fruit> findAll()
    {
        return fruitRepository.findAll().collect(Collectors.toList());
    }
}
