package io.hashimati.repositories;

import io.hashimati.domains.Fruit;
import java.util.*;
import io.micronaut.data.annotation.*;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.model.*;
import io.micronaut.data.repository.CrudRepository;


@Repository
@JdbcRepository(dialect = Dialect.H2)
public interface FruitRepository extends CrudRepository<Fruit, Long> {

    public Optional<Fruit> findByName(String name);

    public Iterable<Fruit> findAllByLetter(String letter);


    public Long updateByName(String query, String name, String letter);

}

