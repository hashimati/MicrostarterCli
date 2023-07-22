package io.hashimati.commands;

import picocli.CommandLine.Command;

import java.awt.*;
import java.net.URI;
import java.util.concurrent.Callable;


@Command(name = "report", aliases = {"bug", "issue"}, description = "To report an issue or to request a new feature.")
public class ReportCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Desktop.getDesktop().browse(new URI("https://github.com/hashimati/MicroCli/issues"));


        return 0;
    }
}
