package io.hashimati.microcli.services;

import io.hashimati.microcli.constants.ProjectConstants;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.GROOVY_LANG;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.KOTLIN_LANG;

@Singleton
public class MicronautJWTSecurityGenerator {
    @Inject
    private TemplatesService templatesService;

    public String generateUserEntity(String language)
    {
        String templatePath;
        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(TemplatesService.SECURITY_USER);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(TemplatesService.SECURITY_USER);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(TemplatesService.SECURITY_USER);

        return "";
    }
    public String generateUserRepository(String language, String database)
    {
        String templatePath;
        String key = database.equalsIgnoreCase("mongodb")? TemplatesService.SECURITY_USER_MONGO_REPOSITORY: TemplatesService.SECURITY_USER_REPOSITORY;
        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(key);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(key);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(key);

        return "";
    }
    public String generateUserService(String language, String database)
    {
        String templatePath;
        String key = database.equalsIgnoreCase("mongodb")? TemplatesService.SECURITY_USER_MONGO_SERVICE: TemplatesService.SECURITY_USER_SERVICE;

        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(key);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(key);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(key);

        return "";
    }
    public String generateUserController(String language, String database)
    {
        String templatePath;
        String key = database.equalsIgnoreCase("mongodb")? TemplatesService.SECURITY_USER_MONGO_CONTROLLER: TemplatesService.SECURITY_USER_CONTROLLER;

        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(key);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(key);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(key);


        return "";
    }
    public String generatePasswrodEncoder(String language){
        String templatePath;
        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(TemplatesService.SECURITY_PASSWORD_ENCODER);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(TemplatesService.SECURITY_PASSWORD_ENCODER);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(TemplatesService.SECURITY_PASSWORD_ENCODER);

        return "";

    }
    public String generateAuthenticationProvider(String language)
    {
        String templatePath;
        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(TemplatesService.SECURITY_AUTHENTICATION_PROVIDER);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(TemplatesService.SECURITY_AUTHENTICATION_PROVIDER);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(TemplatesService.SECURITY_AUTHENTICATION_PROVIDER);

        return "";
    }
    public String generateLoginEvent(String language){
        String templatePath;
        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(TemplatesService.SECURITY_LOGIN_EVENT);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(TemplatesService.SECURITY_LOGIN_EVENT);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(TemplatesService.SECURITY_LOGIN_EVENT);

        return "";
    }
    public String generateLoginStatus(String language){
        String templatePath;
        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(TemplatesService.SECURITY_LOGIN_STATUS);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(TemplatesService.SECURITY_LOGIN_STATUS);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(TemplatesService.SECURITY_LOGIN_STATUS);

        return "";
    }
    public String generateRoles( String language, String... roles){

        return generateRoles(language ,Arrays.asList(roles));
    }
    public String generateRoles(String language,List<String> roles){
        String templatePath;
        if(language.equalsIgnoreCase(GROOVY_LANG)) {
            templatePath = templatesService.getGroovyTemplates().get(TemplatesService.SECURITY_ROLES);
        } else if(language.equalsIgnoreCase(KOTLIN_LANG)) {
            templatePath = templatesService.getKotlinTemplates().get(TemplatesService.SECURITY_ROLES);
        }
        else
            templatePath = templatesService.getJavaTemplates().get(TemplatesService.SECURITY_ROLES);


        return "";
    }

}

