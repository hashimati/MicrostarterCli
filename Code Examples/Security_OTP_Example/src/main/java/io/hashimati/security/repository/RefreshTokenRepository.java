package io.hashimati.security.repository;


import io.hashimati.security.domains.RefreshToken;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.H2)
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(@NonNull @NotBlank String refreshToken);

    long updateByUsername(@NonNull @NotBlank String username,
                          boolean revoked);
    void deleteByUsername(@NonNull @NotBlank String username);

}

