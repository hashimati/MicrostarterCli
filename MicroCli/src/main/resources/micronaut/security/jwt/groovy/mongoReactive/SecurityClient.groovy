package micronaut.security.jwt.groovy.mongoReactive

{securityPackage}

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken


@Client
interface SecurityClient {


    @Get("/login")
    BearerAccessRefreshToken login(@Body  UsernamePasswordCredentials credentials);
}