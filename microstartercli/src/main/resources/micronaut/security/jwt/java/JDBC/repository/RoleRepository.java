package ${securityPackage}.repository;


import ${securityPackage}.domains.Role;
<% if(jdbc) out.print 'import io.micronaut.data.jdbc.annotation.JdbcRepository;'%><% if(mongo) out.print 'import io.micronaut.data.mongodb.annotation.MongoRepository;'%>
<% if(jdbc) out.print 'import io.micronaut.data.model.query.builder.sql.Dialect;'%>
import io.micronaut.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

<% if(jdbc) out.print '@JdbcRepository(dialect = Dialect.'+dialect+')'%><% if(mongo) out.print '@MongoRepository'%>
public interface RoleRepository extends CrudRepository<Name, <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>> {
    public Role findByName(String name);
    public List<User> findAll();
    public boolean existsByName(String name);
    public Long updateByName(String username, LoginStatus);
}