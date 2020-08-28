package io.hashimati.controllers

import io.hashimati.domains.Order
import io.hashimati.utils.Randomizer
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class OrderControllerTest extends Specification{


    @Inject
    EmbeddedApplication application


    @Inject
    @Client("/api/order")
    RxHttpClient client;


    Order body;

    def saving()
    {
        Order order = new Randomizer<Order>( Order.class).getRandomInstance();;
        HttpRequest<Order> request = HttpRequest.POST("/save", order);
        this.body = client.toBlocking().retrieve(request, Order.class);
    }


    def 'testing saving order'() {
        saving()
        expect:
        this.body != null;
    }


    def 'test finding order by Id'() {
        saving()

        HttpRequest<Order> request = HttpRequest.GET("/get?id="+this.body.getId());
        Order body = client.toBlocking().retrieve(request, Order.class);

        expect:
        body.getId() == this.body.getId();
    }
//
//
    def 'test deleting order by id'() {
        saving();
        HttpRequest<Boolean> request = HttpRequest.DELETE("/delete/"+this.body.id);

        Boolean body= client.toBlocking().retrieve(request, Boolean.class);

        println body
        expect:
        body.booleanValue()== true;

    }


    void 'test finding all'() {

        saving();
        HttpRequest<Iterable<Order>> request = HttpRequest.GET("/findAll");
        Iterable<Order> list = client.toBlocking().retrieve(request, Iterable.class);
        System.out.println(list);
        expect:
        list != null;

    }


    void 'test updating order'() {
        saving();
        HttpRequest<Order> request = HttpRequest.PUT("/update", this.body);

        Order order = client.toBlocking().retrieve(request, Order.class);
        expect:
        order != null;

    }
}

