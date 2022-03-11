package io.hashimati.microcli.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import de.codeshelf.consoleui.elements.ConfirmChoice;
import groovy.lang.Tuple2;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityRelation;
import io.hashimati.microcli.services.FlyWayGenerator;
import io.hashimati.microcli.services.LiquibaseGenerator;
import io.hashimati.microcli.services.MicronautEntityGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENTITY_PATH;
import static io.hashimati.microcli.domains.EntityRelationType.OneToMany;
import static io.hashimati.microcli.domains.EntityRelationType.OneToOne;
import static io.hashimati.microcli.utils.PromptGui.*;


@Command(name = "create-relation", aliases = {"relation"}, description = "To create a relationship between two entities")
public class CreateRelationCommand implements Callable<Integer> {

    ConfigurationInfo configurationInfo;
    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
    private String path;
    @Inject
    private MicronautEntityGenerator micronautEntityGenerator;

    @Inject
    private LiquibaseGenerator liquibaseGenerator;

    @Inject
    private FlyWayGenerator flyWayGenerator;

    @Override
    public Integer call() throws Exception {
        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        AnsiConsole.systemInstall();

        configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName(path)));
        //Load Configuration;
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName(path))) ;
        List<String> entities = configurationInfo
                .getEntities()
                .stream()
                .map(Entity::getName)
                .collect(Collectors.toList());



        if(entities.isEmpty()) {

            printlnWarning("Please, define entities first.");
            setToDefault();
            return 0;

        }
        EntityRelation entityRelation = new EntityRelation();
        String e1Input = PromptGui.createListPrompt("e1",
                "Select the first entity (E1):",
                entities.toArray(new String[entities.size()])).getSelectedId();

        Entity e1 = configurationInfo.getEntities().stream().filter(x->x.getName().equals(e1Input)).findFirst().get();
        entityRelation.setE1(e1.getName());
        entityRelation.setE1Package(e1.getEntityPackage());
        entityRelation.setE1Table(e1.getCollectionName());




        String e2Input = PromptGui.createListPrompt("e2",
                "Select the second entity (E2):",
                entities.toArray(new String[entities.size()])).getSelectedId();

        Entity e2 = configurationInfo.getEntities().stream().filter(x->x.getName().equals(e2Input)).findFirst().get();
        entityRelation.setE2(e2.getName());
        entityRelation.setE2Package(e2.getEntityPackage());
        entityRelation.setE2Table(e2.getCollectionName());

        String relation = PromptGui.createListPrompt("relation",
                "Select the relationship type:",
                "ONE_TO_ONE",
                    "ONE_TO_MANY").getSelectedId();

        switch (relation){
            case "ONE_TO_ONE":
                entityRelation.setRelationType(OneToOne);
                break;
            case "ONE_TO_MANY":
                entityRelation.setRelationType(OneToMany);
                break;
        }

        if(configurationInfo.getRelations().stream().anyMatch(x->x.getE1().equals(entityRelation.getE1())
         && x.getE2().equals(entityRelation.getE2())))
        {
            printlnWarning("There is already an exist relationship between these two entities!");
            setToDefault();
            if(PromptGui.createConfirmResult("tellMe","Do you want to override it?", NO).getConfirmed() ==  ConfirmChoice.ConfirmationValue.YES){
                int index = 0;
                for(int i = 0;i < configurationInfo.getRelations().size(); i++) {
                    if(entityRelation.getE2().equals(configurationInfo.getRelations().get(i).getE2())
                    && entityRelation.getE1().equals(configurationInfo.getRelations().get(i).getE1()))
                    {
                        index = i;
                    }
                }
                if(index < configurationInfo.getRelations().size())
                {
                    configurationInfo.getRelations().remove(index);
                }
            }
            else {
                return 0;
            }
        }
        ArrayList relations = new ArrayList(){{
            add(entityRelation);
        }};
        String lang =  configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase();
        String entityFileContent  =micronautEntityGenerator.generateEntity(e1, relations,lang);

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
        GeneratorUtils.createFile(path+entityPath+ "/"+e1.getName()+extension, entityFileContent);




        String entity2FileContent  =micronautEntityGenerator.generateEntity(e2,relations,lang);


        String entity2Path = GeneratorUtils.generateFromTemplate(ENTITY_PATH, new HashMap<String, String>(){{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
        }});
        GeneratorUtils.createFile(path+entity2Path+ "/"+e2.getName()+extension, entity2FileContent);


        if(configurationInfo.getDataMigrationTool() != null)
        {
            if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase"))
            {
                configurationInfo.setLiquibaseSequence(1 + configurationInfo.getLiquibaseSequence());
                e1.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                e2.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());

                Tuple2< String, String> content = liquibaseGenerator.generateForeignKey(path,e1, e2, entityRelation, configurationInfo.getLiquibaseSequence());
                GeneratorUtils.createFile(content.getV1(), content.getV2());
            }
            else if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("flyway"))
            {
                configurationInfo.setLiquibaseSequence(1 + configurationInfo.getLiquibaseSequence());
                e1.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                e2.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());

                Tuple2< String, String> content = flyWayGenerator.addRelationship(e1,entityRelation,configurationInfo.getLiquibaseSequence());
                GeneratorUtils.createFile(content.getV1(), content.getV2());
            }

        }
        configurationInfo.getRelations().add(entityRelation);
        //==== regenerate entity1's Repository

        List<EntityRelation> relations1 = configurationInfo.getRelations().stream().filter(x->x.getE1().equals(e1Input)).collect(Collectors.toList());

        String repositoryFileContent = micronautEntityGenerator.generateRepository(e1, lang, relations1);


        String repoPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<>() {{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
        }});



        GeneratorUtils.createFile(path+"/"+repoPath+ "/"+e1.getName()+"Repository"+extension, repositoryFileContent);

        ///=====

        configurationInfo.writeToFile(path);
        printlnSuccess("The relationship has been created successfully!");
        setToDefault();
        System.gc();
        return 0;
    }
}
