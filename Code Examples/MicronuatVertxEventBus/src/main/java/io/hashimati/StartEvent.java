package io.hashimati;


import io.hashimati.event.ConsumerVerticle;
import io.hashimati.event.SenderVerticle;
import io.hashimati.server.NewServer;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.vertx.core.Vertx;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StartEvent {

    @Inject
    private NewServer newServer;

    @Inject
    private ConsumerVerticle consumerVerticle;

    @Inject
    private SenderVerticle senderVerticle;

    @EventListener
    public void startup(StartupEvent event) throws Exception {

      //  newServer.start(Vertx.vertx());

     Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(consumerVerticle);
        Thread.sleep(5000);

        vertx.deployVerticle(senderVerticle);


        System.out.println("finished Deployment");
//

    }
}
