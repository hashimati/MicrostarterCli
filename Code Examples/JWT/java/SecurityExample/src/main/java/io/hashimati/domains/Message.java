package io.hashimati.domains;

/**
 * @author Ahmed Al Hashmi @hashimati
 */

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class Message<T> {
    private MessageType messageType;
    private String message;
    private T data;
    private MessageCode code;
}
