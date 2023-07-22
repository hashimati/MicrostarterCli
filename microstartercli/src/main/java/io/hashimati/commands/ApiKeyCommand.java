package io.hashimati.commands;

import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;

@CommandLine.Command(name = "apikey", description = "To secure the API with key.")
public class ApiKeyCommand implements Callable<Integer> {

    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
    private String path;
    private TemplatesService templatesService = new TemplatesService() ;

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
                path = GeneratorUtils.getCurrentWorkingPath()+"/" +path ;

            }
            path = path +"/";
        }

        AnsiConsole.systemInstall();
        ansi().eraseScreen();
        File configurationFile = new File(ConfigurationInfo.getConfigurationFileName(path));
        if(!configurationFile.exists()){
            PromptGui.printlnWarning("run \"configure\" command first!");
            return 0;
        }

        ConfigurationInfo configurationInfo  = ConfigurationInfo.fromFile(configurationFile);

  ;
        if(configurationInfo.isSecurityEnable())
        {
            PromptGui.printlnWarning("Security is already enabled!");
            return 0;
        }
        if(configurationInfo.isApikey())
        {
            PromptGui.printlnWarning("API Key is already enabled!");
            return 0;
        }
        String securityPackage = new StringBuilder().append(configurationInfo.getProjectInfo().getDefaultPackage()).append(".security").toString();

        configurationInfo.setApikey(true);
        configurationInfo.setSecurityEnable(true);
        HashMap<String, Feature> features = FeaturesFactory.features(configurationInfo.getProjectInfo());

        String header= PromptGui.inputText("Header", "Enter the header name: ","API-HEADER" ).getInput() ;
        configurationInfo.setApiKeyHeader(header);

        try {
            MicronautProjectValidator.addLombok(path,configurationInfo.getProjectInfo());
            MicronautProjectValidator.addDependency(path,features.get("jasypt"));
            if(!configurationInfo.getProjectInfo().getFeatures().contains("security"))
            {
                configurationInfo.getProjectInfo().getFeatures().add("security");
                MicronautProjectValidator.addDependency(path, features.get("security"));


            }
            if(!configurationInfo.getProjectInfo().getFeatures().contains("reactor")){
                configurationInfo.getProjectInfo().getFeatures().add("reactor");
                configurationInfo.getProjectInfo().getFeatures().add("reactor-http-client");
                MicronautProjectValidator.addDependency(path,features.get("reactor"));
                MicronautProjectValidator.addDependency(path,features.get("reactor-http-client"));

                PromptGui.printlnSuccess("The \"Reactor\" feature is added");

            }
            String language = configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase()
                    .equals("java") ? "java" : "groovy";
            templatesService.loadTemplates(null);

            // generate the domains;
            String domainPath = templatesService.getSecurityApiKeyTemplates().get(templatesService.APIKEY).replace("${lang}", language)
                    .replace("${ext}", language.equalsIgnoreCase("kotlin") ? ".kt" : "."+ language);
            String domainTemplate = templatesService.loadTemplateContent(domainPath);

            String domainContent = GeneratorUtils.generateFromTemplateObj(domainTemplate,
                    new HashMap<String, Object>(){{
                        put("securityPackage", securityPackage);
                        put("mongo", configurationInfo.isMnData() && (configurationInfo.getDataBackendRun().equalsIgnoreCase("data-mongodb") ||configurationInfo.getDataBackendRun().equalsIgnoreCase("data-mongodb-reactive") ) );
                        put("jdbc", configurationInfo.isMnData() && configurationInfo.getDataBackendRun().equalsIgnoreCase("jdbc"));

                    }});

            String domainFilePath = path + "/src/main/"+language+"/" + securityPackage.replace(".", "/") + "/domains/APIKey."+(language.equalsIgnoreCase("kotlin") ? "kt" : language.toLowerCase());
            GeneratorUtils.createFile(domainFilePath, domainContent);


            // generate the repository.
            String repoPath = templatesService.getSecurityApiKeyTemplates().get(templatesService.APIKEY_REPOSITORY).replace("${lang}", language)
                    .replace("${ext}", language.equalsIgnoreCase("kotlin") ? ".kt" : "."+ language);


            String repoTemplate = templatesService.loadTemplateContent(repoPath);

            String repoContent = GeneratorUtils.generateFromTemplateObj(repoTemplate,
                    new HashMap<String, Object>(){{
                        put("securityPackage", securityPackage);
                        put("mongo", configurationInfo.isMnData() && (configurationInfo.getDataBackendRun().equalsIgnoreCase("data-mongodb") ||configurationInfo.getDataBackendRun().equalsIgnoreCase("data-mongodb-reactive") ) );
                        put("jdbc", configurationInfo.isMnData() && configurationInfo.getDataBackendRun().equalsIgnoreCase("jdbc"));
                        put("dialect", configurationInfo.getDatabaseType().toUpperCase());
            }});
            String repoFilePath = path + "/src/main/"+language+"/" + securityPackage.replace(".", "/") + "/repository/ApiKeyRepository."+(language.equalsIgnoreCase("kotlin") ? "kt" : language.toLowerCase());

            GeneratorUtils.createFile(repoFilePath, repoContent);

            //generate the services;

//
//            String servicePath = templatesService.getSecurityApiKeyTemplates().get(templatesService.APIKEY_SERVICE).replace("${lang}", language)
//                    .replace("${ext}", language.equalsIgnoreCase("kotlin") ? ".kt" : "."+ language);
//            String serviceTemplate = templatesService.loadTemplateContent(servicePath);
//
//            String serviceContent = GeneratorUtils.generateFromTemplateObj(serviceTemplate,
//                    new HashMap<String, Object>(){{
//                        put("securityPackage", securityPackage);
//            }});
//            String serviceFilePath = path + "/src/main/"+language+"/" + securityPackage.replace(".", "/") + "/service/ApiKeyService."+(language.equalsIgnoreCase("kotlin") ? "kt" : language.toLowerCase());
//            GeneratorUtils.createFile(serviceFilePath, serviceContent);
            // generate the token validator
            String tokenValidatorPath = templatesService.getSecurityApiKeyTemplates().get(templatesService.APIKEY_TOKEN_VALIDATOR).replace("${lang}", language)
                    .replace("${ext}", language.equalsIgnoreCase("kotlin") ? ".kt" : "."+ language);
            String tokenValidatorTemplate = templatesService.loadTemplateContent(tokenValidatorPath);
            String tokenValidatorContent = GeneratorUtils.generateFromTemplateObj(tokenValidatorTemplate,
                    new HashMap<String, Object>(){{
                        put("securityPackage", securityPackage);
            }});
            String tokenValidatorFilePath = path + "/src/main/"+language+"/" + securityPackage.replace(".", "/") + "/token/ApiKeyTokenValidator."+(language.equalsIgnoreCase("kotlin") ? "kt" : language.toLowerCase());
            GeneratorUtils.createFile(tokenValidatorFilePath, tokenValidatorContent);



            String tokenReaderPath = templatesService.getSecurityApiKeyTemplates().get(templatesService.APIKEY_TOKEN_READER).replace("${lang}", language)
                    .replace("${ext}", language.equalsIgnoreCase("kotlin") ? ".kt" : "."+ language);
            String tokenReaderTemplate = templatesService.loadTemplateContent(tokenReaderPath);
            String tokenReaderContent = GeneratorUtils.generateFromTemplateObj(tokenReaderTemplate,
                    new HashMap<String, Object>(){{
                        put("securityPackage", securityPackage);
                        put("securityTokenHeader", header);
            }});
            String tokenReaderFilePath = path + "/src/main/"+language+"/" + securityPackage.replace(".", "/") + "/token/ApiKeyTokenReader."+(language.equalsIgnoreCase("kotlin") ? "kt" : language.toLowerCase());
            GeneratorUtils.createFile(tokenReaderFilePath, tokenReaderContent);

            String tokenGeneratorPath = templatesService.getSecurityApiKeyTemplates().get(templatesService.APIKEY_TOKEN_GENERATOR).replace("${lang}", language)
                    .replace("${ext}", language.equalsIgnoreCase("kotlin") ? ".kt" : "."+ language);
            String tokenGeneratorTemplate = templatesService.loadTemplateContent(tokenGeneratorPath);
            String tokenGeneratorContent = GeneratorUtils.generateFromTemplateObj(tokenGeneratorTemplate,
                    new HashMap<String, Object>(){{
                        put("securityPackage", securityPackage);
            }});
            String tokenGeneratorFilePath = path + "/src/main/"+language+"/" + securityPackage.replace(".", "/") + "/token/ApiKeyTokenGenerator."+(language.equalsIgnoreCase("kotlin") ? "kt" : language.toLowerCase());
            GeneratorUtils.createFile(tokenGeneratorFilePath, tokenGeneratorContent);


            //generate the controller.

            String controllerPath = templatesService.getSecurityApiKeyTemplates().get(templatesService.APIKEY_CONTROLLER).replace("${lang}", language)
                    .replace("${ext}", language.equalsIgnoreCase("kotlin") ? ".kt" : "."+ language);
            String controllerTemplate = templatesService.loadTemplateContent(controllerPath);

            String controllerContent = GeneratorUtils.generateFromTemplate(controllerTemplate,
                    new HashMap<String, String>(){{
                        put("securityPackage", securityPackage);
            }});
            String controllerFilePath = path + "/src/main/"+language+"/" + securityPackage.replace(".", "/") + "/controllers/ApiKeyController."+(language.equalsIgnoreCase("kotlin") ? "kt" : language.toLowerCase());
            GeneratorUtils.createFile(controllerFilePath, controllerContent);

            // generate the liquibase files
            String liquibaseTemplateContent= templatesService.loadTemplateContent(templatesService.getSecurityApiKeyTemplates().get(TemplatesService.APIKEY_LIQUIBASE));
            String liquibaseFilePath = path + "/src/main/resources/db/changelog/db.apikey.1.xml";
            GeneratorUtils.createFile(liquibaseFilePath, liquibaseTemplateContent);

            String liquibaseChangeTemplateContent= templatesService.loadTemplateContent(templatesService.getSecurityLiquibase().get(TemplatesService.SECURITY_LIQUIBASE_CONFIG));

            String liquibaseChangeFilePath = path + "/src/main/resources/db/liquibase-changelog.xml";
            GeneratorUtils.createFile(liquibaseChangeFilePath, liquibaseChangeTemplateContent);

            MicronautProjectValidator.appendToProperties(path, "micronaut.security.enabled: true");
            configurationInfo.writeToFile(path);
        } catch (GradleReaderException e) {
            throw new RuntimeException(e);

        }




        return 0;
    }
}
