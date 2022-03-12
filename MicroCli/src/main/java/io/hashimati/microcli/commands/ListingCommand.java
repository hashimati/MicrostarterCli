package io.hashimati.microcli.commands;


import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static io.hashimati.microcli.utils.PromptGui.print;
import static io.hashimati.microcli.utils.PromptGui.println;
import static org.fusesource.jansi.Ansi.Color.*;

@Command(name= "ls", description = "Displays a list of configured components.")
public class ListingCommand implements Callable<Integer> {

    @Option(names = {"-e"}, description = "Displays the list of entities")
    private boolean entities;
    @Option(names = {"-paths"}, description = "Displays all paths")
    private boolean paths;
    @Option(names = {"-roles"}, description = "Displays all roles")
    private boolean roles;
    @Option(names = "--path", description = "To specify the working directory.")
    private String path;
    @Option(names = {"-features"}, description = "Displays all roles")
    private boolean features;
    @Override
    public Integer call() throws Exception {
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
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName(path));
        ConfigurationInfo  configurationInfo = null;
        List<Ansi.Color> colorList = Arrays.asList(MAGENTA, CYAN, GREEN, BLUE, WHITE, YELLOW);

        if(!configurationFile.exists()){

            println("The project is not configured. Please, run \"configure\" command", YELLOW);
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
                  println(x.getName(), color);
                  if(paths)
                  {
                      Ansi.Color color2 = colorList.get(new Random().nextInt(colorList.size()));

                      x.getUrls()
                              .forEach(y-> println("path= "+y.getUrl() + ", method= "+y.getMethod(), color2));
                  }
              });
            else
                println("There is no entity!", color);

        }
        if(roles)
        {
            if(!configurationInfo.getSecurityRoles().isEmpty()){
            Ansi.Color color = colorList.get(new Random().nextInt(colorList.size()));
                Ansi.Color color1 = colorList.get(new Random().nextInt(colorList.size()));
                print("Roles= {", color1) ;
                print(configurationInfo.getSecurityRoles().stream().reduce((x,y)->x+", "+ y).get(), colorList.get(new Random().nextInt(colorList.size())));
                print("}", color1);
            }
            else
                PromptGui.print("There is no security roles!", colorList.get(new Random().nextInt(colorList.size())));


        }


        if(paths && !entities && configurationInfo != null)
        {
            AtomicInteger count = new AtomicInteger();
            Ansi.Color labelcolor = colorList.get(new Random().nextInt(colorList.size()));
            Ansi.Color pmColor = colorList.get(new Random().nextInt(colorList.size()));
            configurationInfo.getEntities().forEach(x->{
                println(x.getName()+":", labelcolor);
                if(paths)
                {
                    Ansi.Color color2 = colorList.get(new Random().nextInt(colorList.size()));

                    x.getUrls()
                            .forEach(y->{ print("path= ", pmColor);
                                print(y.getUrl(), color2 ); print(", Method= ", pmColor); print(y.getMethod().toString(), color2);
                                System.out.println();count.getAndIncrement();});
                }
            });

            if(configurationInfo.getProjectInfo().getFeatures().contains("openapi"))
            {

                println("OpenApi: ", labelcolor);
                println("/swagger/views/swagger-ui/index.html", pmColor);
                println("/swagger/views/rapidoc/index.html", pmColor);
                count.addAndGet(2);



            }
            if(configurationInfo.isGraphQlSupport())
            {

                println("Graphql: ", labelcolor);
                println("/graphiql", pmColor);
                println("/graphql", pmColor);
                count.addAndGet(2);
            }
            if(count.get() == 0){
                println("There is no path", labelcolor);
            }
        }
        if(features)
        {
            Ansi.Color fColor = colorList.get(new Random().nextInt(colorList.size()));
            Ansi.Color cColor =colorList.get(new Random().nextInt(colorList.size()));
            print("Features = { " , cColor);
            print(configurationInfo.getProjectInfo().getFeatures().stream()
                    .reduce((x,y)->x+", " + y).get(), fColor);
            print("}", cColor) ;
        }
        return 0;
    }
}
