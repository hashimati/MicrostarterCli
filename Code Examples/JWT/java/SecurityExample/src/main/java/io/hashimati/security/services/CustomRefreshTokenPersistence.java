package io.hashimati.security.services;


import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent;
import io.micronaut.security.token.refresh.RefreshTokenPersistence;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class CustomRefreshTokenPersistence implements RefreshTokenPersistence {
    @Override
    public void persistToken(RefreshTokenGeneratedEvent event) {

    }

    @Override
    public Publisher<Authentication> getAuthentication(String refreshToken) {
        return null;
    }
}
