package io.hashimati.microcli.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ExpandableChoiceResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.*;
import io.hashimati.microcli.services.FlyWayGenerator;
import io.hashimati.microcli.services.LiquibaseGenerator;
import io.hashimati.microcli.services.MicronautEntityGenerator;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import io.micronaut.core.naming.NameUtils;
import io.micronaut.core.util.StringUtils;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.YES;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENTITY_PATH;
import static io.hashimati.microcli.services.TemplatesService.*;
import static io.hashimati.microcli.utils.PromptGui.createListPrompt;
import static io.hashimati.microcli.utils.PromptGui.println;
import static org.fusesource.jansi.Ansi.Color.GREEN;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

@Command(name = "create-entity", aliases ={"entity"}, description = "To create a new entity")
public class CreateEntityCommand implements Callable<Integer> {

//
//    @Option(names = {"--multiple", "-m"}, defaultValue = "1", description = "The number of the entities that you want to define")
//    private String multiple;

    @Option(names = {"--entity-name", "-e", "-n"},  description = "Entity's Name")
    private String entityName;
    @Option(names = {"--record", "-r"},description = "To declare an entity as Java Records.")
    private boolean record;
    private TemplatesService templatesService = new TemplatesService() ;

    @Option(names = {"--collection-name", "-c"}, description = "Entity's collection/table name")
    private String collectionName;
    private ConfigurationInfo configurationInfo;



    @Option(names = {"--no-endpoint"}, description = "To prevent generating a controller for the entity.")
    private boolean noEndpoint;

    @Option(names ={"--graphql", "-gl"}, description = "To generate GraphQL components.")
    private boolean graphql;

    @Option(names={"--cache", "--caffeine"}, description = "To caching with caffeine.")
    private boolean caffeine;
    @Inject
    private MicronautEntityGenerator micronautEntityGenerator;


    @Inject
    private LiquibaseGenerator liquibaseGenerator;

    @Inject
    private FlyWayGenerator flyWayGenerator;



    private final HashMap<String, Feature> features = FeaturesFactory.features();

    public CreateEntityCommand() throws FileNotFoundException {
    }

