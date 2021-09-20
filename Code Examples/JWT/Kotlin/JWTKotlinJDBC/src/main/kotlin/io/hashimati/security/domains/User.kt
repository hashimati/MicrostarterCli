package io.hashimati.security.domains

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.naming.NamingStrategies
import java.time.Instant
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size


@MappedEntity(value = "users", namingStrategy = NamingStrategies.Raw::class)
data class User (
    @field:Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    var id: Long? = null,
    var username:  @Size(min = 5, max = 15) String? = null,

    var email:  @Email String? = null,

    var password:  String? = null,
    var roles: String? = null,

    var dateCreated: Instant? = null,
    var dateUpdated: Instant? = null,
    var active :Boolean = false,
    var disabled :Boolean = false,
    var locked :Boolean= false,
    //    Date expiration
    //
    //    Date passwordExpiration;
    var lastTimeLogin: Instant? = null,
    var lastTimeTryToLogin: Instant? = null,
    var lastLoginStatus: LoginStatus? = null,

    var activationCode: String? = null,
){


    fun removeRole(role: String): Boolean {
        if (roles!!.isBlank()) return false
        val roles = HashSet<String>()
        roles.addAll(Arrays.asList(*this.roles!!.split(",").toTypedArray()))
        val result = roles.remove(role)
        this.roles = roles.stream().reduce { x: String?, y: String? ->
            StringBuilder(
                x
            ).append(",").append(y).toString()
        }.get()
        return result
    }

    fun addRole(role: String): Boolean {
        if (roles!!.isBlank()) {
            roles = role
            return true
        }
        val roles = Arrays.asList(*roles!!.split(",").toTypedArray())
        return if (roles.contains(role)) false else {
            roles.add(role.trim { it <= ' ' })
            this.roles = roles.stream().reduce { x: String?, y: String? ->
                StringBuilder(
                    x
                ).append(",").append(y).toString()
            }.get()
            true
        }
    }
}