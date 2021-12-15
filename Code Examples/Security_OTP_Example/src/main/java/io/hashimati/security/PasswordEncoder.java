package io.hashimati.security;

public interface PasswordEncoder {

    public String encode(String rawPassword);
    public boolean matches(String rawPassword, String encodedPassword);
    public boolean upgradeEncoding(String encodedPassword);



}

