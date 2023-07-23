package ${securityPackage}.controllers

import ${securityPackage}.domains.APIKey
import ${securityPackage}.repository.ApiKeyRepository
import ${securityPackage}.token.ApiKeyTokenGenerator
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import jakarta.inject.Inject
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Controller("/api/security/apikey")
class ApiKeyController {
    @Inject
    private val apiKeyRepository: ApiKeyRepository? = null

    @Inject
    private val apiKeyTokenGenerator: ApiKeyTokenGenerator? = null
    @Produces(MediaType.TEXT_PLAIN)
    @Post("/generateKey")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun  // secure this method in the production environment
            generateKey(@Body request: HashMap<String?, String?>): String {
        val apiKey = APIKey(request["name"], "123456789")
        apiKey.setExpiry(Instant.now().plus(5, ChronoUnit.MINUTES))
        apiKey.setKey(apiKeyTokenGenerator.generateKey(request["name"], request["password"], Instant.now()))
        return apiKeyRepository.save(apiKey).getKey()
    }
}