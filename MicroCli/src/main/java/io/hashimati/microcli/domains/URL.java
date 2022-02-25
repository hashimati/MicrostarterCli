package io.hashimati.microcli.domains;


import io.micronaut.asm.commons.Method;
import io.micronaut.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class URL {
    private String url;
    private HttpMethod method;
}
