package io.hashimati.domains;


import io.micronaut.data.annotation.*;
import lombok.*;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;
import static io.micronaut.data.model.naming.NamingStrategies.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@MappedEntity(value = "fruits", namingStrategy = Raw.class)
@Schema(name="Fruit", description="Fruit Description")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Fruit{
    @Id 
    @GeneratedValue(GeneratedValue.Type.AUTO) 
    @EqualsAndHashCode.Exclude 
    private Long id;

    
    private String name;
	private String letter;
 @DateCreated private Date dateCreated;
  @DateUpdated private Date dateUpdated;
}

