package io.hashimati.security.repository

import io.hashimati.security.domains.RefreshToken
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.util.*
import javax.validation.constraints.NotBlank

@JdbcRepository(dialect = Dialect.H2)
interface RefreshTokenRepository : CrudRepository<RefreshToken, Long> {
    fun findByRefreshToken(@NonNull refreshToken: @NotBlank String?): Optional<RefreshToken?>?
    fun updateByUsername(@NonNull username: @NotBlank String?, revoked: Boolean);

    fun deleteByUsername(@NonNull username: @NotBlank String?)
}