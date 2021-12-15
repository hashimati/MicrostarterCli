package io.hashimati.security.domains;

import io.micronaut.data.annotation.*;
import io.micronaut.data.model.naming.NamingStrategies;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


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

    private String oneTimePassword ;
    private Instant oneTimePasswordExpiry;

    @DateCreated
    private Instant dateCreated;

    @DateUpdated
    private Instant dateUpdated;

    private boolean active ;

    private boolean disabled ;

    private boolean locked;
//    private Date expiration;
//
//    private Date passwordExpiration;
    private Instant lastTimeLogin;
    private Instant lastTimeTryToLogin;
    private LoginStatus lastLoginStatus;

    private String activationCode;
    private String resetPasswordCode = "";


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

