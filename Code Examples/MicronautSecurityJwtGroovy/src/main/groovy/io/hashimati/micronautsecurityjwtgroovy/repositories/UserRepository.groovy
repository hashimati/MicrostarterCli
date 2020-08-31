package io.hashimati.micronautsecurityjwtgroovy.repositories

import io.hashimati.micronautsecurityjwtgroovy.domains.User
import io.micronaut.data.annotation.*
import io.micronaut.data.model.*
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    public boolean existsByUsername(String username);
}