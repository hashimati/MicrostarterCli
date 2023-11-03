package ${securityPackage}.domains;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import java.time.Instant;

@Data
@Introspected
public class LoginEvent {
    private String username, password;
    private LoginStatus status;
    private Date lastTryDate;
    private Instant lastTimeLogin;
}
