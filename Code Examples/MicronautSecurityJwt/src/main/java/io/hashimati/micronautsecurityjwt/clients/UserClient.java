package io.hashimati.micronautsecurityjwt.clients;


import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.hashimati.micronautsecurityjwt.domains.User;


@Client("/api/user")
public interface UserClient {

    @Post("/save")
    public User save(User user);

    @Get("/get")
    public User findById(@Parameter("id") long id);

    @Delete("/delete/{id}")
    public boolean deleteById(@PathVariable("id") long id);

}