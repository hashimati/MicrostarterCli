package micronaut.security.jwt.groovy.JDBC.repository

{securityPackage}.repository

import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.User
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

import java.time.Instant

@JdbcRepository(dialect = Dialect.H2)
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();
    boolean existsByUsername(String username);
    Long updateByUsername(String username, LoginStatus lastLoginStatus, Instant lastTimeTryToLogin);
}