package io.hashimati.security.domains;

import io.micronaut.security.authentication.UsernamePasswordCredentials;
import lombok.Data;

import java.util.Date;

@Data
public class LoginEvent {

    private String username, password;
    private LoginStatus status;
    private Date lastTryDate;
    private Date lastTimeLogin;

}
