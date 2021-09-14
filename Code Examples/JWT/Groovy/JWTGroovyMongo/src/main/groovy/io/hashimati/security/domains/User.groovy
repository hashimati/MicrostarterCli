package io.hashimati.security.domains

import groovy.transform.Canonical

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Canonical
class User {

    String id

    @NotNull
    @Size(min = 5, max = 15)
    String username

    @NotNull
    @Email
    String email
    HashSet<String> roles = new HashSet<>()

    @NotNull
    String password

    Date dateCreated

    Date dateUpdated

    boolean active 

    boolean disabled 
    boolean locked
//    Date expiration
//
//    Date passwordExpiration
    Date lastTimeLogin
    Date lastTimeTryToLogin
    LoginStatus lastLoginStatus

    String activationCode


    boolean addRole(String role)
    {
        return roles.add(role)
    }
    boolean deleteRole(String role)
    {
        return roles.remove(role)
    }
}