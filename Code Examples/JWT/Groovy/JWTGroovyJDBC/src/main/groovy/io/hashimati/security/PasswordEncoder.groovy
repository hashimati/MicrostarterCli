package io.hashimati.security


interface PasswordEncoder {

    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
    boolean upgradeEncoding(String encodedPassword);



}