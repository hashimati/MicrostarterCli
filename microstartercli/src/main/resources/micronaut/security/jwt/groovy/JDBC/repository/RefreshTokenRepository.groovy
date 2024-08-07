package ${securityPackage}.repository

import ${securityPackage}.domains.RefreshToken
import io.micronaut.core.annotation.NonNull
<% if(jdbc) out.print 'import io.micronaut.data.jdbc.annotation.JdbcRepository'%><% if(mongo) out.print 'import io.micronaut.data.mongodb.annotation.MongoRepository'%>
<% if(jdbc) out.print 'import io.micronaut.data.model.query.builder.sql.Dialect'%>
import io.micronaut.data.repository.CrudRepository

import jakarta.validation.constraints.NotBlank

<% if(jdbc) out.print '@JdbcRepository(dialect = Dialect.'+dialect+')'%><% if(mongo) out.print '@MongoRepository'%>
interface RefreshTokenRepository extends CrudRepository<RefreshToken, <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>> {

    public Optional<RefreshToken> findByRefreshToken(@NonNull @NotBlank String refreshToken)

    long updateByUsername(@NonNull @NotBlank String username,
                          boolean revoked);
    void deleteByUsername(@NonNull @NotBlank String username);

}