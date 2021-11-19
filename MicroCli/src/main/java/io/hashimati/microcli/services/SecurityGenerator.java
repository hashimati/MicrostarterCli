package io.hashimati.microcli.services;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.utils.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;

@Singleton
public class SecurityGenerator {

//
    @Inject
    private TemplatesService templatesService;


    public void generateSecurityFiles(String strategy, ArrayList<String> roles, boolean persistRefreshToken) throws IOException, GradleReaderException {
        HashMap<String, Feature> features = FeaturesFactory.features();

        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));
        String rolesDeclaration = "";
        if (!roles.isEmpty())
            rolesDeclaration = roles.stream().map(x -> new EntityAttribute() {{
                        setName(x);
                        setType("String");
                    }}.getFinalStaticDeclaration(configurationInfo.getProjectInfo().getSourceLanguage(), new StringBuilder().append("\"").append(x).append("\"").toString()))
                    .reduce((x, y) -> new StringBuilder(x).append(y).toString()).get();

        auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityControllerTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityDomainsTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityRepositoryTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityServicesTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityUtilsTemplates(), configurationInfo);

        if (configurationInfo.getDataBackendRun().contains("mongoReactive"))
        {
            auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityEventsTemplates(), configurationInfo);

        }
        if(persistRefreshToken)
            auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken,templatesService.getSecurityRefreshTokenTemplates(), configurationInfo);

        configurationInfo.setSecurityRoles(roles);

        MicronautProjectValidator.addDependency(features.get("jasypt"));
        if(!configurationInfo.getProjectInfo().getFeatures().contains("security-annotations"))
        {
            configurationInfo.getProjectInfo().getFeatures().add("security-annotations");
            MicronautProjectValidator.addDependency(features.get("security-annotations"));
        }
        if(strategy.equalsIgnoreCase("jwt")){
        if(!configurationInfo.getProjectInfo().getFeatures().contains("security-jwt"))
        {
            configurationInfo.getProjectInfo().getFeatures().add("security-jwt");
            MicronautProjectValidator.addDependency(features.get("security-jwt"));
            MicronautProjectValidator.appendToProperties(
                    templatesService.loadTemplateContent(
                                    templatesService.getSecurityPropertiesTemplates().get(TemplatesService.SECURITY_JWT_PROPERTIES)
                    )
            );

        }}
        else if(strategy.equalsIgnoreCase("oauth")){
            if(!configurationInfo.getProjectInfo().getFeatures().contains("security-oauth2"))
            {
                configurationInfo.getProjectInfo().getFeatures().add("security-oauth2");
                MicronautProjectValidator.addDependency(features.get("security-oauth2"));


            }
        }
        else if(strategy.equalsIgnoreCase("session")){
            if(!configurationInfo.getProjectInfo().getFeatures().contains("security-session"))
            {
                configurationInfo.getProjectInfo().getFeatures().add("security-session");
                MicronautProjectValidator.addDependency(features.get("security-session"));
                MicronautProjectValidator.appendToProperties(
                        templatesService.loadTemplateContent(
                                templatesService.getSecurityPropertiesTemplates().get(TemplatesService.SECURITY_SESSION_PROPERTIES)
                        )
                );

            }
        }
        if(!configurationInfo.getDatabaseType().toLowerCase().contains("mongo")){
//            String configPath = templatesService.getSecurityLiquibase().get(templatesService.SECURITY_LIQUIBASE_CONFIG);
//            String configContent = templatesService.loadTemplateContent(configPath);
//            configPath = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append(configPath.substring(configPath.indexOf("db"))).toString();
//

            if(!configurationInfo.getProjectInfo().getFeatures().contains("reactor")){
                configurationInfo.getProjectInfo().getFeatures().add("reactor");
                configurationInfo.getProjectInfo().getFeatures().add("reactor-http-client");
                MicronautProjectValidator.addDependency(features.get("reactor"));
                MicronautProjectValidator.addDependency(features.get("reactor-http-client"));

                PromptGui.printlnSuccess("The \"Reactor\" feature is added");

            }
            String userSchemaPath = templatesService.getSecurityLiquibase().get(templatesService.SECURITY_LIQUIBASE_SCHEMA);
            String userSchemaContent = templatesService.loadTemplateContent(userSchemaPath);
            userSchemaPath = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append(userSchemaPath.substring(userSchemaPath.indexOf("db"))).toString();

//            GeneratorUtils.createFile(configPath, configContent);
            GeneratorUtils.createFile(userSchemaPath, userSchemaContent);


        }
        configurationInfo.setSecurityEnable(true);
        configurationInfo.getProjectInfo().dumpToFile();
        configurationInfo.writeToFile();

    }


    public void auxGenerateSecurityFiles(String strategy, String roles, boolean persistRefreshToken, HashMap<String, String> templates, ConfigurationInfo configurationInfo) throws IOException {
        String db = configurationInfo.getDataBackendRun(),
                lang = configurationInfo.getProjectInfo().getSourceLanguage(),
                ext = GeneratorUtils.getSourceFileExtension(lang);
        for (String key : templates.keySet()) {
            String path = templates.get(key);
            path = GeneratorUtils.generateFromTemplate(path, new HashMap<String, String>() {{
                put("auth", strategy);
                put("db", db);
                put("lang", lang);
                put("ext", ext);
            }});

            String filePath = new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/").append(lang).append("/").append(GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage())).append(path.substring(path.indexOf("/security")).replace(new StringBuilder("/").append(strategy).append("/").append(lang).append("/").append(db).toString(), "").replace(new StringBuilder().append("/").append("/").append(lang).append("/").append(db).toString(), "")).toString();
            String template = templatesService.loadTemplateContent(path);

            String securityPackage = new StringBuilder().append(configurationInfo.getProjectInfo().getDefaultPackage()).append(".security").toString();

            String fileContent = GeneratorUtils.generateFromTemplate(template, new HashMap<String, String>() {{
                put("securityPackage", securityPackage);
                put("roles", roles);
                put("persistToken", ""+persistRefreshToken);
                put("dialect", DataTypeMapper.dialectMapper.get(configurationInfo.getDatabaseType().toLowerCase()));
            }});
            GeneratorUtils.createFile(filePath, fileContent);
        }
    }
}
