package ${securityPackage}.services;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.handlers.LogoutHandler;
import jakarta.inject.Singleton;

@Singleton
public class CustomLogoutHandler implements LogoutHandler {



    @Override
    public MutableHttpResponse<?> logout(HttpRequest<?> request) {

        return null;
    }
}
