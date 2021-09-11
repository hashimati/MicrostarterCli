package io.hashimati.security.domains;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.util.Date;

@Data
@Introspected
public class LoginEvent {
    private String username, password;
    private LoginStatus status;
    private Date lastTryDate;
    private Date lastTimeLogin;
}
