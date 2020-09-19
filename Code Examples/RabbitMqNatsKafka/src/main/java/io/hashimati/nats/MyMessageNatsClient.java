package io.hashimati.nats;

import io.hashimati.domains.MyMessage;
import io.micronaut.nats.annotation.NatsClient;
import io.micronaut.nats.annotation.Subject;

@NatsClient
public interface MyMessageNatsClient {

    @Subject("mymessage")
    void send(MyMessage message);

}