package io.hashimati.services;


import io.micronaut.email.Email;
import io.micronaut.email.EmailSender;
import jakarta.inject.Singleton;

@Singleton
public class HelloEmailService {

    private EmailSender<?, ?> emailSender;

    public HelloEmailService(EmailSender<?, ?> emailSender) {
        this.emailSender = emailSender;
    }
    void sendWelcomeEmail() {
        emailSender.send(Email.builder()
                .from("sender@example.com")
                .to("john@example.com")
                .subject("Micronaut test")
                .body("<html><body><strong>Hello</strong> dear Micronaut user.</body></html>", "Hello dear Micronaut user"));



    }
}
