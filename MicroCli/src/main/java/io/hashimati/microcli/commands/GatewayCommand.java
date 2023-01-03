package io.hashimati.microcli.commands;


import io.hashimati.microcli.client.StartSpringClient;
import io.hashimati.microcli.domains.GatewayConfig;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gateway", description = "Create a Gateway for a service", subcommands = {
        GatewayRegisterCommand.class
})
public class GatewayCommand  implements Callable<Integer> {


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
    private String SPRING_CONFIG_YML = "spring.cloud.config.uri: http://host:port";
    private String CONSUL_CONFIG_YML = "spring:\n" +
            " cloud:\n" +
            "  consul:\n" +
            "   host: host\n" +
            "   port: port\n" +
            "   config:\n" +
            "    enabled: true\n" +
            "    prefix: ${spring.application.name}\n" +
            "    format: yaml\n" +
            "    data-key: data\n" +
            "    fail-fast: true\n" +
            "    acl-token: ${CONSUL_TOKEN:}";
    @Inject
    private StartSpringClient startSpringClient;;



   @Inject private TemplatesService templatesService;
    @Option(names = {"-f", "--file"},
            description = "the file to use (default: ${DEFAULT-VALUE})")
    private File file;
    @Option(names = {"--package"}, defaultValue = "com.example", description = "To specify the project's package.\nDefault Value: com.example")
    private String pack;

    @Option(names = "--name", defaultValue = "gateway", description = "To specify the application name.\n It should be without spaces.")
    private String name;

    @Option(names= {"--javaVersion"}, defaultValue = "11",showDefaultValue = CommandLine.Help.Visibility.ALWAYS, description = "To specify the java version.\n Options: 8, 11, 17, 19\nDefault value: 11\nPlease, check: https://start.spring.io/")
    private String javaVersion;

    @Option(names = {"--lang"}, defaultValue = "java", description = "To specify the project's language.\nOptions: java, groovy, kotlin")
    private String language;

    @Option(names = {"-version"}, defaultValue = "3.0.1", description = "To specify the spring boot version.")
    private String version;

    @Option(names = {"--feature"} , description = "To add features by name.")
    List<String> features = new ArrayList<>();



    @Option(names = "--build", defaultValue = "gradle", description = "To specify the build tool.\nOptions: gradle, maven")
    private String build;

    @Option(names = "--artifact", defaultValue = "gateway", description = "To specify the artifact.")
    private String artifact;

    @Option(names = "--port", defaultValue = "8080", description = "To specify the port.")
    private String port;

    @Option(names = "--discoverServer", defaultValue = "localhost", description = "To specify the service discovery server..")
    private String discoveryServer;

    @Option(names = "--discoveryPort", defaultValue = "8761", description = "To specify the discovery server port.")
    private String discoveryPort;

    @Option(names = "--discovery", defaultValue = "eureka", description = "To specify the discovery server type.\nvalues set =[eureka, consul]" )
    private String discovery;
    @Option(names = "--config", defaultValue = "none", description = "To specify the configuration server type.\nvalues set =[spring, consul]" )
    private String config;
    @Option(names = "--configPort", defaultValue = "8888", description = "To specify the configuration server port.]" )
    private String configPort;

    public GatewayCommand() {
    }


    @Override
    public Integer call() throws Exception {

        String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + name + ".zip";

        GatewayConfig gatewayConfig = new GatewayConfig();




        byte[] projectZipFile = startSpringClient.generateGatewayServer(build, language, version, pack, javaVersion, artifact, discovery, config);
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


        gatewayConfig.setName(name);
        gatewayConfig.setPort(port);
        gatewayConfig.setDiscovery(discovery);
        gatewayConfig.setDiscoveryPort(discoveryPort);
        gatewayConfig.setDiscoveryServer(discoveryServer);
        gatewayConfig.setPack(pack);
        gatewayConfig.setArtifact(artifact);
        gatewayConfig.setBuild(build);
        gatewayConfig.setLanguage(language);
        gatewayConfig.setVersion(version);
        gatewayConfig.setJavaVersion(javaVersion);
        gatewayConfig.setGroup(pack);
        gatewayConfig.setVersion(version);
        String gatewayTemplate = templatesService.loadTemplateContent(templatesService.getJavaTemplates().get(templatesService.GATEWAY));
        String extension = "java";
        if(language.equalsIgnoreCase("groovy"))
        {
            extension = "groovy";
            gatewayTemplate = templatesService.loadTemplateContent(templatesService.getGroovyTemplates().get(templatesService.GATEWAY));

        }
        else if(language.equalsIgnoreCase("kotlin"))
        {
            gatewayTemplate = templatesService.loadTemplateContent(templatesService.getKotlinTemplates().get(templatesService.GATEWAY));

            extension = "kt";
        }

        HashMap<String, String> binder = new HashMap<>();
        binder.put("pack", pack);
        binder.put("artifact", "gateway");
        binder.put("appName", "Gateway");




        String content = GeneratorUtils.generateFromTemplate(gatewayTemplate,binder );

        String gatewayFilePath = GeneratorUtils.getCurrentWorkingPath() + "/gateway/src/main/java/" + pack.replace(".", "/") +"/gateway" + "/GatewayApplication."+ extension;
        GeneratorUtils.dumpContentToFile(gatewayFilePath, content);


        if(discovery.equalsIgnoreCase("eureka"))
        {


            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir+"/gateway", EUREKA_CLIENT_YML.replace("host", discoveryServer).replace("port", discoveryPort));
            MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath() + "/gateway", "server.port: "+ port+"\n");
            MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath() + "/gateway", "spring.application.name: gateway\n");
        }
        else
        {
            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir+ "/gateway", CONSUL_CLIENT_YML.replace("host", discoveryServer).replace("port", discoveryPort));
            MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath() + "/gateway", "server.port: "+ port+"\n");
            MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath() + "/gateway", "spring.application.name: gateway\n");
        }

        if(config.equalsIgnoreCase("spring"))
        {
            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir+ "/gateway", SPRING_CONFIG_YML.replace("host", discoveryServer).replace("port", configPort));
        }
        else
        {
            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir+ "/gateway", CONSUL_CONFIG_YML.replace("host", discoveryServer).replace("port", configPort));
        }
        gatewayConfig.writeToFile(GeneratorUtils.getCurrentWorkingPath() + "/gateway");
        return createFileStatus && extract && deleteFile ? 1 : 0;

    }
}

