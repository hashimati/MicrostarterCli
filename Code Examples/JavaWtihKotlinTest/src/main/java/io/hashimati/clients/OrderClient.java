package io.hashimati.clients;


import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import javax.inject.Inject;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.hashimati.domains.Order;


@Client("/api/order")
public interface OrderClient {

    @Post("/save")
    public Order save(Order order);

    @Get("/get")
    public Order findById(@Parameter("id") long id);

    @Delete("/delete/{id}")
    public boolean deleteById(@PathVariable("id") long id);

    @Get("/findAll")
    public Iterable<Order> findAll();

    @Put("/update")
    public Order update(@Body Order order);
}


