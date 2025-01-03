package io.hashimati.microcli.commands;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.config.Feature;
import io.hashimati.config.FeaturesFactory;
import io.hashimati.constants.ProjectConstants;
import io.hashimati.domains.ConfigurationInfo;
import io.hashimati.domains.Entity;
import io.hashimati.domains.EntityAttribute;
import io.hashimati.domains.EntityRelation;
import io.hashimati.microcli.MicronautEntityGenerator;
import io.hashimati.utils.GeneratorUtils;
import io.hashimati.utils.MavenProjectUtils;
import io.hashimati.utils.MicronautProjectValidator;
import io.hashimati.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static io.hashimati.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.constants.ProjectConstants.PathsTemplate.ENTITY_PATH;
import static io.hashimati.constants.ProjectConstants.PathsTemplate.ENUMS;

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




    private HashMap<String, Feature> features = FeaturesFactory.features();

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
          //  entity.setEntityPackage(configurationInfo.getProjectInfo().getDefaultPackage()+".domains");
            entity.setPackages(configurationInfo.getProjectInfo().getDefaultPackage());




            /*
            todo 1. Set package. (done)
                 2. write to a file (done)
                 3. generate Service. (done)
                 4. generate Client (done)
                 5. generate Controller (done)
                 6. gnerate Test Controller. (done)
                 7. generate Test Repository (done)
                 8. Fix the output file path.(done)
                 9. prompt for attributes.
                 10. prompt for validations.
                 11. Configure openAPI.(done)
             */

            attributeLoop: for(;;) {
                ConfirmResult takeAttributeConfirm = PromptGui.createConfirmResult("attribue", "Do you want to add attribute?");

                if(takeAttributeConfirm.getConfirmed() == ConfirmChoice.ConfirmationValue.NO)
                {
                    break attributeLoop;
                }
                else{
                    EntityAttribute entityAttribute = new EntityAttribute();
                    //todo Enter attribute Name.

                    InputResult attrNameResult = PromptGui.inputText("attributeName", "Enter attribute name", "attrbute");
                    entityAttribute.setName(attrNameResult.getInput());

                    //todo Enter attribute Type:

                    ListResult attrTypeResult = PromptGui.createListPrompt("attributeType", "Select Attribute Type:",

                            "String",
                                    "boolean",
                                    "byte",
                                    "short",
                                    "int",
                                    "long",
                                    "float",
                                    "double",
                                    "Date");
                    entityAttribute.setType(attrTypeResult.getSelectedId());
                    //todo Enter ask for Validation

                    //todo take validation
                    entity.getAttributes().add(entityAttribute);

                }
            }

            String lang =  configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase();
            String entityFileContent  =micronautEntityGenerator.generateEntity(entity, new ArrayList<EntityRelation>(),lang);

            String extension =".";
            switch (configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase())
            {
                case KOTLIN_LANG:
                    extension += ProjectConstants.Extensions.KOTLIN;
                    break;
                case GROOVY_LANG:
                    extension += ProjectConstants.Extensions.GROOVY;
                    break;
                case JAVA_LANG:
                default:
                    extension += ProjectConstants.Extensions.JAVA;
                    break;

            }
            String entityPath = GeneratorUtils.generateFromTemplate(ENTITY_PATH, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});

            GeneratorUtils.createFile(System.getProperty("user.dir")+entityPath+ "/"+entity.getName()+extension, entityFileContent);


            //===============
            String repositoryFileContent = micronautEntityGenerator.generateRepository(entity, lang);




            String repoPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});



            GeneratorUtils.createFile(System.getProperty("user.dir")+"/"+repoPath+ "/"+entity.getName()+"Repository"+extension, repositoryFileContent);

        //----------------------

            String serviceFileContent = micronautEntityGenerator.generateService(entity, lang);
            String servicePath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.SERVICES_PATH, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});

            GeneratorUtils.createFile(System.getProperty("user.dir")+servicePath + "/"+entity.getName()+"Service"+
extension, serviceFileContent);


            //============================
            String controllerFileContent = micronautEntityGenerator.generateController(entity, lang);

            String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.CONTROLLER_PATH, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});
            GeneratorUtils.createFile(System.getProperty("user.dir")+controllerPath+ "/"+entity.getName()+"Controller"+extension, controllerFileContent);



            ////==========
            String clientFileContent = micronautEntityGenerator.generateClient(entity, lang);

            String clientPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});
            GeneratorUtils.createFile(System.getProperty("user.dir")+"/src/main/java/"+GeneratorUtils.packageToPath(entity.getClientPackage()) + "/"+entity.getName()+"Client"+extension, clientFileContent);



          //  System.out.println(entityFileContent + "\n" + repositoryFileContent +"\n" + serviceFileContent + "\n" + controllerFileContent + "\n" + clientFileContent);

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
