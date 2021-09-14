package io.hashimati.security.domains

import io.micronaut.core.annotation.Introspected
import java.time.Instant


@Introspected
data class LoginEvent (
 var username: String? = null,
 var password: String? = null,
 var status: LoginStatus? = null,
 var lastTryDate: Instant? = null,
 var lastTimeLogin: Instant? = null,
){

}