package io.hashimati.microcli.commands;

import io.hashimati.domains.ConfigurationInfo;
import io.hashimati.domains.Entity;
import io.hashimati.domains.EntityRelation;
import io.hashimati.microcli.MicronautEntityGenerator;
import io.hashimati.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "create-entity", description = "To create a new entity")
public class CreateEntityCommand implements Callable<Integer> {

//
//    @Option(names = {"--multiple", "-m"}, defaultValue = "1", description = "The number of the entities that you want to define")
//    private String multiple;

    @Option(names = {"--entity-name", "-e"},  description = "First Entity's Name")
    private String entityName;

    @Option(names = {"--collection-name", "-c"}, description = "Entity's collection/table name")
    private String collectionName;
    private ConfigurationInfo configurationInfo;


    @Inject
    private MicronautEntityGenerator micronautEntityGenerator;


    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();


        try
        {
           // To get the current configuration and to configure the project if it's not previously configured.
            configurationInfo=  new ConfigureCommand().call();



            // Reading name if the name is entered in the parameters.
            if(entityName == null) {

                entityName = PromptGui.inputText("entity", "Enter the entity's Name:", "MyEntity").getInput();

            }
            Entity entity = new Entity();
            entity.setName(entityName);


            // reading collections/table name if the user didn't provide it .
            if(collectionName == null)
            {
                String defaultValue = "";
                if(entityName.endsWith("ay") || entityName.endsWith("ey") || entityName.endsWith("iy")|| entityName.endsWith("oy")|| entityName.endsWith("uy")
            ||entityName.endsWith("ao") || entityName.endsWith("eo") || entityName.endsWith("io")|| entityName.endsWith("oo")|| entityName.endsWith("uo"))
                {
                    defaultValue = entityName.toLowerCase()+"s";
                }
                else if(entityName.endsWith("y"))
                {
                    defaultValue = entityName.toLowerCase().substring(0, entityName.toLowerCase().lastIndexOf("y"))+"ies";
                }
                else if(entityName.endsWith("o") || entityName.endsWith("s"))
                {
                    defaultValue = entityName.toLowerCase()+"es";
                }
                else
                {
                    defaultValue = entityName.toLowerCase()+"s";

                }

                collectionName  = PromptGui.inputText("collection", "Enter the entity's collection/table Name:", defaultValue).getInput();
            }
            entity.setCollectionName(collectionName);
            entity.setDatabaseType(configurationInfo.getDatabaseType());


            entity.setFrameworkType(configurationInfo.getDataBackendRun());
            entity.setEntityPackage(configurationInfo.getProjectInfo().getDefaultPackage()+".domains");


            /*
            todo 1. Set package.
                 2. write to a file
                 3. generate Service.
                 4. generate Client
                 5. generate Controller
                 6. gnerate Test Controller.
                 7. generate Test Repository
                 8. Configure openAPI.
             */
            System.out.println(micronautEntityGenerator.generateEntity(entity, new ArrayList<EntityRelation>(), "java"));
            System.out.println(micronautEntityGenerator.generateEntity(entity, new ArrayList<EntityRelation>(), "groovy"));
            System.out.println(micronautEntityGenerator.generateEntity(entity, new ArrayList<EntityRelation>(), "kotlin"));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("The \"-m\" parameter should be of type \"integer\"");
            return (-1);
        }

        return 1;
    }
}
