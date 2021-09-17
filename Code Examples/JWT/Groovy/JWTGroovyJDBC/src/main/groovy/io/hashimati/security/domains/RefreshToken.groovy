package io.hashimati.security.domains

import groovy.transform.Canonical
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.naming.NamingStrategies

import java.time.Instant


@Canonical
@MappedEntity(value = "refreshTokens", namingStrategy = NamingStrategies.Raw.class)
class RefreshToken {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    Long id
    String username
    String refreshToken
    Boolean revoked
    @DateCreated
    Instant dateCreated

    @DateUpdated
    Instant dateUpdated
}

