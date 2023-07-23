package ${securityPackage}.controllers;

import ${securityPackage}.domains.APIKey;
import ${securityPackage}.repository.ApiKeyRepository;
import ${securityPackage}.token.ApiKeyTokenGenerator;
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
        apiKey.setExpiry(Instant.now().plus(500, java.time.temporal.ChronoUnit.MINUTES));
        apiKey.setSecret(apiKeyTokenGenerator.generateKey(request.get("name"), request.get("password"), Instant.now()));
        return apiKeyRepository.save(apiKey).getSecret();


    }

}

