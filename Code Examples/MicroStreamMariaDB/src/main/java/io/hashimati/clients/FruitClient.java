package io.hashimati.clients;


import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import io.hashimati.domains.Fruit;

import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;



@Client("/api/v1/fruit")
public interface FruitClient {

    @Post("/save")
    public Fruit save(Fruit fruit);

    @Get("/get")
    public Fruit findById(@Parameter("id") long id);

    @Delete("/delete/{id}")
    public boolean deleteById(@PathVariable("id") long id);

    @Get("/findAll")
    public Iterable<Fruit> findAll( );

    @Put("/update")
    public Fruit update(@Body Fruit fruit);


}


