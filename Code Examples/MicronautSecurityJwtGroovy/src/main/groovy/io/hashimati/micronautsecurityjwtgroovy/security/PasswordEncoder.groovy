package io.hashimati.micronautsecurityjwtgroovy.security

import org.jasypt.util.password.StrongPasswordEncryptor;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;

@Factory
public class PasswordEncoder  {

    @Prototype
    public StrongPasswordEncryptor strongPasswordEncryptor(){
        return new StrongPasswordEncryptor();
    }

}
