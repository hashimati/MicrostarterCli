package ${securityPackage}.domains

import groovy.transform.Canonical
import io.micronaut.data.annotation.*
import io.micronaut.data.model.naming.NamingStrategies

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import java.time.Instant

@Canonical
@MappedEntity(value = "users", namingStrategy = NamingStrategies.Raw.class)
class User {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    Long id

    @NotNull
    @Size(min = 5, max = 15)
    String username

    @NotNull
    @Email
    String email
    String roles

    @NotNull
    String password

    @DateCreated
    Instant dateCreated

    @DateUpdated
    Instant dateUpdated

    boolean active 

    boolean disabled 

    boolean locked
//    Date expiration
//
//    Date passwordExpiration
    Instant lastTimeLogin
    Instant lastTimeTryToLogin
    LoginStatus lastLoginStatus

    String activationCode


    boolean removeRole(String role) {
        if (roles.isBlank()) return false
        HashSet<String> roles = new HashSet<>()
        roles.addAll(Arrays.asList(this.roles.split(",")))
        boolean result = roles.remove(role)
        this.roles = roles.stream().reduce((x,y)->new StringBuilder(x).append(",").append(y).toString()).get()
        return result
    }

    boolean addRole(String role)
    {

        if(roles.isBlank())
        {
            this.roles = role
            return true
        }
        List<String> roles = Arrays.asList(this.roles.split(","))
        if(roles.contains(role))
            return false
        else
        {
            roles.add(role.trim())
            this.roles = roles.stream().reduce((x,y)->new StringBuilder(x).append(",").append(y).toString()).get()
            return true
        }


    }
}