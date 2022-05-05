package io.hashimati.provider;


import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import io.micronaut.email.javamail.sender.MailPropertiesProvider;
import io.micronaut.email.javamail.sender.SessionProvider;
import jakarta.inject.Singleton;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Singleton
public class GmailProvider implements SessionProvider {

    private final Properties properties;
    private final String username;
    private final String password;

    GmailProvider(MailPropertiesProvider mailPropertiesProvider,
                  @Value("${io.hashimati.username}") String username,
                  @Value("${io.hashimati.password}") String password) {
        this.username = username;
        this.password = password;
        this.properties = mailPropertiesProvider.mailProperties();
    }

    @Override
    public Session session() {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    }
}
