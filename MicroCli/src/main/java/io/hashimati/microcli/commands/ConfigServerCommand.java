package io.hashimati.microcli.commands;

import io.hashimati.microcli.client.StartSpringClient;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;


@CommandLine.Command(name="config", aliases= {"config-server", "config"}, description = "Create a Config Server")
public class ConfigServerCommand implements Callable<Integer> {
    private String EUREKA_CLIENT_YML = "eureka:\n" +
            "  client:\n" +
            "    serviceUrl:\n" +
            "      defaultZone: ${EUREKA_URI:http://host:port/eureka}\n" +
            "    enabled: true\n" +
            "  instance:\n" +
            "    instance-id: gateway\n" +
            "    preferIpAddress: true";

    private String CONSUL_CLIENT_YML = "spring:\n" +
            " cloud:\n" +
            "  consul:\n" +
            "   host: host\n" +
            "   port: port\n" +
            "   discovery:\n" +
            "    instance-id: gateway\n" +
            "    serviceName: gateway-${spring.application.name}";

    public static String configServerYml = "spring:\n" +
            "  cloud:\n" +
            "    config:\n" +
            "      server:\n" +
            "        git:\n" +
            "          uri: http://${GIT_URI}\n";




    @Inject
    private StartSpringClient startSpringClient;;

    @Inject private TemplatesService templatesService;
    @CommandLine.Option(names = {"-f", "--file"},
            description = "the file to use (default: ${DEFAULT-VALUE})")
    private File file;
    @CommandLine.Option(names = {"--package"}, defaultValue = "com.example", description = "To specify the project's package.\nDefault Value: com.example")
    private String pack;

    @CommandLine.Option(names = "--name", defaultValue = "config", description = "To specify the application name.\n It should be without spaces.")
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

    @CommandLine.Option(names = "--discoveryPort", defaultValue = "8761", description = "To specify the discovery port.\nDefault Value: 8761")
    private String discoveryPort;



    @CommandLine.Option(names = "--gitRepo", description = "To specify the git repo.")
    private  String gitRepo ;

    //    @CommandLine.Option(names = "--artifact", defaultValue = "eureka", description = "To specify the artifact.")
//    private String artifact;
@CommandLine.Option(names = "--discovery", defaultValue = "eureka", description = "To specify the discovery server type.\nvalues set =[eureka, consul]" )
private String discovery;

    @CommandLine.Option(names = "--discoveryServer", defaultValue = "localhost", description = "To specify the discovery servier host name or IP.\nDefault Value: localhost")
    private String discoveryServer;
    @Override
    public Integer call() throws Exception {

        String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + name + ".zip";


        byte[] projectZipFile = startSpringClient.generateConfigServer(build, language,version, pack, javaVersion, "config", discovery);
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


        String gatewayTemplate = templatesService.loadTemplateContent(templatesService.getJavaTemplates().get(templatesService.CONFIG_SERVER));
        String extension = "java";
        if(language.equalsIgnoreCase("groovy"))
        {
            extension = "groovy";
            gatewayTemplate = templatesService.loadTemplateContent(templatesService.getGroovyTemplates().get(templatesService.CONFIG_SERVER));

        }
        else if(language.equalsIgnoreCase("kotlin"))
        {
            gatewayTemplate = templatesService.loadTemplateContent(templatesService.getKotlinTemplates().get(templatesService.CONFIG_SERVER));

            extension = "kt";
        }

        HashMap<String, String> binder = new HashMap<>();
        binder.put("pack", pack);
        binder.put("artifact", "config");
        binder.put("appName", "Config");




        String content = GeneratorUtils.generateFromTemplate(gatewayTemplate,binder );

        String eurekaFilePath = GeneratorUtils.getCurrentWorkingPath() + "/config/src/main/java/" + pack.replace(".", "/") +"/config" + "/ConfigApplication."+ extension;
        GeneratorUtils.dumpContentToFile(eurekaFilePath, content);


        //configure ports.
        MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath() + "/config", "server.port: "+ port+"\n");
        MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath() + "/config", "spring.application.name: config\n");
        if(discovery.equalsIgnoreCase("eureka"))
        {
            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir+"/config", EUREKA_CLIENT_YML.replace("host", discoveryServer).replace("port", discoveryPort));
        }
        else
        {
            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir+ "/config", CONSUL_CLIENT_YML.replace("host", discoveryServer).replace("port", discoveryPort));

        }

        if(gitRepo == null || gitRepo.isBlank())
        {
            AnsiConsole.systemInstall();
            ansi().eraseScreen();
            gitRepo = PromptGui.inputText("repo","Please enter the git repo url: ", "https://www.github.com/<username>/<repository>.git").getInput();
        }
        MicronautProjectValidator.appendToProperties(System.getProperty("user.dir")+ "/config", configServerYml.replace("${GIT_URI}" , gitRepo));

        return createFileStatus && extract && deleteFile ? 1 : 0;

    }
}
