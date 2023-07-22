package io.hashimati.commands;

import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(name = "microservice", description = "Create a Microservice")
public class MicroserviceCommand implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        ansi().eraseScreen();
        PromptGui.printlnWarning("This command is not implemented yet!");
        return 0;

    }
}
