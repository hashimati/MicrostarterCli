package io.hashimati.security.services

import io.hashimati.security.domains.RefreshToken
import io.hashimati.security.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.Instant

@Singleton
class CustomRefreshTokenPersistence(private val refreshTokenRepository: RefreshTokenRepository) :
    RefreshTokenPersistence {
    override fun persistToken(event: RefreshTokenGeneratedEvent) {
        if (event != null && event.refreshToken != null && event.authentication != null && event.authentication.name != null) {
            val payload = event.refreshToken
            val refreshToken = RefreshToken()
            refreshToken.id = event.authentication.name
            refreshToken.username = event.authentication.name
            refreshToken.revoked = false
            refreshToken.refreshToken = payload
            refreshToken.dateCreated = Instant.now()
            refreshTokenRepository.save(refreshToken).block()
        }
    }

    override fun getAuthentication(refreshToken: String): Publisher<Authentication> {
        return Flux.create({ emitter: FluxSink<Authentication> ->
            val tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken)
            if (tokenOpt.isPresent) {
                val (_, username, _, revoked) = tokenOpt.get()
                if (revoked!!) {
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
}