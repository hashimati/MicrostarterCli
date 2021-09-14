package io.hashimati.security

import PasswordEncoder
import jakarta.inject.Singleton
import org.jasypt.util.password.StrongPasswordEncryptor

@Singleton
class PasswordEncoderService {
    private val strongPasswordEncryptor: StrongPasswordEncryptor = StrongPasswordEncryptor()
    fun encode(rawPassword: String?): String {
        return strongPasswordEncryptor.encryptPassword(rawPassword)
    }

    fun matches(rawPassword: String?, encodedPassword: String?): Boolean {
        return strongPasswordEncryptor.checkPassword(rawPassword, encodedPassword)
    }

    fun upgradeEncoding(encodedPassword: String?): Boolean {
        throw RuntimeException("This function is not supported")
    }
}