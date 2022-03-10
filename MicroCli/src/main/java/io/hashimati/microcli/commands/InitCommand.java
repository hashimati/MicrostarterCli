package io.hashimati.microcli.commands;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import com.google.common.net.InetAddresses;
import io.hashimati.microcli.client.MicronautLaunchClient;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.fusesource.jansi.Ansi;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "init", description = "To generate a Micronaut project from Micronaut Launch(https:\\\\launch.micronaut.io)")
public class InitCommand implements Callable<Integer> {

    @Inject
    private MicronautLaunchClient micronautLaunchClient;


    @Option(names = {"--package"}, defaultValue = "com.example")
    private String pack;

    @Option(names = "--name", defaultValue = "demo")
    private String name;

    @Option(names= {"--javaVersion"}, defaultValue = "JDK_11",showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String javaVersion;

    @Option(names = {"--lang"}, defaultValue = "JAVA")
    private String language;
    
    @Option(names = "--type", defaultValue = "default")
    private String type; 
    
    @Option(names = {"--feature"})
    List<String> features = new ArrayList<>();

    @Option(names = "--test", defaultValue = "JUNIT")
    String test;

    @Option(names = "--build", defaultValue = "GRADLE")
    private String build;



    @Override
    public Integer call() throws Exception {


            String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/"+ name+ ".zip";
           // PromptGui.println("Downloading "+ name + ".zip from https://launch.micronaut.io/", Ansi.Color.WHITE);
            byte[] projectZipFile = micronautLaunchClient.generateProject(type, pack+"."+name, language, build,test, javaVersion, features);


            if(projectZipFile == null)
            {
                PromptGui.printlnErr("Failed to generate " + name + " project.");
                return 0;

            }

           boolean createFileStatus = GeneratorUtils.writeBytesToFile(projectFilePath, projectZipFile );
         if(createFileStatus)
             PromptGui.printlnSuccess("Complete downloading " + name + ".zip. ");


         boolean extract = GeneratorUtils.unzipFile(projectFilePath);
            if(extract)
             PromptGui.printlnSuccess("Successfully created \""+ name+ "\" project folder!");
           boolean deleteFile = GeneratorUtils.deleteFile(projectFilePath);

           return createFileStatus && extract && deleteFile ? 1:0;
    }

    static class MyAbcCandidates extends ArrayList<String> {
        MyAbcCandidates() { super(Arrays.asList("", "B", "C")); }
    }
}
