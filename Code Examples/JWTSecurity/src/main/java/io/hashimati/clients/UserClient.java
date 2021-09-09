package io.hashimati.clients;


import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.hashimati.domains.User;


@Client("/api/user")
public interface UserClient {

    @Post("/save")
    public User save(User user);

    @Get("/get")
    public User findById(@Parameter("id") long id);

    @Delete("/delete/{id}")
    public boolean deleteById(@PathVariable("id") long id);

    @Get("/findAll")
    public Iterable<User> findAll();

    @Put("/update")
    public User update(@Body User user);
}


