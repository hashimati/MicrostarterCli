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
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import groovy.lang.Tuple2;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.domains.EntityConstraints;
import io.hashimati.microcli.services.LiquibaseGenerator;
import io.hashimati.microcli.services.MicronautEntityGenerator;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.YES;
import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENTITY_PATH;

@Command(name = "add-attribute", description = {"To a new attribute to an entity", "Warning, this command will overwrite the entity."})
public class AddAttributeCommand implements Callable<Integer> {


    @CommandLine.Option(names = {"-e", "--entity"})
    private String entityName;

    @Inject
    private MicronautEntityGenerator micronautEntityGenerator;

    @Inject
    private LiquibaseGenerator liquibaseGenerator;

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
                    configurationInfo.getEntities().stream().map(Entity::getName).collect(Collectors.toList())
                            .toArray(new String[configurationInfo.getEntities().size()])).getSelectedId();
        }

        Entity entity = configurationInfo.getEntities().stream().filter(x->x.getName().equals(this.entityName)).findFirst().get();

        ArrayList<EntityAttribute> newAttributes = new ArrayList<EntityAttribute> ();


        attributeLoop: for(;;) {
            ConfirmResult takeAttributeConfirm = PromptGui.createConfirmResult("attribue", "Do you want to add attribute?", NO);

            if(takeAttributeConfirm.getConfirmed() == ConfirmChoice.ConfirmationValue.NO) {
                break attributeLoop;
            } else{
                EntityAttribute entityAttribute = new EntityAttribute();
                newAttributes.add(entityAttribute);
                //todo Enter attribute Name.

                InputResult attrNameResult = PromptGui.inputText("attributeName", "Enter attribute name", "attribute");
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
                ConfirmResult validationConfirm = PromptGui.createConfirmResult("attribue", "Do you want to add Validations to "+attrNameResult.getInput()+"?", NO);

                if(validationConfirm.getConfirmed() == YES){
                    EntityConstraints entityConstraints = new EntityConstraints();
                    entityConstraints.setEnabled(true);
                    //ask if it's required
                    ConfirmResult requiredValidationConfirm = PromptGui.createConfirmResult("attribue", "Required?", NO);
                    entityConstraints.setRequired(requiredValidationConfirm.getConfirmed() == YES);


                    switch(entityAttribute.getType().toLowerCase()){
                        case "string":
                            ConfirmResult uniqueValidationConfirm = PromptGui.createConfirmResult("attribue", "Unique?", NO);
                            entityConstraints.setUnique(uniqueValidationConfirm.getConfirmed() == YES);

                            entityConstraints.setNotEmpty(PromptGui.createConfirmResult("notEmpty", "Couldn't be empty?", NO).getConfirmed() == YES);

                            if(PromptGui.createConfirmResult("minimum", "Do you want to enter Minimum length?", NO).getConfirmed() == YES)

                            {
                                InputResult minSize = PromptGui.readNumber("min", "Enter the minimum length", "1");
                                entityConstraints.setMin(Long.parseLong(minSize.getInput()));
                            }

                            if(PromptGui.createConfirmResult("minimum", "Do you want to enter Maximum length?", NO).getConfirmed() == YES) {
                                InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum Length", "100");
                                entityConstraints.setMax(Long.parseLong(maxSize.getInput()));
                            }
                            entityConstraints.setEmail(PromptGui.createConfirmResult("email", "Is Email?", NO).getConfirmed() == YES);

                            if(!entityConstraints.isEmail()){
                                if(PromptGui.createConfirmResult("regex", "Regex?", NO).getConfirmed() == YES)
                                {
                                    entityConstraints.setPattern(PromptGui.inputText("regex", "Enter the regex:","").getInput());

                                }

                            }

                            break;

                        case "byte":
                        case "short":
                        case "int":
                        case "long":
                            if(PromptGui.createConfirmResult("minimum", "Do you want to enter Minimum number?", NO).getConfirmed() == YES)
                            {
                                InputResult minSize = PromptGui.readNumber("min", "Enter the minimum number", "1");
                                entityConstraints.setMin(Long.parseLong(minSize.getInput()));
                            }

                            if(PromptGui.createConfirmResult("minimum", "Do you want to enter Maximum number?", NO).getConfirmed() == YES) {
                                InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum number", "100");
                                entityConstraints.setMax(Long.parseLong(maxSize.getInput()));
                            }
                            break;
                        case "float":
                        case "double":
                            if(PromptGui.createConfirmResult("minimum", "Do you want to enter Minimum number?", NO).getConfirmed() == YES)
                            {
                                InputResult minSize = PromptGui.readNumber("min", "Enter the minimum number", "1");
                                entityConstraints.setDecimalMin(Double.parseDouble(minSize.getInput()));
                            }

                            if(PromptGui.createConfirmResult("minimum", "Do you want to enter Maximum number?", NO).getConfirmed() == YES) {
                                InputResult maxSize = PromptGui.readNumber("min", "Enter the maximum number", "100");
                                entityConstraints.setDecimalMax(Double.parseDouble(maxSize.getInput()));
                            }
                            break;
                        case "date":
                            if(PromptGui.createConfirmResult("minimum", "Is Future?", NO).getConfirmed() == YES) {
                                entityConstraints.setFuture(true);
                            }
                            break;
                    }
                    entityAttribute.setConstraints(entityConstraints);
                }
                //todo take validation
                boolean add = entity.getAttributes().add(entityAttribute);

            }
        }

        String lang =  configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase();
        String entityFileContent  =micronautEntityGenerator.generateEntity(entity, configurationInfo.getRelations(),lang);


        String entityPath = GeneratorUtils.generateFromTemplate(ENTITY_PATH, new HashMap<>() {{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
        }});

        GeneratorUtils.createFile(System.getProperty("user.dir")+entityPath+ "/"+entity.getName()+GeneratorUtils.srcFileExtension(lang), entityFileContent);

        if(entity.isGraphQl())
        {
            String entityGraphQlFilename = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append(entity.getName()).append(".graphqls").toString();
            String graphQLSchema =micronautEntityGenerator.generateGraphQLSchema(entity);
            GeneratorUtils.createFile(entityGraphQlFilename, graphQLSchema);


            String queryGraphQlFilename = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append("queries.graphqls").toString();
            String graphQLQuery =micronautEntityGenerator.generateGraphQLQuery(configurationInfo.getEntities());
            GeneratorUtils.createFile(queryGraphQlFilename, graphQLQuery);

        }

        if(Arrays.asList("jpa", "jdbc").contains(configurationInfo.getDataBackendRun().toLowerCase()))
        {
            if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase")){

                Tuple2<String, String> changeLog = liquibaseGenerator.generateCatalog();
                GeneratorUtils.createFile(changeLog.getV1(), changeLog.getV2());


                HashMap<String, String> mapper;
                switch (configurationInfo.getDatabaseType())
                {
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
                        mapper= DataTypeMapper.dialectMapper;
                        break;
                    case "postgres":
                    case "postgressql":
                        mapper = DataTypeMapper.postgresMapper;
                        break;
                    default:
                        mapper = DataTypeMapper.mysqlMapper;
                        break;

                }

                configurationInfo.setLiquibaseSequence(configurationInfo.getLiquibaseSequence()+1);
                entity.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                Tuple2<String, String> addColumns = liquibaseGenerator.generateAddColumnChangeSet(entity,newAttributes, mapper, configurationInfo.getLiquibaseSequence() );
                GeneratorUtils.createFile(addColumns.getV1(), addColumns.getV2());
            }
        }
        configurationInfo.writeToFile();
        System.gc();
        return 0;
    }
}
