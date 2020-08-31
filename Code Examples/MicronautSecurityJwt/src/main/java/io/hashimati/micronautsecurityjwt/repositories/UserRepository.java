package io.hashimati.micronautsecurityjwt.repositories;

import io.hashimati.micronautsecurityjwt.domains.LoginEvent;
import io.hashimati.micronautsecurityjwt.domains.LoginStatus;
import io.hashimati.micronautsecurityjwt.domains.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Date;


@Repository()
public interface UserRepository extends CrudRepository<User, Long>{

    public User findByUsername(String username);
    public boolean existsByUsername(String username);
    public Long updateByUsername(String username, LoginStatus lastLoginStatus, Date lastTimeTryToLogin);

}