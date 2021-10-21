package io.hashimati.microcli.commands;


import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.services.TemplatesService.*;

@Command(name = "configure-metrics", aliases = {"metrics"}, description = "Configure Metrics")
public class ConfigureMetricsCommand implements Callable<Integer> {
    public static ConfigurationInfo configurationInfo;


    public static ProjectInfo projectInfo;
    private MicronautProjectValidator projectValidator = new MicronautProjectValidator();


    private TemplatesService templatesService = new TemplatesService() ;


    @Override
    public Integer call() throws Exception {
         HashMap<String, Feature> features  = FeaturesFactory.features();

        projectInfo
                =  projectValidator.getProjectInfo();
        configurationInfo =  ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));

        AnsiConsole.systemInstall();
        org.fusesource.jansi.AnsiConsole.systemInstall();
        ListResult registryOption = PromptGui.createListPrompt("registry", "Select micrometer registry: ", "Influxdb", "prometheus");

        String registry  = registryOption.getSelectedId();

        if(projectInfo.getFeatures().contains( "micrometer")){
        projectInfo.getFeatures().addAll(Arrays.asList(
                "management",
                "micrometer"
//                        "micrometer-graphite",
//                        "micrometer-statsd"
        ));
        configurationInfo.setMicrometer(true);
        try {
            MicronautProjectValidator.addDependency(features.get("management"));
            MicronautProjectValidator.addDependency(features.get("micrometer"));
        } catch (GradleReaderException e) {
            e.printStackTrace();
        }}
        MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent
                (templatesService.getMicrometersTemplates().get(MICROMETERS_yml)));

        if(registry.equalsIgnoreCase("prometheus"))
        {

            if(!projectInfo.getFeatures().contains("micrometer-prometheus"))
            {projectInfo.getFeatures().addAll(Arrays.asList(

                    "micrometer-prometheus"
//                        "micrometer-graphite",
//                        "micrometer-statsd"
            ));
            try {

                MicronautProjectValidator.addDependency(features.get("micrometer-prometheus"));
            } catch (GradleReaderException e) {
                e.printStackTrace();
            }
            }

//                MicronautProjectValidator.addDependency(features.get("micrometer-graphite"));
//                MicronautProjectValidator.addDependency(features.get("micrometer-statsd"));



            MicronautProjectValidator.appendToProperties(templatesService.loadTemplateContent
                    (templatesService.getMicrometersTemplates().get(PROMETHEUS_yml)));

            projectInfo.dumpToFile();


        }
        else if(registry.equalsIgnoreCase("influxdb"))
        {
            String org = PromptGui.inputText("org", "Enter the \"org\": ", "org").getInput();
            String bucket = PromptGui.inputText("bucket", "Enter the bucket:", "bucket").getInput();
            String token = PromptGui.inputText("token", "Enter the token: ", "secret").getInput();

            if(!projectInfo.getFeatures().contains("micrometer-influx")) {
                projectInfo.getFeatures().addAll(Arrays.asList(
                        "micrometer-influx"
                ));
                try {
                    MicronautProjectValidator.addDependency(features.get("micrometer-prometheus"));
                } catch (GradleReaderException e) {
                    e.printStackTrace();
                }
            }

            String influxTemplate = templatesService.loadTemplateContent
                    (templatesService.getMicrometersTemplates().get(INFLUX_yml));

            MicronautProjectValidator.appendToProperties(GeneratorUtils.generateFromTemplate(influxTemplate, new HashMap<String, String>(){{
                put("org", org);
                put("bucket", bucket);
                put("token", token);
            }}));
           projectInfo.dumpToFile();

        }

        return 0;
    }
}
