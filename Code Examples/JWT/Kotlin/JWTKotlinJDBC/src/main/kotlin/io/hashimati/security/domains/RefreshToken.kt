package io.hashimati.security.domains

import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies
import java.time.Instant
import javax.validation.constraints.NotBlank

@MappedEntity
data class RefreshToken(
    @field:Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,

    @NotBlank
    var username: String,

    @NotBlank
    var refreshToken: String,

    var revoked: Boolean,

    @DateCreated
    var dateCreated: Instant? = null

)