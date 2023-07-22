package io.hashimati.commands.spring;


import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.services.FlyWayGenerator;
import io.hashimati.microcli.services.LiquibaseGenerator;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine;

import jakarta.inject.Inject;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "create-entity", aliases ={"entity, e"}, description = "Create a Spring Entity")
public class CreateEntityCommand implements Callable<Integer> {
    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
    private String path;
    @CommandLine.Option(names = {"-n", "--name"}, description = "The name of the entity")
    private String name;

    @CommandLine.Option(names = {"-gl", "--graphql"}, description = "To generate graphql endpoints")
    private boolean graphql;
    @CommandLine.Option(names = {"--record", "-r"},description = "To declare an entity as Java Records.")
    private boolean record;
    private TemplatesService templatesService = new TemplatesService() ;

    @CommandLine.Option(names = {"--collection-name", "-c"}, description = "Entity's collection/table name")
    private String collectionName;
    private ConfigurationInfo configurationInfo;

    @CommandLine.Option(names = {"--no-endpoint"}, description = "To prevent generating a controller for the entity.")
    private boolean noEndpoint;

    @Inject
    private LiquibaseGenerator liquibaseGenerator;

    @Inject
    private FlyWayGenerator flyWayGenerator;


    @Override
    public Integer call() throws Exception {
        templatesService.loadTemplates(null);

        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        if(name == null)
        {
            name = PromptGui.inputText("name", "The name of the entity", "ENtity").getInput();
        }


        return 0;
    }
}
