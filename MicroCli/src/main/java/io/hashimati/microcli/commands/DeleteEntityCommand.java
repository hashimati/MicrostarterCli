package io.hashimati.microcli.commands;

import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.utils.PromptGui.*;

@Command(name = "delete-entity", aliases ={"delEntity", "delentity"}, description = "To delete existing entity")
public class DeleteEntityCommand implements Callable<Integer> {


    @CommandLine.Option(names={"-e", "--entity"})
    private String entityName;

    private ConfigurationInfo configurationInfo;
    @Override
    public Integer call() throws Exception {
        configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));
        Optional<Entity> entityOptional =configurationInfo
                .getEntities()
                .stream()
                .filter(x->x.getName().equals(entityName))
                .findFirst();


        if(entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            String path = System.getProperty("user.dir") + "/src/main/" + configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase() + "/" +
                    GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage())
                    + "/";


            //delete Client
            GeneratorUtils.deleteFile(path+"client/"+ entityName+"Client"+GeneratorUtils.srcFileExtension(configurationInfo.getProjectInfo().getSourceLanguage()));

            //delete controller
            GeneratorUtils.deleteFile(path+"controllers/"+ entityName+"Controller"+GeneratorUtils.srcFileExtension(configurationInfo.getProjectInfo().getSourceLanguage()));

            //delete service
            GeneratorUtils.deleteFile(path+"services/"+ entityName+"Service"+GeneratorUtils.srcFileExtension(configurationInfo.getProjectInfo().getSourceLanguage()));

            //delete repository
            GeneratorUtils.deleteFile(path+"repositories/"+ entityName+"Repository"+GeneratorUtils.srcFileExtension(configurationInfo.getProjectInfo().getSourceLanguage()));

            //delete entity file
            GeneratorUtils.deleteFile(path+"domains/"+ entityName+GeneratorUtils.srcFileExtension(configurationInfo.getProjectInfo().getSourceLanguage()));
;
            configurationInfo.getEntities().remove(entity);

            configurationInfo.writeToFile();
            printlnSuccess("The job is completed");
            setToDefault();
        }
        else {
            printlnErr(entityName+ " isn't exist!");
            setToDefault();
        }
        System.gc();
        return 0;
    }
}
