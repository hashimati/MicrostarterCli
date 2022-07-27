package io.hashimati.microcli.commands;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import io.hashimati.lang.parsers.engines.ServiceParsingEngine;
import io.hashimati.lang.syntax.ServiceSyntax;
import io.hashimati.microcli.client.MicronautLaunchClient;
import io.hashimati.microcli.services.ServiceGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "init", description = "To generate a Micronaut project from Micronaut Launch(https:\\\\launch.micronaut.io).\nPlease, check: https://launch.micronaut.io/swagger/views/swagger-ui/index.html")
public class InitCommand implements Callable<Integer> {

    @Inject
    private MicronautLaunchClient micronautLaunchClient;

    @Inject
    private ServiceGenerator serviceGenerator;
    @Option(names = {"-f", "--file"}, defaultValue = "service.mdl",
            description = "the file to use (default: ${DEFAULT-VALUE})")
    private File file;
    @Option(names = {"--package"}, defaultValue = "com.example", description = "To specify the project's package.\nDefault Value: com.example")
    private String pack;

    @Option(names = "--name", defaultValue = "demo", description = "To sepcify the application name.\n It should be without spaces.")
    private String name;

    @Option(names= {"--javaVersion"}, defaultValue = "JDK_11",showDefaultValue = CommandLine.Help.Visibility.ALWAYS, description = "To specify the java version.\n Options: JDK_8, JDK_11, JDK_17\nDefault value: JDK_11\nPlease, check: https://launch.micronaut.io/select-options")
    private String javaVersion;

    @Option(names = {"--lang"}, defaultValue = "JAVA", description = "To specify the project's language.\nOptions: JAVA, GROOVY, KOTLIN\nPlease, check: https://launch.micronaut.io/select-options")
    private String language;
    
    @Option(names = "--type", defaultValue = "default", description = "To specify the application type.\nOptions:DEFAULT, CLI, FUNCTION, GRPC, MESSAGING\nPlease, check: https://launch.micronaut.io/select-options")
    private String type; 
    
    @Option(names = {"--feature"} , description = "To add features by name. Please, check: https://launch.micronaut.io/application-types/DEFAULT/features")
    List<String> features = new ArrayList<>();

    @Option(names = "--test", defaultValue = "JUNIT",description = "To specify the the test framework.\nOptions: JUNIT, SPOCK, KOTEST\nPlease, check: https://launch.micronaut.io/select-options")
    String test;

    @Option(names = "--build", defaultValue = "GRADLE", description = "To specify the build tool.\nOptions: GRADLE, GRADLE_KOTLIN, MAVEN\nPlease, check: https://launch.micronaut.io/select-options")
    private String build;



    @Override
    public Integer call() throws Exception {


        if(file != null){
            String content = GeneratorUtils.getFileContent(file);
            ServiceSyntax serviceSyntax = new ServiceParsingEngine().parse(content);
//            System.out.println(serviceSyntax.getName());
//            System.out.println(serviceSyntax.getEntities().get(0));
//            System.out.println("----");
//            System.out.println(serviceSyntax.getEntities().get(1));
//            System.out.println(serviceSyntax.getEntities().stream().map(x->x.getName()).collect(Collectors.toList()));
            return serviceGenerator.initiateService(serviceSyntax);


        }
        else {
            String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + name + ".zip";
            // PromptGui.println("Downloading "+ name + ".zip from https://launch.micronaut.io/", Ansi.Color.WHITE);
            byte[] projectZipFile = micronautLaunchClient.generateProject(type, pack + "." + name, language, build, test, javaVersion, features);


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

            return createFileStatus && extract && deleteFile ? 1 : 0;
        }

    }

    static class MyAbcCandidates extends ArrayList<String> {
        MyAbcCandidates() { super(Arrays.asList("", "B", "C")); }
    }
}
