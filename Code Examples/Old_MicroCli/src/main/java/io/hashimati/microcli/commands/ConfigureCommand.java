package io.hashimati.microcli.commands;


import io.hashimati.domains.ConfigurationInfo;
import io.hashimati.utils.ConfigurationInitializer;
import org.fusesource.jansi.AnsiConsole;
//import org.jline.reader.LineReader;
//import org.jline.reader.LineReaderBuilder;
//import org.jline.terminal.Terminal;
//import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.util.concurrent.Callable;

@Command(name = "configure", description = "To create the configuration file.")
public class ConfigureCommand implements Callable<ConfigurationInfo> {

//
//    @Inject
//    private ConfigurationInitializer configurationInitializer;
//    @Override
    public ConfigurationInfo call() throws Exception {

        AnsiConsole.systemInstall();
//        Terminal terminal = TerminalBuilder
//                .builder()
//                .system(true)
//
//                .streams(System.in, System.out)
//                .build();
//
//        LineReader lineReader = LineReaderBuilder
//                .builder()
//                .terminal(terminal)
//                .build();
//
//        String prompt2 = "What's your name? ";
//        String name = lineReader.readLine(prompt2);
       new  ConfigurationInitializer().init();
        return ConfigurationInitializer.configurationInfo;
    }
}
