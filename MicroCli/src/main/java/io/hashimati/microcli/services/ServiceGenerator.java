package io.hashimati.microcli.services;

import io.hashimati.lang.syntax.ServiceSyntax;
import io.hashimati.microcli.client.MicronautLaunchClient;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

@Singleton
public class ServiceGenerator {

    @Inject
    private MicronautLaunchClient micronautLaunchClient;

    public Integer initiateService(ServiceSyntax serviceSyntax) throws IOException {

        String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + serviceSyntax.getName() + ".zip";




        // PromptGui.println("Downloading "+ name + ".zip from https://launch.micronaut.io/", Ansi.Color.WHITE);
        byte[] projectZipFile = micronautLaunchClient.generateProject("default", serviceSyntax.getPackage()+"."+serviceSyntax.getName().toLowerCase(), serviceSyntax.getLanguage().toUpperCase(), serviceSyntax.getBuild().toUpperCase(), "JUNIT", "JDK_11", new ArrayList<>());

        System.out.println(serviceSyntax.getName());
        System.out.println(serviceSyntax.getPackage());
        System.out.println(serviceSyntax.getLanguage());
        System.out.println(serviceSyntax.getBuild());


        if (projectZipFile == null) {
            PromptGui.printlnErr("Failed to generate " + serviceSyntax.getName() + " project.");
            return 0;
        }

        boolean createFileStatus = GeneratorUtils.writeBytesToFile(projectFilePath, projectZipFile);
        if (createFileStatus)
            PromptGui.printlnSuccess("Complete downloading " + serviceSyntax.getName() + ".zip. ");


        boolean extract = GeneratorUtils.unzipFile(projectFilePath, GeneratorUtils.getCurrentWorkingPath());
        if (extract)
            PromptGui.printlnSuccess("Successfully created \"" + serviceSyntax.getName() + "\" project folder!");
        boolean deleteFile = GeneratorUtils.deleteFile(projectFilePath);


        return null;
    }
}
