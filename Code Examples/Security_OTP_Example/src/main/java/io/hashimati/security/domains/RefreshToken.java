package io.hashimati.security.domains;

import io.micronaut.data.annotation.*;
import io.micronaut.data.model.naming.NamingStrategies;
import lombok.Data;

import java.time.Instant;

@Data
@MappedEntity(value = "refreshTokens", namingStrategy = NamingStrategies.Raw.class)
public class RefreshToken {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;
    private String username;
    private String refreshToken;
    private Boolean revoked;
    @DateCreated
    private Instant dateCreated;

    @DateUpdated
    private Instant dateUpdated;
}

