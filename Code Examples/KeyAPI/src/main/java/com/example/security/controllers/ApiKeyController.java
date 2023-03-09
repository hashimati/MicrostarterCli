package com.example.security.controllers;

import com.example.security.domains.APIKey;
import com.example.security.repository.ApiKeyRepository;
import com.example.security.token.ApiKeyTokenGenerator;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.HashMap;

@Controller("/api/security/apikey")
public class ApiKeyController {
    public ApiKeyController() {

    }

    @Inject
    private ApiKeyRepository apiKeyRepository;

    @Inject
    private ApiKeyTokenGenerator apiKeyTokenGenerator;

    @Produces(MediaType.TEXT_PLAIN)
    @Post("/generateKey")
    @Secured(SecurityRule.IS_ANONYMOUS) // secure this method in the production environment
    String generateKey(@Body HashMap<String, String> request) {
        APIKey apiKey = new APIKey(request.get("name"), "123456789");
        apiKey.setExpiry(Instant.now().plus(10000, java.time.temporal.ChronoUnit.MINUTES));
        apiKey.setSecret("Hello");
        return apiKeyRepository.save(apiKey).getSecret();


    }

}


