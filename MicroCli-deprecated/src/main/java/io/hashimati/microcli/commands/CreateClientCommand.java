package io.hashimati.microcli.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

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

@Command(name = "create-client", aliases = {"client"}, description = "To create client component.")
public class CreateClientCommand implements Callable<Integer> {
    @Inject
    private MicronautComponentGenerator micronautComponentGenerator;
    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
    private String path;
    @Override
    public Integer call() throws Exception {
        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        else {
            File directory = new File(path);
            if(!directory.exists()) {
                directory = new File(GeneratorUtils.getCurrentWorkingPath()+"/"+ path);
                if(!directory.exists()){

                    PromptGui.printlnErr("Cannot find the working path!");
                    return null;
                }
            }
            path = path + "/";
        }
        AnsiConsole.systemInstall();
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName(path)) );
        String packageName = PromptGui.inputText("pack", "Enter the client's package: ", configurationInfo.getProjectInfo().getDefaultPackage()).getInput();

        String className = PromptGui.inputText("className", "Enter client name: ", "MyClient").getInput();

        String serviceId = PromptGui.inputText("map", "Enter the Service ID: ", "/my-service").getInput();
        String lang = configurationInfo.getProjectInfo().getSourceLanguage();
        String content = micronautComponentGenerator.generateClient(packageName, className, serviceId,lang, configurationInfo.isMicrometer());



        String extension = GeneratorUtils.getSourceFileExtension(lang);
        String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.GENERAL_PATH, new HashMap<String, String>(){{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(packageName));
        }});

        createFile(path+controllerPath+ "/"+className+extension, content);

        printlnSuccess(className + " is create successfully!");
        setToDefault();
        System.gc();
        return 0;
    }
}
