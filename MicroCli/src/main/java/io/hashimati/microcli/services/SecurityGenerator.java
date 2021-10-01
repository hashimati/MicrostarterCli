package io.hashimati.microcli.services;


import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.MicronautProjectValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Singleton
public class SecurityGenerator {

//
    @Inject
    private TemplatesService templatesService;
    @Inject
    private HashMap<String, Feature> features  ;

    public void generateSecurityFiles(String strategy, ArrayList<String> roles, boolean persistRefreshToken) throws IOException, GradleReaderException {
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));
        String rolesDeclaration = "";
        if(!roles.isEmpty())
            rolesDeclaration =roles.stream().map(x->new EntityAttribute(){{
            setName(x);
            setType("String");
        }}.getFinalStaticDeclaration(configurationInfo.getProjectInfo().getSourceLanguage(), x))
                        .reduce((x, y)-> new StringBuilder(x).append(y).toString()).get();

        auxGenerateSecurityFiles(strategy, rolesDeclaration,persistRefreshToken, templatesService.getSecurityTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration,persistRefreshToken, templatesService.getSecurityControllerTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration,persistRefreshToken, templatesService.getSecurityDomainsTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration,persistRefreshToken, templatesService.getSecurityRepositoryTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration,persistRefreshToken, templatesService.getSecurityServicesTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, rolesDeclaration,persistRefreshToken, templatesService.getSecurityUtilsTemplates(), configurationInfo);
        if(persistRefreshToken)
            auxGenerateSecurityFiles(strategy, rolesDeclaration, persistRefreshToken,templatesService.getSecurityRefreshTokenTemplates(), configurationInfo);

        configurationInfo.setSecurityRoles(roles);

        if(!configurationInfo.getProjectInfo().getFeatures().contains("security-annotations"))
        {
            configurationInfo.getProjectInfo().getFeatures().add("security-annotations");
            MicronautProjectValidator.addDependency(features.get("security-annotations"));
        }
        if(!configurationInfo.getProjectInfo().getFeatures().contains("security-jwt"))
        {
            configurationInfo.getProjectInfo().getFeatures().add("security-jwt");
            MicronautProjectValidator.addDependency(features.get("security-jwt"));
            MicronautProjectValidator.appendToProperties(
                    GeneratorUtils.getFileContent(
                            new File(
                                    templatesService.getSecurityPropertiesTemplates().get(TemplatesService.SECURITY_JWT_PROPERTIES)
                            )
                    )

            );
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
                put("db", db);
                put("lang", lang);
                put("ext", ext);
            }});

            String filePath = System.getProperty("user.dir")+"/src/main/"+ lang + "/"+ GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage())
                    + path.substring(path.indexOf("/security")).replace("/"+ lang + "/" + db, "" );
            String template = templatesService.loadTemplateContent(path);

            String securityPackage = new StringBuilder().append(configurationInfo.getProjectInfo().getDefaultPackage()).append(".security").toString();

            String fileContent = GeneratorUtils.generateFromTemplate(template, new HashMap<String, String>() {{
                put("securityPackage", securityPackage);
                put("roles", "");
                put("persistToken", ""+persistRefreshToken);
            }});

            System.out.println("-----");
            System.out.println(filePath);
            System.out.println(fileContent);
            System.out.println("-----");

            GeneratorUtils.createFile(filePath, fileContent);
        }
    }
}
