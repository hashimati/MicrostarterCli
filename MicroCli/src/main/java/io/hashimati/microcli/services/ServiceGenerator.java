package io.hashimati.microcli.services;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ListResult;
import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.lang.parsers.engines.ValidationParser;
import io.hashimati.lang.syntax.EntitySyntax;
import io.hashimati.lang.syntax.EnumSyntax;
import io.hashimati.lang.syntax.ServiceSyntax;
import io.hashimati.microcli.client.MicronautLaunchClient;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.*;
import io.hashimati.microcli.utils.*;
import io.micronaut.core.naming.NameUtils;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.YES;
import static io.hashimati.microcli.constants.ProjectConstants.AnnotationTypes.*;
import static io.hashimati.microcli.constants.ProjectConstants.DaoConstants.*;
import static io.hashimati.microcli.constants.ProjectConstants.DatabasesConstants.*;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.GROOVY_LANG;
import static io.hashimati.microcli.constants.ProjectConstants.Metrics.*;
import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENTITY_PATH;
import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENUMS;
import static io.hashimati.microcli.constants.ProjectConstants.Tracing.JAEGER;
import static io.hashimati.microcli.constants.ProjectConstants.Tracing.ZIPKIN;
import static io.hashimati.microcli.services.TemplatesService.*;
import static io.hashimati.microcli.services.TemplatesService.PROMETHEUS_yml;
import static io.hashimati.microcli.utils.GeneratorUtils.altValue;
import static io.hashimati.microcli.utils.PromptGui.*;
import static io.micronaut.http.HttpMethod.*;
import static io.micronaut.http.HttpMethod.DELETE;

@Singleton
public class ServiceGenerator {

    @Inject
    private MicronautLaunchClient micronautLaunchClient;
    private final TemplatesService templatesService = new TemplatesService() ;
    public static ProjectInfo projectInfo;
    private final MicronautProjectValidator projectValidator = new MicronautProjectValidator();
    private HashMap<String, Feature> features; //  =FeaturesFactory.features(projectInfo);

    @Inject
    private MicronautEntityGenerator micronautEntityGenerator;
    @Inject
    private LiquibaseGenerator liquibaseGenerator;

    @Inject
    private FlyWayGenerator flyWayGenerator;

