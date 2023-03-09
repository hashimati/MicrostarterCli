package $

import groovy.transform.Canonical

{securityPackage}.domains

import groovy.transform.Canonical
import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies

import java.time.Instant


@Canonical
@MappedEntity(value = "apikeys", namingStrategy = NamingStrategies.Raw.class)
class APIKey {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%> id
    private String name
    private String secret

    private Instant expiry

    @DateCreated
    private Instant created

    @DateUpdated
    private Instant updated
}

