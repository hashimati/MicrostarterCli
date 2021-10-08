package micronaut.security.jwt.groovy.JDBC.services

{securityPackage}.services

import ${securityPackage}.domains.RefreshToken
import ${securityPackage}.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class CustomRefreshTokenPersistence implements RefreshTokenPersistence {
    private final RefreshTokenRepository refreshTokenRepository
    private static final Logger log = LoggerFactory.getLogger(micronaut.security.jwt.groovy.mongoReactive.services.CustomRefreshTokenPersistence.class)

    CustomRefreshTokenPersistence(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository
    }

    @Override
    void persistToken(RefreshTokenGeneratedEvent event) {
        log.info("Persist Token by {}", event.getAuthentication().getName())
        if (event != null &&
                event.getRefreshToken() != null &&
                event.getAuthentication() != null &&
                event.getAuthentication().getName() != null) {
            String payload = event.getRefreshToken()
            RefreshToken refreshToken = new RefreshToken()
            refreshToken.setRefreshToken(payload)
            refreshToken.setUsername(event.getAuthentication().getName())
            refreshToken.setRevoked(false)
            refreshTokenRepository.save(refreshToken)
        }
    }

    @Override
    Publisher<Authentication> getAuthentication(String refreshToken) {
        log.info("Get Authentication by refreshToken:{}", refreshToken)
        return Flux.create(emitter -> {
            Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken)
            if (tokenOpt.isPresent()) {
                RefreshToken token = tokenOpt.get()
                if (token.getRevoked()) {
                    emitter.error(new OauthErrorResponseException(io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT, "refresh token revoked", null))
                } else {
                    emitter.next(Authentication.build(token.getUsername()))
                    emitter.complete()
                }
            } else {
                emitter.error(new OauthErrorResponseException(io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT, "refresh token not found", null))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