    public Integer initiateService(ServiceSyntax serviceSyntax) throws Exception, GradleReaderException {


        String projectFilePath = GeneratorUtils.getCurrentWorkingPath() + "/" + serviceSyntax.getName() + ".zip";


        //generating Project
        byte[] projectZipFile = micronautLaunchClient.generateProject("default", GeneratorUtils.altValue(altValue(serviceSyntax.getPackage(), "io.demo") + "." + serviceSyntax.getName(), "io.demo." + serviceSyntax.getName()), GeneratorUtils.altValue(serviceSyntax.getLanguage(), "JAVA").toUpperCase(), GeneratorUtils.altValue(serviceSyntax.getBuild(), "GRADLE").toUpperCase(), "JUNIT", "JDK_11", new ArrayList<>());

        if (projectZipFile == null) {
            PromptGui.printlnErr("Failed to generate " + serviceSyntax.getName() + " project.");
            return 0;
        }

        boolean createFileStatus = GeneratorUtils.writeBytesToFile(projectFilePath, projectZipFile);
        if (createFileStatus)
            PromptGui.printlnSuccess("Complete downloading " + serviceSyntax.getName() + ".zip. ");
        boolean extract = GeneratorUtils.unzipFile(projectFilePath, GeneratorUtils.getCurrentWorkingPath());
        if (extract)
            PromptGui.printlnSuccess("Successfully created \"" + serviceSyntax.getName() + "\" project folder!");
        boolean deleteFile = GeneratorUtils.deleteFile(projectFilePath);
        //end generating project

        String workingPath = GeneratorUtils.getCurrentWorkingPath() + "/" + serviceSyntax.getName();
        //project configuration:
        ConfigurationInfo configurationInfo = readConfigurationFromServiceSyntax(serviceSyntax);
        Integer configureResult = configureService(configurationInfo, workingPath);
        //end project configuration.

        if(serviceSyntax.getEnums() != null && !serviceSyntax.getEnums().isEmpty()){
            List<EnumClass> enums = serviceSyntax.getEnums().stream().map(e->readEnumFromEnumSyntax(configurationInfo, e)).collect(Collectors.toList());

            for(EnumClass enumClass: enums)
            {
                boolean isExist = configurationInfo.getEnums().stream().anyMatch(x->x.getName().equals(enumClass.getName()));
                if(isExist)
                {
                    printlnWarning("Warning: "+ enumClass.getName() + " is already exist. ");
                    setToDefault();
                    for(EnumClass e : configurationInfo.getEnums())
                    {
                        if(e.getName().equals(enumClass.getName())) {
                            e.getValues().addAll(enumClass.getValues());
                            enumClass.getValues().addAll(e.getValues());
                            break;
                        }
                    }
                }
                String enumFilePath = GeneratorUtils.generateFromTemplate(ENUMS, new HashMap<String, String>(){{
                    put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                    put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                }});

                String extension = ".java";
                switch (configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase())
                {
                    case GROOVY_LANG:
                        extension= ".groovy";
                        break;
                    case KOTLIN_LANG:
                        extension = ".kt";
                        break;
                    default:
                        extension = ".java";
                        break;
                }
                String outPutPath = workingPath+enumFilePath+"/"+enumClass.getName()+extension;


                GeneratorUtils.createFile(outPutPath.replace("\\", "/"), micronautEntityGenerator.generateEnum(enumClass, configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase()));

                //                System.out.println(micronautEntityGenerator.generateEnum(enumClass, configurationInfo.getProjectInfo().getSourceLanguage()));
//                System.out.println(micronautEntityGenerator.generateEnum(enumClass, "groovy"));
//                System.out.println(micronautEntityGenerator.generateEnum(enumClass, "kotlin"));
                if(!isExist)
                    configurationInfo.getEnums().add(enumClass);
                configurationInfo.writeToFile(workingPath);


                //if graphql is supported
                if(configurationInfo.isGraphQlSupport())
                {
                    String enumGraphQlFilename = new StringBuilder().append(workingPath).append("/src/main/resources/").append(enumClass.getName()).append(".graphqls").toString();
                    String enumContent =micronautEntityGenerator.generateEnumGraphQL(enumClass);
                    GeneratorUtils.createFile(enumGraphQlFilename, enumContent);
                }


            }
        }

        //--entity generating Entity
        if(serviceSyntax.getEntities() != null && !serviceSyntax.getEntities().isEmpty()){
            List<Entity> entities = serviceSyntax.getEntities().stream().map(e -> readyEntityFromEntitySyntax(configurationInfo, e)).collect(Collectors.toList());
            configurationInfo.getEntities().addAll(entities);
            configurationInfo.writeToFile(workingPath);


            String lang =  configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase();

            String extension =GeneratorUtils.getSourceFileExtension(lang);

            for(Entity entity : entities)
            {



                String entityFileContent  =micronautEntityGenerator.generateEntity(entity, configurationInfo.getRelations(),lang).replaceAll("\n\n\n", "\n");

                String entityPath = GeneratorUtils.generateFromTemplate(ENTITY_PATH, new HashMap<String, String>(){{
                    put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                    put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                }});
                GeneratorUtils.createFile(workingPath+"/"+entityPath+ "/"+entity.getName()+extension, entityFileContent);

                if(!entity.isNoEndpoints()){

                    if(configurationInfo.getDatabaseType().equalsIgnoreCase(MicroStream_Embedded_Storage)) {
                        entity.setMicrostreamRoot( entity.getName());
                     //   entity.setMicrostreamPath(inputText("directory", "Enter the storage directory: ", "your-path").getInput());
                        //entity.setMicrostreamPath();
                        entity.setMicrostreamRootClass( new StringBuilder().append(entity.getMicrostreamPackage()).append(".").append(entity.getName()).append("Data").toString());
                        entity.setMicrostreamChannelCount((short) 4);
                        String microstreamPropertiesTemplate = templatesService.loadTemplateContent
                                (templatesService.getProperties().get(MICROSTREAM_YML));


                        String microstreamProperties = new SimpleTemplateEngine().createTemplate(microstreamPropertiesTemplate).make(new HashMap<String, Object>(){{
                            put("root", entity.getMicrostreamRoot());
                            put("storageDirectory",entity.getMicrostreamPath());
                            put("rootClass", entity.getMicrostreamRootClass());
                            put("count", entity.getMicrostreamChannelCount());
                        }}).toString();
                        MicronautProjectValidator.appendToProperties(workingPath, microstreamProperties);


                        //===============


                    }

                    //generate Repository
                    String repositoryFileContent = micronautEntityGenerator.generateRepository(entity, lang, null);
                    String repoPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<String, String>(){{
                        put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                        put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                    }});
                    GeneratorUtils.createFile(workingPath+"/"+repoPath+ "/"+entity.getName()+"Repository"+extension, repositoryFileContent);
                    if(configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb") && !configurationInfo.isMnData())
                    {
                        templatesService.loadTemplates(null);
                        String mongoDbDatabasePropertiesTemplate = templatesService.loadTemplateContent
                                (templatesService.getProperties().get(MDB_COLLECTION_YML));
                        String mongoDbDatabaseProperties = GeneratorUtils.generateFromTemplate(mongoDbDatabasePropertiesTemplate, new HashMap<String, String> (){{
                            put("collection", entity.getName());
                            put("collections", entity.getCollectionName());
                        }});
                        MicronautProjectValidator.appendToProperties(workingPath, mongoDbDatabaseProperties);
                    }

                    //generate services

                    String serviceFileContent = micronautEntityGenerator.generateService(entity, lang);
                    String servicePath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.SERVICES_PATH, new HashMap<String, String>(){{
                        put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                        put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                    }});

                    GeneratorUtils.createFile(workingPath+servicePath + "/"+entity.getName()+"Service"+
                            extension, serviceFileContent);

                    if(MicronautProjectValidator.isApplication(workingPath))
                    {                //============================

                        String controllerFileContent = micronautEntityGenerator.generateController(entity, lang);

                        entity.getUrls().add(new URL(){{
                            setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/get");
                            setMethod(GET);
                        }});

                        entity.getUrls().add(new URL(){{
                            setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/findAll");
                            setMethod(GET);
                        }});

                        entity.getUrls().add(new URL(){{
                            setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/save");
                            setMethod(POST);
                        }});

                        entity.getUrls().add(new URL(){{
                            setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/update");
                            setMethod(PUT);
                        }});
                        entity.getUrls().add(new URL(){{
                            setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/delete/{id}");
                            setMethod(DELETE);
                        }});

                        entity.getAttributes().stream()
                                .forEach(x->{
                                    if(x.isFindByMethod())
                                    {
                                        entity.getUrls().add(new URL(){{
                                            setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/findBy"+NameUtils.capitalize(x.getName()));
                                            setMethod(DELETE);
                                        }});
                                    }
                                    if(x.isFindAllMethod())
                                    {
                                        entity.getUrls().add(new URL(){{
                                            setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/findAllBy"+NameUtils.capitalize(x.getName()));
                                            setMethod(DELETE);
                                        }});
                                    }

                                });
                        entity.getUpdateByMethods().keySet().forEach(x->{
                            entity.getUrls().add(new URL(){{
                                setUrl("/api/v1/"+NameUtils.camelCase(entity.getName(), true)+"/updateBy"+NameUtils.capitalize(x));
                                setMethod(DELETE);
                            }});
                        });
                        String controllerPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.CONTROLLER_PATH, new HashMap<String, String>() {{
                            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                            put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                        }});


                        GeneratorUtils.createFile(workingPath + controllerPath + "/" + entity.getName() + "Controller" + extension, controllerFileContent);


                        ////==========
                        String clientFileContent = micronautEntityGenerator.generateClient(entity, lang);

                        if(entity.getDatabaseType().equalsIgnoreCase(MicroStream_Embedded_Storage))
                        {
                            String rootClassContent = micronautEntityGenerator.generateMicrostreamRootDataClass(entity, configurationInfo.getProjectInfo().getSourceLanguage());
                            String root = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.MICROSTREAM_PATH, new HashMap<String, String>(){{
                                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                            }});



                            GeneratorUtils.createFile(workingPath+"/"+root+ "/"+entity.getName()+"Data"+extension, rootClassContent);

                        }

                        String clientPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.REPOSITORY_PATH, new HashMap<String, String>() {{
                            put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                            put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                        }});
                        GeneratorUtils.createFile(workingPath + "/src/main/" + configurationInfo.getProjectInfo().getSourceLanguage() + "/" + GeneratorUtils.packageToPath(entity.getClientPackage()) + "/" + entity.getName() + "Client" + extension, clientFileContent);

                        if (entity.isGraphQl()) {
                            entity.setGraphQl(true);

                            String factoyFileContent = micronautEntityGenerator.generateGraphQLFactory(configurationInfo.getEntities(), lang);

                            String factoryPath = GeneratorUtils.generateFromTemplate(ProjectConstants.PathsTemplate.GRAPHQL_PATH, new HashMap<String, String>() {{
                                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
                            }});
                            GeneratorUtils.createFile(workingPath + factoryPath + "/QueryFactory" + extension, factoyFileContent);


                            String resolverFileContent = micronautEntityGenerator.generateGraphQLResolver(entity, lang);
                            GeneratorUtils.createFile(workingPath + factoryPath + "/" + entity.getName() + "QueryResolver" + extension, resolverFileContent);


                            String entityGraphQlFilename = new StringBuilder().append(workingPath).append("/src/main/resources/").append(entity.getName()).append(".graphqls").toString();
                            String graphQLSchema = micronautEntityGenerator.generateGraphQLSchema(entity);
                            GeneratorUtils.createFile(entityGraphQlFilename, graphQLSchema);


                            String queryGraphQlFilename = new StringBuilder().append(workingPath).append("/src/main/resources/").append("queries.graphqls").toString();
                            String graphQLQuery = micronautEntityGenerator.generateGraphQLQuery(configurationInfo.getEntities());
                            GeneratorUtils.createFile(queryGraphQlFilename, graphQLQuery);

                        }


                        String randromizerFileContent = micronautEntityGenerator.generateRandomizer(entity, lang);
                        GeneratorUtils.createFile(workingPath + "/src/test/" + configurationInfo.getProjectInfo().getSourceLanguage() + "/" + GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage() + ".utils") + "/Randomizer" + extension, randromizerFileContent);

