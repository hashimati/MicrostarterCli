package io.hashimati.microcli.commands.spring;


import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue;
import io.hashimati.microcli.client.StartSpringClient;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.domains.spring.SpringDependencies;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.spring.config.SpringFeaturesFactory;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.File;
import java.lang.module.Configuration;
import java.util.*;
import java.util.concurrent.Callable;

import static com.google.googlejavaformat.OpsBuilder.BlankLineWanted.YES;
import static io.hashimati.microcli.utils.PromptGui.createConfirmResult;
import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(name = "spring-starter",aliases = "spring",  description = "Create a Spring Starter", subcommands = {
        CommandLine.HelpCommand.class,
        ConfigureCommand.class
})
public class SpringStarter  implements Callable<Integer> {



    private HashMap<String, Feature> springFeaturesFactory ;

    @Inject
    private StartSpringClient startSpringClient;



    @Inject private TemplatesService templatesService;
    @CommandLine.Option(names = {"-f", "--file"},
            description = "the file to use (default: ${DEFAULT-VALUE})")
    private File file;
    @CommandLine.Option(names = {"--package"}, description = "To specify the project's package.\nDefault Value: com.example")
    private String pack;

    @CommandLine.Option(names = "--name", description = "To specify the application name.\n It should be without spaces.")
    private String name;

    @CommandLine.Option(names= {"--javaVersion", "-jv"}, defaultValue = "11",showDefaultValue = CommandLine.Help.Visibility.ALWAYS, description = "To specify the java version.\n Options: 8, 11, 17, 19\nDefault value: 11\nPlease, check: https://start.spring.io/")
    private String javaVersion;

    @CommandLine.Option(names = {"--lang", "--language"}, description = "To specify the project's language.\nOptions: java, groovy, kotlin")
    private String language;

    @CommandLine.Option(names = {"-version", "-v"}, defaultValue = "3.0.1", description = "To specify the spring boot version.")
    private String version;

    @CommandLine.Option(names = {"--feature"} , description = "To add features by name.")
    List<String> features = new ArrayList<>();



    @CommandLine.Option(names = "--build",  description = "To specify the build tool.\nOptions: gradle, maven")
    private String build;


    @CommandLine.Option(names = "--port", defaultValue = "8888", description = "To specify the port.\nDefault Value: 8761")
    private String port;

