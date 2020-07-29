package io.hashimati.domains;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hashimati.utils.GeneratorUtils;
import io.hashimati.utils.Visitor;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;

/**
 * @author Ahmed Al Hashmi (@Hashimati)
 */
@Data
public class ConfigurationInfo {

    private ProjectInfo projectInfo;
    private String databaseName,
            databaseType, // H2, MariaDB, MySQL, Oracle, Postgres, MS_SQL, or Mongo
            dataBackendRun, // JPA, JDBC or none
            dataMigrationTool; //liquibase, Flyway
    HashSet<Entity> entities = new HashSet<>();
    HashSet<EntityRelation>  relations = new HashSet<EntityRelation>();
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
            pw = new PrintWriter(new File(getConfigurationFileName()));
            pw.print( GeneratorUtils.toPrettyFormat(toJson()));
            pw.flush();
            pw.close();
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
