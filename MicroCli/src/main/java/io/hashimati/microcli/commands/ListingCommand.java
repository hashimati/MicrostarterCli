package io.hashimati.microcli.commands;


import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static org.fusesource.jansi.Ansi.Color.*;

@Command(name= "ls", description = "Displays a list of configured components.")
public class ListingCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-e"}, description = "Displays the list of entities")
    private boolean entities;
    @CommandLine.Option(names = {"-paths"}, description = "Displays all paths")
    private boolean paths;
    @CommandLine.Option(names = {"-roles"}, description = "Displays all roles")
    private boolean roles;
    @Override
    public Integer call() throws Exception {
        AnsiConsole.systemInstall();
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName());
        ConfigurationInfo  configurationInfo = null;
        List<Ansi.Color> colorList = Arrays.asList(MAGENTA, CYAN, GREEN, BLUE, WHITE, YELLOW);

        if(!configurationFile.exists()){

            PromptGui.println("The project is not configured. Please, run \"configure\" command", YELLOW);
            return 0;
        }
        else {
            configurationInfo = ConfigurationInfo.fromFile(configurationFile);
        }
        if(entities && configurationInfo != null)
        {

            Ansi.Color color = colorList.get(new Random().nextInt(colorList.size()));

            if(!configurationInfo.getEntities().isEmpty())
              configurationInfo.getEntities().forEach(x->{
                  PromptGui.println(x.getName(), color);
                  if(paths)
                  {
                      Ansi.Color color2 = colorList.get(new Random().nextInt(colorList.size()));

                      x.getUrls()
                              .forEach(y-> PromptGui.println("path= "+y.getUrl() + ", method= "+y.getMethod(), color2));
                  }
              });
            else
                PromptGui.println("There is no entity!", color);

        }
        if(roles)
        {
            Ansi.Color color = colorList.get(new Random().nextInt(colorList.size()));
            configurationInfo.getSecurityRoles().forEach(x->{
                PromptGui.println(x, color);

            });


        }


        if(paths && !entities && configurationInfo != null)
        {
            AtomicInteger count = new AtomicInteger();
            Ansi.Color labelcolor = colorList.get(new Random().nextInt(colorList.size()));
            Ansi.Color pmColor = colorList.get(new Random().nextInt(colorList.size()));
            configurationInfo.getEntities().forEach(x->{
                PromptGui.println(x.getName()+":", labelcolor);
                if(paths)
                {
                    Ansi.Color color2 = colorList.get(new Random().nextInt(colorList.size()));

                    x.getUrls()
                            .forEach(y->{ PromptGui.print("path= ", pmColor);PromptGui.print(y.getUrl(), color2 ); PromptGui.print(", Method= ", pmColor); PromptGui.print(y.getMethod().toString(), color2);
                                System.out.println();count.getAndIncrement();});
                }
            });

            if(configurationInfo.getProjectInfo().getFeatures().contains("openapi"))
            {

                PromptGui.println("OpenApi: ", labelcolor);
                PromptGui.println("/swagger/views/swagger-ui/index.html", pmColor);
                PromptGui.println("/swagger/views/rapidoc/index.html", pmColor);
                count.addAndGet(2);



            }
            if(configurationInfo.isGraphQlSupport())
            {

                PromptGui.println("Graphql: ", labelcolor);
                PromptGui.println("/graphiql", pmColor);
                PromptGui.println("/graphql", pmColor);
                count.addAndGet(2);
            }
            if(count.get() == 0){
                PromptGui.println("There is no path", labelcolor);
            }
        }
        return 0;
    }
}
