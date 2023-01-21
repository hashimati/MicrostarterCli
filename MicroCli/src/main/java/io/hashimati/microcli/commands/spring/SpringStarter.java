package io.hashimati.microcli.commands.spring;


import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue;
import io.hashimati.microcli.client.StartSpringClient;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.domains.spring.SpringDependencies;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.File;
import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Callable;

import static com.google.googlejavaformat.OpsBuilder.BlankLineWanted.YES;
import static io.hashimati.microcli.utils.PromptGui.createConfirmResult;
import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(name = "spring-starter",aliases = "spring",  description = "Create a Spring Starter", subcommands = {
        CommandLine.HelpCommand.class,
        ConfigureCommand.class
})
public class SpringStarter  implements Callable<Integer> {



    @Inject
    private StartSpringClient startSpringClient;



    @Inject private TemplatesService templatesService;
    @CommandLine.Option(names = {"-f", "--file"},
            description = "the file to use (default: ${DEFAULT-VALUE})")
    private File file;
    @CommandLine.Option(names = {"--package"},  description = "To specify the project's package.\nDefault Value: com.example")
    private String pack;

    @CommandLine.Option(names = "--name", defaultValue = "demo", description = "To specify the application name.\n It should be without spaces.")
    private String name;

    @CommandLine.Option(names= {"--javaVersion"}, defaultValue = "11",showDefaultValue = CommandLine.Help.Visibility.ALWAYS, description = "To specify the java version.\n Options: 8, 11, 17, 19\nDefault value: 11\nPlease, check: https://start.spring.io/")
    private String javaVersion;

    @CommandLine.Option(names = {"--lang"}, defaultValue = "java", description = "To specify the project's language.\nOptions: java, groovy, kotlin")
    private String language;

    @CommandLine.Option(names = {"-version"}, defaultValue = "3.0.1", description = "To specify the spring boot version.")
    private String version;

    @CommandLine.Option(names = {"--feature"} , description = "To add features by name.")
    List<String> features = new ArrayList<>();



    @CommandLine.Option(names = "--build", defaultValue = "gradle", description = "To specify the build tool.\nOptions: gradle, maven")
    private String build;


    @CommandLine.Option(names = "--port", defaultValue = "8888", description = "To specify the port.\nDefault Value: 8761")
    private String port;

    @CommandLine.Option(names = "--artifact", defaultValue = "demo", description = "To specify the artifact.")
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
        ProjectInfo projectInfo = new ProjectInfo();

        LinkedHashSet<String> dependencies = new LinkedHashSet<>();



        AnsiConsole.systemInstall();
        org.fusesource.jansi.AnsiConsole.systemInstall();
        ansi().eraseScreen();

        if(pack == null)
            projectInfo.setDefaultPackage(PromptGui.inputText("package", "Enter the package", "demo.example").getInput());
        else
            projectInfo.setDefaultPackage(pack);

        if(name == null)
            projectInfo.setArtifact(PromptGui.inputText("artifact", "Enter the artifact", "demo").getInput());
        else
            projectInfo.setArtifact(name);

        if(language == null)
            projectInfo.setSourceLanguage(PromptGui.inputText("language", "Enter the language", "java").getInput());
        else
            projectInfo.setSourceLanguage(language);

        if(build == null)
            projectInfo.setBuildTool(PromptGui
                    .createListPrompt("build", "Select the build tool", "gradle", "maven")
                    .getSelectedId());
        else
            projectInfo.setBuildTool(build);

