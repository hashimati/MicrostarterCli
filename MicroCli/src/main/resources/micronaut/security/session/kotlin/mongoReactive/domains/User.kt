package ${securityPackage}.domains

import org.jetbrains.annotations.NotNull
import java.time.Instant
import javax.validation.constraints.Email
import javax.validation.constraints.Size

import kotlin.collections.HashSet

data class User (
    var id: String? = null,

    var username:  @Size(min = 5, max = 15) String? = null,

    var email:  @Email String? = null,

    var password:  String? = null,
    var roles: HashSet<String> = HashSet<String>(),

    var dateCreated: Instant? = null,
    var dateUpdated: Instant? = null,
    var active :Boolean = false,
    var disabled :Boolean = false,
    var locked :Boolean= false,
    //    Date expiration
    //
    //    Date passwordExpiration;
    var lastTimeLogin: Instant? = null,
    var lastTimeTryToLogin: Instant? = null,
    var lastLoginStatus: LoginStatus? = null,

    var activationCode: String? = null,
    var resetPasswordCode: String? = null,

    ){}