package io.hashimati.microcli.utils;
/**
 * @author Ahmed Al Hashmi
 */
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.CheckboxResult;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.services.TemplatesService;
import io.micronaut.runtime.Micronaut;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.GROOVY_LANG;
import static io.hashimati.microcli.services.TemplatesService.*;
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

    public void  init() throws IOException, XmlPullParserException, GradleReaderException {

        templatesService.loadTemplates(null);


      //  Runtime.getRuntime().addShutdownHook(new Thread(()->{}));
        projectInfo
                =  projectValidator.getProjectInfo();


        if(projectInfo == null || !MicronautProjectValidator.isValidProject())
        {
            printlnErr("The current directory is not a directory of a Micronaut Application project");
            System.exit(0);
        }
        else if(projectInfo.getFeatures().contains("properties")){
            printlnErr("Microcli doesn't support configuration with \".properties\" files.");
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

            if(!projectInfo.getFeatures().contains("reactor") &&!projectInfo.getFeatures().contains("rxjava2") && !projectInfo.getFeatures().contains("rxjava3") ){

                ListResult reactiveFramework = PromptGui.createListPrompt("reactiveFramework", "Select Reactive Framework: ", "reactor", "rxjava2", "rxjava3");

                configurationInfo.setReactiveFramework(reactiveFramework.getSelectedId());

                if(configurationInfo.getReactiveFramework().equalsIgnoreCase("reactor")) {
                    MicronautProjectValidator.addDependency(features.get("reactor"));
                    MicronautProjectValidator.addDependency(features.get("reactor-http-client"));
                }
                else if(configurationInfo.getReactiveFramework().equalsIgnoreCase("rxjava2")) {
                    MicronautProjectValidator.addDependency(features.get("rxjava2"));
                    MicronautProjectValidator.addDependency(features.get("rxjava2-http-client"));
                    MicronautProjectValidator.addDependency(features.get("rxjava2-http-server-netty"));

                }
                else if(configurationInfo.getReactiveFramework().equalsIgnoreCase("rxjava3")) {
                    MicronautProjectValidator.addDependency(features.get("rxjava3"));
                    MicronautProjectValidator.addDependency(features.get("rxjava3-http-client"));

                }
            }
            else {
                if(projectInfo.getFeatures().contains("reactor"))
                    configurationInfo.setReactiveFramework("reactor");
                else if(projectInfo.getFeatures().contains("rxjava2"))
                    configurationInfo.setReactiveFramework("rxjava2");
                else if(projectInfo.getFeatures().contains("rxjava3"))
                    configurationInfo.setReactiveFramework("rxjava3");
            }

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

                if(Arrays.asList("oracle", "sqlserver", "mysql", "postgres", "mariadb").contains(configurationInfo.getDatabaseType().toLowerCase())) {
                    ListResult testWithH2Input = PromptGui.createListPrompt("h2Option", "What do you want to use for testing? ", "Testcontainers (recommended, check https://www.testcontainers.org/)", "H2");
                    if(testWithH2Input.getSelectedId().equalsIgnoreCase("h2"))
                    {
                        testWithH2 = true;
                    }
                }
            }




            //Getting JDBC or JPA from the user.
            //if(!isDatabaseConfiguredByDefault.get())
            if(!configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb")) {
                ArrayList<String> options = new ArrayList<String>();
                options.add("JDBC");
                options.add("JPA");
                if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                    options.add("GORM");
                if(!configurationInfo.getDatabaseType().equalsIgnoreCase("oracle"))
                    options.add("R2DBC");
                ListResult databaseBackend = PromptGui.createListPrompt("databaseBackend", "Select Database Backend: ", options.toArray(new String[options.size()]));
                configurationInfo.setDataBackendRun(databaseBackend.getSelectedId());



                ListResult dataMigrationTool = PromptGui.createListPrompt("databaseMigration", "Select Data Migration: ", "liquibase", "Flyway", "none");
                configurationInfo.setDataMigrationTool(dataMigrationTool.getSelectedId());


                Feature databaseFeature = null;
                switch (databaseBackend.getSelectedId())
                {
                    case "JPA" :

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
                    case "GORM":
                        configurationInfo.setGorm(true);
                        MicronautProjectValidator.addDependency(features.get("tomcat-jdbc"));
                        projectInfo.getFeatures().add("tomcat-jdbc");
                        databaseFeature = features.get("hibernate-gorm");

                        projectInfo.getFeatures().add("hibernate-gorm");
                        break;
                    case "R2DBC":
                        MicronautProjectValidator.addDependency(features.get("r2dbc"));
                        projectInfo.getFeatures().add("r2dbc");
                        projectInfo.getFeatures().add("reactor");
                        databaseFeature = features.get("data-r2dbc");
                        projectInfo.getFeatures().add("r2dbc-data");
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
                            setMaven(features.get("h2").getTestMaven());
                        }});
                        if(configurationInfo.getDataBackendRun().equalsIgnoreCase("r2dbc"))
                        {
                            MicronautProjectValidator.addDependency(new Feature(){{
                                setGradle(features.get("h2").getTestRdbcGradle());
                                setMaven(features.get("h2").getTestRdbcMaven());

                            }});
                        }
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
                         MicronautProjectValidator.appendJDBCToProperties(databasetype, true, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    else if (configurationInfo.getDataBackendRun().equalsIgnoreCase("jpa") || configurationInfo.getDataBackendRun().equalsIgnoreCase("gorm")) {

                        MicronautProjectValidator.appendJPAToProperties(configurationInfo.isGorm()? new StringBuilder().append(databasetype).append("_gorm").toString() : databasetype, true, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    }
                    else if(configurationInfo.getDataBackendRun().equalsIgnoreCase("r2dbc")){
                        MicronautProjectValidator.appendR2DBCToProperties(databasetype+"_r2dbc", true, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    }
                    if(!databasetype.equalsIgnoreCase("h2")) {

                        if(configurationInfo.getDataBackendRun().equalsIgnoreCase("r2dbc"))
                            MicronautProjectValidator.appendR2DBCToProperties(databasetype + "_r2dbc_test", false, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                        else
                            MicronautProjectValidator.appendJDBCToProperties(databasetype + "_test", false, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    }
                }



                MicronautProjectValidator.addDependency(databaseFeature,
                        features.get("jdbc-hikari"));

                if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase"))
                {
                    projectInfo.getFeatures().add("liquibase");
                    MicronautProjectValidator.addDependency(features.get("liquibase"));
                    MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent(templatesService.getProperties().get(TemplatesService.LIQUIBASE_yml)));
                }
                else if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("flyway")){
                    projectInfo.getFeatures().add("flyway");
                    MicronautProjectValidator.addDependency(features.get("flyway"));
                    MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent(templatesService.getFlywayTemplates().get(TemplatesService.FLYAWAY_YML)));
                }
                projectInfo.dumpToFile();
            }
            else {
                configurationInfo.setDataBackendRun("none");
                projectInfo.getFeatures().add("mongo-reactive");
                MicronautProjectValidator.addDependency(features.get("mongo-reactive"));
//                MicronautProjectValidator.addDependency(features.get("embed.mongo"));
                projectInfo.getFeatures().add("testcontainers");
                MicronautProjectValidator.addDependency(features.get("testcontainers"));
                //todo add dependencies to build files;
                String mongoProperties = templatesService.loadTemplateContent
                        (templatesService.getProperties().get(TemplatesService.MONGODB_yml));
                MicronautProjectValidator.appendToProperties(mongoProperties);

                if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                    if(PromptGui.createConfirmResult("gorm", "Do you want to use GORM?").getConfirmed()== ConfirmChoice.ConfirmationValue.YES)
                    {
                        configurationInfo.setGorm(true);
                        MicronautProjectValidator.addDependency(features.get("mongo-gorm"));
                        projectInfo.getFeatures().add("mongo-gorm");
                    }
                projectInfo.dumpToFile();
            }

        }

        if(!projectInfo.getFeatures().contains("rabbitmq") &&
                !projectInfo.getFeatures().contains("kafka") &&
                !projectInfo.getFeatures().contains("nats") &&
                !projectInfo.getFeatures().contains("gcp-pubsub"))
        {
            ListResult messagingTypeResult = PromptGui.createListPrompt("databaseType", "Select Messaging type: ", "Nats", "RabbitMQ", "Kafka","GCP-PubSub", "none");
            configurationInfo.setMessaging(messagingTypeResult.getSelectedId().toLowerCase());
            if(!messagingTypeResult.getSelectedId().equalsIgnoreCase("none")){

                projectInfo.getFeatures().add(configurationInfo.getMessaging());
                MicronautProjectValidator.addDependency(features.get(configurationInfo.getMessaging()));
                projectInfo.dumpToFile();


                //AddingYaml
                if(!configurationInfo.getMessaging().equalsIgnoreCase("gcp-pubsub")) // current the properties is not added, to be added in the future.
                {
                    templatesService.loadTemplates(null);
                    String messagingProperties = templatesService.loadTemplateContent
                            (templatesService.getProperties().get(configurationInfo.getMessaging().toUpperCase())); /// The index == to featureName.toUppercase
                    MicronautProjectValidator.appendToProperties(messagingProperties);
                }
                // End adding Yaml
            }
        }

        if(!projectInfo.getFeatures().contains("cache-caffeine")){
            ConfirmResult caffeineSupport = createConfirmResult("caffeine", "Do you want to add cache-caffeine support?");

            if(caffeineSupport.getConfirmed() == ConfirmChoice.ConfirmationValue.YES){
                projectInfo.getFeatures().add("cache-caffeine");
                configurationInfo.setCaffeine(true);
                MicronautProjectValidator.addDependency(features.get("cache-caffeine"));

                projectInfo.dumpToFile();
            }
        }
        if(!projectInfo.getFeatures().contains("micrometer")){
            ConfirmResult micrometer = createConfirmResult("micrometer", "Do you want to add micrometer-prometheus feature?");

            if(micrometer.getConfirmed() == ConfirmChoice.ConfirmationValue.YES)
            {
                projectInfo.getFeatures().addAll(Arrays.asList(
                        "management",
                        "micrometer",
                        "micrometer-prometheus",
                        "micrometer-graphite",
                        "micrometer-statsd"
                ));
                configurationInfo.setMicrometer(true);
                MicronautProjectValidator.addDependency(features.get("management"));
                MicronautProjectValidator.addDependency(features.get("micrometer"));
                MicronautProjectValidator.addDependency(features.get("micrometer-prometheus"));
                MicronautProjectValidator.addDependency(features.get("micrometer-graphite"));
                MicronautProjectValidator.addDependency(features.get("micrometer-statsd"));


                MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent
                        (templatesService.getMicrometersTemplates().get(MICROMETERS_yml)));

                MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent
                        (templatesService.getMicrometersTemplates().get(PROMETHEUS_yml)));

                MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent
                        (templatesService.getMicrometersTemplates().get(GRAPHITE_yml)));

                MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent
                        (templatesService.getMicrometersTemplates().get(STATSD_yml)));
                projectInfo.dumpToFile();

            }
        }
