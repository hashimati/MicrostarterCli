package io.hashimati.microcli.commands;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ExpandableChoiceResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.*;
import io.hashimati.microcli.services.MicronautEntityGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.YES;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENTITY_PATH;
import static io.hashimati.microcli.utils.PromptGui.println;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

@Command(name = "create-entity", aliases ={"entity"}, description = "To create a new entity")
public class CreateEntityCommand implements Callable<Integer> {

//
//    @Option(names = {"--multiple", "-m"}, defaultValue = "1", description = "The number of the entities that you want to define")
//    private String multiple;

    @Option(names = {"--entity-name", "-e"},  description = "First Entity's Name")
    private String entityName;

    @Option(names = {"--collection-name", "-c"}, description = "Entity's collection/table name")
    private String collectionName;
    private ConfigurationInfo configurationInfo;



    @Option(names = {"--no-endpoint"}, description = "To prevent generating controller.")
    private boolean noEndpoint;

    @Option(names ={"--graphql"}, description = "To generate GraphQL")
    private boolean graphql;

    @Inject
    private MicronautEntityGenerator micronautEntityGenerator;




    private final HashMap<String, Feature> features = FeaturesFactory.features();

    public CreateEntityCommand() throws FileNotFoundException {
    }

    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        org.fusesource.jansi.AnsiConsole.systemInstall();
        ansi().eraseScreen();
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
            entity.setDatabaseName(configurationInfo.getDatabaseName());

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
                ConfirmResult takeAttributeConfirm = PromptGui.createConfirmResult("attribue", "Do you want to add an attribute?");

