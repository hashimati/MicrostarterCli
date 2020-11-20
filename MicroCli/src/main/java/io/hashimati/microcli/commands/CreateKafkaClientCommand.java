package io.hashimati.microcli.commands;
/*
 * @author Ahmed Al Hashmi
 */
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.services.MicronautComponentGenerator;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static io.hashimati.microcli.utils.GeneratorUtils.createFile;
import static io.hashimati.microcli.utils.PromptGui.*;

@CommandLine.Command(name= "create-kafka-client", aliases = {"kafka-client", "kafkaClient", "KafkaClient"}, description = "To create Kafka Client.")
public class CreateKafkaClientCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-e", "--entity"})
    String entityName;

    @Inject
    private MicronautComponentGenerator micronautComponentGenerator;

    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName());
        ConfigurationInfo  configurationInfo;
        if(!configurationFile.exists()){
            configurationInfo =  new ConfigureCommand().call();
        }
        else {
            configurationInfo = ConfigurationInfo.fromFile(configurationFile);
        }
        ProjectInfo projectInfo = configurationInfo.getProjectInfo();
        //add Kafka Dependencies
        if(!configurationInfo
                .getProjectInfo().getFeatures().contains("kafka")){

            TemplatesService templatesService = new TemplatesService();
            projectInfo.getFeatures().add("kafka");
            try {
                MicronautProjectValidator.addDependency(FeaturesFactory.features().get("kafka"));
            } catch (GradleReaderException e) {
                e.printStackTrace();
            }

            projectInfo.dumpToFile();


            //AddingYaml
            templatesService.loadTemplates(null);
            String messagingProperties = templatesService.loadTemplateContent
                    (templatesService.getProperties().get("kafka"));
            MicronautProjectValidator.appendToProperties(messagingProperties);

            configurationInfo.writeToFile();
            // End adding Yaml

        }
        String packageName = PromptGui.inputText("pack", "Enter the class's package: ", configurationInfo.getProjectInfo().getDefaultPackage()).getInput();
        String className = PromptGui.inputText("className", "Enter the class name: ", "KafkaListener").getInput();
        String topic = PromptGui.inputText("topic", "Enter the topic name", className).getInput();
        if(entityName == null)
        {
            entityName = PromptGui.createListPrompt("entity", "Select Message Type: ",
                    configurationInfo.getEntities().stream().map(x->x.getName()).collect(Collectors.toList())
                            .toArray(new String[configurationInfo.getEntities().size()])).getSelectedId();
        }


        Entity entity = configurationInfo.getEntities().stream().filter(x->x.getName().equals(this.entityName)).findFirst().get();

        String lang = configurationInfo.getProjectInfo().getSourceLanguage();
        String content = micronautComponentGenerator.generateKafkaClient(packageName, className, topic, entity,lang);


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
