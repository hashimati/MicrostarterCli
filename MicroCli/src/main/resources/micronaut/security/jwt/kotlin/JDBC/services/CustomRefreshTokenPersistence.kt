package ${securityPackage}.services

import ${securityPackage}.domains.RefreshToken
import ${securityPackage}.repository.RefreshTokenRepository
import ${securityPackage}.authentication.Authentication
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.Instant


@Singleton
class CustomRefreshTokenPersistence(private val refreshTokenRepository: RefreshTokenRepository) :
    RefreshTokenPersistence {
    override fun persistToken(event: RefreshTokenGeneratedEvent) {
        log.info("Persist Token by {}", event.authentication.name)
        if ( event.refreshToken != null && event.authentication != null && event.authentication.name != null) {
            val payload = event.refreshToken
            val refreshToken = RefreshToken(1, "", "", false, Instant.now());
            refreshToken.refreshToken = payload
            refreshToken.username = event.authentication.name
            refreshToken.revoked = false
            refreshTokenRepository.save(refreshToken)
        }
    }

    override fun getAuthentication(refreshToken: String): Publisher<Authentication> {
        log.info("Get Authentication by refreshToken:{}", refreshToken)
        return Flux.create({ emitter: FluxSink<Authentication> ->
            val tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken)
            if (tokenOpt!!.isPresent) {
                val (_, username, _, revoked) = tokenOpt.get()
                if (revoked) {
                    emitter.error(
                        OauthErrorResponseException(
                            IssuingAnAccessTokenErrorCode.INVALID_GRANT,
                            "refresh token revoked",
                            null
                        )
                    )
                } else {
                    emitter.next(Authentication.build(username))
                    emitter.complete()
                }
            } else {
                emitter.error(
                    OauthErrorResponseException(
                        IssuingAnAccessTokenErrorCode.INVALID_GRANT,
                        "refresh token not found",
                        null
                    )
                )
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CustomRefreshTokenPersistence::class.java)
    }
}
