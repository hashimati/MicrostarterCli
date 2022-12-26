package io.hashimati.microcli.commands;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.YES;


@CommandLine.Command(name = "register", description = "To configure the gateway to registered services' endpoint")
public class GatewayRegisterCommand implements Callable<Integer> {


    public static final String SERVICE_CONFIG = "---\nspring:\n" +
            "  cloud:\n" +
            "    gateway:\n" +
            "      routes:\n" +
            "        - id: ${id}\n" +
            "          uri: ${uri}\n" +
            "          predicates:\n" +
            "            - Path=${path}\n" +
            "${paths}";


    @CommandLine.Option(names = {"-d", "--service-directory"}, description = "The registered services' directory")
    private String servicePath;

    @Override
    public Integer call() throws Exception {

        if(servicePath == null)
        {
            String serviceId = PromptGui.inputText("serviceId", "Please enter the service id: ","service-id").getInput();
            String serviceUrl = PromptGui.inputText("serviceUrl", "Please enter the service url: ", "lb://serviceid").getInput();

            ArrayList<String> paths = new ArrayList<>();
            boolean enteringPath = false;
            do{
                String path = PromptGui.inputText("path", "Please enter the path: ", "/path").getInput();
                paths.add(path);
                enteringPath = PromptGui.createConfirmResult("enteringPath", "Do you want to enter another path? ", NO).getConfirmed() == YES;
            }while(enteringPath);
            HashMap<String, String> binder = new HashMap<>();
            binder.put("id", serviceId);
            binder.put("uri", serviceUrl);
            binder.put("path", paths.get(0));
            paths.remove(0);
            if(!paths.isEmpty())
            {
                String pathsString = paths.stream().reduce("", (a, b)-> a + "            - Path=" + b + "\n");
                binder.put("paths", pathsString);
            }
            else
            {
                binder.put("paths", "");
            }


            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir,
                    GeneratorUtils.generateFromTemplate(SERVICE_CONFIG, binder));


        }
        else{

            File serviceDir = new File(servicePath);
            if(!serviceDir.exists())
            {
                PromptGui.printlnErr("The service directory does not exist");
                return 1;
            }
            if(!serviceDir.isDirectory())
            {
                PromptGui.printlnErr("The service directory is not a directory");
                return 1;
            }
            if(!serviceDir.canRead())
            {
                PromptGui.printlnErr("The service directory is not readable");
                return 1;
            }
            File config = new File(serviceDir, "MicroCliConfig.json");
            if(!config.exists())
            {
                PromptGui.printlnErr("The service directory does not contain a MicroCliConfig.json file");
                return 1;
            }
            if(!config.canRead())
            {
                PromptGui.printlnErr("The MicroCliConfig.json file is not readable");
                return 1;
            }
            ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(config);
            if(configurationInfo == null)
            {
                PromptGui.printlnErr("The MicroCliConfig.json file is not readable");
                return 1;
            }
            String serviceId  = configurationInfo.getServiceId();
            String serviceUrl = "lb:/"+configurationInfo.getServiceId().replace("-", "");
            ArrayList<String> paths = new ArrayList<>();
            paths.add("/");
            paths.addAll(configurationInfo.getEntities().stream().map(e->"/api/"+ e.getName()).collect(Collectors.toSet()));

            HashMap<String, String> binder = new HashMap<>();
            binder.put("id", serviceId);
            binder.put("uri", serviceUrl);
            binder.put("path", paths.get(0));
            paths.remove(0);
            if(!paths.isEmpty())
            {
                String pathsString = paths.stream().reduce("", (a, b)-> a + "            - Path=" + b + "\n");
                binder.put("paths", pathsString);
            }
            else
            {
                binder.put("paths", "");
            }
            String currentDir = System.getProperty("user.dir");
            MicronautProjectValidator.appendToProperties(currentDir,
                    GeneratorUtils.generateFromTemplate(SERVICE_CONFIG, binder));


        }
        return 0;
    }
}
