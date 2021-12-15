package io.hashimati.security.services;


import io.hashimati.security.domains.RefreshToken;
import io.hashimati.security.repository.RefreshTokenRepository;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.errors.OauthErrorResponseException;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Optional;

import static io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT;

@Singleton
public class CustomRefreshTokenPersistence implements RefreshTokenPersistence {
    private final RefreshTokenRepository refreshTokenRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomRefreshTokenPersistence.class);

    public CustomRefreshTokenPersistence(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void persistToken(RefreshTokenGeneratedEvent event) {
        log.info("Persist Token by {}", event.getAuthentication().getName());
        if (event != null &&
                event.getRefreshToken() != null &&
                event.getAuthentication() != null &&
                event.getAuthentication().getName() != null) {
            String payload = event.getRefreshToken();
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setRefreshToken(payload);
            refreshToken.setUsername(event.getAuthentication().getName());
            refreshToken.setRevoked(false);
            refreshTokenRepository.save(refreshToken);
        }
    }

    @Override
    public Publisher<Authentication> getAuthentication(String refreshToken) {
        log.info("Get Authentication by refreshToken:{}", refreshToken);
        return Flux.create(emitter -> {
            Optional<RefreshToken> tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken);
            if (tokenOpt.isPresent()) {
                RefreshToken token = tokenOpt.get();
                if (token.getRevoked()) {
                    emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null));
                } else {
                    emitter.next(Authentication.build(token.getUsername()));
                    emitter.complete();
                }
            } else {
                emitter.error(new OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null));
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}

