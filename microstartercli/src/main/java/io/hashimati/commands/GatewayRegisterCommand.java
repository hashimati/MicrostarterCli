package io.hashimati.commands;

import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.GatewayConfig;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import io.micronaut.core.naming.NameUtils;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.YES;
import static org.fusesource.jansi.Ansi.ansi;


@CommandLine.Command(name = "register", description = "To configure the gateway to registered services' endpoint")
public class GatewayRegisterCommand implements Callable<Integer> {


    public static final String SERVICE_CONFIG = "---\nspring:\n" +
            "  cloud:\n" +
            "    gateway:\n" +
            "      routes:\n" ;



    @CommandLine.Option(names = {"-d", "--service-directory"}, description = "The registered services' directory")
    private String servicePath;

    @Override
    public Integer call() throws Exception {

        AnsiConsole.systemInstall();
        ansi().eraseScreen();


        GatewayConfig gatewayConfig = GatewayConfig.readFromFile(GeneratorUtils.getCurrentWorkingPath());


        if(servicePath == null)
        {
            var serviceIdRead = PromptGui.inputText("serviceId", "Please enter the service id: ","service");
            String serviceId = serviceIdRead.getInput();
            var serviceUrlRead = PromptGui.inputText("serviceUrl", "Please enter the service name:", "myApp");
            String serviceUrl = "lb://"+NameUtils.hyphenate(serviceUrlRead.getInput(), true);
            ArrayList<String> paths = new ArrayList<>();
            boolean enteringPath = false;
            do{
                String path = PromptGui.inputText("path", "Please enter the path: ", "/api/v1/{entity}/**").getInput();
                paths.add(path);
                enteringPath = PromptGui.createConfirmResult("enteringPath", "Do you want to enter another path? ", NO).getConfirmed() == YES;
            }while(enteringPath);


            gatewayConfig.getRoutes().add(new GatewayConfig.Route(){{
                setId(serviceId);
                setUri(serviceUrl);
                setPaths(paths);
            }});
            String newConfiguration = SERVICE_CONFIG + gatewayConfig.getRoutes().stream().map(r->r.toProperties())
                    .collect(Collectors.joining("\n"));

            String currentDir = System.getProperty("user.dir");
            if(gatewayConfig.getRoutesConfiguration() == null || gatewayConfig.getRoutesConfiguration().isBlank()) {
                MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath(), newConfiguration);
                gatewayConfig.setRoutesConfiguration(newConfiguration);
            }
            else {
                String applicationYml = GeneratorUtils.getFileContent(new File(GeneratorUtils.getCurrentWorkingPath() + "/src/main/resources/application.yml"))
                        .replace(gatewayConfig.getRoutesConfiguration(), newConfiguration);
                GeneratorUtils.dumpContentToFile(GeneratorUtils.getCurrentWorkingPath() + "/src/main/resources/application.yml",applicationYml);
                gatewayConfig.setRoutesConfiguration(newConfiguration);
            }

            gatewayConfig.writeToFile(GeneratorUtils.getCurrentWorkingPath());
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
            String serviceUrl = "lb://"+NameUtils.hyphenate(configurationInfo.getAppName(), true);
            ArrayList<String> paths = new ArrayList<>();
//            paths.add("/**");
            paths.addAll(configurationInfo.getEntities().stream().map(e->"/api/"+ e.getName()).collect(Collectors.toSet()));

            gatewayConfig.getRoutes().add(new GatewayConfig.Route(){{
                setId(serviceId);
                setUri(serviceUrl);
                setPaths(paths);
            }});
            String newConfiguration = SERVICE_CONFIG + gatewayConfig.getRoutes().stream().map(r->r.toProperties())
                    .collect(Collectors.joining("\n"));

            String currentDir = System.getProperty("user.dir");
            if(gatewayConfig.getRoutesConfiguration() == null || gatewayConfig.getRoutesConfiguration().isBlank()) {
                MicronautProjectValidator.appendToProperties(GeneratorUtils.getCurrentWorkingPath(), newConfiguration);
                gatewayConfig.setRoutesConfiguration(newConfiguration);
            }
            else {
                String applicationYml = GeneratorUtils.getFileContent(new File(GeneratorUtils.getCurrentWorkingPath() + "/src/main/resources/application.yml"))
                        .replace(gatewayConfig.getRoutesConfiguration(), newConfiguration);
                GeneratorUtils.dumpContentToFile(GeneratorUtils.getCurrentWorkingPath() + "/src/main/resources/application.yml",applicationYml);
                gatewayConfig.setRoutesConfiguration(newConfiguration);
            }
            gatewayConfig.writeToFile(GeneratorUtils.getCurrentWorkingPath());
        }
        return 0;
    }
}
