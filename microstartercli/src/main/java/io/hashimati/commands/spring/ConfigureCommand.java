package io.hashimati.commands.spring;

import io.hashimati.microcli.spring.services.ConfigurationService;
import picocli.CommandLine;

import jakarta.inject.Inject;
import java.util.concurrent.Callable;


@CommandLine.Command(name = "configure", description = "Configure the project")
public class ConfigureCommand implements Callable<Integer> {


    @Inject
    private ConfigurationService configurationService;

    @CommandLine.Option(names = {"-path", "--path"}, description = "The path to the project")
    private String path;
    @Override
    public Integer call() throws Exception {
        return configurationService.configure(path);

    }
}
