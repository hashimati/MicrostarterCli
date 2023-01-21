package io.hashimati.microcli.spring.services;

import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.spring.config.SpringFeaturesFactory;
import io.hashimati.microcli.spring.utils.ProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.KOTLIN_LANG;
import static io.hashimati.microcli.constants.ResultConstants.*;
import static io.hashimati.microcli.spring.utils.GeneratorUtils.getWorkingDirectory;

public class ConfigurationService {
    private ProjectValidator projectValidator = new ProjectValidator();

    private FeaturesFactory featuresFactory = new FeaturesFactory();
    public Integer configure(String path) throws IOException, XmlPullParserException {
        PromptGui.println("Detecting Project information", Ansi.Color.CYAN);
        ConfigurationInfo configurationInfo = new ConfigurationInfo();

        String currentDir = path;
        if(currentDir == null || currentDir.isBlank())
            currentDir = getWorkingDirectory();


        File configurationFile = new File(currentDir+"/MicroCliConfig.json");
        if(configurationFile.exists())
        {
            return CONFIGURATION_FILE_EXIST;
        }
        if(!projectValidator.isMavenOrGradle()){
            return INVALID_PROJECT;
        }
        else{
            configurationInfo.getProjectInfo().setBuildTool(
                    projectValidator.isMaven()? "maven": (projectValidator.isGradleKotlin()?"gradle_kotlin":"gradle")
            );
        }
        PromptGui.println("The build tool is "+ configurationInfo.getProjectInfo().getBuildTool(), Ansi.Color.BLUE);

        configurationInfo.getProjectInfo().setSourceLanguage(projectValidator.getProjectLanguage());
        PromptGui.println("The project's language is "+ configurationInfo.getProjectInfo().getSourceLanguage(), Ansi.Color.MAGENTA);

        configurationInfo.getProjectInfo().setSrcExtension(configurationInfo.getProjectInfo().getSourceLanguage().equals(KOTLIN_LANG)?"kt":configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase());
        configurationInfo.setJavaVersion(projectValidator.getJdkVersion());
        PromptGui.println("The Java version is "+ configurationInfo.getJavaVersion(), Ansi.Color.RED);

        configurationInfo.getProjectInfo().setDefaultPackage(projectValidator.getDefaultPackage());
        PromptGui.println("The main package is "+ configurationInfo.getProjectInfo().getDefaultPackage(), Ansi.Color.YELLOW);

        configurationInfo.getProjectInfo().setArtifact(projectValidator.getArtifact());
        configurationInfo.setAppName(configurationInfo.getProjectInfo().getArtifact());

        PromptGui.printlnSuccess("Finished detecting project information!");


        var databaseResult = PromptGui.inputText("database", "Enter Database's name:", configurationInfo.getProjectInfo().getArtifact());
        configurationInfo.setDatabaseName(databaseResult.getInput());

        var databaseTypeResult =PromptGui.createListPrompt("databaseType", "Select Database Type: ",
                "MongoDB", "Couchbase", "Neo4J", "Cassandra",
                "H2", "MySQL", "MariaDB", "Postgres",
                "Oracle", "SqlServer", "IBM DB2"
        );
        var databaseType ="";
        configurationInfo.setDatabaseType((databaseType=databaseTypeResult.getSelectedId().toLowerCase()));
        if(Arrays.asList("h2", "mysql", "mariadb", "postgres",
                "sqlserver").contains(databaseType))
        {
            var backend =PromptGui.createListPrompt("backend", "Select Database backend: ",
                    "JDBC", "R2DBC", "JPA");
            configurationInfo.setDataBackendRun(backend.getSelectedId().toLowerCase());
        }
        else if(Arrays.asList("mongodb", "couchbase", "cassandra").contains(databaseType))
        {
            var backend =PromptGui.createListPrompt("backend", "Select Database backend: ",
                    "Blocking", "Non-Blocking");
            configurationInfo.setDataBackendRun(backend.getSelectedId().toLowerCase());
        }
        else if(Arrays.asList("oracle", "ibm db2").contains(databaseType))
        {
            var backend =PromptGui.createListPrompt("backend", "Select Database backend: ",
                    "JDBC", "JPA");
            configurationInfo.setDataBackendRun(backend.getSelectedId().toLowerCase());
        }
        else{
            configurationInfo.setDataBackendRun("blocking");
        }

        HashMap<String, Feature> features = SpringFeaturesFactory.dependencies();

        //adding openAPI via SpringDoc. It requires web.
        projectValidator.addDependency(features.get("web"));
        projectValidator.addDependency(features.get("webFlux"));
        projectValidator.addDependency(features.get("springdoc"));


        switch (configurationInfo.getDatabaseType()){
            case "mongodb":
                switch (configurationInfo.getDataBackendRun()){
                    case "blocking":
                        projectValidator.addDependency(features.get("mongodb"));
                        break;
                    case "non-blocking":
                        projectValidator.addDependency(features.get("mongodb-reactive"));
                        break;
                }
                break;
            case "couchbase":
                switch (configurationInfo.getDataBackendRun()){
                    case "blocking":
                        projectValidator.addDependency(features.get("couchbase"));

                        break;
                    case "non-blocking":
                        projectValidator.addDependency(features.get("couchbase-reactive"));

                        break;
                }
                break;
            case "neo4j":
                projectValidator.addDependency(features.get("neo4j"));

                break;
            case "cassandra":
                switch (configurationInfo.getDataBackendRun()){
                    case "blocking":
                        projectValidator.addDependency(features.get("cassandra"));
                        break;
                    case "non-blocking":
                        projectValidator.addDependency(features.get("cassandra-reactive"));
                        break;
                }
                break;
            case "mysql":
                switch (configurationInfo.getDataBackendRun()){
                    case "jpa":
                        projectValidator.addDependency(features.get("data-jpa"));
                        projectValidator.addDependency(features.get("mysql"));
                        projectValidator.addDependency(features.get("liquibase"));

                        break;
                    case "jdbc":
                        projectValidator.addDependency(features.get("data-jdbc"));
                        projectValidator.addDependency(features.get("mysql"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                    case "r2dbc":
                        projectValidator.addDependency(features.get("data-r2dbc"));
                        projectValidator.addDependency(features.get("mysql"));
                        projectValidator.addDependency(features.get("mysql-r2dbc"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                }
                break;
            case "mariadb":
                switch (configurationInfo.getDataBackendRun()){
                    case "jpa":
                        projectValidator.addDependency(features.get("data-jpa"));
                        projectValidator.addDependency(features.get("mariadb"));
                        projectValidator.addDependency(features.get("liquibase"));

                        break;
                    case "jdbc":
                        projectValidator.addDependency(features.get("data-jdbc"));
                        projectValidator.addDependency(features.get("mariadb"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                    case "r2dbc":
                        projectValidator.addDependency(features.get("data-r2dbc"));
                        projectValidator.addDependency(features.get("mariadb"));
                        projectValidator.addDependency(features.get("mariadb-r2dbc"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                }
                break;
            case "oracle":
                switch (configurationInfo.getDataBackendRun()){
                    case "jpa":
                        projectValidator.addDependency(features.get("data-jpa"));
                        projectValidator.addDependency(features.get("oracle"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                    case "jdbc":
                        projectValidator.addDependency(features.get("data-jdbc"));
                        projectValidator.addDependency(features.get("oracle"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                }
                break;
            case "sqlserver":
                switch (configurationInfo.getDataBackendRun()){
                    case "jpa":
                        projectValidator.addDependency(features.get("data-jpa"));
                        projectValidator.addDependency(features.get("sqlserver"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                    case "jdbc":
                        projectValidator.addDependency(features.get("data-jdbc"));
                        projectValidator.addDependency(features.get("sqlserver"));
                        projectValidator.addDependency(features.get("liquibase"));

                        break;
                    case "r2dbc":
                        projectValidator.addDependency(features.get("data-r2dbc"));
                        projectValidator.addDependency(features.get("sqlserver"));
                        projectValidator.addDependency(features.get("sqlserver-r2dbc"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                }
                break;
            case "ibm db2":
                switch (configurationInfo.getDataBackendRun()){
                    case "jpa":
                        projectValidator.addDependency(features.get("data-jpa"));
                        projectValidator.addDependency(features.get("ibm db2"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                    case "jdbc":
                        projectValidator.addDependency(features.get("data-jdbc"));
                        projectValidator.addDependency(features.get("ibm db2"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;

                }
                break;
            case "h2":
                switch (configurationInfo.getDataBackendRun()){
                    case "jpa":
                        projectValidator.addDependency(features.get("data-jpa"));
                        projectValidator.addDependency(features.get("h2"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                    case "jdbc":
                        projectValidator.addDependency(features.get("data-jdbc"));
                        projectValidator.addDependency(features.get("h2"));
                        projectValidator.addDependency(features.get("liquibase"));

                        break;
                    case "r2dbc":
                        projectValidator.addDependency(features.get("data-r2dbc"));
                        projectValidator.addDependency(features.get("h2"));
                        projectValidator.addDependency(features.get("h2-r2dbc"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                }
                break;
            case "postgres":
                switch (configurationInfo.getDataBackendRun()){
                    case "jpa":
                        projectValidator.addDependency(features.get("data-jpa"));
                        projectValidator.addDependency(features.get("postgres"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                    case "jdbc":
                        projectValidator.addDependency(features.get("data-jdbc"));
                        projectValidator.addDependency(features.get("postgres"));
                        projectValidator.addDependency(features.get("liquibase"));

                        break;
                    case "r2dbc":
                        projectValidator.addDependency(features.get("data-r2dbc"));
                        projectValidator.addDependency(features.get("postgres"));
                        projectValidator.addDependency(features.get("postgres-r2dbc"));
                        projectValidator.addDependency(features.get("liquibase"));
                        break;
                }
                break;
        }




        configurationInfo.writeToFile(getWorkingDirectory());

        return CONFIGURATION_DONE;
    }
}
