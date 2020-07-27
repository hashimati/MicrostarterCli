package io.hashimati.utils;

import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.domains.ConfigurationInfo;
import io.hashimati.domains.ProjectInfo;
import lombok.Data;

import java.io.File;
import java.io.IOException;


public class ConfigurationInitializer {

    public static  ConfigurationInfo configurationInfo;

    public static ProjectInfo projectInfo;
    private ProjectValidator projectValidator = new ProjectValidator();
    public void init() throws IOException {

      //  Runtime.getRuntime().addShutdownHook(new Thread(()->{}));

        projectInfo
                =  projectValidator.getProjectInfo();
        if(!ProjectValidator.isValidProject())
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

            //Getting Database Name;
            InputResult  databaseNameResult = PromptGui.inputText("databaseName", "Enter the database name: ", "MyDatabase");
            configurationInfo.setDatabaseName( databaseNameResult.getInput());



            //Getting database type
            ListResult databaseTypeResult = PromptGui.createListPrompt("databaseType","Select Database Type: ",  "H2", "MySql", "MariaDB", "Postgres", "MS_SQL", "MongoDB");
            configurationInfo.setDatabaseType(databaseTypeResult.getSelectedId());



            //Getting JDBC or JPA from the user.
            if(!configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb")) {
                ListResult databaseBackend = PromptGui.createListPrompt("databaseBackend", "Select Database Backend: ", "JDBC", "JPA");
                configurationInfo.setDataBackendRun(databaseBackend.getSelectedId());
                switch (databaseBackend.getSelectedId())
                {
                    case "JPA":
                        projectInfo.getFeatures().add("data-jpa");

                        break;
                    case "JDBC":
                        projectInfo.getFeatures().add("data-jdbc");
                        break;
                }
                projectInfo.dumpToFile();

                //todo add dependencies to build file;

            }
            else {
                configurationInfo.setDataBackendRun("none");
                projectInfo.getFeatures().add("mongo-reactive");
                //todo add dependencies to build files;
            }

        }
        projectInfo.getFeatures().add("openapi");
        projectInfo.dumpToFile();
        //todo add dependencies to build files.

        configurationInfo.setProjectInfo(projectInfo);

        //System.out.println(configurationInfo);
        configurationInfo.writeToFile();

    }

}


