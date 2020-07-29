package io.hashimati.utils;

import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.config.Feature;
import io.hashimati.config.FeaturesFactory;
import io.hashimati.domains.ConfigurationInfo;
import io.hashimati.domains.ProjectInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


@Singleton
public class ConfigurationInitializer {

    public static  ConfigurationInfo configurationInfo;


    public static ProjectInfo projectInfo;
    private MicronautProjectValidator projectValidator = new MicronautProjectValidator();



    @Inject
    private HashMap<String, Feature> features  =FeaturesFactory.features();

    public void init() throws IOException {


      //  Runtime.getRuntime().addShutdownHook(new Thread(()->{}));

        projectInfo
                =  projectValidator.getProjectInfo();
        if(!MicronautProjectValidator.isValidProject())
        {
            System.out.println("The current directory is not a directory of Micronaut proejct");
            System.exit(0);
        }
        File file = new File("MicroCliConfig.json");




        if(file.isFile() && file.exists())
        {
            configurationInfo =  ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));
        }
        else {
            configurationInfo = new ConfigurationInfo();//ask for Database Name;

            if(projectInfo.getBuildTool().equalsIgnoreCase("maven"))
            {
                configurationInfo.setAppName(MicronautProjectValidator.getAppNameFromMaven());
            }
            else if(projectInfo.getBuildTool().equalsIgnoreCase("gradle"))
            {
                configurationInfo.setAppName(MicronautProjectValidator.getAppNameFromGradle());
            }
            //Getting Database Name;
            InputResult  databaseNameResult = PromptGui.inputText("databaseName", "Enter the database name: ", "MyDatabase");
            configurationInfo.setDatabaseName( databaseNameResult.getInput());



            //Getting database type
            ListResult databaseTypeResult = PromptGui.createListPrompt("databaseType","Select Database Type: ",  "MongoDB","H2", "MySql", "MariaDB", "Postgres", "Sql Server-JRE_8","Sql Server-JRE_11","Sql Server-JRE_14" );
            configurationInfo.setDatabaseType(databaseTypeResult.getSelectedId());



            //Getting JDBC or JPA from the user.
            if(!configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb")) {
                ListResult databaseBackend = PromptGui.createListPrompt("databaseBackend", "Select Database Backend: ", "JDBC", "JPA");
                configurationInfo.setDataBackendRun(databaseBackend.getSelectedId());

                ListResult dataMigrationTool = PromptGui.createListPrompt("databaseMigration", "Select Data Migration: ", "liquibase", "Flyway", "none");
                configurationInfo.setDataMigrationTool(dataMigrationTool.getSelectedId());


                Feature databaseFeature = null;
                switch (databaseBackend.getSelectedId())
                {
                    case "JPA":
                        projectInfo.getFeatures().add("data-jpa");
                        databaseFeature = features.get("data-jpa");
                        break;
                    case "JDBC":
                        projectInfo.getFeatures().add("data-jdbc");
                        databaseFeature = features.get("data-jdbc");
                        break;
                }


                //todo add dependencies to build file;

                MicronautProjectValidator.addDependency(databaseFeature,
                        features.get("jdbc-hikari"),
                        features.get(databaseTypeResult.getSelectedId().toLowerCase()),
                        features.get("h2"));

                if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase"))
                {
                    projectInfo.getFeatures().add("liquibase");
                    MicronautProjectValidator.addDependency(features.get("liquibase"));
                }
                else if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("flyway")){
                    projectInfo.getFeatures().add("flyway");
                    MicronautProjectValidator.addDependency(features.get("flyway"));
                }
                projectInfo.dumpToFile();
            }
            else {
                configurationInfo.setDataBackendRun("none");
                projectInfo.getFeatures().add("mongo-reactive");
                MicronautProjectValidator.addDependency(features.get("mongo-reactive"));
                //todo add dependencies to build files;
            }

        }
        projectInfo.getFeatures().add("openapi");
        MicronautProjectValidator.addDependency(features.get("openapi"));
        projectInfo.dumpToFile();
        //todo add dependencies to build files.


        MicronautProjectValidator.addingOpenApiToApplicationFile(configurationInfo.getAppName());
        MicronautProjectValidator.addLombok();
        configurationInfo.setProjectInfo(projectInfo);


        //System.out.println(configurationInfo);
        configurationInfo.writeToFile();

    }

}


