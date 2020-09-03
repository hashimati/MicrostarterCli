package io.hashimati.microcli.commands;


import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name="create-kafka-listener", aliases = {"kafka-listener", "kafkaListener"}, description = "Creating Kafak Listener componenet")
public class CreateKafkaListenerCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
