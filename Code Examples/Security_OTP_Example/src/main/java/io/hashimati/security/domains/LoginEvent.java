package io.hashimati.security.domains;

import lombok.Data;

import java.time.Instant;

@Data
public class LoginEvent {

    private String username, password;
    private LoginStatus status;
    private Instant lastTryDate;
    private Instant lastTimeLogin;

}

