package io.hashimati.microcli.services;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class MicronautJWTSecurityGenerator {
    @Inject
    private TemplatesService templatesService;

    public String generateUserEntity()
    {

        return "";
    }
    public String generateUserRepository()
    {

        return "";
    }
    public String generateUserService()
    {

        return "";
    }
    public String generateUserController()
    {

        return "";
    }
    public String generatePasswrodEncoder(){
        return "";

    }
    public String generateAuthenticationProvider()
    {
        return "";
    }
    public String generateLoginEvent(){
        return "";
    }
    public String generateLoginStatus(){
        return "";
    }
    public String generateRoles(String... roles){
        return generateRoles(Arrays.asList(roles));
    }
    public String generateRoles(List<String> roles){
        return "";
    }

}