    @Override
    public Integer call() throws Exception {



        AnsiConsole.systemInstall();
        org.fusesource.jansi.AnsiConsole.systemInstall();
        ansi().eraseScreen();
        try {
            // To get the current configuration and to configure the project if it's not previously configured.
            configurationInfo = new ConfigureCommand().call();


            // Reading name if the name is entered in the parameters.
            if (entityName == null) {

                entityName = PromptGui.inputText("entity", "Enter the entity's Name:", "MyEntity").getInput();

            }

            if (ProjectConstants.javaKeywords.contains(entityName.toLowerCase())
                    || ProjectConstants.kotlinKeywords.contains(entityName.toLowerCase())
                    || ProjectConstants.groovyKeywords.contains(entityName.toLowerCase())) {
                PromptGui.printlnErr("Please, avoid to use java, kotlin, or groovy languages keywords!");
                return 0;
            }
            Entity entity = new Entity();

            entity.setName(StringUtils.capitalize(entityName));

            entity.setNoEndpoints(noEndpoint);
            // reading collections/table name if the user didn't provide it .
            if(!noEndpoint)
            {
                if (collectionName == null) {
                    String defaultValue = "";
                    if (entityName.endsWith("ay") || entityName.endsWith("ey") || entityName.endsWith("iy") || entityName.endsWith("oy") || entityName.endsWith("uy")
                            || entityName.endsWith("ao") || entityName.endsWith("eo") || entityName.endsWith("io") || entityName.endsWith("oo") || entityName.endsWith("uo")) {
                        defaultValue = entityName.toLowerCase() + "s";
                    } else if (entityName.endsWith("y")) {
                        defaultValue = entityName.toLowerCase().substring(0, entityName.toLowerCase().lastIndexOf("y")) + "ies";
                    } else if (entityName.endsWith("o") || entityName.endsWith("s")) {
                        defaultValue = entityName.toLowerCase() + "es";
                    } else {
                        defaultValue = entityName.toLowerCase() + "s";

                    }

                    collectionName = PromptGui.inputText("collection", "Enter the entity's collection/table Name:", defaultValue).getInput();
                }
                else  collectionName = "none";
                entity.setCollectionName(collectionName);



            }
            //  entity.setEntityPackage(configurationInfo.getProjectInfo().getDefaultPackage()+".domains");
            entity.setDatabaseType(configurationInfo.getDatabaseType());
            entity.setFrameworkType(configurationInfo.getDataBackendRun());
            entity.setDatabaseName(configurationInfo.getDatabaseName());
            entity.setReactiveFramework(configurationInfo.getReactiveFramework());
            entity.setMnData(configurationInfo.isMnData());
            entity.setGorm(configurationInfo.isGorm());
            entity.setMicrometer(configurationInfo.isMicrometer());
            entity.setTracingEnabled(configurationInfo.isTracingEnabled());
            entity.setPackages(configurationInfo.getProjectInfo().getDefaultPackage());
            entity.setSecurityStrategy(configurationInfo.getSecurityStrategy());
            entity.setSecurityEnabled(configurationInfo.isSecurityEnable());
            entity.setJavaVersion(configurationInfo.getJavaVersion());


            if(entity.getJavaVersion().matches("^(1[4-9]|[2-9][0-9])$"))
            {
                if(!record){
                   var result =  PromptGui.createConfirmResult("recode", "Do you want to user Java Records? ",YES);
                   record = result.getConfirmed() == YES? true:false;
                }
                entity.setJavaRecord(record);
            }
            else
                entity.setJavaRecord(false);



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


            attributeLoop:
            for (; ; ) {
                ConfirmResult takeAttributeConfirm = PromptGui.createConfirmResult("attribute", "Do you want to add an attribute?", NO);

                if (takeAttributeConfirm.getConfirmed() == ConfirmChoice.ConfirmationValue.NO) {
                    break attributeLoop;
                } else {
                    EntityAttribute entityAttribute = new EntityAttribute();
                    //todo Enter attribute Name.

                    InputResult attrNameResult = PromptGui.inputText("attributeName", "Enter the attribute name", "attribute");
                    entityAttribute.setName(attrNameResult.getInput());

                    if (entity.getAttributes().stream().map(x -> x.getName().toLowerCase()).collect(Collectors.toList()).contains(
                            entityAttribute.getName().toLowerCase()
                    )) {
                        PromptGui.printlnErr("The attribute's Name is already exist!");
                        continue attributeLoop;
                    }
                    ;
                    if (ProjectConstants.javaKeywords.contains(entityAttribute.getName().toLowerCase())
                            || ProjectConstants.kotlinKeywords.contains(entityAttribute.getName().toLowerCase())
                            || ProjectConstants.groovyKeywords.contains(entityAttribute.getName().toLowerCase())) {
                        PromptGui.printlnErr("Please, avoid to use java, kotlin, or groovy languages keywords!");
                        continue attributeLoop;
                    }
                    //todo Enter attribute Type:

                    ListResult attrTypeResult = PromptGui.dataTypePrompt(configurationInfo.getEnums().stream().map(x -> x.getName()).collect(Collectors.toList()));
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

                    if (!Arrays.asList("String", "boolean", "short", "int", "long", "float", "double", "Date").contains(attrTypeResult.getSelectedId())) {
                        entityAttribute.setEnumuration(true);
                        entityAttribute.setPremetive(false);
                        entityAttribute.setTypePackage(configurationInfo.getProjectInfo().getDefaultPackage() + ".enums." + entityAttribute.getName());
                    }
                    entityAttribute.setType(attrTypeResult.getSelectedId());
                    //todo Enter ask for Validation
                    //todo take validation
                    ConfirmResult validationConfirm = PromptGui.createConfirmResult("attribute", "Do you want to add Validations to " + attrNameResult.getInput() + "?", NO);

                    if (validationConfirm.getConfirmed() == YES) {
                        EntityConstraints entityConstraints = new EntityConstraints();
                        entityConstraints.setEnabled(true);
                        //ask if it's required
                        ConfirmResult requiredValidationConfirm = PromptGui.createConfirmResult("attribute", "Required?", NO);
                        entityConstraints.setRequired(requiredValidationConfirm.getConfirmed() == YES);


                        switch (entityAttribute.getType().toLowerCase()) {
                            case "string":
                                ConfirmResult uniqueValidationConfirm = PromptGui.createConfirmResult("attribute", "Unique?", NO);
                                entityConstraints.setUnique(uniqueValidationConfirm.getConfirmed() == YES);

                                entityConstraints.setNotEmpty(PromptGui.createConfirmResult("notEmpty", "Couldn't be empty?", NO).getConfirmed() == YES);

                                if (PromptGui.createConfirmResult("minimum", "Do you want to enter a minimum length?", NO).getConfirmed() == YES) {
                                    InputResult minSize = PromptGui.readNumber("min", "Enter the minimum length", "1");
                                    entityConstraints.setMin(Long.parseLong(minSize.getInput()));
                                }

                                if (PromptGui.createConfirmResult("minimum", "Do you want to enter a maximum length?", NO).getConfirmed() == YES) {
                                    InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum Length", "100");
                                    entityConstraints.setMax(Long.parseLong(maxSize.getInput()));
                                }
                                entityConstraints.setEmail(PromptGui.createConfirmResult("email", "Is Email?", NO).getConfirmed() == YES);

                                if (!entityConstraints.isEmail()) {
                                    if (PromptGui.createConfirmResult("regex", "Regex?", NO).getConfirmed() == YES) {
                                        entityConstraints.setPattern(PromptGui.inputText("regex", "Enter the regex:", "").getInput());

                                    }

                                }

                                break;

                            case "byte":
                            case "short":
                            case "int":
                            case "long":
                                if (PromptGui.createConfirmResult("minimum", "Do you want to enter a Minimum number?", NO).getConfirmed() == YES) {
                                    InputResult minSize = PromptGui.readNumber("min", "Enter the minimum number", "1");
                                    entityConstraints.setMin(Long.parseLong(minSize.getInput()));
                                }

                                if (PromptGui.createConfirmResult("minimum", "Do you want to enter a Maximum number?", NO).getConfirmed() == YES) {
                                    InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum number", "100");
                                    entityConstraints.setMax(Long.parseLong(maxSize.getInput()));
                                }
                                break;
                            case "float":
                            case "double":
                                if (PromptGui.createConfirmResult("minimum", "Do you want to enter a Minimum number?", NO).getConfirmed() == YES) {
                                    InputResult minSize = PromptGui.readNumber("min", "Enter the minimum number", "1");
                                    entityConstraints.setDecimalMin(Double.parseDouble(minSize.getInput()));
                                }

                                if (PromptGui.createConfirmResult("minimum", "Do you want to enter a Maximum number?", NO).getConfirmed() == YES) {
                                    InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum number", "100");
                                    entityConstraints.setDecimalMax(Double.parseDouble(maxSize.getInput()));
                                }
                                break;
                            case "date":
                                if (PromptGui.createConfirmResult("minimum", "Is Future?", NO).getConfirmed() == YES) {
                                    entityConstraints.setFuture(true);
                                }
                                break;
                        }
                        entityAttribute.setConstraints(entityConstraints);
                    }

                    if(!noEndpoint)
                    if (Arrays.asList("String", "boolean", "short", "int", "long", "float", "double").contains(attrTypeResult.getSelectedId())) {

                        String n = NameUtils.capitalize(entityAttribute.getName());
                        var method = PromptGui.createChoiceResult("methods", "Implement the following methods, REST endpoints, and GraphQL:", "findAllBy" + n, "findBy" + n);
                        entityAttribute.setFindAllMethod(method.getSelectedIds().contains("findAllBy" + n));
                        entityAttribute.setFindByMethod(method.getSelectedIds().contains("findBy" + n));

                    }

                    //todo take validation
                    entity.getAttributes().add(entityAttribute);

                }
            }

            //Todo Update By Attribute
            if(!noEndpoint) if (!entity.getAttributes().isEmpty())
           {
               String[] attributes = entity.getAttributes().stream().map(x->x.getName()).collect(Collectors.toList()).toArray(new String[entity.getAttributes().size()]);
               updateByLoop:
               for(;;)
               {
                   var toAddUpdateBy = PromptGui.createConfirmResult("toAddUpdateBy", "Do you want to add updateBy{Attribute}() method & REST Services?", NO);
                   if(toAddUpdateBy.getConfirmed() == NO)
                       break updateByLoop;
                   else {
                       var queryAttr = PromptGui.createListPrompt("queryAttribute", "Select the query attribute: ",attributes).getSelectedId();
                       if(entity.getUpdateByMethods().containsKey(queryAttr))
                       {
                           PromptGui.printlnErr("updateBy"+NameUtils.capitalize(queryAttr)+"() is already selected.");
                           continue updateByLoop;
                       }
                       var updateAttributes = PromptGui.createChoiceResult("updateAttributes", "Select the attributes you want to update: ", attributes).getSelectedIds();

                       if(updateAttributes.isEmpty()){
                           PromptGui.printlnErr("You should select at least one attribute. ");
                           continue updateByLoop;
                       }
                       entity.getUpdateByMethods().putIfAbsent(queryAttr, updateAttributes);
                   }
               }

           }
            if(!noEndpoint)
            if(caffeine)
            {
                entity.setCached(true);

                templatesService.loadTemplates(null);
                String caffineTepmlate = templatesService.loadTemplateContent
                        (templatesService.getProperties().get(CAFFEINE_YML));
                HashMap<String, String> binder  = new HashMap<>();
                binder.putIfAbsent("tableName", entity.getCollectionName());
                MicronautProjectValidator.appendToProperties( new SimpleTemplateEngine().createTemplate(caffineTepmlate).make(binder).toString());
            }


            String lang =  configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase();
            String entityFileContent  =micronautEntityGenerator.generateEntity(entity, configurationInfo.getRelations(),lang).replaceAll("\n\n\n", "\n");

            String extension =GeneratorUtils.getSourceFileExtension(lang);

            String entityPath = GeneratorUtils.generateFromTemplate(ENTITY_PATH, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});
            GeneratorUtils.createFile(System.getProperty("user.dir")+entityPath+ "/"+entity.getName()+extension, entityFileContent);

            if(!noEndpoint) {



                //===============
                String repositoryFileContent = micronautEntityGenerator.generateRepository(entity, lang, null);





                String repoPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<String, String>(){{
                    put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                    put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                }});



                GeneratorUtils.createFile(System.getProperty("user.dir")+"/"+repoPath+ "/"+entity.getName()+"Repository"+extension, repositoryFileContent);

                if(configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb") && !configurationInfo.isMnData())
                {
                    templatesService.loadTemplates(null);
                    String mongoDbDatabasePropertiesTemplate = templatesService.loadTemplateContent
                            (templatesService.getProperties().get(MDB_COLLECTION_YML));
                    String mongoDbDatabaseProperties = GeneratorUtils.generateFromTemplate(mongoDbDatabasePropertiesTemplate, new HashMap<String, String> (){{
                        put("collection", entity.getName());
                        put("collections", collectionName);
                    }});
                    MicronautProjectValidator.appendToProperties(mongoDbDatabaseProperties);
                }
                //----------------------

                String serviceFileContent = micronautEntityGenerator.generateService(entity, lang);
                String servicePath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.SERVICES_PATH, new HashMap<String, String>(){{
                    put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                    put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                }});

                GeneratorUtils.createFile(System.getProperty("user.dir")+servicePath + "/"+entity.getName()+"Service"+
                        extension, serviceFileContent);

                if(MicronautProjectValidator.isApplication())
                {                //============================

                    String controllerFileContent = micronautEntityGenerator.generateController(entity, lang);

                    String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.CONTROLLER_PATH, new HashMap<String, String>() {{
                        put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                        put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                    }});
                    GeneratorUtils.createFile(System.getProperty("user.dir") + controllerPath + "/" + entity.getName() + "Controller" + extension, controllerFileContent);


                    ////==========
                    String clientFileContent = micronautEntityGenerator.generateClient(entity, lang);


                    String clientPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<String, String>() {{
                        put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                        put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                    }});
                    GeneratorUtils.createFile(System.getProperty("user.dir") + "/src/main/" + configurationInfo.getProjectInfo().getSourceLanguage() + "/" + GeneratorUtils.packageToPath(entity.getClientPackage()) + "/" + entity.getName() + "Client" + extension, clientFileContent);

