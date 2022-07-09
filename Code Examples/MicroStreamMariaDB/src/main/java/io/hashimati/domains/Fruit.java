package io.hashimati.domains;


import java.util.Objects;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Schema(name="Fruit", description="Fruit Description")


public class Fruit{
    private String id;

    private String name;
	private String letter;

	public Fruit(){	}
	public Fruit(String id, String name, String letter){
		this.id = id;
		this.name = name;
		this.letter = letter;
	}
	public String getId() { return id; }

	public String getName() { return name; }

	public String getLetter() { return letter; }

	public void setId(String id) { this.id = id; }

	public void setName(String name) { this.name = name; }

	public void setLetter(String letter) { this.letter = letter; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Fruit)) return false;
		 Fruit fruit = (Fruit) o;
		return  Objects.equals(id, fruit.id) &&  Objects.equals(name, fruit.name) &&  Objects.equals(letter, fruit.letter);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name, letter);
	}
}

