package com.example.domains;


import io.micronaut.data.annotation.*;
import java.util.Objects;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;
import static io.micronaut.data.model.naming.NamingStrategies.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@MappedEntity(value = "fruits", namingStrategy = Raw.class)
@Schema(name="Fruit", description="Fruit Description")

public class Fruit{
    @Id 
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    
    private String name;
    @DateCreated private Date dateCreated;
    @DateUpdated private Date dateUpdated;

	public Fruit(){	}

	public Long getId() { return id; }

	public String getName() { return name; }


	public Date getDateCreated() {
		return dateCreated;
	}
	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setId(Long id) { this.id = id; }

	public void setName(String name) { this.name = name; }

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Fruit)) return false;
		 Fruit fruit = (Fruit) o;
		return  Objects.equals(id, fruit.id) &&  Objects.equals(name, fruit.name);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}

