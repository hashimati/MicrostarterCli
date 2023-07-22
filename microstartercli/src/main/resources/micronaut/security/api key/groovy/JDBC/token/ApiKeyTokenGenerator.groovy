package ${securityPackage}.token


import jakarta.inject.Singleton
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor

import java.time.Instant

@Singleton
class ApiKeyTokenGenerator {

    String generateKey(String name,String password,  Instant time)
    {

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor()

        encryptor.setPassword(password)
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES")
        return  encryptor.encrypt(new StringBuilder()
                .append(name)
                .append( time.toString()).toString())

    }
}
