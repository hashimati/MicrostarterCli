package io.hashimati.microcli.commands;

import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.services.MicronautComponentGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.utils.GeneratorUtils.createFile;
import static io.hashimati.microcli.utils.PromptGui.*;

@CommandLine.Command(name= "create-kafka-client", aliases = {"kafka-client", "kafkaClient", "KafkaClient"}, description = "To create Kafka Client.")
public class CreateKafkaClientCommand implements Callable<Integer> {
    @Inject
    private MicronautComponentGenerator micronautComponentGenerator;
    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()) );
        String packageName = PromptGui.inputText("pack", "Enter the job's package: ", configurationInfo.getProjectInfo().getDefaultPackage()).getInput();

        String className = PromptGui.inputText("className", "Enter job name: ", "MyJob").getInput();
        String topic = PromptGui.inputText("topic", "Enter the topic:", "mytopic").getInput();

        String messageType = PromptGui.inputText("messageType", "Enter message type: ", "String").getInput();

        String messagePackage = null;
        if(!Arrays.asList("string", "integer", "int", "byte", "short", "byte", "char", "Character", "float", "double", "boolean").contains(messageType.toLowerCase()))
        {
            messagePackage = inputText("messageTypePackage", "Enter the package of " + messageType + ":", "com.example").getInput();
        }

        String lang = configurationInfo.getProjectInfo().getSourceLanguage();
        String content = micronautComponentGenerator.generateKafkaClient(packageName, className,topic, messageType,messagePackage, lang);



        String extension = GeneratorUtils.getSourceFileExtension(lang);
        String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.GENERAL_PATH, new HashMap<String, String>(){{
            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
            put("defaultPackage", GeneratorUtils.packageToPath(packageName));
        }});

        createFile(System.getProperty("user.dir")+controllerPath+ "/"+className+extension, content);

        printlnSuccess(className + " is create successfully!");
        setToDefault();

        return 0;
    }
}
