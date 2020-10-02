package com.example.security;


import io.micronaut.data.annotation.*;
import io.micronaut.data.jdbc.annotation.ColumnTransformer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



@Data
@MappedEntity("roles")
public class Role {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @EqualsAndHashCode.Exclude
    private long id;


    private String name;

    @Nullable
    @MappedProperty("users")
    @Relation(value = Relation.Kind.MANY_TO_MANY, mappedBy = "roles", cascade = Relation.Cascade.ALL)
    private HashSet<User> users= new HashSet<>();
}
