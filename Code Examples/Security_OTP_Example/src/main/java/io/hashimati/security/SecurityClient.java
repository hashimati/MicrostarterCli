package io.hashimati.security;


import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;

@Client
public interface SecurityClient {


    @Get("/login")
    public BearerAccessRefreshToken login(@Body  UsernamePasswordCredentials credentials);
}

