package io.hashimati.security;

import jakarta.inject.Singleton;
import org.jasypt.util.password.StrongPasswordEncryptor;

@Singleton
public class PasswordEncoderService implements PasswordEncoder{
    private StrongPasswordEncryptor strongPasswordEncryptor = new StrongPasswordEncryptor();
    @Override
    public String encode(String rawPassword) {
        return strongPasswordEncryptor.encryptPassword(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return strongPasswordEncryptor.checkPassword(rawPassword, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        throw new RuntimeException("This function is not supported");
    }
}

