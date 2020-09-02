package io.hashimati.microcli.commands;

import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.services.MicronautComponentGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;


@Command(name = "create-controller", aliases = {"controller", "c", "control"})
public class CreateControllerCommand implements Callable<Integer> {
    @Inject
    private MicronautComponentGenerator micronautComponentGenerator;
    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()) );
        String packageName = PromptGui.inputText("pack", "Enter the controller's package: ", configurationInfo.getProjectInfo().getDefaultPackage()).getInput();
        String className = PromptGui.inputText("className", "Enter controller name: ", "MyController").getInput();

        String path = PromptGui.inputText("map", "Enter the controller's path: ", "/"+className).getInput();
        String lang = configurationInfo.getProjectInfo().getSourceLanguage();
        String content = micronautComponentGenerator.generateController(packageName, className, path,lang);



        String extension =GeneratorUtils.getSourceFileExtension(lang);
        String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.CONTROLLER_PATH, new HashMap<String, String>(){{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(packageName));
        }});

        GeneratorUtils.createFile(System.getProperty("user.dir")+controllerPath+ "/"+className+extension, content);

        PromptGui.printlnSuccess(className + " is create successfully!");
        PromptGui.println("", Ansi.Color.WHITE);

        return null;
    }
}
