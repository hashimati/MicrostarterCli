package ${securityPackage}.token

import jakarta.inject.Singleton
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import java.time.Instant

@Singleton
class ApiKeyTokenGenerator {
    fun generateKey(name: String?, password: String?, time: Instant): String {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setPassword(password)
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES")
        return encryptor.encrypt(
            StringBuilder()
                .append(name)
                .append(time.toString()).toString()
        )
    }
}