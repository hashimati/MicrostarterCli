package io.hashimati.microcli.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.GeneratorUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.KOTLIN_LANG;

@Singleton
public class SecurityGenerator {

//
    @Inject
    private TemplatesService templatesService;



    public void generateSecurityFiles(String strategy, ArrayList<String> roles) throws IOException {
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));

        auxGenerateSecurityFiles(strategy, roles, templatesService.getSecurityTemplates(), configurationInfo);


        auxGenerateSecurityFiles(strategy, roles, templatesService.getSecurityControllerTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, roles, templatesService.getSecurityDomainsTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, roles, templatesService.getSecurityRepositoryTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, roles, templatesService.getSecurityServicesTemplates(), configurationInfo);
        auxGenerateSecurityFiles(strategy, roles, templatesService.getSecurityUtilsTemplates(), configurationInfo);

        //System.out.println(templatesService.loadTemplateContent("micronaut/security/java/JDBC/domains/LoginEvent.java"));
    }


    public void auxGenerateSecurityFiles(String strategy, ArrayList<String> roles, HashMap<String, String> templates, ConfigurationInfo configurationInfo) throws IOException {
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
            }});

            System.out.println("-----");
            System.out.println(filePath);
            System.out.println(fileContent);
            System.out.println("-----");
            GeneratorUtils.createFile(filePath, fileContent);
        }
    }
}
