package io.hashimati.security.services

import io.hashimati.security.domains.RefreshToken
import io.hashimati.security.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

import java.time.Instant

import static io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT

@Singleton
 class CustomRefreshTokenPersistence implements RefreshTokenPersistence {


    private final RefreshTokenRepository refreshTokenRepository
     CustomRefreshTokenPersistence(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository
    }
    @Override
     void persistToken(RefreshTokenGeneratedEvent event) {
        if (event != null &&
                event.getRefreshToken() != null &&
                event.getAuthentication() != null &&
                event.getAuthentication().getName() != null) {
            String payload = event.getRefreshToken()

            RefreshToken refreshToken = new RefreshToken()
            refreshToken.setId(event.getAuthentication().getName())
            refreshToken.setUsername(event.getAuthentication().getName())
            refreshToken.setRevoked(false)
            refreshToken.setRefreshToken(payload)
            refreshToken.setDateCreated(Instant.now())
            refreshTokenRepository.save(refreshToken).block()
        }
    }

    @Override
     Publisher<Authentication> getAuthentication(String refreshToken) {


        return Flux.create(emitter -> {
            Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken)
            if (tokenOpt.isPresent()) {
                RefreshToken token = tokenOpt.get()
                if (token.getRevoked()) {
                    emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null))
                } else {
                    emitter.next(Authentication.build(token.getUsername()))
                    emitter.complete()
                }
            } else {
                emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}

