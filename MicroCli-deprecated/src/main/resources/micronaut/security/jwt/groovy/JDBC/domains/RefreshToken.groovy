package ${securityPackage}.domains

import groovy.transform.Canonical
import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies

import java.time.Instant

@Canonical
@MappedEntity(value = "refreshTokens", namingStrategy = NamingStrategies.Raw.class)
class RefreshToken {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%> id
    String username
    String refreshToken
    Boolean revoked
    @DateCreated
    Instant dateCreated

    @DateUpdated
    Instant dateUpdated
}