                    if (graphql) {
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
                        String graphQLSchema = micronautEntityGenerator.generateGraphQLSchema(entity);
                        GeneratorUtils.createFile(entityGraphQlFilename, graphQLSchema);


                        String queryGraphQlFilename = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append("queries.graphqls").toString();
                        String graphQLQuery = micronautEntityGenerator.generateGraphQLQuery(configurationInfo.getEntities());
                        GeneratorUtils.createFile(queryGraphQlFilename, graphQLQuery);

                    }
                    //========
                    String controllertest = micronautEntityGenerator.generateTestController(entity, lang, configurationInfo.getProjectInfo().getTestFramework());
                    String langDir = configurationInfo.getProjectInfo().getSourceLanguage();
                    switch (configurationInfo.getProjectInfo().getTestFramework().toLowerCase()) {
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
                    GeneratorUtils.createFile(System.getProperty("user.dir") + "/src/test/" + langDir + "/" + GeneratorUtils.packageToPath(entity.getRestPackage()) + "/" + entity.getName() + "ControllerTest" + extension, controllertest);
                }
                else if(MicronautProjectValidator.isFunction())
                {
                    //todo create RequestHandler for lambda
                    ProjectInfo projectInfo = configurationInfo.getProjectInfo();
                    // generate Lambda functions

                    String save, update, delete, find, findall;
                    if(projectInfo.getApplicationType().contains("aws-lambda")){
                        save =micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), LAMBDA_FUNCTION_SAVE_REQUEST);
                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "SaveRequestHandler"+
                                extension, save);
                        delete=micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), LAMBDA_FUNCTION_DELETE_REQUEST);
                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "DeleteRequestHandler"+
                                extension, delete);
                        find= micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), LAMBDA_FUNCTION_FIND_REQUEST);
                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindRequestHandler"+
                                extension, find);
                        findall=micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), LAMBDA_FUNCTION_FINDALL_REQUEST);
                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindAllRequestHandler"+
                                extension, findall);
                        update=micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), LAMBDA_FUNCTION_UPDATE_REQUEST);
                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "UpdateRequestHandler"+
                                extension, update);
                    }

                    // generate Oracle functions
