package io.hashimati.event;

import io.vertx.core.AbstractVerticle;

import javax.inject.Singleton;

@Singleton
public class SenderVerticle extends AbstractVerticle
{
    @Override
    public void start() throws Exception {
            vertx.eventBus().send("vertx", new String("Hello Micronaut's Event-bus "));
    }
}
