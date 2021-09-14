package io.hashimati.security.repository;


import io.hashimati.security.domains.LoginStatus;
import io.hashimati.security.domains.User;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

@JdbcRepository(dialect = Dialect.H2)
public interface RefreshTokenRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    public List<User> findAll();
    public boolean existsByUsername(String username);
    public Long updateByUsername(String username, LoginStatus lastLoginStatus, Date lastTimeTryToLogin);
}