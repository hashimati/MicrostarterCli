package io.hashimati.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.ConfigurationInitializer;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import jakarta.inject.Inject;
import java.io.File;
import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;



@Command(name = "configure", description = {"This command will configure the Micronaut application with MicrostarterCli command.", "To create the configuration file."})
public class ConfigureCommand implements Callable<ConfigurationInfo> {

//
//    @Inject
//    private ConfigurationInitializer configurationInitializer;
//    @Override

    @Option(names = "--path", description = "To specify the working directory.")
    private String path;

    @Option(names = "--spring", description = "To configure a spring application")
    private boolean spring;
//    @Option(names = "--microservice", description = "To configure the microservices project.")
//    private boolean microservice;

    public void setPath(String path)
    {
        this.path = path; 
    }
    @Inject
    private SecurityCommand securityCommand;
    public ConfigurationInfo call() throws Exception {


        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        else {
            File directory = new File(path);
            if(!directory.exists()) {
                directory = new File(GeneratorUtils.getCurrentWorkingPath()+"/"+ path);
                        if(!directory.exists()){

                            PromptGui.printlnErr("Cannot find the working path!");
                            return null;
                        }
            }
            path = path + "/";
        }

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

            if(spring)
            {

                return null;
            }
            else new  ConfigurationInitializer().init(path);
//            if(PromptGui.createConfirmResult("security", "Do you want to configure security?", ConfirmChoice.ConfirmationValue.NO).getConfirmed() == ConfirmChoice.ConfirmationValue.YES){
//                securityCommand.call();
//            }
        } catch (GradleReaderException e) {
            e.printStackTrace();
        }
        return ConfigurationInitializer.configurationInfo;
    }
}
