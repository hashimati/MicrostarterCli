package com.example.security;


import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.jdbc.annotation.JoinTable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserRepository extends CrudRepository<User, Long> {


    public User findByUsername(String username);

    @Override
    public List<User> findAll();
    public boolean existsByUsername(String username);
    public Long updateByUsername(String username, LoginStatus lastLoginStatus, Date lastTimeTryToLogin);

}

