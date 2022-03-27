package ${securityPackage}.repository

import ${securityPackage}.domains.RefreshToken
import io.micronaut.core.annotation.NonNull
<% if(jdbc) out.print 'import io.micronaut.data.jdbc.annotation.JdbcRepository'%><% if(mongo) out.print 'import io.micronaut.data.mongodb.annotation.MongoRepository'%>
<% if(jdbc) out.print 'import io.micronaut.data.model.query.builder.sql.Dialect'%>
import io.micronaut.data.repository.CrudRepository
import java.util.*
import javax.validation.constraints.NotBlank

<% if(jdbc) out.print '@JdbcRepository(dialect = Dialect.H2)'%><% if(mongo) out.print '@MongoRepository'%>
interface RefreshTokenRepository : CrudRepository<RefreshToken, <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>> {
    fun findByRefreshToken(@NonNull refreshToken: @NotBlank String?): Optional<RefreshToken?>?
    fun updateByUsername(@NonNull username: @NotBlank String?, revoked: Boolean);

    fun deleteByUsername(@NonNull username: @NotBlank String?)
}