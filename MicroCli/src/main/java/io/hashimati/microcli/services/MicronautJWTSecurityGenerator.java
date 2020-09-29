package io.hashimati.microcli.services;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class MicronautJWTSecurityGenerator {
    @Inject
    private TemplatesService templatesService;

    public String generateUserEntity(String language)
    {

        return "";
    }
    public String generateUserRepository(String language)
    {

        return "";
    }
    public String generateUserService(String language)
    {

        return "";
    }
    public String generateUserController(String language)
    {

        return "";
    }
    public String generatePasswrodEncoder(String language){
        return "";

    }
    public String generateAuthenticationProvider(String language)
    {
        return "";
    }
    public String generateLoginEvent(String language){
        return "";
    }
    public String generateLoginStatus(String language){
        return "";
    }
    public String generateRoles( String language, String... roles){
        return generateRoles(language ,Arrays.asList(roles));
    }
    public String generateRoles(String language,List<String> roles){
        return "";
    }

}

