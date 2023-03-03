package ${securityPackage}.domains;


import io.micronaut.data.annotation.*;
import io.micronaut.data.model.naming.NamingStrategies;

import java.time.Instant;

@MappedEntity(value = "keys", namingStrategy = NamingStrategies.Raw.class)
public class APIKey {

    @Id
     @GeneratedValue(GeneratedValue.Type.AUTO)
    private <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%> id;
    private String name, key;

    private Instant expiry;

    @DateCreated
    private Instant created;

    @DateUpdated
    private Instant updated;


    public APIKey(String name, String key){
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>  getId() {
        return id;
    }

    public void setId(<% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>  id) {
        this.id = id;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }
}
