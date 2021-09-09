package io.hashimati.controllers;

import io.hashimati.domains.User;
import io.hashimati.utils.Randomizer;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class UserControllerTest {

    @Inject
    @Client("/api/user")
    RxHttpClient client;

    User user;



    @Test
    void save() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        User user = new Randomizer<User>(User.class).getRandomInstance();
        HttpRequest<User> request = HttpRequest.POST("/save", user);
        this.user = client.toBlocking().retrieve(request, User.class);
        assertNotNull(user);
    }

    @Test
    void findById() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        save();
        HttpRequest<User> request = HttpRequest.GET("/get?id="+this.user.getId());
        User user = client.toBlocking().retrieve(request, User.class);
        assertNotNull(user);
        assertEquals(user.getId() , this.user.getId());
    }

    @Test
    void deleteById() throws InstantiationException, IllegalAccessException, NoSuchFieldException{
        save();
        HttpRequest<Boolean> request = HttpRequest.DELETE("/delete/"+this.user.getId());
        Boolean body= client.toBlocking().retrieve(request, Boolean.class);
        assertTrue(body.booleanValue());
    }

    @Test
    void findAll() throws InstantiationException, IllegalAccessException, NoSuchFieldException{

        save();
        HttpRequest<Iterable<User>> request = HttpRequest.GET("/findAll");
        Iterable<User> list = client.toBlocking().retrieve(request, Iterable.class);
        System.out.println(list);
        assertNotNull(list);

    }

    @Test
    void update() throws InstantiationException, IllegalAccessException, NoSuchFieldException{
        save();
        HttpRequest<User> request = HttpRequest.PUT("/update", this.user);
        User user = client.toBlocking().retrieve(request, User.class);
        assertNotNull(user);
    }
}

