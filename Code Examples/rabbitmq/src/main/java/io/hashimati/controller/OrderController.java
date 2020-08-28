package io.hashimati.controller;


import io.hashimati.domains.Order;
import io.hashimati.rabbitmq.client.OrderClient;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;

@Controller("/")
public class OrderController {

    @Inject
    OrderClient orderClient;

    @Get("/")
    public Order test(){

        Order order = new Order();
        order.setMessage("Hello RabbitMq");
        order.setQoute("$ 4.00");
        orderClient.sendOrder(order);
        return order;
    }
}
