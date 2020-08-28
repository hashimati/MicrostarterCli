package io.hashimati.microcli;

import io.hashimati.microcli.commands.ConfigureCommand;
import io.hashimati.microcli.commands.CreateEntityCommand;
import io.hashimati.microcli.commands.CreateEnumCommand;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "MicroCli", description = "...",
        mixinStandardHelpOptions = true, subcommands = {ConfigureCommand.class, CreateEntityCommand.class, CreateEnumCommand.class})
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
