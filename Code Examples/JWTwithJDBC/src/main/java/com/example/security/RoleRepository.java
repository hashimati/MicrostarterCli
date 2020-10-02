package com.example.security;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;


@JdbcRepository(dialect = Dialect.MYSQL)
public interface RoleRepository extends CrudRepository<Role, Long> {

    public Optional<Role> findByName(String name);

}
