package io.hashimati.ahmed.repository;

import io.hashimati.ahmed.domains.Person;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface PersonRepository {

    @SqlUpdate("insert into user (username, password) values(:username, :password)")
    @GetGeneratedKeys
    public Person save(Person p);
}