       configurationInfo.setMonolithic( createConfirmResult("Monolithic", "Is the project a monolithic application?", ConfirmationValue.YES).getConfirmed() == ConfirmationValue.YES);
       if(configurationInfo.isMonolithic()){

           configurationInfo.setPort(Integer.parseInt(PromptGui.inputText("port", "Enter the port", "8080").getInput()));
           configurationInfo.setDiscoveryClientEnabled( createConfirmResult("Service Discovery", "Do you want to use service discovery?", ConfirmationValue.YES).getConfirmed() == ConfirmationValue.YES);
           if(configurationInfo.isDiscoveryClientEnabled()) {
               configurationInfo.setDiscoveryClient(PromptGui.createListPrompt("Service Discovery", "Select the service discovery client?", "Eureka", "Consul", "Zookeeper", "Kubernetes").getSelectedId());
               if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("eureka")) {
                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Eureka Server URL", "Enter the Eureka Server URL", "localhost").getInput());
                   configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Eureka Server Port", "Enter the Eureka Server Port", "8761").getInput());
               }
               else if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("Consul")) {
                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Consul Server URL", "Enter the Consul Server URL", "localhost").getInput());
                     configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Consul Server Port", "Enter the Consul Server Port", "8500").getInput());
               }else if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("Zookeeper")) {
                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Zookeeper Server URL", "Enter the Zookeeper Server URL", "localhost").getInput());
                     configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Zookeeper Server Port", "Enter the Zookeeper Server Port", "2181").getInput());

               }
               else if(configurationInfo.getDiscoveryClient().equalsIgnoreCase("Kubernetes")){
                   configurationInfo.setDiscoveryServer(PromptGui.inputText("Kubernetes Server URL", "Enter the Kubernetes Server URL", "localhost").getInput());
                        configurationInfo.setDiscoveryServerPort(PromptGui.inputText("Kubernetes Server Port", "Enter the Kubernetes Server Port", "8080").getInput());

            }
       }
       }
       else
       {
           configurationInfo.setPort(Integer.parseInt(PromptGui.inputText("port", "Enter the port", "0").getInput()));
       }
       configurationInfo.setServiceId(PromptGui.inputText("Service Id", "Enter the service id", name).getInput());
       configurationInfo.setReactiveFramework("reactor");
       configurationInfo.setLombok( createConfirmResult("Lombok", "Do you want to use lombok?", ConfirmationValue.NO).getConfirmed() == ConfirmationValue.YES);
       configurationInfo.setSpringBootAnnotation(true);
       configurationInfo.setDatabaseName(PromptGui.inputText("Database Name", "Enter the database name", name).getInput());
       configurationInfo.setDatabaseType(PromptGui.createListPrompt("Database Type", "Select the database type", "mongodb", "h2", "mysql", "postgresql", "mariadb", "oracle", "sqlserver").getSelectedId());
       if(!configurationInfo.getDatabaseType().equals("mongodb"))
       {
           configurationInfo.setDataBackendRun(PromptGui.createListPrompt("Data Backend", "Select data backend?", "JPA", "JDBC", "R2DBC").toString());

           configurationInfo.setDataMigrationTool(PromptGui.createListPrompt("Data Migration Tool", "Select data migration tool?", "Flyway", "Liquibase", "none").toString());

       }
       configurationInfo.setMessaging(PromptGui.createListPrompt("Messaging", "Select the messaging?", "RabbitMQ", "Kafka", "none").toString());

         configurationInfo.setCaffeine( createConfirmResult("Caffeine", "Do you want to use caffeine?", ConfirmationValue.NO).getConfirmed() == ConfirmationValue.YES);
         configurationInfo.setMicrometer( createConfirmResult("Micrometer", "Do you want to use micrometer?", ConfirmationValue.NO).getConfirmed() == ConfirmationValue.YES);

         if(configurationInfo.isMicrometer())
         {

             String framework = PromptGui.createListPrompt("Micrometer Framework", "Select the micrometer framework?", "Prometheus", "InfluxDB", "Graphite").getSelectedId();
             configurationInfo.setPrometheus(framework.equals("Prometheus"));
             configurationInfo.setInflux(framework.equals("InfluxDB"));
             configurationInfo.setGraphite(framework.equals("Graphite"));
         }

         configurationInfo.setTracingFramework(PromptGui.createListPrompt("Tracing Framework", "Select the tracing framework?", "Zipkin", "Jaeger", "none").toString());
         if(!configurationInfo.getTracingFramework().equalsIgnoreCase("none"))
             configurationInfo.setTracingEnabled(true);

         configurationInfo.setGraphQLIntegrationLib(PromptGui.createListPrompt("GraphQL Integration Lib", "Select the GraphQL Framework", "Spring", "Netflix", "KickStarter", "none").toString());
            if(!configurationInfo.getGraphQLIntegrationLib().equalsIgnoreCase("none"))
                configurationInfo.setGraphQlSupport(true);

        configurationInfo.setViews(PromptGui.createListPrompt("Views", "Select the views?", "Thymeleaf", "Freemarker", "none").toString());
        if(configurationInfo.getViews().equalsIgnoreCase("Thymeleaf")) {
            configurationInfo.setEnableViews(true);
        }else if(configurationInfo.getViews().equalsIgnoreCase("Freemarker")) {
            configurationInfo.setEnableViews(true);
        }


        String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + name + ".zip";
        name = artifact;


        byte[] projectZipFile = startSpringClient.generateApp(build, language,version, pack, javaVersion, artifact,"");
        if (projectZipFile == null) {
            PromptGui.printlnErr("Failed to generate " + name + " project.");
            return 0;
        }

        boolean createFileStatus = GeneratorUtils.writeBytesToFile(projectFilePath, projectZipFile);
        if (createFileStatus)
            PromptGui.printlnSuccess("Complete downloading " + name + ".zip. ");


        boolean extract = GeneratorUtils.unzipFile(projectFilePath, GeneratorUtils.getCurrentWorkingPath());
        if (extract)
            PromptGui.printlnSuccess("Successfully created \"" + name + "\" project folder!");
        boolean deleteFile = GeneratorUtils.deleteFile(projectFilePath);


        return 0;
    }
}
