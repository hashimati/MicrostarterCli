package ${securityPackage}.domains

import java.time.Instant

data class RefreshToken (
    var id: String? = null,
    var username: String? = null,
    var refreshToken: String? = null,
    var revoked: Boolean? = null,
    var dateCreated: Instant? = null,
){
    
}