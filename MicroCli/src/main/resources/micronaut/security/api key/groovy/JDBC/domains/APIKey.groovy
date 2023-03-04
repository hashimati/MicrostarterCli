package ${securityPackage}.domains


import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies

import java.time.Instant

@MappedEntity(value = "keys", namingStrategy = NamingStrategies.Raw.class)
class APIKey {

    @Id
     @GeneratedValue(GeneratedValue.Type.AUTO)
    private <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%> id
    private String name, key

    private Instant expiry

    @DateCreated
    private Instant created

    @DateUpdated
    private Instant updated


    APIKey(String name, String key){
        this.name = name
        this.key = key
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getKey() {
        return key
    }

    void setKey(String key) {
        this.key = key
    }

    <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>  getId() {
        return id
    }

    void setId(<% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>  id) {
        this.id = id
    }

    Instant getExpiry() {
        return expiry
    }

    void setExpiry(Instant expiry) {
        this.expiry = expiry
    }

    Instant getCreated() {
        return created
    }

    void setCreated(Instant created) {
        this.created = created
    }

    Instant getUpdated() {
        return updated
    }

    void setUpdated(Instant updated) {
        this.updated = updated
    }
}
