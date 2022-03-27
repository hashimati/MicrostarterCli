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
import java.util.*;

import static io.hashimati.microcli.services.TemplatesService.REFRESH_TOKEN_REPOSITORY;
import static io.hashimati.microcli.services.TemplatesService.SECURITY_CLIENT;

@Singleton
public class SecurityGenerator {

//
    @Inject
    private TemplatesService templatesService;


    public void generateSecurityFiles(String path, String strategy, HashSet<String> roles, boolean persistRefreshToken) throws IOException, GradleReaderException {

        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName(path)));

        HashMap<String, Feature> features = FeaturesFactory.features(configurationInfo.getProjectInfo());

        String rolesDeclaration = "";
        if (!roles.isEmpty())
            rolesDeclaration = roles.stream().map(x -> new EntityAttribute() {{
                        setName(x);
                        setType("String");
                    }}.getFinalStaticDeclaration(configurationInfo.getProjectInfo().getSourceLanguage(), new StringBuilder().append("\"").append(x).append("\"").toString()))
                    .reduce((x, y) -> new StringBuilder(x).append(y).toString()).get();
        if(!strategy.equalsIgnoreCase("jwt"))
        {
            templatesService.getSecurityTemplates().remove(SECURITY_CLIENT);
            templatesService.getSecurityTemplates().remove(REFRESH_TOKEN_REPOSITORY);
        }
        auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityTemplates(), configurationInfo);
        auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityControllerTemplates(), configurationInfo);
        auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityDomainsTemplates(), configurationInfo);
        auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityRepositoryTemplates(), configurationInfo);
        auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityServicesTemplates(), configurationInfo);
        auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityUtilsTemplates(), configurationInfo);

        if (configurationInfo.getDataBackendRun().contains("mongoReactive"))
        {
            auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken, templatesService.getSecurityEventsTemplates(), configurationInfo);

        }
        if(persistRefreshToken && strategy.equalsIgnoreCase("jwt"))
            auxGenerateSecurityFiles(path, strategy, rolesDeclaration, persistRefreshToken,templatesService.getSecurityRefreshTokenTemplates(), configurationInfo);

        configurationInfo.setSecurityRoles(roles);
        configurationInfo.setSecurityEnable(true);
        configurationInfo.setSecurityStrategy(strategy);
        MicronautProjectValidator.addDependency(path,features.get("jasypt"));
        if(!configurationInfo.getProjectInfo().getFeatures().contains("security-annotations"))
        {
            configurationInfo.getProjectInfo().getFeatures().add("security-annotations");
            MicronautProjectValidator.addDependency(path, features.get("security-annotations"));
        }
        if(strategy.equalsIgnoreCase("jwt")){
        if(!configurationInfo.getProjectInfo().getFeatures().contains("security-jwt"))
        {
            configurationInfo.getProjectInfo().getFeatures().add("security-jwt");
            MicronautProjectValidator.addDependency(path, features.get("security-jwt"));
            MicronautProjectValidator.appendToProperties(path,
                    templatesService.loadTemplateContent(
                                    templatesService.getSecurityPropertiesTemplates().get(TemplatesService.SECURITY_JWT_PROPERTIES)
                    )
            );

        }}
        else if(strategy.equalsIgnoreCase("basic")){
            if(!configurationInfo.getProjectInfo().getFeatures().contains("security"))
            {
                configurationInfo.getProjectInfo().getFeatures().add("security");
                MicronautProjectValidator.addDependency(path, features.get("security"));


            }
        }
        else if(strategy.equalsIgnoreCase("oauth")){
            if(!configurationInfo.getProjectInfo().getFeatures().contains("security-oauth2"))
            {
                configurationInfo.getProjectInfo().getFeatures().add("security-oauth2");
                MicronautProjectValidator.addDependency(path, features.get("security-oauth2"));


            }
        }
        else if(strategy.equalsIgnoreCase("session")){
            if(!configurationInfo.getProjectInfo().getFeatures().contains("security-session"))
            {
                configurationInfo.getProjectInfo().getFeatures().add("security-session");
                MicronautProjectValidator.addDependency(path,features.get("security-session"));
                MicronautProjectValidator.appendToProperties(path
                        ,
                        templatesService.loadTemplateContent(
                                templatesService.getSecurityPropertiesTemplates().get(TemplatesService.SECURITY_SESSION_PROPERTIES)
                        )
                );

            }
        }
        if(!configurationInfo.getDatabaseType().toLowerCase().contains("mongo")){
//            String configPath = templatesService.getSecurityLiquibase().get(templatesService.SECURITY_LIQUIBASE_CONFIG);
//            String configContent = templatesService.loadTemplateContent(configPath);
//            configPath = new StringBuilder().append(path).append("/src/main/resources/").append(configPath.substring(configPath.indexOf("db"))).toString();
//

            if(!configurationInfo.getProjectInfo().getFeatures().contains("reactor")){
                configurationInfo.getProjectInfo().getFeatures().add("reactor");
                configurationInfo.getProjectInfo().getFeatures().add("reactor-http-client");
                MicronautProjectValidator.addDependency(path,features.get("reactor"));
                MicronautProjectValidator.addDependency(path,features.get("reactor-http-client"));

                PromptGui.printlnSuccess("The \"Reactor\" feature is added");

            }
            String userSchemaPath = templatesService.getSecurityLiquibase().get(templatesService.SECURITY_LIQUIBASE_SCHEMA);
            String userSchemaContent = templatesService.loadTemplateContent(userSchemaPath);
            userSchemaPath = new StringBuilder().append(path).append("/src/main/resources/").append(userSchemaPath.substring(userSchemaPath.indexOf("db"))).toString();

//            GeneratorUtils.createFile(configPath, configContent);
            GeneratorUtils.createFile(userSchemaPath, userSchemaContent);


        }
        configurationInfo.setSecurityEnable(true);
        configurationInfo.getProjectInfo().dumpToFile(path);
        configurationInfo.writeToFile(path);

    }


    public void auxGenerateSecurityFiles(String cwd, String strategy, String roles, boolean persistRefreshToken, HashMap<String, String> templates, ConfigurationInfo configurationInfo) throws IOException {
        String db =configurationInfo.isMnData()? "JDBC": configurationInfo.getDataBackendRun(),
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
            String filePath = new StringBuilder().append(cwd).append("/src/main/").append(lang).append("/").append(GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage())).append(path.substring(path.indexOf("/security")).replace(new StringBuilder("/").append(strategy).append("/").append(lang).append("/").append(db).toString(), "").replace(new StringBuilder().append("/").append("/").append(lang).append("/").append(db).toString(), "")).toString();

            String template = templatesService.loadTemplateContent(path);

            String securityPackage = new StringBuilder().append(configurationInfo.getProjectInfo().getDefaultPackage()).append(".security").toString();

            String fileContent = GeneratorUtils.generateFromTemplateVsObject(template, new HashMap<String, Object>() {{
                put("securityPackage", securityPackage);
                put("mongo", configurationInfo.isMnData() && configurationInfo.getDataBackendRun().equalsIgnoreCase("data-mongodb"));
                put("jdbc", configurationInfo.isMnData() && configurationInfo.getDataBackendRun().equalsIgnoreCase("jdbc"));
                put("roles", roles);
                put("persistToken", ""+persistRefreshToken);
                put("dialect", DataTypeMapper.dialectMapper.get(configurationInfo.getDatabaseType().toLowerCase()));
            }});

            GeneratorUtils.createFile(filePath, fileContent);
        }
    }
}
