package io.hashimati.domains;


import io.micronaut.data.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

import static io.micronaut.data.model.naming.NamingStrategies.Raw;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Schema(name="User", description="User Description")
@MappedEntity(value = "users", namingStrategy = Raw.class)
public class User{
    @Id 
    @GeneratedValue(GeneratedValue.Type.AUTO) 
    @EqualsAndHashCode.Exclude
    private Long id;

    private String username;
	private String email;
	private String password;
    @DateCreated
    private Date dateCreated;

    @DateUpdated
    private Date dateUpdated;

}

