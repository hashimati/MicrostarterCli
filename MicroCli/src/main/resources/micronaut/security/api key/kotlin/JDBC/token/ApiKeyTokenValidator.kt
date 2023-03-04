package ${securityPackage}.token

import ${securityPackage}.repository.ApiKeyRepository
import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.token.validator.TokenValidator
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import java.time.Instant

@Singleton
class ApiKeyTokenValidator(apiKeyRepository: ApiKeyRepository) : TokenValidator {
    private val apiKeyRepository: ApiKeyRepository

    init {
        this.apiKeyRepository = apiKeyRepository
    }

    fun validateToken(token: String?, request: HttpRequest<*>?): Publisher<Authentication> {
        return if (request == null || !request.path.startsWith("/api")) {
            Publishers.empty<Authentication>()
        } else apiKeyRepository.findByKey(token)
            .filter { x -> x.getExpiry().isAfter(Instant.now()) }
            .map { principle -> Authentication.build(principle.getName()) }
            .map(Publishers::just).orElse(Publishers.empty())
    }
}