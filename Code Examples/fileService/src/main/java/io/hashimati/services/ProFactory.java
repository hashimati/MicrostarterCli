package io.hashimati.services;


import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

import java.util.HashMap;

@Singleton
public class ProFactory {
    @Bean
    private HashMap<String, String> fileRepositroy = new HashMap<>();
}
