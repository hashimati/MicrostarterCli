package io.hashimati.microcli.commands;


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name = "create-configuration", aliases = {"configuration"}, description = "To create configuration component.")
public class CreateConfigurationCommand implements Callable<Integer> {

    @Option(names = {"--name", "-n"}, description = "Name of the configuration")
    private String name;

    @Override
    public Integer call() throws Exception {

        System.out.println("To be implemented");

        return null;
    }
}
