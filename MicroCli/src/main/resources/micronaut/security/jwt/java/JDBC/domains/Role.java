package ${securityPackage}.domains;

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
@MappedEntity(value = "Roles", namingStrategy = NamingStrategies.Raw.class)
public class Role {

    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @Size(min = 5, max = 15)
    private String name;

}