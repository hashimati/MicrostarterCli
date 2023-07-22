package io.hashimati.microcli.services;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.hashimati.microcli.utils.MicronautProjectValidator;
import lombok.SneakyThrows;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import jakarta.inject.Inject;

import static io.hashimati.microcli.services.TemplatesService.CONTROLLER;

@Deprecated
@Command(name = "generate", description = "generating domains" )
public class GenerateFiles implements  Runnable {


    @Inject
    private TemplatesService templatesService;

    @Option(names = {"-a", "--all"}, interactive = true)
    private String a;

    @Parameters(interactive = true, description = "Enter Name", index = "0", descriptionKey = "enter Name")
    public String b;

    @Option(names = "--path")
    private String path;


    @Option(names = {"-v", "--verbose"}, description="Verbose option")
    private boolean verbose;


    @SneakyThrows
    @Override
    public void run() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println(templatesService.loadTemplateContent(templatesService.getJavaTemplates().get(CONTROLLER)));
        if (verbose) {
            System.out.println("Is real Project:"+ MicronautProjectValidator.isMavenOrGradle(path));
            System.out.println("Main Files: "+ MicronautProjectValidator.getMainPackage(path));
            System.out.println("Dependencies: " + MicronautProjectValidator.getGradleDependencies(path));
            System.out.println("Maven Dependencies " + MicronautProjectValidator.getMavenDependencies(path));
        }
    }
}
