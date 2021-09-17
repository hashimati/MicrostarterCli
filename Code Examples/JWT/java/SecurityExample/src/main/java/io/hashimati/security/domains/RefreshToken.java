package io.hashimati.security.domains;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import lombok.Data;

import java.time.Instant;

@Data
public class RefreshToken {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;
    private String username;
    private String refreshToken;
    private Boolean revoked;
    @DateCreated
    private Instant dateCreated;
}
