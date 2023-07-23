package ${securityPackage}.repository

import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.User
<% if(jdbc) out.print 'import io.micronaut.data.jdbc.annotation.JdbcRepository'%><% if(mongo) out.print 'import io.micronaut.data.mongodb.annotation.MongoRepository'%>
<% if(jdbc) out.print 'import io.micronaut.data.model.query.builder.sql.Dialect'%>
import io.micronaut.data.repository.CrudRepository
import java.time.Instant

<% if(jdbc) out.print '@JdbcRepository(dialect = Dialect.'+dialect+')'%><% if(mongo) out.print '@MongoRepository'%>
interface UserRepository : CrudRepository<User, <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>> {
    fun findByUsername(username: String?): User?
    override fun findAll(): List<User?>
    fun existsByUsername(username: String?): Boolean
    fun updateByUsername(username: String?, lastLoginStatus: LoginStatus?, lastTimeTryToLogin: Instant?): Long?
}