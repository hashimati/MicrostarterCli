package io.hashimati.gorm

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class TestingGroovyGormTest {

    @Inject
    EmbeddedApplication application

    @Test
    void testItWorks() {
        assert application.running == true
    }

}
