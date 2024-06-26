package io.hashimati.commands;


import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.domains.URL;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import jakarta.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.services.TemplatesService.*;
import static io.micronaut.http.HttpMethod.GET;

@Command(name = "configure-metrics", aliases = {"metrics"}, description = "To configure Metrics Registries.")
public class ConfigureMetricsCommand implements Callable<Integer> {
    public static ConfigurationInfo configurationInfo;
    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
    private String path;

    public static ProjectInfo projectInfo;
    private MicronautProjectValidator projectValidator = new MicronautProjectValidator();




    @Inject
    TemplatesService templatesService;

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
        projectInfo
                =  projectValidator.getProjectInfo(path);
         HashMap<String, Feature> features  = FeaturesFactory.features(projectInfo);
         AnsiConsole.systemInstall();
        AnsiConsole.systemInstall();



        File configurationFile = new File(ConfigurationInfo.getConfigurationFileName(path));
        if(!configurationFile.exists()){
            PromptGui.printlnWarning("run \"configure\" command first!");
            return 0;
        }
        configurationInfo =  ConfigurationInfo.fromFile(configurationFile);


          ListResult registryOption = PromptGui.createListPrompt("registry", "Select micrometer registry: ", "Influxdb", "prometheus", "graphite", "statsd");

        String registry  = registryOption.getSelectedId();

        if(!projectInfo.getFeatures().contains( "micrometer")){
        projectInfo.getFeatures().addAll(Arrays.asList(
                "management",
                "micrometer"//,
                   //    "micrometer-graphite",
                //"micrometer-statsd"
        ));
        configurationInfo.setMicrometer(true);
        try {
            MicronautProjectValidator.addDependency(path,features.get("management"));
            MicronautProjectValidator.addDependency(path,features.get("micrometer"));
            MicronautProjectValidator.appendToProperties(path,templatesService.loadTemplateContent
                    (templatesService.getMicrometersTemplates().get(MICROMETERS_yml)));

        } catch (GradleReaderException e) {
            e.printStackTrace();
        }}

        if(registry.equalsIgnoreCase("prometheus"))
        {
            configurationInfo.setPrometheus(true);
            configurationInfo.getUrls().add(new URL(){{
                setMethod(GET);
                setScope("/metrics");
                setUrl("/metrics/prometheus");
            }});

            if(configurationInfo.isPrometheus()){
                PromptGui.printlnWarning("\"Prometheus\" is already configured!");

                configurationInfo.writeToFile(path);
                return 0;
            }
            if(!projectInfo.getFeatures().contains("micrometer-prometheus"))
            {
                projectInfo.getFeatures().addAll(Arrays.asList(

                    "micrometer-prometheus"
//                        "micrometer-graphite",
//                        "micrometer-statsd"
                ));
                try {

                    MicronautProjectValidator.addDependency(path,features.get("micrometer-prometheus"));
                } catch (GradleReaderException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                configurationInfo.setPrometheus(true);
                boolean result = configurationInfo.writeToFile(path);
                return result? 1:0;
            }

//                MicronautProjectValidator.addDependency(features.get("micrometer-graphite"));
//                MicronautProjectValidator.addDependency(features.get("micrometer-statsd"));



            MicronautProjectValidator.appendToProperties(path, templatesService.loadTemplateContent
                    (templatesService.getMicrometersTemplates().get(PROMETHEUS_yml)));


            configurationInfo.setPrometheus(true);
            projectInfo.dumpToFile(path);


            //generating prometheus.yml with micronaut job configuration. 
            String prometheusJobConfig = templatesService.loadTemplateContent(templatesService.getMicrometersTemplates().get(PROMETHEUS_JOB_YML));
            GeneratorUtils.createFile(path + "/prometheus.yml", prometheusJobConfig);
        }
        else if(registry.equalsIgnoreCase("influxdb"))
        {
            if(configurationInfo.isInflux()){
                PromptGui.printlnWarning("\"influxdb\" is already configured!");
                return 0;
            }
            String org = PromptGui.inputText("org", "Enter the \"org\": ", "org").getInput();
            String bucket = PromptGui.inputText("bucket", "Enter the bucket:", "bucket").getInput();
            String token = PromptGui.inputText("token", "Enter the token: ", "secret").getInput();

            if(!projectInfo.getFeatures().contains("micrometer-influx")) {
                projectInfo.getFeatures().addAll(Arrays.asList(
                        "micrometer-influx"
                ));
                try {
                    MicronautProjectValidator.addDependency(path,features.get("micrometer-influx"));
                } catch (GradleReaderException e) {
                    e.printStackTrace();
                }
            }
            else{
                configurationInfo.setInflux(true);
                boolean result = configurationInfo.writeToFile(path);
                return result? 1:0;
            }

            String influxTemplate = templatesService.loadTemplateContent
                    (templatesService.getMicrometersTemplates().get(INFLUX_yml));

            MicronautProjectValidator.appendToProperties(path, GeneratorUtils.generateFromTemplate(influxTemplate, new HashMap<String, String>(){{
                put("org", org);
                put("bucket", bucket);
                put("token", token);
            }}));
            configurationInfo.setInflux(true);
           projectInfo.dumpToFile(path);

        }
        else if(registry.equalsIgnoreCase("graphite"))
        {
            projectInfo.getFeatures().addAll(Arrays.asList(

                        "micrometer-graphite"
//                        "micrometer-statsd"
            ));
            try {

                MicronautProjectValidator.addDependency(path,features.get("micrometer-graphite"));
            } catch (GradleReaderException e) {
                e.printStackTrace();
            }
            MicronautProjectValidator.appendToProperties(path, templatesService.loadTemplateContent
                    (templatesService.getMicrometersTemplates().get(GRAPHITE_yml)));


            configurationInfo.setGraphite(true);
            projectInfo.dumpToFile(path);
        }
        else if(registry.equalsIgnoreCase("statsd"))
        {
            projectInfo.getFeatures().addAll(Arrays.asList(

                    "micrometer-statsd"
//                        "micrometer-statsd"
            ));
            try {

                MicronautProjectValidator.addDependency(path,features.get("micrometer-statsd"));
            } catch (GradleReaderException e) {
                e.printStackTrace();
            }
            MicronautProjectValidator.appendToProperties(path, templatesService.loadTemplateContent
                    (templatesService.getMicrometersTemplates().get(STATSD_yml)));


            configurationInfo.setStatsd(true);
            projectInfo.dumpToFile(path);
        }
        boolean result = configurationInfo.writeToFile(path);
        return result? 1:0;
    }
}