                        //========
                        String controllertest = micronautEntityGenerator.generateTestController(entity, lang, configurationInfo.getProjectInfo().getTestFramework());
                        String langDir = configurationInfo.getProjectInfo().getSourceLanguage();
                        switch (configurationInfo.getProjectInfo().getTestFramework().toLowerCase()) {
                            case "spock":
                                extension = ".groovy";
                                langDir = "groovy";
                                break;
                            case "kotest":
                                extension = ".kt";
                                langDir = "kotlin";
                                break;
                            default:
                                extension = extension;
                                break;
                        }
                        GeneratorUtils.createFile(workingPath + "/src/test/" + langDir + "/" + GeneratorUtils.packageToPath(entity.getRestPackage()) + "/" + entity.getName() + "ControllerTest" + extension, controllertest);
                    }

                    if (Arrays.asList("jpa", "jdbc", "r2dbc").contains(configurationInfo.getDataBackendRun().toLowerCase())) {
                        HashMap<String, String> mapper;
                        switch (configurationInfo.getDatabaseType()) {
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
                                mapper = DataTypeMapper.dialectMapper;
                                break;
                            case "postgres":
                            case "postgressql":
                                mapper = DataTypeMapper.postgresMapper;
                                break;
                            default:
                                mapper = DataTypeMapper.mysqlMapper;
                                break;

                        }

                        if (configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase")) {

                            Tuple2<String, String> changeLog = liquibaseGenerator.generateCatalog(workingPath);
                            GeneratorUtils.createFile(changeLog.getV1(), changeLog.getV2());


                            configurationInfo.setLiquibaseSequence(configurationInfo.getLiquibaseSequence() + 1);
                            entity.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                            Tuple2<String, String> schema = liquibaseGenerator.generateSchema(workingPath,configurationInfo.getEntities(), configurationInfo.getRelations(), mapper, configurationInfo.getLiquibaseSequence());
                            GeneratorUtils.createFile(schema.getV1(), schema.getV2());
                        }
                        if (configurationInfo.getDataMigrationTool().equalsIgnoreCase("flyway")) {
                            configurationInfo.setLiquibaseSequence(configurationInfo.getLiquibaseSequence() + 1);
                            entity.setLiquibaseSequence(configurationInfo.getLiquibaseSequence());
                            Tuple2<String, String> schema = flyWayGenerator.createTable(entity, configurationInfo.getLiquibaseSequence());
                            GeneratorUtils.createFile(schema.getV1(), schema.getV2());
                        }
                    }


                }
            }
        }


        //--endGenerating Entities
        return createFileStatus && extract && deleteFile && (configureResult == 1) ? 1 : 0;

    }
    public ConfigurationInfo readConfigurationFromServiceSyntax(ServiceSyntax serviceSyntax){
        return new ConfigurationInfo(){{
            setAppName(serviceSyntax.getName());
            setPort(Integer.parseInt(altValue(serviceSyntax.getPort(), "8080")));
            setReactiveFramework(altValue(serviceSyntax.getReactive(), "reactor"));
            setGraphQlSupport(serviceSyntax.isGraphql());
            setDatabaseType(altValue(findDatabaseConstantString(serviceSyntax.getDatabase()),ProjectConstants.DatabasesConstants.H2));
            setDatabaseName(altValue(serviceSyntax.getDatabaseName(), serviceSyntax.getName()));

            if(RELATIONAL_DATABASES.stream().map(x->x.toLowerCase()).collect(Collectors.toList()).contains(getDatabaseType().toLowerCase())) {
                setDataBackendRun(altValue(serviceSyntax.getDao(), JDBC));
            }
            else if(getDatabaseType().equalsIgnoreCase(MongoDB))
            {
                setDataBackendRun(altValue(serviceSyntax.getDao(), DATA_MONGODB));
            }
            else if(getDatabaseType().equalsIgnoreCase(MicroStream_Embedded_Storage)){
                setDataBackendRun("microstream");
            }
            setNonBlocking(NON_BLOCKING_DAOS.contains(getDataBackendRun()));
            setDataMigrationTool(altValue(serviceSyntax.getMigrationTool(), "none"));

            if(serviceSyntax.getCache() != null)
            setCaffeine(serviceSyntax.getCache().equalsIgnoreCase("caffeine"));

            setLombok(false);
            setAnnotation(serviceSyntax.getAnnotation());
            if(serviceSyntax.getAnnotation().equalsIgnoreCase(JAXRS)) {
                setJaxRsAnnotation(true);
            } else if(serviceSyntax.getAnnotation().equalsIgnoreCase(SPRING_BOOT))
                setSpringBootAnnotation(true);
            else if(serviceSyntax.getAnnotation().equalsIgnoreCase(MICRONAUT)) {
                setMicronautAnnotation(true);
            }
            else
                setMicronautAnnotation(true);


            if(serviceSyntax.getMetrics() != null)
            {
                setMicrometer(true);
                if(serviceSyntax.getMetrics().equalsIgnoreCase(INFLUXDB)){
                    setInflux(true);
                }
                else    if(serviceSyntax.getMetrics().equalsIgnoreCase(GRAPHITE)){
                    setGraphite(true);
                }
                else if(serviceSyntax.getMetrics().equalsIgnoreCase(STATSD)){
                    setStatsd(true);
                }

            }

            setAwsKey(altValue(serviceSyntax.getAwsKey(), "aws_key"));
            setAwsSecret(altValue(serviceSyntax.getAwsKey(), "aws_secret"));

        }};
    }

    public Integer configureService(ConfigurationInfo configurationInfo, String workingPath) throws IOException, XmlPullParserException, GradleReaderException {



        templatesService.loadTemplates(null);
        projectInfo
                =  MicronautProjectValidator.getProjectInfo(workingPath);


        features  = FeaturesFactory.features(projectInfo);
        if(projectInfo == null || !MicronautProjectValidator.isValidProject(workingPath))
        {
            printlnErr("The current directory is not a directory of a Micronaut Application/Function project");
            System.exit(0);
        }
        else if(projectInfo.getFeatures().contains("properties")){
            printlnErr("MicrostarterCli doesn't support configuration with \".properties\" files.");
            System.exit(0);
        }
        File file = new File(workingPath+"/"+"MicroCliConfig.json");


        configurationInfo.setProjectInfo(projectInfo);

        if(file.isFile() && file.exists())
        {
            configurationInfo =  ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName(workingPath)));
            if(configurationInfo.isConfigured()) return 1; // the project is configured by MicroCli. Then, exit configuration.
        }
        else{

            if(projectInfo.getBuildTool().equalsIgnoreCase("maven"))
            {
                configurationInfo.setAppName(MicronautProjectValidator.getAppNameFromMaven(workingPath));
            }
            else if(projectInfo.getBuildTool().equalsIgnoreCase("gradle"))
            {
                configurationInfo.setAppName(MicronautProjectValidator.getAppNameFromGradle(workingPath));
            }

            configurationInfo.setJavaVersion(MicronautProjectValidator.getJavaVersion(workingPath));
            try
            {
                if(configurationInfo.getPort()< 0 || configurationInfo.getPort() > 65535)
                {
                    configurationInfo.setPort(8080);
                    PromptGui.printlnWarning(configurationInfo.getPort() +" is not valid port number. The port is set to 8080.");
                }
            }
            catch (Exception ex)
            {
                configurationInfo.setPort(8080);
                PromptGui.printlnWarning(configurationInfo.getPort() +" is not valid port number. The port is set to 8080.");
            }
            MicronautProjectValidator.appendToProperties(workingPath,"---\n" +
                    "micronaut.server.port: "+configurationInfo.getPort()+"\n" +
                    "---");

                if(configurationInfo.getReactiveFramework()!= null){

                }
                else if(projectInfo.getFeatures().contains("reactor"))
                    configurationInfo.setReactiveFramework("reactor");
                else if(projectInfo.getFeatures().contains("rxjava2"))
                    configurationInfo.setReactiveFramework("rxjava2");
                else if(projectInfo.getFeatures().contains("rxjava3"))
                    configurationInfo.setReactiveFramework("rxjava3");
            if(configurationInfo.isJaxRsAnnotation()){
                MicronautProjectValidator.addDependency(workingPath, features.get("jax-rs"));
            }


            boolean testWithH2 = false;
            if(!configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb") && !configurationInfo.getDatabaseType().equalsIgnoreCase(MicroStream_Embedded_Storage)) {
                ArrayList<String> options = new ArrayList<String>();
                options.add("JDBC");
                options.add("JPA");
                if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                    options.add("GORM");
                if(!configurationInfo.getDatabaseType().equalsIgnoreCase("oracle"))
                    options.add("R2DBC");
                ListResult databaseBackend = PromptGui.createListPrompt("databaseBackend", "Select Database Backend: ", options.toArray(new String[options.size()]));
                configurationInfo.setDataBackendRun(databaseBackend.getSelectedId());
                configurationInfo.setMnData(!configurationInfo.getDataBackendRun().equalsIgnoreCase("GORM"));


                ListResult dataMigrationTool = PromptGui.createListPrompt("databaseMigration", "Select Data Migration: ", "liquibase", "Flyway", "none");
                configurationInfo.setDataMigrationTool(dataMigrationTool.getSelectedId());


                Feature databaseFeature = null;
                switch (databaseBackend.getSelectedId())
                {

                    case "GORM":
                        configurationInfo.setGorm(true);
                        MicronautProjectValidator.addDependency(workingPath,features.get("tomcat-jdbc"));
                        projectInfo.getFeatures().add("tomcat-jdbc");
                        databaseFeature = features.get("hibernate-gorm");

                        projectInfo.getFeatures().add("hibernate-gorm");
                        break;
                    case "R2DBC":
                        configurationInfo.setNonBlocking(true);

                        //The below three lines to be deleted.
//                        MicronautProjectValidator.addDependency(workingPath,features.get("r2dbc"));
//                        projectInfo.getFeatures().add("r2dbc");
//                        projectInfo.getFeatures().add("reactor");
                        databaseFeature = features.get("data-r2dbc");
                        projectInfo.getFeatures().add("r2dbc-data");
                        break;
                    case "JPA" :

                        if(!projectInfo.getFeatures().contains("data-jpa")) {
                            projectInfo.getFeatures().add("data-jpa");
                            databaseFeature = features.get("data-jpa");
                        }
                        break;
                    case "JDBC":
                        if(!projectInfo.getFeatures().contains("data-jdbc")) {
                            projectInfo.getFeatures().add("data-jdbc");
                            databaseFeature = features.get("data-jdbc");
                        }
                        break;
                }





                //adding database:
                String databasetype = configurationInfo.getDatabaseType().toLowerCase();
                if(!projectInfo.getFeatures().contains(databasetype) )
                {


                    projectInfo.getFeatures().add(databasetype);

                    if(testWithH2){
                        Feature dbFeature = features.get(databasetype);
                        dbFeature.setTestGradle("");
                        dbFeature.setTestMaven(null);
                        MicronautProjectValidator.addDependency(workingPath,dbFeature);

                        MicronautProjectValidator.addDependency(workingPath,new Feature(){{
                            setGradle(features.get("h2").getTestGradle());
                            getMaven().add(features.get("h2").getTestMaven());
                        }});
                        if(configurationInfo.getDataBackendRun().equalsIgnoreCase("r2dbc"))
                        {
                            MicronautProjectValidator.addDependency(workingPath,new Feature(){{
                                setGradle(features.get("h2").getTestRdbcGradle());
                                getMaven().add(features.get("h2").getTestRdbcMaven());

                            }});
                        }
                    }
                    else {
                        MicronautProjectValidator.addDependency(workingPath,
                                features.get(databasetype));
                        if(configurationInfo.getDataBackendRun().equalsIgnoreCase("R2DBC"))
                            MicronautProjectValidator.addR2DBCependency(workingPath,
                                    features.get(databasetype));
                        if(Arrays.asList("sqlserver", "oracle", "mysql", "mariadb").contains(databasetype))
                        {
                            if(!projectInfo.getFeatures().contains("testcontainers")) {

                                projectInfo.getFeatures().add("testcontainers");
                                MicronautProjectValidator.addDependency(workingPath,features.get("testcontainers"));
                                if(projectInfo.getTestFramework().equalsIgnoreCase("spock")){
                                    MicronautProjectValidator.addDependency(workingPath,features.get(("testcontainers-spock")));
                                }
                                else
                                    MicronautProjectValidator.addDependency(workingPath,features.get(("junit-jupiter")));

                            }
                        }
                    }

                    if(configurationInfo.getDataBackendRun().equalsIgnoreCase("jdbc"))
                        MicronautProjectValidator.appendJDBCToProperties(workingPath,databasetype, true, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    else if (configurationInfo.getDataBackendRun().equalsIgnoreCase("jpa") || configurationInfo.getDataBackendRun().equalsIgnoreCase("gorm")) {

                        MicronautProjectValidator.appendJPAToProperties(workingPath,configurationInfo.isGorm()? new StringBuilder().append(databasetype).append("_gorm").toString() : databasetype, true, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    }
                    else if(configurationInfo.getDataBackendRun().equalsIgnoreCase("r2dbc")){
                        MicronautProjectValidator.appendR2DBCToProperties(workingPath,databasetype+"_r2dbc", true, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    }
                    if(!databasetype.equalsIgnoreCase("h2")) {
                        if(configurationInfo.getDataBackendRun().equalsIgnoreCase("r2dbc"))
                            MicronautProjectValidator.appendR2DBCToProperties(workingPath,databasetype + "_r2dbc_test", false, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                        else
                            MicronautProjectValidator.appendJDBCToProperties(workingPath, databasetype + "_test", false, testWithH2, configurationInfo.getDatabaseName(), configurationInfo.getDataMigrationTool());
                    }
                }



                MicronautProjectValidator.addDependency(workingPath,databaseFeature);
                MicronautProjectValidator.addDependency(workingPath,features.get("jdbc-hikari"));

                if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("liquibase"))
                {
                    projectInfo.getFeatures().add("liquibase");
                    MicronautProjectValidator.addDependency(workingPath,features.get("liquibase"));
                    MicronautProjectValidator.appendToProperties(workingPath,templatesService.loadTemplateContent(templatesService.getProperties().get(TemplatesService.LIQUIBASE_yml)));
                    Tuple2<String, String> changeLog = Tuple.tuple(workingPath +"/src/main/resources/db/liquibase-changelog.xml",templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_CATALOG)));

                    GeneratorUtils.createFile(changeLog.getV1(), changeLog.getV2());
                }
                else if(configurationInfo.getDataMigrationTool().equalsIgnoreCase("flyway")){
                    projectInfo.getFeatures().add("flyway");
                    MicronautProjectValidator.addDependency(workingPath,features.get("flyway"));
                    MicronautProjectValidator.appendToProperties(workingPath,templatesService.loadTemplateContent(templatesService.getFlywayTemplates().get(TemplatesService.FLYAWAY_YML)));
                }
                projectInfo.dumpToFile(workingPath);
            }
            else if(configurationInfo.getDatabaseType().equalsIgnoreCase("mongodb")){

                ArrayList<String> options = new ArrayList<String>();
                options.add("data-mongodb");
                options.add("data-mongodb-reactive");
                options.add("mongo-reactive");


                ListResult databaseBackend = PromptGui.createListPrompt("databaseBackend", "Select Database Backend: ", options.toArray(new String[options.size()]));
                configurationInfo.setDataBackendRun(databaseBackend.getSelectedId());

                // configurationInfo.setDataBackendRun("none");
                if(configurationInfo.getDataBackendRun().equalsIgnoreCase("data-mongodb")) {
                    projectInfo.getFeatures().add("data-mongodb");
                    projectInfo.getFeatures().add("mongo-sync");
                    configurationInfo.setDataBackendRun("data-mongodb");
                    MicronautProjectValidator.addDependency(workingPath,features.get("data-mongodb"));
                    MicronautProjectValidator.addDependency(workingPath,features.get("mongo-sync"));
                    configurationInfo.setMnData(true);
                    configurationInfo.setNonBlocking(false);
                }
                else if(configurationInfo.getDataBackendRun().equalsIgnoreCase("data-mongodb-reactive")) {
                    projectInfo.getFeatures().add("data-mongodb-reactive");
                    MicronautProjectValidator.addDependency(workingPath,features.get("data-mongodb-reactive"));
                    MicronautProjectValidator.addDependency(workingPath,features.get("mongo-sync"));
                    configurationInfo.setDataBackendRun("data-mongodb-reactive");
                    configurationInfo.setNonBlocking(true);
                    configurationInfo.setMnData(true);
                }
                else {
                    projectInfo.getFeatures().add("mongo-reactive");
                    configurationInfo.setDataBackendRun("mongoReactive");
                    MicronautProjectValidator.addDependency(workingPath,features.get("mongo-reactive"));
                    configurationInfo.setMnData(false);
                    configurationInfo.setNonBlocking(true);
                }
//                MicronautProjectValidator.addDependency(workingPath,features.get("embed.mongo"));
                projectInfo.getFeatures().add("testcontainers");
                MicronautProjectValidator.addDependency(workingPath,features.get("testcontainers"));
                if(projectInfo.getTestFramework().equalsIgnoreCase("spock")){
                    MicronautProjectValidator.addDependency(workingPath,features.get(("testcontainers-spock")));
                }
                else
                    MicronautProjectValidator.addDependency(workingPath,features.get(("junit-jupiter")));
                //todo add dependencies to build files;
                String mongoProperties = templatesService.loadTemplateContent
                        (templatesService.getProperties().get(TemplatesService.MONGODB_yml));

                //to add the database name if the back is data-mongodb.
                if(configurationInfo.isMnData())
                    mongoProperties =mongoProperties.replace("\n","")+ "/"+configurationInfo.getDatabaseName();


                MicronautProjectValidator.appendToProperties(workingPath,mongoProperties);


                if(!configurationInfo.isMnData()) {
                    String mongoDbDatabasePropertiesTemplate = templatesService.loadTemplateContent
                            (templatesService.getProperties().get(MDB_yml));

                    final ConfigurationInfo cf = configurationInfo;
                    String mongoDbDatabaseProperties = GeneratorUtils.generateFromTemplate(mongoDbDatabasePropertiesTemplate, new HashMap<String, String>() {{
                        put("dbName", cf.getDatabaseName());
                    }});
                    MicronautProjectValidator.appendToProperties(workingPath,mongoDbDatabaseProperties);

                    String mongodbConfigurationTemplate = "";
                    String ext = projectInfo.getSourceLanguage();
                    switch (projectInfo.getSourceLanguage().toLowerCase()) {
                        case JAVA_LANG:
                            mongodbConfigurationTemplate = templatesService.loadTemplateContent(templatesService.getJavaTemplates().get(MONGODB_CONFIGURATION));
                            break;
                        case GROOVY_LANG:
                            mongodbConfigurationTemplate = templatesService.loadTemplateContent(templatesService.getGroovyTemplates().get(MONGODB_CONFIGURATION));
                            break;
                        case KOTLIN_LANG:
                            mongodbConfigurationTemplate = templatesService.loadTemplateContent(templatesService.getKotlinTemplates().get(MONGODB_CONFIGURATION));
                            ext = "kt";
                            break;
                    }
                    String mongoConfigurationContent = GeneratorUtils.generateFromTemplate(mongodbConfigurationTemplate, new HashMap<String, String>() {{
                        put("projectPackage", projectInfo.getDefaultPackage());
                    }});
                    GeneratorUtils.createFile(workingPath + "/src/main/" + projectInfo.getSourceLanguage() + "/" + GeneratorUtils.packageToPath(projectInfo.getDefaultPackage()) + "/config/MongodbConfiguration." + ext, mongoConfigurationContent);
                }
                if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                    if(PromptGui.createConfirmResult("gorm", "Do you want to use GORM?", NO).getConfirmed()== ConfirmChoice.ConfirmationValue.YES)
                    {
                        configurationInfo.setGorm(true);
                        MicronautProjectValidator.addDependency(workingPath,features.get("mongo-gorm"));
                        projectInfo.getFeatures().add("mongo-gorm");
                        configurationInfo.setDataBackendRun("mongoGorm");
                    }
                projectInfo.dumpToFile(workingPath);
            }
            else if(configurationInfo.getDatabaseType().equalsIgnoreCase(MicroStream_Embedded_Storage)){
                projectInfo.getFeatures().add("microstream");
                configurationInfo.setDataBackendRun("microstream");
                MicronautProjectValidator.addDependency(workingPath,features.get("microstream"));
                configurationInfo.setMnData(false);
                configurationInfo.setNonBlocking(false);

            }




            if(configurationInfo.getMessaging()!=null)
            {
                projectInfo.getFeatures().add(configurationInfo.getMessaging());
                MicronautProjectValidator.addDependency(workingPath,features.get(configurationInfo.getMessaging()));
                projectInfo.dumpToFile(workingPath);


                //AddingYaml
                if(!configurationInfo.getMessaging().equalsIgnoreCase("gcp-pubsub")) // current the properties is not added, to be added in the future.
                {
                    templatesService.loadTemplates(null);
                    String messagingProperties = templatesService.loadTemplateContent
                            (templatesService.getProperties().get(configurationInfo.getMessaging().toUpperCase())); /// The index == to featureName.toUppercase
                    MicronautProjectValidator.appendToProperties(workingPath,messagingProperties);
                }
            }

            if(configurationInfo.isCaffeine())
            {
                projectInfo.getFeatures().add("cache-caffeine");
                MicronautProjectValidator.addDependency(workingPath,features.get("cache-caffeine"));
                projectInfo.dumpToFile(workingPath);
            }

            if(configurationInfo.isMicrometer())
            {
                projectInfo.getFeatures().addAll(Arrays.asList(
                        "management",
                        "micrometer"

                ));
                MicronautProjectValidator.addDependency(workingPath,features.get("management"));
                MicronautProjectValidator.addDependency(workingPath,features.get("micrometer"));
                MicronautProjectValidator.appendToProperties(workingPath,templatesService.loadTemplateContent
                        (templatesService.getMicrometersTemplates().get(MICROMETERS_yml)));

                MicronautProjectValidator.appendToProperties(workingPath,templatesService.loadTemplateContent
                        (templatesService.getMicrometersTemplates().get(PROMETHEUS_yml)));

                if(configurationInfo.isPrometheus())
                {
                    configurationInfo.setPrometheus(true);
                    configurationInfo.getUrls().add(new URL(){{
                        setMethod(GET);
                        setScope("/metrics");
                        setUrl("/metrics/prometheus");
                    }});

                    if(configurationInfo.isPrometheus()){
                        PromptGui.printlnWarning("\"Prometheus\" is already configured!");

                        configurationInfo.writeToFile(workingPath);
                        return 0;
                    }
                    if(!projectInfo.getFeatures().contains("micrometer-prometheus"))
                    {
                        projectInfo.getFeatures().addAll(List.of(

                                "micrometer-prometheus"
//                        "micrometer-graphite",
//                        "micrometer-statsd"
                        ));
                        try {

                            MicronautProjectValidator.addDependency(workingPath,features.get("micrometer-prometheus"));
                        } catch (GradleReaderException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        configurationInfo.setPrometheus(true);
                        boolean result = configurationInfo.writeToFile(workingPath);
                        return result? 1:0;
                    }

//                MicronautProjectValidator.addDependency(features.get("micrometer-graphite"));
//                MicronautProjectValidator.addDependency(features.get("micrometer-statsd"));



                    MicronautProjectValidator.appendToProperties(workingPath, templatesService.loadTemplateContent
                            (templatesService.getMicrometersTemplates().get(PROMETHEUS_yml)));


                    configurationInfo.setPrometheus(true);
                    projectInfo.dumpToFile(workingPath);


                    //generating prometheus.yml with micronaut job configuration.
                    String prometheusJobConfig = templatesService.loadTemplateContent(templatesService.getMicrometersTemplates().get(PROMETHEUS_JOB_YML));
                    GeneratorUtils.createFile(workingPath + "/prometheus.yml", prometheusJobConfig);
                }
                else if(configurationInfo.isInflux())
                {
                    if(configurationInfo.isInflux()){
                        PromptGui.printlnWarning("\"influxdb\" is already configured!");
                        return 0;
                    }
                    String org = PromptGui.inputText("org", "Enter the \"org\": ", "org").getInput();
                    String bucket = PromptGui.inputText("bucket", "Enter the bucket:", "bucket").getInput();
                    String token = PromptGui.inputText("token", "Enter the token: ", "secret").getInput();

                    if(!projectInfo.getFeatures().contains("micrometer-influx")) {
                        projectInfo.getFeatures().addAll(List.of(
                                "micrometer-influx"
                        ));
                        try {
                            MicronautProjectValidator.addDependency(workingPath,features.get("micrometer-influx"));
                        } catch (GradleReaderException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        configurationInfo.setInflux(true);
                        boolean result = configurationInfo.writeToFile(workingPath);
                        return result? 1:0;
                    }

                    String influxTemplate = templatesService.loadTemplateContent
                            (templatesService.getMicrometersTemplates().get(INFLUX_yml));

                    MicronautProjectValidator.appendToProperties(workingPath, GeneratorUtils.generateFromTemplate(influxTemplate, new HashMap<String, String>(){{
                        put("org", org);
                        put("bucket", bucket);
                        put("token", token);
                    }}));
                    configurationInfo.setInflux(true);
                    projectInfo.dumpToFile(workingPath);

                }
                else if(configurationInfo.isGraphite())
                {
                    projectInfo.getFeatures().addAll(List.of(

                            "micrometer-graphite"
//                        "micrometer-statsd"
                    ));
                    try {

                        MicronautProjectValidator.addDependency(workingPath,features.get("micrometer-graphite"));
                    } catch (GradleReaderException e) {
                        e.printStackTrace();
                    }
                    MicronautProjectValidator.appendToProperties(workingPath, templatesService.loadTemplateContent
                            (templatesService.getMicrometersTemplates().get(GRAPHITE_yml)));


                    configurationInfo.setGraphite(true);
                    projectInfo.dumpToFile(workingPath);
                }
                else if(configurationInfo.isStatsd())
                {
                    projectInfo.getFeatures().addAll(List.of(

                            "micrometer-statsd"
//                        "micrometer-statsd"
                    ));
                    try {

                        MicronautProjectValidator.addDependency(workingPath,features.get("micrometer-statsd"));
                    } catch (GradleReaderException e) {
                        e.printStackTrace();
                    }
                    MicronautProjectValidator.appendToProperties(workingPath, templatesService.loadTemplateContent
                            (templatesService.getMicrometersTemplates().get(STATSD_yml)));


                    configurationInfo.setStatsd(true);
                    projectInfo.dumpToFile(workingPath);
                }

                projectInfo.dumpToFile(workingPath);




            }

            if(configurationInfo.isTracingEnabled())
            {
                if(configurationInfo.getTracingFramework().equalsIgnoreCase("tracing-"+JAEGER)){


                    projectInfo.getFeatures().add("tracing-jaeger");
                    MicronautProjectValidator.addDependency(workingPath,features.get("tracing-jaeger"));
                    MicronautProjectValidator.appendToProperties(workingPath,templatesService.loadTemplateContent
                            (templatesService.getDistributedTracingTemplates().get(DISTRIBUTED_TRACING_JAEGER)));
                    configurationInfo.setTracingFramework("tracing-jaeger");
                }

//            else if(tracing.getSelectedId().equalsIgnoreCase("Google Cloud Trace")){
//            }

                else if(configurationInfo.getTracingFramework().equalsIgnoreCase("tracing-"+ZIPKIN)){

                    projectInfo.getFeatures().add("tracing-zipkin");
                    MicronautProjectValidator.addDependency(workingPath,features.get("tracing-zipkin"));
                    MicronautProjectValidator.appendToProperties(workingPath,templatesService.loadTemplateContent
                            (templatesService.getDistributedTracingTemplates().get(DISTRIBUTED_TRACING_ZIPKIN)));
                   configurationInfo.setTracingFramework("tracing-zipkin");
                }

            }

            if(configurationInfo.isGraphQlSupport())
            {
                projectInfo.getFeatures().add("graphql");
                MicronautProjectValidator.addDependency(workingPath,features.get("graphql"));
                MicronautProjectValidator.addDependency(workingPath,features.get("graphql-java-tools"));
                configurationInfo.setGraphQLIntegrationLib("graphql-java-tools");
                projectInfo.dumpToFile(workingPath);
                configurationInfo.getUrls().add(
                        new URL(){{
                            setScope("/GraphQL");
                            setUrl("/graphiql");
                            setMethod(GET);


                        }});
                configurationInfo.getUrls().add(new URL(){{
                    setScope("/GraphQL");
                    setUrl("" +
                            "/graphql");
                    setMethod(POST);
                }});

                templatesService.loadTemplates(null);
                String graphQLproperties = templatesService.loadTemplateContent
                        (templatesService.getProperties().get(GRAPHQL_yml));
                MicronautProjectValidator.appendToProperties(workingPath,graphQLproperties);
            }

            if(configurationInfo.isSupportFileService()){

                if(configurationInfo.getFileServiceType().equalsIgnoreCase("aws")){

                    MicronautProjectValidator.addDependency(workingPath, features.get("aws-s3"));
                    templatesService.loadTemplates(null);
                    try {
                        String awsPropertiesTemplate = templatesService.loadTemplateContent
                                (templatesService.getProperties().get(AWS_CONFIGURATION_PROPERTIES));

                        HashMap<String, String> awsMap = new HashMap<String, String>();
                        awsMap.put("key", configurationInfo.getAwsKey());
                        awsMap.put("secret", configurationInfo.getAwsSecret());
                        String awsProperties = new SimpleTemplateEngine().createTemplate(awsPropertiesTemplate).make(awsMap).toString();
                        MicronautProjectValidator.appendToProperties(workingPath, awsProperties);


                        String awsConfigurationTemplate = templatesService.loadTemplateContent(getTemplatePath(AWS_CONFIGURATION, projectInfo.getSourceLanguage().toLowerCase()));
                        String awsConfiguration = new SimpleTemplateEngine().createTemplate(awsConfigurationTemplate)
                                .make(new HashMap(){{
                                    put("mainPackage", projectInfo.getDefaultPackage()+".config");
                                }}).toString();

                        GeneratorUtils.createFile(workingPath+ "/src/main/" + projectInfo.getSourceLanguage()+"/"+GeneratorUtils.packageToPath(projectInfo.getDefaultPackage()+".config") + "/AwsCredentials." +(projectInfo.getSourceLanguage().equalsIgnoreCase("Kotlin")? "kt": projectInfo.getSourceLanguage().toLowerCase()), awsConfiguration);



                        String awsFactoryTemplate = templatesService.loadTemplateContent(getTemplatePath(AWS_CONFIGURATION_FACTORY, projectInfo.getSourceLanguage().toLowerCase()));
                        String awsFactory = new SimpleTemplateEngine().createTemplate(awsFactoryTemplate)
                                .make(new HashMap(){{
                                    put("mainPackage", projectInfo.getDefaultPackage()+".config");
                                }}).toString();

                        GeneratorUtils.createFile(workingPath+ "/src/main/" + projectInfo.getSourceLanguage()+"/"+GeneratorUtils.packageToPath(projectInfo.getDefaultPackage()+".config") + "/AwsClientFactory." +(projectInfo.getSourceLanguage().equalsIgnoreCase("Kotlin")? "kt": projectInfo.getSourceLanguage().toLowerCase()), awsFactory);




                        String awsServiceTemplate = templatesService.loadTemplateContent(getTemplatePath(AWS_S3_SERVICE, projectInfo.getSourceLanguage().toLowerCase()));
                        String awsService = new SimpleTemplateEngine().createTemplate(awsServiceTemplate)
                                .make(new HashMap(){{
                                    put("mainPackage", projectInfo.getDefaultPackage()+".services");
                                }}).toString();

                        GeneratorUtils.createFile(workingPath+ "/src/main/" + projectInfo.getSourceLanguage()+"/"+GeneratorUtils.packageToPath(projectInfo.getDefaultPackage()+".services") + "/FileService." +(projectInfo.getSourceLanguage().equalsIgnoreCase("Kotlin")? "kt": projectInfo.getSourceLanguage().toLowerCase()), awsService);


                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if (configurationInfo.getFileServiceType().equalsIgnoreCase("gcp")){

                }
                else if(configurationInfo.getFileServiceType().equalsIgnoreCase("azure"))
                {

                }
                else {
                    try {
                        String fileServiceTemplate = templatesService.loadTemplateContent(getTemplatePath(FILE_SYSTEM_SERVICE, projectInfo.getSourceLanguage().toLowerCase()));

                        String fileService = new SimpleTemplateEngine().createTemplate(fileServiceTemplate)
                                .make(new HashMap(){{
                                    put("mainPackage", projectInfo.getDefaultPackage()+".services");
                                }}).toString();
                        GeneratorUtils.createFile(workingPath+ "/src/main/" + projectInfo.getSourceLanguage()+"/"+GeneratorUtils.packageToPath(projectInfo.getDefaultPackage()+".services") + "/FileService." +(projectInfo.getSourceLanguage().equalsIgnoreCase("Kotlin")? "kt": projectInfo.getSourceLanguage().toLowerCase()), fileService);

                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }



                }


            }


            if(projectInfo.getApplicationType().equalsIgnoreCase("default"))
            {
                if(!projectInfo.getFeatures().contains("openapi"))
                {

                    projectInfo.getFeatures().add("openapi");
                    MicronautProjectValidator.addOpenapi(workingPath);
                    MicronautProjectValidator.addExposingSwaggerUI(workingPath);
                    MicronautProjectValidator.addingOpenApiToApplicationFile(workingPath+"/",configurationInfo.getAppName());

                    templatesService.loadTemplates(null);
                    String openAPIProperties = templatesService.loadTemplateContent
                            (templatesService.getProperties().get(OPENAPI_yml));
                    MicronautProjectValidator.appendToProperties(workingPath,openAPIProperties);

                    try {

                        String openAPIPropertiesFile =  new TemplatesService().loadTemplateContent(OPEN_API_PATH);
                        GeneratorUtils.createFile(new StringBuilder().append(workingPath).append("/src/main/resources/openapi.properties").toString(), openAPIPropertiesFile);
                    }catch (Exception ex)
                    {

                    }

                }
                configurationInfo.getUrls().add(
                        new URL(){{
                            setScope("/OpenAPI");
                            setMethod(GET);
                            setUrl("/swagger/views/swagger-ui/index.html");
                        }}
                );
                configurationInfo.getUrls().add(
                        new URL(){{
                            setScope("/OpenAPI");
                            setMethod(GET);
                            setUrl("/swagger/views/rapidoc/index.html");
                        }}
                );
            }


        }

        try {

            String logBackContent =  new TemplatesService().loadTemplateContent(LOGBACK_PATH);
            GeneratorUtils.createFile(new StringBuilder().append(workingPath).append("/src/main/resources/logback.xml").toString(), logBackContent);
        }catch (Exception ex)
        {

        }
        if(configurationInfo.isLombok())
            MicronautProjectValidator.addLombok(workingPath,projectInfo);
        //MicronautProjectValidator.addDependency(workingPath,features.get("openapi"));



        projectInfo.dumpToFile(workingPath);
        //todo add dependencies to build files.

        configurationInfo.setAppName(MicronautProjectValidator.getAppName(workingPath));
        configurationInfo.setProjectInfo(projectInfo);
        printlnSuccess("micronaut-cli.yml file has been updated");

        configurationInfo.setConfigured(true);
        configurationInfo.writeToFile(workingPath);

        setToDefault();
        return 1;
        }
    public Entity readyEntityFromEntitySyntax(final ConfigurationInfo configurationInfo, final EntitySyntax entitySyntax)
    {

        return  new Entity(){{
            setName(NameUtils.capitalize(entitySyntax.getName()));
            setDatabaseName(configurationInfo.getDatabaseName());

            setDatabaseType(configurationInfo.getDatabaseType());
            setCached(configurationInfo.isCaffeine());
            setNonBlocking(configurationInfo.isNonBlocking());
            setJaxRs(configurationInfo.isJaxRsAnnotation());
            setFileServiceType(configurationInfo.getFileServiceType());
            setLombok(configurationInfo.isLombok());
            setPageable(entitySyntax.isPagination());
            setCollectionName(altValue(entitySyntax.getTableCollectionName(), entitySyntax.getName()+"s"));
            if(getDatabaseType().equalsIgnoreCase(MongoDB) || getDatabaseType().equalsIgnoreCase(MicroStream_Embedded_Storage))
            {
                setIdType("String");
            }
            else {
                setIdType("Long");
            }
            setMicrostreamPath(entitySyntax.getMicrostreamPath());
            setFrameworkType(configurationInfo.getDataBackendRun());
            setDatabaseName(configurationInfo.getDatabaseName());
            setReactiveFramework(configurationInfo.getReactiveFramework());
            setMnData(configurationInfo.isMnData());
            setGorm(configurationInfo.isGorm());
            setMicrometer(configurationInfo.isMicrometer());
            setTracingEnabled(configurationInfo.isTracingEnabled());
            setPackages(configurationInfo.getProjectInfo().getDefaultPackage());
            setSecurityStrategy(configurationInfo.getSecurityStrategy());
            setSecurityEnabled(configurationInfo.isSecurityEnable());
            setJavaVersion(configurationInfo.getJavaVersion());
            setNonBlocking(configurationInfo.isNonBlocking());
            setJavaRecord(entitySyntax.isRecords() && getJavaVersion().matches("^(1[4-9]|[2-9][0-9])$"));
            final String cf = configurationInfo.getDataBackendRun();
            getAttributes().addAll(
                    entitySyntax.getAttributesDeclarations().stream().map(x->{
                        return new EntityAttribute(){{

                            setName(x.getName());
                            setType(x.getType());
                            setFile(x.getType().equalsIgnoreCase("file"));
                            setJdbc(cf.equalsIgnoreCase("jdbc"));
                            setJpa(cf.equalsIgnoreCase("jpa"));
                            EntityConstraints constraints = new EntityConstraints();
                            if(x.getConstraints() !=null && !x.getConstraints().isEmpty())
                            {
                                constraints.setNullable(true);

                                for(String c : x.getConstraints())
                                {
                                    if(c.equalsIgnoreCase("required"))
                                    {
                                        constraints.setRequired(true);
                                    }
                                    else if(c.equalsIgnoreCase("notnull"))
                                    {
                                        constraints.setNullable(false);

                                    }

                                    else if(c.equalsIgnoreCase("notempty"))
                                    {
                                        constraints.setNotempty(true);
                                        constraints.setNotEmpty(true);

                                    }
                                    else if(c.equalsIgnoreCase("notBlank")){
                                        constraints.setNotBlank(true);
                                    }
                                    else if(c.equalsIgnoreCase("email"))
                                    {
                                        constraints.setEmail(true);
                                    }
                                    else if(c.equalsIgnoreCase("unique"))
                                    {
                                        constraints.setUnique(true);
                                    }
                                    else if(c.trim().matches("min\\s*\\(\\s*\\d+\\s*\\)"))
                                    {
                                        constraints.setMin(Long.parseLong(ValidationParser.getMinMax(c.trim())));
                                        constraints.setDecimalMin(Double.parseDouble(ValidationParser.getMinMax(c.trim())));
                                    }
                                    else if(c.trim().matches("max\\s*\\(\\s*\\d+\\s*\\)"))
                                    {
                                        constraints.setMax(Long.parseLong(ValidationParser.getMinMax(c.trim())));
                                        constraints.setDecimalMax(Double.parseDouble(ValidationParser.getMinMax(c.trim())));
                                    }
                                    else if(c.trim().matches("regex\\([.\\w\\{ \\} \\[\\]\\(\\)\\.\\:\\'\\<\\>\\\" \\,\\?\\\\ \\*\\+]*\\)"))
                                    {
                                        constraints.setPattern(ValidationParser.getRegex(c.trim()));
                                    }
                                    else if(c.trim().matches("size\\(\\d+\\s*\\-\\s*\\d+\\)"))
                                    {
                                        var minMax = ValidationParser.parseSize(c.trim());
                                        constraints.setMinSize(Long.parseLong(minMax._1()));
                                        constraints.setMaxSize(Long.parseLong(minMax._2()));

                                    }


                                }

                            }
                            setConstraints(constraints);
                        }};
                    }).collect(Collectors.toList())

            );




        }};
    }


    public EnumClass readEnumFromEnumSyntax(final ConfigurationInfo configurationInfo, EnumSyntax enumSyntax)
    {
       return new EnumClass(){{
           setName(enumSyntax.getName());
           HashSet<String> vs = new HashSet<String>();
           vs.addAll(enumSyntax.getEnums());
           setValues(vs);
           setEnumPackage(configurationInfo.getProjectInfo().getDefaultPackage() + ".enums");
       }} ;
    }
    private String getTemplatePath(String key, String language) {

        if ("groovy".equals(language)) {
            return templatesService.getGroovyTemplates().get(key);
        } else if ("kotlin".equals(language)) {
            return templatesService.getKotlinTemplates().get(key);
        } else if ("java".equals(language)) {
            return templatesService.getJavaTemplates().get(key);
        }
        else
            return templatesService.getJavaTemplates().get(key);
    }




}
