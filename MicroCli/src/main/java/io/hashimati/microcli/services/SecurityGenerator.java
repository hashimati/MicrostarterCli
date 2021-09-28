package io.hashimati.microcli.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.JAVA_LANG;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.KOTLIN_LANG;

@Singleton
public class SecurityGenerator {

//
    @Inject
    private TemplatesService templatesService;



    public void generateSecurityFiles(String strategy, ArrayList<String> roles) throws FileNotFoundException, JsonProcessingException {
        ConfigurationInfo configurationInfo = ConfigurationInfo.fromFile(new File(ConfigurationInfo.getConfigurationFileName()));

        ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();
        System.out.println(loader.getResource("classpath:micronaut/security").get());

        String db = configurationInfo.getDataBackendRun(),
                lang = configurationInfo.getProjectInfo().getSourceLanguage(),
                ext = lang.equalsIgnoreCase(KOTLIN_LANG)? "kt":lang;
        for(String key : templatesService.getSecurityTemplates().keySet()){
            String path = templatesService.getSecurityTemplates().get(key);
            path = GeneratorUtils.generateFromTemplate(path, new HashMap<String, String>(){{
                put("db", db);
                put("lang", lang);
                put("ext", ext);
            }});

            System.out.println(templatesService.loadTemplateContent(path));
        }
        //System.out.println(templatesService.loadTemplateContent("micronaut/security/java/JDBC/domains/LoginEvent.java"));
    }
}
