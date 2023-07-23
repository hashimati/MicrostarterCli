package ${securityPackage}.domains;

import lombok.Data;

import java.time.Instant;

@Data
public class RefreshToken {
    private String id;
    private String username;
    private String refreshToken;
    private Boolean revoked;
    private Instant dateCreated;
}
