package io.hashimati.microcli.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name= "create-rabbitmq-listener", aliases = {"rabbitmqListener", "rabbitmq-Listener"}, description = "To create rabbitmq listener component")
public class CreateRabbitMQListenerCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
