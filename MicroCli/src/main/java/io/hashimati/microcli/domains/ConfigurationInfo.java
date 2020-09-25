package io.hashimati.microcli.domains;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import io.hashimati.microcli.utils.Visitor;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import static io.hashimati.microcli.utils.PromptGui.printlnSuccess;

/**
 * @author Ahmed Al Hashmi (@Hashimati)
 */
@Data
public class ConfigurationInfo {

    private ProjectInfo projectInfo;
    private String appName,
            databaseName,
            databaseType, // H2, MariaDB, MySQL, Oracle, Postgres, MS_SQL, or Mongo
            dataBackendRun, // JPA, JDBC or none
            dataMigrationTool, //liquibase, Flyway
            messaging,
            graphQLIntegrationLib;
    boolean graphQlSupport;
    HashSet<Entity> entities = new HashSet<>();
    ArrayList<EntityRelation> relations = new ArrayList<>();
    HashSet<EnumClass> enums = new HashSet<>();

    public ConfigurationInfo visit(Visitor<ConfigurationInfo> visitor)
    {
        return visitor.visit(this);
    }



    public String toJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
       return om.writeValueAsString(this);
    }

    public boolean writeToFile() {
        PrintWriter pw = null;
        try {

            File configurationFile = new File(getConfigurationFileName());
            boolean isAlreadyExist = configurationFile.exists();

            pw = new PrintWriter(configurationFile);
            pw.print( GeneratorUtils.toPrettyFormat(toJson()));

            pw.flush();
            pw.close();
            if(isAlreadyExist)
                printlnSuccess("Configuration file has been updated!");
            else
                printlnSuccess("Configuration file has been created!");

            PromptGui.setToDefault();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  false;
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;

        }
    }

    public  static String getConfigurationFileName() {
        return "MicroCliConfig.json";
    }

    public static ConfigurationInfo fromFile(File microCliConfig) throws JsonProcessingException, FileNotFoundException {
        String json = GeneratorUtils.getFileContent(microCliConfig);
        ObjectMapper om = new ObjectMapper();

        return om.readValue(json, ConfigurationInfo.class);
    }

}
