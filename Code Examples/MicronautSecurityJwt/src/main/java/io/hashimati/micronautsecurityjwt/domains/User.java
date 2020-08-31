package io.hashimati.micronautsecurityjwt.domains;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.Relation;
import lombok.*;

import java.util.Date;
import java.util.List;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity(name = "users")
public class User {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @EqualsAndHashCode.Exclude
  private long id;


  @NotNull
  @Column(unique = true)
  @Size(min = 5, max = 15)
  private String username;


  @Email
  @Column(unique = true)
  private String email;


  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles;

  @NotNull
  private String password;


  @DateCreated
  private Date dateCreated;


  @DateUpdated
  private String dateUpdated;

  private boolean active = false;

  private boolean disabled = false;

  private boolean locked = false;
  private Date expiration;

  private Date passwordExpiration;
  private Date lastTimeLogin;
  private Date lastTimeTryToLogin;
  private LoginStatus lastLoginStatus;

}