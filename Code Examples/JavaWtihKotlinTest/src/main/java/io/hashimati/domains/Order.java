package io.hashimati.domains;






import io.micronaut.data.annotation.*;


import lombok.*;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Schema(name="Order", description="Order Description")

@MappedEntity("orders")
public class Order{
    
    
    
    @Id 
    @GeneratedValue(GeneratedValue.Type.AUTO) 
    @EqualsAndHashCode.Exclude 
    private Long id;

    
    private String qoute;
	private String dealer;
	private String side;
    @DateCreated 
    private Date dateCreated;

    @DateUpdated 
    private Date dateUpdated;

    
    
    
    
}

