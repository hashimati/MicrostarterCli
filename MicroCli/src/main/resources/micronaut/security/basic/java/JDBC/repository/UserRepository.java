package ${securityPackage}.repository;


import ${securityPackage}.domains.LoginStatus;
import ${securityPackage}.domains.User;
<% if(jdbc) out.print 'import io.micronaut.data.jdbc.annotation.JdbcRepository;'%><% if(mongo) out.print 'import io.micronaut.data.mongodb.annotation.MongoRepository;'%>
<% if(jdbc) out.print 'import io.micronaut.data.model.query.builder.sql.Dialect;'%>

import io.micronaut.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

<% if(jdbc) out.print '@JdbcRepository(dialect = Dialect.'+dialect+')'%><% if(mongo) out.print '@MongoRepository'%>
public interface UserRepository extends CrudRepository<User, <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>> {
    public User findByUsername(String username);
    public List<User> findAll();
    public boolean existsByUsername(String username);
    public Long updateByUsername(String username, LoginStatus lastLoginStatus, Instant lastTimeTryToLogin);
}