package io.hashimati.nats;

import io.hashimati.domains.MyMessage;
import io.micronaut.messaging.annotation.Body;
import io.micronaut.nats.annotation.NatsListener;
import io.micronaut.nats.annotation.Subject;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NatsListener
public class MyMessageNatsListener {
    private static final Logger log= LoggerFactory.getLogger(MyMessageNatsListener.class);

    @Subject("mymessage")
    public void receive(@Body MyMessage message)
    {
        log.info("Received {}", message);
    }
}