    @CommandLine.Option(names = "--artifact", description = "To specify the artifact.")
    private String artifact;
    @CommandLine.Option(names = "--ld", description = "To list all dependencies.")
    private boolean listDependencies;
    @Override
    public Integer call() throws Exception {

        if(listDependencies)
        {
            System.out.println("Listing all dependencies");
            SpringDependencies dependencies  = startSpringClient.getSpringDependencies();
            System.out.println(dependencies.toString());
            return 0;
        }
        ConfigurationInfo configurationInfo = new ConfigurationInfo();
        configurationInfo.setFramework("spring");
        ProjectInfo projectInfo = new ProjectInfo();

        configurationInfo.setProjectInfo(projectInfo);
        LinkedHashSet<String> dependencies = new LinkedHashSet<>();
        LinkedHashSet<String> postDependencies = new LinkedHashSet<>();


        AnsiConsole.systemInstall();
        org.fusesource.jansi.AnsiConsole.systemInstall();
        ansi().eraseScreen();

        if(pack == null) {
            projectInfo.setDefaultPackage(PromptGui.inputText("package", "Enter the package", "com.example").getInput());
            pack = projectInfo.getDefaultPackage();
        }
        else
            projectInfo.setDefaultPackage(pack);

        if(name == null)
            projectInfo.setArtifact(name = PromptGui.inputText("artifact", "Enter the artifact", "demo").getInput());
        else
            projectInfo.setArtifact(name);

        if(language == null)
            projectInfo.setSourceLanguage(language = PromptGui.createListPrompt("language", "Select the language", "java", "groovy", "kotlin").getSelectedId());
        else
            projectInfo.setSourceLanguage(language);



        if(build == null)
            projectInfo.setBuildTool(build = PromptGui
                    .createListPrompt("build", "Select the build tool", "gradle", "maven")
                    .getSelectedId());
        else
            projectInfo.setBuildTool(build);
        System.out.println(projectInfo.getBuildTool());

       configurationInfo.setMonolithic( createConfirmResult("Monolithic", "Is the project a monolithic application?", ConfirmationValue.YES).getConfirmed() == ConfirmationValue.YES);
       if(configurationInfo.isMonolithic()){

           configurationInfo.setPort(Integer.parseInt(PromptGui.inputText("port", "Enter the port", "8080").getInput()));
           configurationInfo.setDiscoveryClientEnabled( createConfirmResult("Service Discovery", "Do you want to use service discovery?", ConfirmationValue.YES).getConfirmed() == ConfirmationValue.YES);
           if(configurationInfo.isDiscoveryClientEnabled()) {
               configurationInfo.setDiscoveryClient(PromptGui.createListPrompt("Service Discovery", "Select the service discovery client?", "Eureka", "Consul", "Zookeeper", "Kubernetes").getSelectedId());

               if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("eureka")) {
                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Eureka Server URL", "Enter the Eureka Server URL", "localhost").getInput());
                   configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Eureka Server Port", "Enter the Eureka Server Port", "8761").getInput());

                   dependencies.add("cloud-eureka");
               }
               else if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("Consul")) {
                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Consul Server URL", "Enter the Consul Server URL", "localhost").getInput());
                     configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Consul Server Port", "Enter the Consul Server Port", "8500").getInput());

                    dependencies.add("cloud-starter-consul-discovery");
               }else if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("Zookeeper")) {
                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Zookeeper Server URL", "Enter the Zookeeper Server URL", "localhost").getInput());
                     configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Zookeeper Server Port", "Enter the Zookeeper Server Port", "2181").getInput());
                    dependencies.add("cloud-starter-zookeeper-discovery");
               }
//               else if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("Kubernetes")){
//                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Kubernetes Server URL", "Enter the Kubernetes Server URL", "localhost").getInput());
//                        configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Kubernetes Server Port", "Enter the Kubernetes Server Port", "8080").getInput());
//
//            }
       }
       }
       else
       {
           configurationInfo.setPort(Integer.parseInt(PromptGui.inputText("port", "Enter the port", "0").getInput()));
       }
       configurationInfo.setServiceId(PromptGui.inputText("Service Id", "Enter the service id", name).getInput());
       configurationInfo.setReactiveFramework("reactor");
        if(language.equalsIgnoreCase("java")){
            configurationInfo.setLombok(createConfirmResult("Lombok", "Do you want to use lombok?", ConfirmationValue.NO).getConfirmed() == ConfirmationValue.YES);
            if (configurationInfo.isLombok())
                dependencies.add("lombok");
        }
       configurationInfo.setSpringBootAnnotation(true);
       configurationInfo.setDatabaseName(PromptGui.inputText("Database Name", "Enter the database name", name).getInput());
       configurationInfo.setDatabaseType(PromptGui.createListPrompt("Database Type", "Select the database type", "mongodb", "h2", "mysql", "postgresql", "mariadb", "oracle", "sqlserver").getSelectedId());
       if(Arrays.asList("h2", "mysql", "postgresql", "mariadb", "oracle", "sqlserver").contains(configurationInfo.getDatabaseType()))
       {
           configurationInfo.setDataMigrationTool(PromptGui.createListPrompt("Data Migration Tool", "Select data migration tool?", "Liquibase", "Flyway", "none").getSelectedId());
           if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("Flyway"))
               dependencies.add("flyway");
           else if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("Liquibase"))
               dependencies.add("liquibase");
           switch (configurationInfo.getDatabaseType()){
                case "mysql":
                     dependencies.add("mysql");
                     break;
                case "postgresql":
                     dependencies.add("postgresql");
                     break;
                case "mariadb":
                     dependencies.add("mariadb");
                     break;
                case "oracle":
                     dependencies.add("oracle");
                     break;
                case "sqlserver":
                     dependencies.add("sqlserver");
                     break;
               default:
                     dependencies.add("h2");
                     break;
           }

           String springData = PromptGui.createListPrompt("Data Backend", "Select Spring Data: ", "JPA", "JDBC", "R2DBC").getSelectedId();
              if(springData.equalsIgnoreCase("JPA")) {
                  dependencies.add("data-jpa");
              }
              else if(springData.equalsIgnoreCase("JDBC")) {
                  dependencies.add("data-jdbc");
                  dependencies.add("jdbc");
              }
              else if(springData.equalsIgnoreCase("R2DBC")) {
                  dependencies.add("data-r2dbc");
                  configurationInfo.setNonBlocking(true);
              }
       }
       else if(configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb"))
       {
            String springData = PromptGui.createListPrompt("Data Backend", "Select Spring Data: ", "mongodb", "mongodb reactive").getSelectedId();

            if(springData.equalsIgnoreCase("mongodb")) {
                dependencies.add("data-mongodb");
            }
            else if(springData.equalsIgnoreCase("mongodb reactive")) {
                configurationInfo.setNonBlocking(true);
                dependencies.add("data-mongodb-reactive");
            }


       }
       configurationInfo.setMessaging(PromptGui.createListPrompt("Messaging", "Select the messaging?", "RabbitMQ", "Kafka", "none").getSelectedId());
       if(!configurationInfo.getMessaging().equalsIgnoreCase("none"))

       {
           dependencies.add("cloud-stream");
            if (configurationInfo.getMessaging().equalsIgnoreCase("RabbitMQ")) {
                dependencies.add("amqp");
            } else if (configurationInfo.getMessaging().equalsIgnoreCase("Kafka")) {
                dependencies.add("kafka");
                dependencies.add("kafka-streams");
            }
        }

         configurationInfo.setCaffeine( createConfirmResult("Caffeine", "Do you want to use cache?", ConfirmationValue.NO).getConfirmed() == ConfirmationValue.YES);
            if(configurationInfo.isCaffeine())
                dependencies.add("cache");
         configurationInfo.setMicrometer( createConfirmResult("Micrometer", "Do you want to use micrometer?", ConfirmationValue.NO).getConfirmed() == ConfirmationValue.YES);

         if(configurationInfo.isMicrometer())
         {

             String framework = PromptGui.createListPrompt("Micrometer Framework", "Select the micrometer framework?", "Prometheus", "InfluxDB", "Graphite").getSelectedId();
             configurationInfo.setPrometheus(framework.equals("Prometheus"));
             if(configurationInfo.isPrometheus())
             {
                 dependencies.add("prometheus");
                 configurationInfo.setPrometheus(true);
             }
             configurationInfo.setInflux(framework.equals("InfluxDB"));
                if(configurationInfo.isInflux())
                {
                    dependencies.add("influx");
                    configurationInfo.setInflux(true);
                }
             configurationInfo.setGraphite(framework.equals("Graphite"));
                if(configurationInfo.isGraphite())
                {
                    configurationInfo.setGraphite(true);
                    dependencies.add("graphite");
                }
         }

         configurationInfo.setTracingFramework(PromptGui.createListPrompt("Tracing Framework", "Select the tracing framework?", "Zipkin", "Jaeger", "none").getSelectedId().toLowerCase());
         if(!configurationInfo.getTracingFramework().equalsIgnoreCase("none")) {
             configurationInfo.setTracingEnabled(true);
             dependencies.add("distributed-tracing");
                if(configurationInfo.getTracingFramework().equalsIgnoreCase("Zipkin")) {
                    dependencies.add("zipkin");
                }
                else if(configurationInfo.getTracingFramework().equalsIgnoreCase("Jaeger")) {
                    postDependencies.add("jaeger");
                    configurationInfo.setTracingFramework("jaeger");
                }
         }
         configurationInfo.setGraphQLIntegrationLib(PromptGui.createListPrompt("GraphQL Integration Lib", "Select the GraphQL Framework", "Spring", "Netflix-DGS",  "none").getSelectedId().toLowerCase());
            if(!configurationInfo.getGraphQLIntegrationLib().equalsIgnoreCase("none")) {
                configurationInfo.setGraphQlSupport(true);

                if(configurationInfo.getGraphQLIntegrationLib().equalsIgnoreCase("Spring")) {
                    dependencies.add("graphql");
                }
                else if(configurationInfo.getGraphQLIntegrationLib().equalsIgnoreCase("Netflix-DGS")) {
                    postDependencies.add("netflix-dgs");
                }                else if(configurationInfo.getGraphQLIntegrationLib().equalsIgnoreCase("KickStarter"))
                    postDependencies.add("graphql-kickstarter");
            }
        configurationInfo.setViews(PromptGui.createListPrompt("Views", "Select the views?", "Thymeleaf", "Freemarker", "none").getSelectedId());
        if(configurationInfo.getViews().equalsIgnoreCase("Thymeleaf")) {
            configurationInfo.setEnableViews(true);
            dependencies.add("thymeleaf");
        }else if(configurationInfo.getViews().equalsIgnoreCase("Freemarker")) {
            configurationInfo.setEnableViews(true);
            dependencies.add("freemarker");
        }


        String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + projectInfo.getArtifact() + ".zip";



        dependencies.removeIf(x->x== null); //remove null values
        dependencies.add("web");
        dependencies.add("actuator");
        dependencies.add("webflux");
        postDependencies.add("springdoc");
        System.out.println(dependencies);
        byte[] projectZipFile = startSpringClient.generateApp(build, language,version, pack, javaVersion, name,dependencies.toArray(new String[dependencies.size()]));
        if (projectZipFile == null) {
            PromptGui.printlnErr("Failed to generate " + projectInfo.getArtifact() + " project.");
            return 0;
        }

        boolean createFileStatus = GeneratorUtils.writeBytesToFile(projectFilePath, projectZipFile);
        if (createFileStatus)
            PromptGui.printlnSuccess("Complete downloading " + projectInfo.getArtifact() + ".zip. ");


        boolean extract = GeneratorUtils.unzipFile(projectFilePath, GeneratorUtils.getCurrentWorkingPath());
        if (extract)
            PromptGui.printlnSuccess("Successfully created \"" + projectInfo.getArtifact() + "\" project folder!");
        boolean deleteFile = GeneratorUtils.deleteFile(projectFilePath);

        if(deleteFile)
        {

            configurationInfo.writeToFile(projectInfo.getArtifact() + "/");

            if (!postDependencies.isEmpty()) {
                springFeaturesFactory = SpringFeaturesFactory.dependencies();
                for (String postDependency : postDependencies) {

                    try {
                        MicronautProjectValidator.addDependency(projectInfo.getArtifact(), springFeaturesFactory.get(postDependency));
                    } catch (GradleReaderException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return 0;
    }
}
