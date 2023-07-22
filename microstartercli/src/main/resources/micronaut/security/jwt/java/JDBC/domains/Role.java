package ${securityPackage}.domains;

import io.micronaut.data.annotation.*;
import io.micronaut.data.model.naming.NamingStrategies;
import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@MappedEntity(value = "Roles", namingStrategy = NamingStrategies.Raw.class)
public class Role {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @EqualsAndHashCode.Exclude
    private <% if(jdbc) out.print 'Long'%><% if(mongo) out.print 'String'%> id;

    @NotNull
    @Size(min = 5, max = 15)
    private String name;

}