package io.hashimati.security.services;

import io.hashimati.security.repository.RefreshTokenRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.endpoints.LogoutController;
import io.micronaut.security.handlers.LogoutHandler;
import jakarta.inject.Singleton;

@Singleton
public class CustomLogoutHandler implements LogoutHandler {

    private final RefreshTokenRepository refreshTokenRepository;

    public CustomLogoutHandler(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;

    }

    @Override
    public MutableHttpResponse<?> logout(HttpRequest<?> request) {

        return null;
    }
}