package ${securityPackage}.repository

import ${securityPackage}.domains.RefreshToken
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

import javax.validation.constraints.NotBlank

@JdbcRepository(dialect = Dialect.H2)
interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    public Optional<RefreshToken> findByRefreshToken(@NonNull @NotBlank String refreshToken)

    long updateByUsername(@NonNull @NotBlank String username,
                          boolean revoked);
    void deleteByUsername(@NonNull @NotBlank String username);

}