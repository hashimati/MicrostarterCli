package io.hashimati.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import javax.inject.Singleton;

@Singleton
public class NewServer extends AbstractVerticle {

    public void start(Vertx vertx)
    {
        vertx.createHttpServer().requestHandler(
                r->{
                    r.response()
                            .putHeader("content-type", "text/plain")
                            .end("Hello from Vert.x! via Micronaut");
                }
        ).listen(2020);

    }

}
