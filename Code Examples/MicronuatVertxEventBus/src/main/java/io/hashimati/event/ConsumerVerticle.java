package io.hashimati.event;
import io.vertx.core.AbstractVerticle;
import javax.inject.Singleton;
@Singleton
public class ConsumerVerticle extends AbstractVerticle {


    @Override
    public void start() throws Exception {
        System.out.println("Seeping 5 seconds");
            vertx.eventBus().consumer("vertx", h -> {
                System.out.println(h.body().toString());
            });

    }
}
