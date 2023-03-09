package com.example.security.token;


import io.micronaut.security.token.reader.HttpHeaderTokenReader;
import jakarta.inject.Singleton;

@Singleton
public class ApiKeyTokenReader extends HttpHeaderTokenReader {
    public  static final String X_API_XXX = "X-API-CCC";

    @Override
    protected String getPrefix() {
        return null;
    }

    @Override
    protected String getHeaderName() {
        return X_API_XXX ;
    }
}

