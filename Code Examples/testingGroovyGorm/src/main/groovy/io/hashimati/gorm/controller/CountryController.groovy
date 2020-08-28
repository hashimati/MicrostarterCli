package io.hashimati.gorm.controller

import com.sun.org.slf4j.internal.LoggerFactory
import io.hashimati.gorm.domains.Country
import io.hashimati.gorm.services.CountryService
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.reactivex.Flowable
import io.reactivex.Single

import javax.inject.Inject
import java.util.logging.Logger

@Controller("/")
class CountryController {
    @Inject
    CountryService countryService
    static final Logger log = LoggerFactory.getLogger(CountryController.class);


    @Post("/save")
    Single<Country> save(@Body Country country)
    {
        log.info("Saving  country : {}", country);

        //TODO: enter your logic here before saving.
        return countryService
    }


    @Get("/find")
    Single<Country> findById(@Parameter("id") String id)
    {
        log.info("Finding Country By Id: {}", id);

        return CountryService.findById(id)
    }

    @Get("/find/all")
    Flowable<Country> findAll()
    {
        log.info("Finding all ${className}");
        return CountryService.findAll()
    }
}
