package io.hashimati;


import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.View;

import java.util.Collections;

@Controller("/")
public class controllers {

    @Get
    @View("Hello")
    public HttpResponse Hello(@QueryValue(defaultValue = "Hashim Al Hashmi") String name){

        return HttpResponse.ok(CollectionUtils.mapOf("name", name));
    }
}
