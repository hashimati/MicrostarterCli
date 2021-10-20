package io.hashimati.microcli.commands;


import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.awt.*;
import java.net.URI;
import java.util.concurrent.Callable;

@Command(name = "contact", aliases = {"mail", "email"}, description = "To contact the developer.")
public class ContactCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Desktop.getDesktop().mail( URI.create("mailto:hashimati.ahmed@gmail.com"));

        return 0;
    }
}
