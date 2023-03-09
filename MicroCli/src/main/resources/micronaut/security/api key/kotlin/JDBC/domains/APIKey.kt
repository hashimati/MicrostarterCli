package ${securityPackage}.domains

import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies
import java.time.Instant

@MappedEntity(value = "apikeys", namingStrategy = NamingStrategies.Raw::class)
class APIKey(var name: String, var key: String) {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null
    var expiry: Instant? = null

    @DateCreated
    var created: Instant? = null

    @DateUpdated
    var updated: Instant? = null

}