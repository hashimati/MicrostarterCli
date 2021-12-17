package io.hashimati.microcli.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.ConfigurationInitializer;
import io.hashimati.microcli.utils.GradleReaderException;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;



@Command(name = "configure", description = {"This command will configure the Micronaut application with MicroCli command.", "To create the configuration file."})
public class ConfigureCommand implements Callable<ConfigurationInfo> {

//
//    @Inject
//    private ConfigurationInitializer configurationInitializer;
//    @Override
    public ConfigurationInfo call() throws Exception {

        AnsiConsole.systemInstall();
        ansi().eraseScreen();

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
        try {
            new  ConfigurationInitializer().init();
        } catch (GradleReaderException e) {
            e.printStackTrace();
        }
        return ConfigurationInitializer.configurationInfo;
    }
}
