package io.hashimati.micronautsecurityjwtgroovy.clients



import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client;
import javax.inject.Inject
import io.reactivex.Flowable
import io.reactivex.Single

import io.hashimati.micronautsecurityjwtgroovy.domains.User


@Client("/api/user")
interface UserClient {

    @Post("/save")
    User save(User user)

    @Get("/get")
    User findById(@Parameter("id") long id)

    @Delete("/delete/{id}")
    boolean deleteById(@PathVariable("id") long id)

}