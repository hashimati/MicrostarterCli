package io.hashimati.domains;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class Fruit {
    private String id;
    private String name;
    private String letter;
}