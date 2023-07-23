package ${securityPackage}.domains

import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies
import java.time.Instant

@MappedEntity(value = "apikeys", namingStrategy = NamingStrategies.Raw::class)
data class APIKey(
    @field:Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    var id: <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%>? = null,
    var name: String, var key: String,
    @DateCreated
    var created: Instant? = null,
    @DateUpdated
    var updated: Instant? = null) {

}