package com.example.security;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.*;
import io.micronaut.data.jdbc.annotation.JoinTable;
import lombok.*;

import javax.annotation.Nullable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Introspected
@MappedEntity("users")
public class User {



    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @EqualsAndHashCode.Exclude
    private long id;

    @NotNull

    @Size(min = 5, max = 15)
    private String username;

    @NotNull
    @Email
    private String email;




    @MappedProperty("roles")
    @Relation(value = Relation.Kind.MANY_TO_MANY, mappedBy = "users", cascade = Relation.Cascade.PERSIST)
    private HashSet<Role> roles = new HashSet<>() ;

    @NotNull
    private String password;

    @DateCreated
    private Date dateCreated;

    @DateUpdated
    private Date dateUpdated;

    private boolean active  = true;

    private boolean disabled ;

    private boolean locked;
    private Date expiration;

    private Date passwordExpiration;

    @Nullable
    private Date lastTimeLogin;
    @Nullable
    private Date lastTimeTryToLogin;
    @Nullable
    private LoginStatus lastLoginStatus;

    @Nullable
    private String activationCode;
}

