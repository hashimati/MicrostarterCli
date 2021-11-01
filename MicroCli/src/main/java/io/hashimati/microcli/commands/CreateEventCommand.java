package io.hashimati.microcli.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command
public class CreateEventCommand implements Callable<Integer> {

    @Option(names = {"--name", "-n"}, description = "Event's name")
    @Override
    public Integer call() throws Exception {
        return null;
    }
}
