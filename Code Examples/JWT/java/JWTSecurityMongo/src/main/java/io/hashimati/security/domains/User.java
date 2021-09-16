package io.hashimati.security.domains;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    private String id;

    @NotNull
    @Size(min = 5, max = 15)
    private String username;

    @NotNull
    @Email
    private String email;
    private HashSet<String> roles = new HashSet<>();

    @NotNull
    private String password;

    private Date dateCreated;

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
    
    
    public boolean addRole(String role)
    {
       return roles.add(role);
    }
    public boolean deleteRole(String role)
    {
        return roles.remove(role);
    }
}