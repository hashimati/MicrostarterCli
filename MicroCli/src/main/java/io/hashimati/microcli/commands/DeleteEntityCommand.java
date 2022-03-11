package io.hashimati.microcli.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import groovy.lang.Tuple2;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.services.LiquibaseGenerator;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.utils.PromptGui.*;

@Command(name = "delete-entity", aliases ={"delEntity", "delentity"}, description = "To delete an entity")
public class DeleteEntityCommand implements Callable<Integer> {

    @CommandLine.Option(names={"-e", "--entity"})
    private String entityName;

    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
    private String path;
    @Inject
    private LiquibaseGenerator liquibaseGenerator;
    private ConfigurationInfo configurationInfo;
    @Override
    public Integer call() throws Exception {
        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName(path)));
        Optional<Entity> entityOptional =configurationInfo
                .getEntities()
                .stream()
                .filter(x->x.getName().equals(entityName))
                .findFirst();


        if(entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            String path = this.path + "/src/main/" + configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase() + "/" +
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

            if(Arrays.asList("jpa", "jdbc").contains(configurationInfo.getDataBackendRun().toLowerCase()))
            {
                if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase")){

                    Tuple2<String, String> changeLog = liquibaseGenerator.generateCatalog(path);
                    GeneratorUtils.createFile(changeLog.getV1(), changeLog.getV2());


                    HashMap<String, String> mapper;
                    switch (configurationInfo.getDatabaseType())
                    {
                        case "oracle":
                            mapper = DataTypeMapper.oracleMapper;
                            break;
                        case "sqlserver":
                            mapper = DataTypeMapper.mssqlMapper;
                            break;
                        case "mysql":
                        case "mariadb":
                            mapper = DataTypeMapper.mysqlMapper;
                            break;
                        case "h2":
                            mapper= DataTypeMapper.dialectMapper;
                            break;
                        case "postgres":
                        case "postgressql":
                            mapper = DataTypeMapper.postgresMapper;
                            break;
                        default:
                            mapper = DataTypeMapper.mysqlMapper;
                            break;

                    }
                    configurationInfo.setLiquibaseSequence(configurationInfo.getLiquibaseSequence()+1);
                    entity.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                    Tuple2<String, String> addColumns = liquibaseGenerator.generateDropTableChangeSet(path,entity, mapper, configurationInfo.getLiquibaseSequence() );
                    GeneratorUtils.createFile(addColumns.getV1(), addColumns.getV2());
                }
            }
            configurationInfo.getEntities().remove(entity);
            configurationInfo.writeToFile(path);
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