//                    if(projectInfo.getApplicationType().contains("oracle-function")){
//                        save = micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), ORACLE_FUNCTION_SAVE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "SaveRequestHandler"+
//                                extension, save);
//
//                        delete=micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), ORACLE_FUNCTION_DELETE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "DeleteRequestHandler"+
//                                extension, delete);
//
//                        find=micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), ORACLE_FUNCTION_FIND_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindRequestHandler"+
//                                extension, find);
//
//                        findall=micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), ORACLE_FUNCTION_FINDALL_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindAllRequestHandler"+
//                                extension, findall);
//
//                        update=micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), ORACLE_FUNCTION_UPDATE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "UpdateRequestHandler"+
//                                extension, update);
//                    }

                    // generate Azure functions
//                    if(projectInfo.getApplicationType().contains("azure-function")){
//                        save =  micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), AZURE_FUNCTION_SAVE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "SaveRequestHandler"+
//                                extension, save);
//
//                        delete=  micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), AZURE_FUNCTION_DELETE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "DeleteRequestHandler"+
//                                extension, delete);
//
//                        find=   micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), AZURE_FUNCTION_FIND_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindRequestHandler"+
//                                extension, find);
//
//                        findall=  micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), AZURE_FUNCTION_FINDALL_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindAllRequestHandler"+
//                                extension, findall);
//
//                        update=  micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), AZURE_FUNCTION_UPDATE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "UpdateRequestHandler"+
//                                extension, update);
//                    }
                    // generate Goolge functions
