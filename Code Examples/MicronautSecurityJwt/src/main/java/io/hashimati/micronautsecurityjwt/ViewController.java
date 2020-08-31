package io.hashimati.micronautsecurityjwt;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/")
public class ViewController {


    public static  Logger logger = LoggerFactory.getLogger(ViewController.class);
    @View("index")
    @Get("/")
    public HttpResponse home()
    {
            return HttpResponse.ok(); 
    }
    
}