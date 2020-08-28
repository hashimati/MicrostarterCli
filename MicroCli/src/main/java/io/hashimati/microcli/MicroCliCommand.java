package io.hashimati.microcli;

import io.hashimati.microcli.commands.*;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "MicroCli", description = "...",
        mixinStandardHelpOptions = true, subcommands = {ConfigureCommand.class, CreateEntityCommand.class, CreateEnumCommand.class, CreateRelationCommand.class, DeleteEntityCommand.class, DeleteAttributeCommand.class,AddAttributeCommand.class})
public class MicroCliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(MicroCliCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
