package io.hashimati.microcli.commands;

import io.hashimati.microcli.client.StartSpringClient;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;



@CommandLine.Command(name = "eureka", description = "Create a Eureka Server")
public class EurekaCommand implements Callable<Integer> {


    @Inject
    private StartSpringClient startSpringClient;;

    @Inject private TemplatesService templatesService;
    @CommandLine.Option(names = {"-f", "--file"},
            description = "the file to use (default: ${DEFAULT-VALUE})")
    private File file;
    @CommandLine.Option(names = {"--package"}, defaultValue = "com.example", description = "To specify the project's package.\nDefault Value: com.example")
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

@CommandLine.Option(names = "--port", defaultValue = "8761", description = "To specify the port.\nDefault Value: 8761")
    private String port;
//    @CommandLine.Option(names = "--artifact", defaultValue = "eureka", description = "To specify the artifact.")
//    private String artifact;



    @Override
    public Integer call() throws Exception {
        String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + name + ".zip";


        byte[] projectZipFile = startSpringClient.generateEurekaServer(build, language, version, pack, javaVersion, "eureka");
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


        String gatewayTemplate = templatesService.loadTemplateContent(templatesService.getJavaTemplates().get(templatesService.EUREKA_SERVER));
        String extension = "java";
        if(language.equalsIgnoreCase("groovy"))
        {
            extension = "groovy";
            gatewayTemplate = templatesService.loadTemplateContent(templatesService.getGroovyTemplates().get(templatesService.EUREKA_SERVER));

        }
        else if(language.equalsIgnoreCase("kotlin"))
        {
            gatewayTemplate = templatesService.loadTemplateContent(templatesService.getKotlinTemplates().get(templatesService.EUREKA_SERVER));

            extension = "kt";
        }

        HashMap<String, String> binder = new HashMap<>();
        binder.put("pack", pack);
        binder.put("artifact", "eureka");
        binder.put("appName", "Eureka");




        String content = GeneratorUtils.generateFromTemplate(gatewayTemplate,binder );

        String eurekaFilePath = GeneratorUtils.getCurrentWorkingPath() + "/eureka/src/main/java/" + pack.replace(".", "/") +"/eureka" + "/EurekaApplication."+ extension;
        GeneratorUtils.dumpContentToFile(eurekaFilePath, content);


        //configure ports.
        MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath() + "/eureka", "server.port: "+ port+"\n");

        return createFileStatus && extract && deleteFile ? 1 : 0;

    }
}

