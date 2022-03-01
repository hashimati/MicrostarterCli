package io.hashimati.microcli.domains;


import io.micronaut.asm.commons.Method;
import io.micronaut.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class URL {
    private String scope ;
    private String url;
    private HttpMethod method;
    private HashSet<String> roles = new HashSet<String>();
}
