package io.hashimati.micronautsecurityjwtgroovy.controllers

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;



@Controller("/")
class ViewController {

        @View("index")
        @Get("/")
        public HttpResponse home()
        {
                return HttpResponse.ok(); 
        }
}