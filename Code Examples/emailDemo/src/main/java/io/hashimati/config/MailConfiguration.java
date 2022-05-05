package io.hashimati.config;


import io.micronaut.context.annotation.Configuration;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.runtime.context.env.ConfigurationAdvice;

@ConfigurationProperties("mail")
public class MailConfiguration {

    private String username;
    private String password;

}
