package io.hashimati.microcli.services;

import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.utils.GeneratorUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

@Singleton
public class MicronautJWTSecurityGenerator {
    @Inject
    private TemplatesService templatesService;

    public String generateUserEntity(String language)
    {
        String key =  TemplatesService.SECURITY_USER;
        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }
    public String generateUserRepository(String language, String database)
    {

        String key = database.equalsIgnoreCase("mongodb")? TemplatesService.SECURITY_USER_MONGO_REPOSITORY: TemplatesService.SECURITY_USER_REPOSITORY;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }
    public String generateUserService(String language, String database)
    {

        String key = database.equalsIgnoreCase("mongodb")? TemplatesService.SECURITY_USER_MONGO_REPOSITORY: TemplatesService.SECURITY_USER_REPOSITORY;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }
    public String generateUserController(String language, String database)
    {

        String key = database.equalsIgnoreCase("mongodb")? TemplatesService.SECURITY_USER_MONGO_REPOSITORY: TemplatesService.SECURITY_USER_REPOSITORY;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }
    public String generatePasswrodEncoder(String language){
        String key =  TemplatesService.SECURITY_PASSWORD_ENCODER;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );

    }
    public String generateAuthenticationProvider(String language)
    {
        String key =  TemplatesService.SECURITY_AUTHENTICATION_PROVIDER;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }
    public String generateLoginEvent(String language){
        String key =  TemplatesService.SECURITY_LOGIN_EVENT;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }
    public String generateLoginStatus(String language){
        String key =  TemplatesService.SECURITY_LOGIN_STATUS;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }
    public String generateRoles( String language, String... roles){

        return generateRoles(language ,Arrays.asList(roles));
    }
    public String generateRoles(String language,List<String> roles){
        String key =  TemplatesService.SECURITY_ROLES;

        HashMap<String, String> map = new HashMap<>();

        return generate(key, map, language );
    }


    public String generate(String key, HashMap<String, String> map, String lang)
    {
        String templatePath = "";
        switch (lang.toLowerCase())
        {
            case JAVA_LANG:
                templatePath =  templatesService.getJavaTemplates().get(key);
                break;
            case KOTLIN_LANG:
                templatePath =  templatesService.getKotlinTemplates().get(key);
                break;
            case GROOVY_LANG:
                templatePath =  templatesService.getGroovyTemplates().get(key);
                break;
            default:
                templatePath =  templatesService.getJavaTemplates().get(key);
                break;
        }
        return GeneratorUtils.generateFromTemplate(templatesService.loadTemplateContent(templatePath), map);
    }

}

