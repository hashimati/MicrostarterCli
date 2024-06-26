package ${securityPackage}

import ${securityPackage}.PasswordEncoder
import org.jasypt.util.password.StrongPasswordEncryptor

@Singleton
class PasswordEncoderService implements PasswordEncoder{
    private StrongPasswordEncryptor strongPasswordEncryptor = new StrongPasswordEncryptor()
    @Override
    public String encode(String rawPassword) {
        return strongPasswordEncryptor.encryptPassword(rawPassword)
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return strongPasswordEncryptor.checkPassword(rawPassword, encodedPassword)
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        throw new RuntimeException("This function is not supported")
    }
}
