package io.hashimati.microcli.services;


import io.hashimati.microcli.utils.MicronautProjectValidator;
import lombok.SneakyThrows;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import javax.inject.Inject;

import static io.hashimati.microcli.services.TemplatesService.CONTROLLER;

@Command(name = "generate", description = "generating domains" )
public class GenerateFiles implements  Runnable {


    @Inject
    private TemplatesService templatesService;

    @Option(names = {"-a", "--all"}, interactive = true)
    private String a;

    @Parameters(interactive = true, description = "Enter Name", index = "0", descriptionKey = "enter Name")
    public String b;



    @Option(names = {"-v", "--verbose"}, description="Verbose option")
    private boolean verbose;


    @SneakyThrows
    @Override
    public void run() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println(templatesService.loadTemplateContent(templatesService.getJavaTemplates().get(CONTROLLER)));
        if (verbose) {
            System.out.println("Is real Project:"+ MicronautProjectValidator.isMavenOrGradle());
            System.out.println("Main Files: "+ MicronautProjectValidator.getMainPackage());
            System.out.println("Dependencies: " + MicronautProjectValidator.getGradleDependencies());
            System.out.println("Maven Dependencies " + MicronautProjectValidator.getMavenDependencies());
        }
    }
}
