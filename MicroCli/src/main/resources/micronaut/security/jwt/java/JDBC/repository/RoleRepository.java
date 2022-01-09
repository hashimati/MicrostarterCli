package ${securityPackage}.repository;


import ${securityPackage}.domains.Role;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

@JdbcRepository(dialect = Dialect.${dialect})
public interface RoleRepository extends CrudRepository<Name, Long> {
    public Role findByName(String name);
    public List<User> findAll();
    public boolean existsByName(String name);
    public Long updateByName(String username, LoginStatus);
}