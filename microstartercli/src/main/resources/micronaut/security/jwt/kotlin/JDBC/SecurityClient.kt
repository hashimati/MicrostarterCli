package ${securityPackage}

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.render.BearerAccessRefreshToken

@Client("/api/security/")
interface SecurityClient {
    @Get("/login")
    fun login(@Body credentials: UsernamePasswordCredentials?): BearerAccessRefreshToken?
}