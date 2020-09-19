package io.hashimati.controller;

import io.hashimati.domains.MyMessage;
import io.hashimati.nats.MyMessageNatsClient;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;

@Controller("/")
public class MyMessageController {

    @Inject
    private MyMessageNatsClient myMessageNatsClient;

    @Post("/add")
    public MyMessage add(@Body MyMessage message)
    {
        myMessageNatsClient.send(message);
        return message;
    }
}
