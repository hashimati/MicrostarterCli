package io.hashimati.microcli.commands;


import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name= "create-rabbitmq-client", aliases = {"rabbitmqClient", "rabbitmq-client"}, description = "To create rabbitmq client component")
public class CreateRabbitMQClientCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
