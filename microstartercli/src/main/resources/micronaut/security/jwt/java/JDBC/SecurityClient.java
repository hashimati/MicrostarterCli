package ${securityPackage};


import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.render.BearerAccessRefreshToken;

@Client("/api/security/")
public interface SecurityClient {


    @Post("/login")
    public BearerAccessRefreshToken login(@Body  UsernamePasswordCredentials credentials);
}
