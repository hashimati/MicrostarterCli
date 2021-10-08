package micronaut.security.jwt.groovy.mongoReactive.domains

{securityPackage}.domains

import groovy.transform.Canonical

import java.time.Instant

@Canonical
public class RefreshToken {
    private String id
    private String username
    private String refreshToken
    private Boolean revoked
    private Instant dateCreated
}
