package ${securityPackage}.domains

import groovy.transform.Canonical

import java.time.Instant

@Canonical
class LoginEvent {

    String username, password
    LoginStatus status
    Instant lastTryDate
    Instant lastTimeLogin

}
