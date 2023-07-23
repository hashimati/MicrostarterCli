package io.hashimati.microcli.commands;


import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "create-configuration", aliases = {"configuration"}, description = "To create configuration component.")
public class CreateConfigurationCommand implements Callable<Integer> {
    @Option(names = "--path", description = "To specify the working directory.")
    private String path;
    @Option(names = {"--name", "-n"}, description = "Name of the configuration")
    private String name;

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
        System.out.println("To be implemented");

        return null;
    }
}
