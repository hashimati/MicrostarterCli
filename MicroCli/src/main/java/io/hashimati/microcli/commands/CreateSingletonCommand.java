package io.hashimati.microcli.commands;

import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.services.MicronautComponentGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.utils.GeneratorUtils.createFile;
import static io.hashimati.microcli.utils.PromptGui.printlnSuccess;
import static io.hashimati.microcli.utils.PromptGui.setToDefault;


@Command(name= "create-singleton", aliases = {"singleton", "single", "service"}, description = {"To creae Singleton."})
public class CreateSingletonCommand implements Callable<Integer> {
    @Inject
    private MicronautComponentGenerator micronautComponentGenerator;
    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()) );
        String packageName = PromptGui.inputText("pack", "Enter the singleton's package: ", configurationInfo.getProjectInfo().getDefaultPackage()).getInput();

        String className = PromptGui.inputText("className", "Enter singleton name: ", "MySingleton").getInput();
        String lang = configurationInfo.getProjectInfo().getSourceLanguage();
        String content = micronautComponentGenerator.generateSingleton(packageName, className,lang);



        String extension = GeneratorUtils.getSourceFileExtension(lang);
        String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.GENERAL_PATH, new HashMap<String, String>(){{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(packageName));
        }});

        createFile(System.getProperty("user.dir")+controllerPath+ "/"+className+extension, content);

        printlnSuccess(className + " is created successfully!");
        setToDefault();

        System.gc();
        return 0;
    }
}