                if(takeAttributeConfirm.getConfirmed() == ConfirmChoice.ConfirmationValue.NO)
                {
                    break attributeLoop;
                }
                else{
                    EntityAttribute entityAttribute = new EntityAttribute();
                    //todo Enter attribute Name.

                    InputResult attrNameResult = PromptGui.inputText("attributeName", "Enter the attribute name", "attrbute");
                    entityAttribute.setName(attrNameResult.getInput());

                    //todo Enter attribute Type:

                    ListResult attrTypeResult = PromptGui.dataTypePrompt(configurationInfo.getEnums().stream().map(x->x.getName()).collect(Collectors.toList()));
//                            PromptGui.createListPrompt("attributeType", "Select Attribute Type:",
//
//                            "String",
//                                    "boolean",
//                                    "byte",
//                                    "short",
//                                    "int",
//                                    "long",
//                                    "float",
//                                    "double",
//                                    "Date");

                    if(!Arrays.asList("String", "boolean", "short", "int", "long", "float", "double", "Date").contains(attrTypeResult.getSelectedId()))
                    {
                        entityAttribute.setEnumuration(true);
                        entityAttribute.setPremetive(false);
                    }

                    entityAttribute.setType(attrTypeResult.getSelectedId());
                    //todo Enter ask for Validation
                    //todo take validation
                    ConfirmResult validationConfirm = PromptGui.createConfirmResult("attribue", "Do you want to add Validations to "+attrNameResult.getInput()+"?");

                        if(validationConfirm.getConfirmed() == YES){
                            EntityConstraints   entityConstraints = new EntityConstraints();
                            entityConstraints.setEnabled(true);
                            //ask if it's required
                            ConfirmResult requiredValidationConfirm = PromptGui.createConfirmResult("attribue", "Required?");
                            entityConstraints.setRequired(requiredValidationConfirm.getConfirmed() == YES);


                            switch(entityAttribute.getType().toLowerCase()){
                                case "string":
                                    ConfirmResult uniqueValidationConfirm = PromptGui.createConfirmResult("attribue", "Unique?");
                                    entityConstraints.setUnique(uniqueValidationConfirm.getConfirmed() == YES);

                                    entityConstraints.setNotEmpty(PromptGui.createConfirmResult("notEmpty", "Couldn't be empty?").getConfirmed() == YES);

                                    if(PromptGui.createConfirmResult("minimum", "Do you want to enter a minimum length?").getConfirmed() == YES)

                                    {
                                        InputResult minSize = PromptGui.readNumber("min", "Enter the minimum length", "1");
                                        entityConstraints.setMin(Long.parseLong(minSize.getInput()));
                                    }

                                    if(PromptGui.createConfirmResult("minimum", "Do you want to enter a maximum length?").getConfirmed() == YES) {
                                        InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum Length", "100");
                                        entityConstraints.setMax(Long.parseLong(maxSize.getInput()));
                                    }
                                    entityConstraints.setEmail(PromptGui.createConfirmResult("email", "Is Email?").getConfirmed() == YES);

                                    if(!entityConstraints.isEmail()){
                                        if(PromptGui.createConfirmResult("regex", "Regex?").getConfirmed() == YES)
                                        {
                                            entityConstraints.setPattern(PromptGui.inputText("regex", "Enter the regex:","").getInput());

                                        }

                                    }

                                    break;

                                case "byte":
                                case "short":
                                case "int":
                                case "long":
                                    if(PromptGui.createConfirmResult("minimum", "Do you want to enter a Minimum number?").getConfirmed() == YES)
                                    {
                                        InputResult minSize = PromptGui.readNumber("min", "Enter the minimum number", "1");
                                        entityConstraints.setMin(Long.parseLong(minSize.getInput()));
                                    }

                                    if(PromptGui.createConfirmResult("minimum", "Do you want to enter a Maximum number?").getConfirmed() == YES) {
                                        InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum number", "100");
                                        entityConstraints.setMax(Long.parseLong(maxSize.getInput()));
                                    }
                                    break;
                                case "float":
                                case "double":
                                    if(PromptGui.createConfirmResult("minimum", "Do you want to enter a Minimum number?").getConfirmed() == YES)
                                    {
                                        InputResult minSize = PromptGui.readNumber("min", "Enter the minimum number", "1");
                                        entityConstraints.setDecimalMin(Double.parseDouble(minSize.getInput()));
                                    }

                                    if(PromptGui.createConfirmResult("minimum", "Do you want to enter a Maximum number?").getConfirmed() == YES) {
                                        InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum number", "100");
                                        entityConstraints.setDecimalMax(Double.parseDouble(maxSize.getInput()));
                                    }
                                    break;
                                case "date":
                                    if(PromptGui.createConfirmResult("minimum", "Is Future?").getConfirmed() == YES) {
                                        entityConstraints.setFuture(true);
                                    }
                                    break;
                            }
                            entityAttribute.setConstraints(entityConstraints);
                        }
                    //todo take validation
                    entity.getAttributes().add(entityAttribute);

                }
            }

            String lang =  configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase();
            String entityFileContent  =micronautEntityGenerator.generateEntity(entity, configurationInfo.getRelations(),lang);

            String extension =GeneratorUtils.getSourceFileExtension(lang);

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

            if(!noEndpoint) {
                String controllerFileContent = micronautEntityGenerator.generateController(entity, lang);

                String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.CONTROLLER_PATH, new HashMap<String, String>() {{
                    put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                    put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                }});
                GeneratorUtils.createFile(System.getProperty("user.dir") + controllerPath + "/" + entity.getName() + "Controller" + extension, controllerFileContent);
            }


            ////==========
            String clientFileContent = micronautEntityGenerator.generateClient(entity, lang);



            String clientPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});
            GeneratorUtils.createFile(System.getProperty("user.dir")+"/src/main/"+configurationInfo.getProjectInfo().getSourceLanguage()+"/"+GeneratorUtils.packageToPath(entity.getClientPackage()) + "/"+entity.getName()+"Client"+extension, clientFileContent);
            configurationInfo.getEntities().add(entity);
            if(graphql)
            {
                entity.setGraphQl(true);
                String factoyFileContent = micronautEntityGenerator.generateGraphQLFactory(configurationInfo.getEntities(), lang);

                String factoryPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.GRAPHQL_PATH, new HashMap<String, String>() {{
                    put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                    put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                }});
                GeneratorUtils.createFile(System.getProperty("user.dir") + factoryPath + "/QueryFactory" + extension, factoyFileContent);



                String resolverFileContent = micronautEntityGenerator.generateGraphQLResolver(entity, lang);
                GeneratorUtils.createFile(System.getProperty("user.dir") + factoryPath + "/" + entity.getName() + "QueryResolver" + extension, resolverFileContent);


                String entityGraphQlFilename = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append(entity.getName()).append(".graphqls").toString();
                String graphQLSchema =micronautEntityGenerator.generateGraphQLSchema(entity);
                GeneratorUtils.createFile(entityGraphQlFilename, graphQLSchema);


                String queryGraphQlFilename = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append("queries.graphqls").toString();
                String graphQLQuery =micronautEntityGenerator.generateGraphQLQuery(configurationInfo.getEntities());
                GeneratorUtils.createFile(queryGraphQlFilename, graphQLQuery);

            }



            ///====== Generate Randomizer
            String randromizerFileContent = micronautEntityGenerator.generateRandomizer(entity, lang);
            GeneratorUtils.createFile(System.getProperty("user.dir")+"/src/test/"+configurationInfo.getProjectInfo().getSourceLanguage()+"/"+GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()+".utils") + "/Randomizer"+extension, randromizerFileContent);

            //========
            String controllertest = micronautEntityGenerator.generateTestController(entity, lang, configurationInfo.getProjectInfo().getTestFramework());
            String langDir = configurationInfo.getProjectInfo().getSourceLanguage();
            switch (configurationInfo.getProjectInfo().getTestFramework().toLowerCase())
            {
                case "spock":
                    extension = ".groovy";
                    langDir = "groovy";
                    break;
                case "kotest":
                    extension = ".kt";
                    langDir = "kotlin";
                    break;
                default:
                    extension = extension;
                    break;
            }
            GeneratorUtils.createFile(System.getProperty("user.dir")+"/src/test/"+langDir+"/"+GeneratorUtils.packageToPath(entity.getRestPackage()) +"/"+ entity.getName()+"ControllerTest"+extension, controllertest);




//            configurationInfo.getEntities().add(entity);
            configurationInfo.writeToFile();


          //  System.out.println(entityFileContent + "\n" + repositoryFileContent +"\n" + serviceFileContent + "\n" + controllerFileContent + "\n" + clientFileContent);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            println("Failed to create an entity!", RED);

            return (-1);
        }
        System.gc();
        return 1;
    }
}
