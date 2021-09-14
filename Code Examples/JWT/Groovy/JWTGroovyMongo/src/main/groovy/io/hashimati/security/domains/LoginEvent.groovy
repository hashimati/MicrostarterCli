package io.hashimati.security.domains

import groovy.transform.Canonical

@Canonical
class LoginEvent {
    String username, password
    LoginStatus status
    Date lastTryDate
    Date lastTimeLogin
}

