package io.hashimati.rabbitmq.consumer;


import io.hashimati.domains.Order;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

@RabbitListener
public class OrderConsumer {

    @Queue("orders")
    public void receive(Order order)
    {
        System.out.println(order);
    }
}
