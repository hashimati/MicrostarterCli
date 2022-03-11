package io.hashimati.microcli.commands;

import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.services.MicronautComponentGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import io.micronaut.core.naming.NameUtils;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static io.hashimati.microcli.utils.GeneratorUtils.createFile;
import static io.hashimati.microcli.utils.PromptGui.printlnSuccess;

@Command(name = "event", aliases = {"create-event", "createEvent"}, description = {"To create event component."})
public class CreateEventCommand implements Callable<Integer> {

    @Option(names = {"--name", "-n"}, description = "Event's name")
    @Inject
    private MicronautComponentGenerator micronautComponentGenerator;
    @Option(names = "--path", description = "To specify the working directory.")
    private String path;

    @Override
    public Integer call() throws Exception {
        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        AnsiConsole.systemInstall();
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName(path));
        ConfigurationInfo  configurationInfo;
        if(!configurationFile.exists()){
            configurationInfo =  new ConfigureCommand().call();
        }
        else {
            configurationInfo = ConfigurationInfo.fromFile(configurationFile);
        }
        ProjectInfo projectInfo = configurationInfo.getProjectInfo();

        if(configurationInfo.getEntities().isEmpty())
        {
            PromptGui.println("Please, use \"entity\" command to an event class." , Ansi.Color.MAGENTA);
            return 0;
        }
        String eventName = PromptGui.createListPrompt("event", "Select Event Type: ",
                configurationInfo.getEntities().stream().map(x->x.getName()).collect(Collectors.toList())
                        .toArray(new String[configurationInfo.getEntities().size()])).getSelectedId();

        String lang = configurationInfo.getProjectInfo().getSourceLanguage();
        String extension = GeneratorUtils.getSourceFileExtension(lang);

        String publisherContent = micronautComponentGenerator.generateEventPublisher(projectInfo.getDefaultPackage(), eventName, NameUtils.camelCase(eventName), projectInfo.getSourceLanguage());

        String listenerContent = micronautComponentGenerator.generateEventListener(projectInfo.getDefaultPackage(), eventName, NameUtils.camelCase(eventName), projectInfo.getSourceLanguage());


        String parent = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.GENERAL_PATH, new HashMap<String, String>(){{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
        }});

        createFile(path+parent+ "/events/"+eventName+"Publisher"+extension, publisherContent);
        printlnSuccess("Created " + "\"" + path+parent+ "/events/"+eventName+"Publisher"+extension + "\"");

        createFile(path+parent+ "/events/"+eventName+"Listener"+extension, listenerContent);
        printlnSuccess("Created " + "\"" + path+parent+ "/events/"+eventName+"Listener"+extension + "\"");

        return null;
    }
}
