package io.hashimati.ssl.controller;


import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/h")
public class Hello {


    @Get("/")
    public String hello(@QueryValue("n") String name){return "Hello " + name; }

}
