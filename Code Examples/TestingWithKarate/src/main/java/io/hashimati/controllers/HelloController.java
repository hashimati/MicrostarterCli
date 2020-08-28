package io.hashimati.controllers;


import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller
public class HelloController {


    @Get(value = "/hello", produces = MediaType.TEXT_PLAIN)
    public String sayHello(@QueryValue(defaultValue = "World", value = "n") String name)
    {
        return "Hello " + name;

    }
}
