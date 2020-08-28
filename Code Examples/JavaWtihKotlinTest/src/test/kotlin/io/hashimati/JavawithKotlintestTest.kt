package io.hashimati

import io.kotlintest.specs.StringSpec
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest

@MicronautTest
class JavawithKotlintestTest(private val application: EmbeddedApplication<*>): StringSpec({

    "test the server is running" {
        assert(application.isRunning())
    }
})
