package ${securityPackage}.repository

import ${securityPackage}.domains.LoginStatus
import ${securityPackage}.domains.User
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.time.Instant

@JdbcRepository(dialect = Dialect.H2)
interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String?): User?
    override fun findAll(): List<User?>
    fun existsByUsername(username: String?): Boolean
    fun updateByUsername(username: String?, lastLoginStatus: LoginStatus?, lastTimeTryToLogin: Instant?): Long?
}