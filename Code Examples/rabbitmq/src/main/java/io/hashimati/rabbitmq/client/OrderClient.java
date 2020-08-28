package io.hashimati.rabbitmq.client;


//This Producers, //create orders queue in the RabbitMQ Console

import io.hashimati.domains.Order;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient
public interface OrderClient {

    @Binding("orders")
    void sendOrder(Order order);
}
