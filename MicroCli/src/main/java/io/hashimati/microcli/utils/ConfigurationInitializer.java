package io.hashimati.microcli.utils;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.CheckboxResult;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.services.TemplatesService;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static io.hashimati.microcli.services.TemplatesService.GRAPHQL_yml;
import static io.hashimati.microcli.services.TemplatesService.OPENAPI_yml;
import static io.hashimati.microcli.utils.PromptGui.*;


@Singleton
public class ConfigurationInitializer {

    public static  ConfigurationInfo configurationInfo;


    public static ProjectInfo projectInfo;
    private MicronautProjectValidator projectValidator = new MicronautProjectValidator();


    private TemplatesService templatesService = new TemplatesService() ;
    @Inject
    private HashMap<String, Feature> features  =FeaturesFactory.features();

    public ConfigurationInitializer() throws FileNotFoundException {
    }

    public void init() throws IOException, XmlPullParserException {



      //  Runtime.getRuntime().addShutdownHook(new Thread(()->{}));
        projectInfo
                =  projectValidator.getProjectInfo();



        if(!MicronautProjectValidator.isValidProject())
        {
            printlnErr("The current directory is not a directory of Micronaut proejct");
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


//            AtomicBoolean isDatabaseConfiguredByDefault = new AtomicBoolean(false);
//            Arrays.asList("h2", "reactive-mongo", "postgres", "sqlserver", "oracle", "cassandra","neo4j").forEach(
//                    x->{
//                        if(projectInfo.getFeatures().contains(x)){
//                            isDatabaseConfiguredByDefault.set(true);
//                            configurationInfo.setDatabaseType(x);
//                        }
//                    }
//            );

            //Getting Database Name;
            InputResult  databaseNameResult = PromptGui.inputText("databaseName", "Enter the database name: ", "MyDatabase");
            configurationInfo.setDatabaseName( databaseNameResult.getInput());




            //Micronaut Launch generates supports testcontainers by default. This variable to ask the users their preferences to go with Test Container or H2
            boolean testWithH2 = false;


//            if(!isDatabaseConfiguredByDefault.get())
            //Getting database type
            {
                ListResult databaseTypeResult = PromptGui.createListPrompt("databaseType", "Select Database Type: ", "MongoDB", "H2", "MySql", "MariaDB", "Postgres", "Oracle", "Sqlserver");
                configurationInfo.setDatabaseType(databaseTypeResult.getSelectedId());

                if(Arrays.asList("oracle", "sqlserver", "mysql", "postgres").contains(configurationInfo.getDatabaseType().toLowerCase())) {
                    ListResult testWithH2Input = PromptGui.createListPrompt("h2Option", "What do you want to use for testing: ", "Testcontainers (recommended, check https://www.testcontainers.org/)", "H2");
                    if(testWithH2Input.getSelectedId().equalsIgnoreCase("h2"))
                    {
                        testWithH2 = true;
                    }
                }
            }




            //Getting JDBC or JPA from the user.
            //if(!isDatabaseConfiguredByDefault.get())
            if(!configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb")) {
                ListResult databaseBackend = PromptGui.createListPrompt("databaseBackend", "Select Database Backend: ", "JDBC", "JPA");
                configurationInfo.setDataBackendRun(databaseBackend.getSelectedId());



                ListResult dataMigrationTool = PromptGui.createListPrompt("databaseMigration", "Select Data Migration: ", "liquibase", "Flyway", "none");
                configurationInfo.setDataMigrationTool(dataMigrationTool.getSelectedId());


                Feature databaseFeature = null;
                switch (databaseBackend.getSelectedId())
                {
                    case "JPA":
                        if(!projectInfo.getFeatures().contains("data-jpa")) {
                            projectInfo.getFeatures().add("data-jpa");
                            databaseFeature = features.get("data-jpa");
                        }
                        break;
                    case "JDBC":
                        if(!projectInfo.getFeatures().contains("data-jdbc")) {
                            projectInfo.getFeatures().add("data-jdbc");
                            databaseFeature = features.get("data-jdbc");
                        }
                        break;
                }





                //adding database:
                String databasetype = configurationInfo.getDatabaseType().toLowerCase();
                if(!projectInfo.getFeatures().contains(databasetype) )
                {


                    projectInfo.getFeatures().add(databasetype);
                    if(testWithH2){
                        Feature dbFeature = features.get(databasetype);
                        dbFeature.setTestGradle("");
                        dbFeature.setTestMaven(null);
                        MicronautProjectValidator.addDependency(dbFeature);

                        MicronautProjectValidator.addDependency(new Feature(){{
                            setGradle(features.get("h2").getTestGradle());
                        }});
                    }
                    else {
                        MicronautProjectValidator.addDependency(
                                features.get(databasetype));
                        if(Arrays.asList("sqlserver", "oracle", "mysql", "mariadb").contains(databasetype))
                        {
                            if(!projectInfo.getFeatures().contains("testcontainers")) {

                                projectInfo.getFeatures().add("testcontainers");
                                MicronautProjectValidator.addDependency(features.get("testcontainers"));
                            }
                        }
                    }

                    if(configurationInfo.getDataBackendRun().equalsIgnoreCase("jdbc"))
                         MicronautProjectValidator.appendJDBCToProperties(databasetype, true, testWithH2);
                    else if (configurationInfo.getDataBackendRun().equalsIgnoreCase("jpa"))
                        MicronautProjectValidator.appendJPAToProperties(databasetype, true, testWithH2);
                    if(!databasetype.equalsIgnoreCase("h2"))
                        MicronautProjectValidator.appendJDBCToProperties(databasetype+"_test", false, testWithH2);

                }



                MicronautProjectValidator.addDependency(databaseFeature,
                        features.get("jdbc-hikari"));

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
                templatesService.loadTemplates(null);
                String mongoProperties = templatesService.loadTemplateContent
                        (templatesService.getProperties().get(TemplatesService.MONGODB_yml));
                MicronautProjectValidator.appendToProperties(mongoProperties);

                projectInfo.dumpToFile();
            }

        }

        if(!projectInfo.getFeatures().contains("rabbitmq") &&
                !projectInfo.getFeatures().contains("kafka") &&
                !projectInfo.getFeatures().contains("nats"))
        {
            ListResult messagingTypeResult = PromptGui.createListPrompt("databaseType", "Select Messaging type: ", "Nats", "RabbitMQ", "Kafka", "none");
            configurationInfo.setMessaging(messagingTypeResult.getSelectedId().toLowerCase());
            if(!messagingTypeResult.getSelectedId().equalsIgnoreCase("none")){

                projectInfo.getFeatures().add(configurationInfo.getMessaging());
                MicronautProjectValidator.addDependency(features.get(configurationInfo.getMessaging()));
                projectInfo.dumpToFile();


                //AddingYaml
                templatesService.loadTemplates(null);
                String messagingProperties = templatesService.loadTemplateContent
                        (templatesService.getProperties().get(configurationInfo.getMessaging().toUpperCase())); /// The index == to featureName.toUppercase
                MicronautProjectValidator.appendToProperties(messagingProperties);
                // End adding Yaml
            }
        }
       // if(!projectInfo.getFeatures().contains("graphql"))
        {
            ConfirmResult graphqlSupport = createConfirmResult("graphql", "Do you want to add GraphQL support?");


            if(graphqlSupport.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {


                projectInfo.getFeatures().add("graphql");
                configurationInfo.setGraphQlSupport(graphqlSupport.getConfirmed() == ConfirmChoice.ConfirmationValue.YES);
                MicronautProjectValidator.addDependency(features.get("graphql"));


                String graphqlLib = PromptGui.createListPrompt("graphqlLib", "Choose GraphQl Integration Library", "GraphQL-Java-Tools", "GraphQL-SPQR").getSelectedId().toLowerCase();
                switch(graphqlLib){

                    case "graphql-java-tools":
                        MicronautProjectValidator.addDependency(features.get("graphql-java-tools"));
                        configurationInfo.setGraphQLIntegrationLib("graphql-java-tools");
                        break; 
                    case "graphql-spqr":
                        MicronautProjectValidator.addDependency(features.get("graphql-spqr"));
                        configurationInfo.setGraphQLIntegrationLib("graphql-spqr");
                        break; 
                }
                projectInfo.dumpToFile();


                templatesService.loadTemplates(null);
                String graphQLproperties = templatesService.loadTemplateContent
                        (templatesService.getProperties().get(GRAPHQL_yml));
                MicronautProjectValidator.appendToProperties(graphQLproperties);
            }
        }

      if(!projectInfo.getFeatures().contains("openapi"))
      {

          projectInfo.getFeatures().add("openapi");
          MicronautProjectValidator.addOpenapi();
          MicronautProjectValidator.addExposingSwaggerUI();
          MicronautProjectValidator.addingOpenApiToApplicationFile(configurationInfo.getAppName());

          templatesService.loadTemplates(null);
          String openAPIProperties = templatesService.loadTemplateContent
                 (templatesService.getProperties().get(OPENAPI_yml));
          MicronautProjectValidator.appendToProperties(openAPIProperties);



      }
    //MicronautProjectValidator.addDependency(features.get("openapi"));
        projectInfo.dumpToFile();
        //todo add dependencies to build files.

        MicronautProjectValidator.addLombok();
        configurationInfo.setProjectInfo(projectInfo);
        printlnSuccess("micronaut-cli.yml file has been updated");

        //System.out.println(configurationInfo);
        configurationInfo.writeToFile();



        setToDefault();
        System.gc();
    }

}


