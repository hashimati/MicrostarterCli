package io.hashimati.microcli.commands;


import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.URL;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static io.hashimati.microcli.utils.PromptGui.println;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.Color.YELLOW;

@Command(name= "urls", description = "To configure Intercept URL Map.")
public class InterceptURLCommand implements Callable<Integer>
{

    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName());
        ConfigurationInfo  configurationInfo = null;
        List<Ansi.Color> colorList = Arrays.asList(MAGENTA, CYAN, GREEN, BLUE, WHITE, YELLOW);

        if(!configurationFile.exists()){

            println("The project is not configured. Please, run \"configure\" command", YELLOW);
            return 0;
        }
        else {
            configurationInfo = ConfigurationInfo.fromFile(configurationFile);
        }

        if(!configurationInfo.isSecurityEnable())
        {
            PromptGui.printlnWarning("Please, configure security first.");
            return 0;
        }
       String entityName =  PromptGui.createListPrompt("entity", "Select an entity: ", configurationInfo.getEntities().stream().map(x->x.getName()).collect(Collectors.toList()).toArray(new String[]{})).getSelectedId();

        ArrayList<URL> urls = configurationInfo.getEntities().stream().filter(x -> x.getName().equals(entityName)).findFirst()
                .get().getUrls();

         var roles =  new ArrayList<String>(){{

             add("isAnonymous()");
             add( "isAuthenticated()");
         }};
         roles.addAll(configurationInfo.getSecurityRoles());

        urls.stream().forEach(x->{
            try {
                var selectedRoles = PromptGui.createChoiceResult("roles", "Choose roles for: "+x.getUrl(), roles.toArray(new String[]{})).getSelectedIds();
                x.setRoles(selectedRoles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        configurationInfo.writeToFile();



        return 100;
    }
}