//                    if(projectInfo.getApplicationType().contains("google-cloud-function")){
//
//                        save =  micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), GOOGLE_FUNCTION_SAVE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "SaveRequestHandler"+
//                                extension, save);
//
//                        delete=  micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), GOOGLE_FUNCTION_DELETE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "DeleteRequestHandler"+
//                                extension, delete);
//
//                        find= micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), GOOGLE_FUNCTION_FIND_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindRequestHandler"+
//                                extension, find);
//
//                        findall=  micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), GOOGLE_FUNCTION_FINDALL_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "FindAllRequestHandler"+
//                                extension, findall);
//
//                        update=   micronautEntityGenerator.generateFunction(entity, projectInfo.getSourceLanguage(), GOOGLE_FUNCTION_UPDATE_REQUEST);
//                        GeneratorUtils.createFile(System.getProperty("user.dir")+ "/src/main/" + projectInfo.getSourceLanguage()+GeneratorUtils.packageToPath(entity.getLambdaPackage()) + "/" + entity.getName()+ "UpdateRequestHandler"+
//                                extension, update);
//                    }
                }


                ///====== Generate Randomizer
                String randromizerFileContent = micronautEntityGenerator.generateRandomizer(entity, lang);
                GeneratorUtils.createFile(System.getProperty("user.dir") + "/src/test/" + configurationInfo.getProjectInfo().getSourceLanguage() + "/" + GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage() + ".utils") + "/Randomizer" + extension, randromizerFileContent);

                if (Arrays.asList("jpa", "jdbc").contains(configurationInfo.getDataBackendRun().toLowerCase())) {
                    HashMap<String, String> mapper;
                    switch (configurationInfo.getDatabaseType()) {
                        case "oracle":
                            mapper = DataTypeMapper.oracleMapper;
                            break;
                        case "sqlserver":
                            mapper = DataTypeMapper.mssqlMapper;
                            break;
                        case "mysql":
                        case "mariadb":
                            mapper = DataTypeMapper.mysqlMapper;
                            break;
                        case "h2":
                            mapper = DataTypeMapper.dialectMapper;
                            break;
                        case "postgres":
                        case "postgressql":
                            mapper = DataTypeMapper.postgresMapper;
                            break;
                        default:
                            mapper = DataTypeMapper.mysqlMapper;
                            break;

                    }

                    if (configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase")) {

                        Tuple2<String, String> changeLog = liquibaseGenerator.generateCatalog();
                        GeneratorUtils.createFile(changeLog.getV1(), changeLog.getV2());


                        configurationInfo.setLiquibaseSequence(configurationInfo.getLiquibaseSequence() + 1);
                        entity.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                        Tuple2<String, String> schema = liquibaseGenerator.generateSchema(configurationInfo.getEntities(), configurationInfo.getRelations(), mapper, configurationInfo.getLiquibaseSequence());
                        GeneratorUtils.createFile(schema.getV1(), schema.getV2());
                    }
                    if (configurationInfo.getDataMigrationTool().equalsIgnoreCase("flyway")) {

//

                        configurationInfo.setLiquibaseSequence(configurationInfo.getLiquibaseSequence() + 1);
                        entity.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                        Tuple2<String, String> schema = flyWayGenerator.createTable(entity, configurationInfo.getLiquibaseSequence());
                        GeneratorUtils.createFile(schema.getV1(), schema.getV2());
                    }
                }
            }
            configurationInfo.getEntities().add(entity);

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
