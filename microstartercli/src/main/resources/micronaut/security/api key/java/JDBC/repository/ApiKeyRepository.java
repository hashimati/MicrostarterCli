package ${securityPackage}.repository;

import ${securityPackage}.domains.APIKey;
import io.micronaut.data.repository.CrudRepository;
<% if(jdbc) out.print 'import io.micronaut.data.jdbc.annotation.JdbcRepository;'%><% if(mongo) out.print 'import io.micronaut.data.mongodb.annotation.MongoRepository;'%>
<% if(jdbc) out.print 'import io.micronaut.data.model.query.builder.sql.Dialect;'%>

import java.util.Optional;


<% if(jdbc) out.print '@JdbcRepository(dialect = Dialect.'+dialect+')'%><% if(mongo) out.print '@MongoRepository'%>
public interface ApiKeyRepository extends CrudRepository<APIKey, <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%> > {

    public Optional<APIKey> findByName(String name);
    public Optional<APIKey> findBySecret(String secret);

}

