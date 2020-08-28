package io.hashimati;

import com.intuit.karate.junit5.Karate;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;


@MicronautTest
public class TestHelloController {
    @Inject
    EmbeddedApplication application;


    @Karate.Test
    Karate testHello(){
        System.out.println(application.isRunning());
        return Karate.run("classpath:karate/testHello.feature").relativeTo(getClass()).tags("@first");
    }
}
