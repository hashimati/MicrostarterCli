package com.example.security.repository;

import com.example.security.domains.APIKey;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

import java.util.Optional;


@JdbcRepository(dialect = Dialect.H2)
public interface ApiKeyRepository extends CrudRepository<APIKey, Long > {

    public Optional<APIKey> findByName(String name);
    public Optional<APIKey> findBySecret(String secret);

}


