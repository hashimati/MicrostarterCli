package io.hashimati

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = @Info(
            title = "demo",
            version = "0.0"
    )
)
import groovy.transform.CompileStatic

@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.run(Application, args)
    }
}
