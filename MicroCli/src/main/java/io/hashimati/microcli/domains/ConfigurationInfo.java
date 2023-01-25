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
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
@Data
public class ConfigurationInfo {



    private ProjectInfo projectInfo;
    //this is an incremental liquibase files
    private int liquibaseSequence;
    private String appName,
            databaseName,
            databaseType, // H2, MariaDB, MySQL, Oracle, Postgres, MS_SQL, or Mongo
            dataBackendRun, // JPA, JDBC or none
            dataMigrationTool, //liquibase, Flyway
            messaging,
            graphQLIntegrationLib;



    private boolean relationalDatabase ;

    private String annotation; //{micronaut:default, JAX-RS, SpringBoot]
    private boolean jaxRsAnnotation; // to be replaced with annotation
    private boolean micronautAnnotation;
    private boolean springBootAnnotation;
    private boolean enableViews;
    private String views;
    private int port;
    boolean graphQlSupport;
    private String reactiveFramework;
    private String serviceId;
    private boolean discoveryClientEnabled;
    private String discoveryClient;
    private String consulUrl;
    private String discoveryServer;
    private String discoveryServerPort;

    private boolean configServerEnabled;

    private String configServer;
    private String configServerUrl;
    private String configServerPort;
    private String configFormat;


    private boolean grpcSupport;
    private boolean isLombok = false;
    private boolean nonBlocking;
    private boolean micrometer;
    private boolean influx, prometheus, graphite, statsd;
    private boolean caffeine;
    boolean gorm;
    private boolean securityEnable;
    private String securityStrategy = "none"; //none, jwt, basic, session.
    private boolean securityJWTPropagate;
    private boolean monolithic;

    private String securityJWTSecret;
    private String currentPropagateString;
    private HashSet<String> propagateServices = new HashSet<>();
    private String authenticationStrategy;
    private String tracingFramework;
    private boolean tracingEnabled;
    private boolean mnData;
    private String javaVersion;
    private String fileServiceType; // filesystem, aws, gcp, azure
    private String awsKey, awsSecret;
    HashSet<Entity> entities = new HashSet<>();
    ArrayList<EntityRelation> relations = new ArrayList<>();
    HashSet<EnumClass> enums = new HashSet<>();
    private boolean configured;
    private boolean supportFileService;
    private HashSet<String> securityRoles = new HashSet<>();
    public ConfigurationInfo visit(Visitor<ConfigurationInfo> visitor)
    {
        return visitor.visit(this);
    }

    private HashSet<URL> urls = new HashSet<>();


    public String toJson() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
       return om.writeValueAsString(this);
    }

    public boolean writeToFile(String path) {
        PrintWriter pw = null;
        try {

            File configurationFile = new File(getConfigurationFileName(path));
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

    public  static String getConfigurationFileName(String path) {
        if(!path.endsWith("/")) path +="/";
        return path + "MicroCliConfig.json";
    }

    public static ConfigurationInfo fromFile(File microCliConfig) throws JsonProcessingException, FileNotFoundException {
        String json = GeneratorUtils.getFileContent(microCliConfig);
        ObjectMapper om = new ObjectMapper();

        return om.readValue(json, ConfigurationInfo.class);
    }

}
