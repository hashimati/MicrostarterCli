package io.hashimati.microcli.commands;

import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.services.MicronautEntityGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENTITY_PATH;


@Command(name = "delete-attribute",aliases = {"delAttr", "delattr"}, description = "to delete attribute from an entity")
public class DeleteAttributeCommand implements Callable<Integer>
{
    @Option(names = {"-e", "--entity"})
    private String entityName;

    @Inject
    private MicronautEntityGenerator micronautEntityGenerator;


    @Override
    public Integer call() throws Exception {


        AnsiConsole.systemInstall();
        ConfigurationInfo configurationInfo =ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));

        if(configurationInfo.getEntities().isEmpty())
        {
            System.out.println("There is no entities");
            return 0;
        }

        if(entityName == null)
        {
            entityName = PromptGui.createListPrompt("entity", "Select Entity: ",
                    configurationInfo.getEntities().stream().map(x->x.getName()).collect(Collectors.toList())
            .toArray(new String[configurationInfo.getEntities().size()])).getSelectedId();
        }

        Entity entity = configurationInfo.getEntities().stream().filter(x->x.getName().equals(this.entityName)).findFirst().get();


        HashSet<String> selected = PromptGui.createChoiceResult("attributes", "Choose attributes",entity.getAttributes().stream().map(x->x.getName()).collect(Collectors.toList()).toArray(new String[entity.getAttributes().size()])).getSelectedIds();


        entity.getAttributes().removeAll(entity.getAttributes().stream()
        .filter(x->selected.contains(x.getName())).collect(Collectors.toList()));

        String lang =  configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase();
        String entityFileContent  =micronautEntityGenerator.generateEntity(entity, configurationInfo.getRelations(),lang);


        String entityPath = GeneratorUtils.generateFromTemplate(ENTITY_PATH, new HashMap<String, String>(){{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
        }});

        GeneratorUtils.createFile(System.getProperty("user.dir")+entityPath+ "/"+entity.getName()+GeneratorUtils.srcFileExtension(lang), entityFileContent);
        configurationInfo.writeToFile();
        return 0;
    }
}