//        if(!projectInfo.getFeatures().contains("graphql"))
//        {
//            ConfirmResult graphqlSupport = createConfirmResult("graphql", "Do you want to add GraphQL support?");
//
//
//            if(graphqlSupport.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {
//
//
//                projectInfo.getFeatures().add("graphql");
//                configurationInfo.setGraphQlSupport(graphqlSupport.getConfirmed() == ConfirmChoice.ConfirmationValue.YES);
//                MicronautProjectValidator.addDependency(features.get("graphql"));
//
//
//                String graphqlLib = PromptGui.createListPrompt("graphqlLib", "Choose GraphQL Integration Library", "GraphQL-Java-Tools", "GraphQL-SPQR").getSelectedId().toLowerCase();
//                switch(graphqlLib){
//
//                    case "graphql-java-tools":
//                        MicronautProjectValidator.addDependency(features.get("graphql-java-tools"));
//                        configurationInfo.setGraphQLIntegrationLib("graphql-java-tools");
//                        break;
//                    case "graphql-spqr":
//                        MicronautProjectValidator.addDependency(features.get("graphql-spqr"));
//                        configurationInfo.setGraphQLIntegrationLib("graphql-spqr");
//                        break;
//                }
//                projectInfo.dumpToFile();
//
//
//                templatesService.loadTemplates(null);
//                String graphQLproperties = templatesService.loadTemplateContent
//                        (templatesService.getProperties().get(GRAPHQL_yml));
//                MicronautProjectValidator.appendToProperties(graphQLproperties);
//            }
//        }

        if(!projectInfo.getFeatures().contains("graphql"))
        {
            ConfirmResult graphqlSupport = createConfirmResult("graphql", "Do you want to add GraphQL-Java-Tools support?");
            if(graphqlSupport.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {
                projectInfo.getFeatures().add("graphql");
                configurationInfo.setGraphQlSupport(graphqlSupport.getConfirmed() == ConfirmChoice.ConfirmationValue.YES);
                MicronautProjectValidator.addDependency(features.get("graphql"));
                MicronautProjectValidator.addDependency(features.get("graphql-java-tools"));
                configurationInfo.setGraphQLIntegrationLib("graphql-java-tools");
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

        MicronautProjectValidator.addLombok(projectInfo);
    //MicronautProjectValidator.addDependency(features.get("openapi"));
        projectInfo.dumpToFile();
        //todo add dependencies to build files.


        configurationInfo.setProjectInfo(projectInfo);
        printlnSuccess("micronaut-cli.yml file has been updated");

        //System.out.println(configurationInfo);
        configurationInfo.writeToFile();



        setToDefault();
        System.gc();
    }

}


