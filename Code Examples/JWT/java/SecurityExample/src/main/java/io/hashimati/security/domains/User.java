package io.hashimati.security.domains;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.*;
import io.micronaut.data.annotation.event.EntityEventMapping;
import io.micronaut.data.model.naming.NamingStrategies;
import lombok.*;

import java.util.*;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@MappedEntity(value = "users", namingStrategy = NamingStrategies.Raw.class)
public class User {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @Size(min = 5, max = 15)
    private String username;

    @NotNull
    @Email
    private String email;
    private String roles;

    @NotNull
    private String password;

    @DateCreated
    private Date dateCreated;

    @DateUpdated
    private Date dateUpdated;

    private boolean active ;

    private boolean disabled ;

    private boolean locked;
//    private Date expiration;
//
//    private Date passwordExpiration;
    private Date lastTimeLogin;
    private Date lastTimeTryToLogin;
    private LoginStatus lastLoginStatus;

    private String activationCode;
    

    public boolean removeRole(String role) {
        if (roles.isBlank()) return false;
        HashSet<String> roles = new HashSet<>();
        roles.addAll(Arrays.asList(this.roles.split(",")));
        boolean result = roles.remove(role);
        this.roles = roles.stream().reduce((x,y)->new StringBuilder(x).append(",").append(y).toString()).get();
        return result;
    }
    public boolean addRole(String role)
    {

        if(roles.isBlank())
        {
            this.roles = role;
            return true;
        }
        List<String> roles = Arrays.asList(this.roles.split(","));
        if(roles.contains(role))
            return false;
        else
        {
            roles.add(role.trim());
            this.roles = roles.stream().reduce((x,y)->new StringBuilder(x).append(",").append(y).toString()).get();
            return true;
        }


    }
}