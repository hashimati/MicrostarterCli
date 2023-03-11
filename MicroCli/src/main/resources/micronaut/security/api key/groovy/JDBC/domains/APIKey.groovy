package ${securityPackage}.domains

import groovy.transform.Canonical


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
    APIKey(String name, String secret) {
        this.name = name
        this.secret = secret
    }
    Long getId()
    {
        return id;
    }
    void setId(Long id)
    {
        this.id = id;
    }

    String getName()
    {
        return name;
    }
    void setName(String name)
    {
        this.name = name;
    }
    String getSecret()
    {
        return secret;
    }
    void setSecret(String secret)
    {
        this.secret = secret;
    }
    Instant getExpiry()
    {
        return expiry;
    }
    void setExpiry(Instant expiry)
    {
        this.expiry = expiry;
    }
    Instant getCreated()
    {
        return created;
    }
    void setCreated(Instant created)
    {
        this.created = created;
    }
    Instant getUpdated()
    {
        return updated;
    }
    void setUpdated(Instant updated)
    {
        this.updated = updated;
    }
}

