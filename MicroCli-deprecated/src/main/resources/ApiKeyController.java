package io.hashimati.controllers;

import io.hashimati.domains.APIKey;
import io.hashimati.repository.ApiKeyRepository;
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

@Controller("/security")
public class ApiKeyController {
    public ApiKeyController() {

    }

    @Inject
    private ApiKeyRepository apiKeyRepository;

    @Produces(MediaType.TEXT_PLAIN)
    @Post("/generateKey")
    @Secured(SecurityRule.IS_ANONYMOUS)
    String generateKey(@Body HashMap<String, String> request) {
        APIKey apiKey = new APIKey(request.get("name"), request.get("key"));
        apiKey.setExpiry(Instant.now().plus(5, java.time.temporal.ChronoUnit.MINUTES));
        apiKeyRepository.save(apiKey);
        return "Hello " + apiKey.getName();

    }

}
