package ${securityPackage}.domains

import groovy.transform.Canonical
import ${securityPackage}.domains.LoginStatus

@Canonical
class LoginEvent {
    String username, password
    LoginStatus status
    Date lastTryDate
    Instant lastTimeLogin
}

