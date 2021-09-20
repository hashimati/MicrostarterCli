package io.hashimati.security;
interface PasswordEncoder {
    fun encode(rawPassword: String?): String?
    fun matches(rawPassword: String?, encodedPassword: String?): Boolean
    fun upgradeEncoding(encodedPassword: String?): Boolean
}
