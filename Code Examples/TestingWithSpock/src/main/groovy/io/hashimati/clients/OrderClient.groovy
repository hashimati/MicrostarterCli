package io.hashimati.clients



import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client;
import javax.inject.Inject
import io.reactivex.Flowable
import io.reactivex.Single

import io.hashimati.domains.Order


@Client("/api/order")
interface OrderClient {

    @Post("/save")
    Order save(@Body Order order)

    @Get("/get")
    Order findById(@Parameter("id") long id)

    @Delete("/delete/{id}")
    boolean deleteById(@PathVariable("id") long id)

    @Get("/findAll")
    Iterable<Order> findAll()


    @Put("/update")
    Order update(@Body Order order)

}


