package com.example.security;

import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

@MappedEntity
public class UserRole {


    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "roles")
    private User user;
    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "users")
    private Role role;
}
