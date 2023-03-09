package com.example.controllers;

import com.example.domains.Fruit;
import com.example.utils.Randomizer;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;





import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class FruitControllerTest {

    @Inject
    @Client("/api/v1/fruit")
    HttpClient client;

    HashMap<String, Object> randomObject;
    

    @Test
    void save() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Randomizer randomizer= new Randomizer<Fruit>(Fruit.class);
        randomObject = randomizer.getRandomInstance();
        
        HttpRequest<HashMap<String, Object>> request = HttpRequest.POST("/save", randomObject).basicAuth("admin", "admin");
        this.randomObject = client.toBlocking().retrieve(request, HashMap.class);
        assertNotNull(randomObject);
    }

    @Test
    void findById() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        save();
         
        MutableHttpRequest<Object> request = HttpRequest.GET("/get?id="+this.randomObject.get("id")).basicAuth("admin", "admin");
        HashMap<String, Object> randomObject = client.toBlocking().retrieve(request, HashMap.class);
        assertNotNull(randomObject);
    }

    @Test
    void deleteById() throws InstantiationException, IllegalAccessException, NoSuchFieldException{
        save();
         
        MutableHttpRequest<Object> request = HttpRequest.DELETE("/delete/"+this.randomObject.get("id")).basicAuth("admin", "admin");
        Boolean body= client.toBlocking().retrieve(request, Boolean.class);
        assertTrue(body.booleanValue());
    }

    @Test
    void findAll() throws InstantiationException, IllegalAccessException, NoSuchFieldException{
        save();
         
        MutableHttpRequest<Object> request = HttpRequest.GET("/findAll").basicAuth("admin", "admin");
        Iterable<Fruit> list = client.toBlocking().retrieve(request, Iterable.class);
        assertNotNull(list);

    }

    @Test
    void update() throws InstantiationException, IllegalAccessException, NoSuchFieldException{
        save();
         
        HttpRequest<HashMap<String, Object>> request = HttpRequest.PUT("/update", this.randomObject).basicAuth("admin", "admin");
        HashMap<String, Object> randomObject = client.toBlocking().retrieve(request, HashMap.class);
        assertNotNull(randomObject);
    }
}


