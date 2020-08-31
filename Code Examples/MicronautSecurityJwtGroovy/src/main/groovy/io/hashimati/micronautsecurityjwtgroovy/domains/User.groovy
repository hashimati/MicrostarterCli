package io.hashimati.micronautsecurityjwtgroovy.domains

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated

import javax.validation.constraints.*
import groovy.transform.Canonical
import groovy.transform.CompileStatic
import javax.persistence.*
import java.util.Date
import java.util.List

@CompileStatic
@Canonical
@Entity(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	 long id;


	@NotNull
	@Column(unique = true)
	@Size(min = 5, max = 15)
	 String username;


	@Email
	@Column(unique = true)
	 String email;


	@ElementCollection(fetch = FetchType.EAGER)
	 List<String> roles;

	@NotNull
	 String password;


	@DateCreated
	 Date dateCreated;


	@DateUpdated
	 String dateUpdated;

	 boolean active = false;

	 boolean disabled = false;

	 boolean locked = false;
	 Date expiration;

	 Date passwordExpiration;

}