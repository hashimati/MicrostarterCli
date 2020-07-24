package io.hashimati.config;


import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.util.HashMap;

@Factory
public class FeaturesFactory {


    @Singleton
    public HashMap<String, Feature> features(){

        HashMap<String, Feature> features = new HashMap<>();
        features.putIfAbsent("flyway", new Feature(){{
            setName("flyway");
            setGradle("implementation(\"io.micronaut.flyway:micronaut-flyway\")");
            setMaven("<dependency>\n" +
                    "    <groupId>io.micronaut.flyway</groupId>\n" +
                    "    <artifactId>micronaut-flyway</artifactId>\n" +
                    "    <scope>compile</scope>\n" +
                    "</dependency>");

        }});

        features.put("jdbc-hikari", new Feature(){{
            setName("jdbc-hikari");
            setMaven("<dependency>\n" +
                    "    <groupId>io.micronaut.sql</groupId>\n" +
                    "    <artifactId>micronaut-jdbc-hikari</artifactId>\n" +
                    "    <scope>compile</scope>\n" +
                    "</dependency>\n");
            setGradle("implementation(\"io.micronaut.sql:micronaut-jdbc-hikari\")");
        }});

        features.put("data-jdbc", new Feature(){{
            setName("data-jdbc");
            setMaven("<dependency>\n" +
                    "    <groupId>io.micronaut.data</groupId>\n" +
                    "    <artifactId>micronaut-data-jdbc</artifactId>\n" +
                    "    <scope>compile</scope>\n" +
                    "</dependency>");
            setGradle("implementation(\"io.micronaut.data:micronaut-data-jdbc\")");
        }});

        features.put("mongo-reactive", new Feature(){{
            setName("mongo-reactive");
            setMaven("<dependency>\n" +
                    "    <groupId>io.micronaut.mongodb</groupId>\n" +
                    "    <artifactId>micronaut-mongo-reactive</artifactId>\n" +
                    "    <scope>compile</scope>\n" +
                    "</dependency>\n");
            setGradle("implementation(\"io.micronaut.mongodb:micronaut-mongo-reactive\")");
        }});

        features.put("data-hibernate-jpa", new Feature(){{
            setName("data-hibernate-jpa");
            setMaven("<dependency>\n" +
                    "    <groupId>io.micronaut.data</groupId>\n" +
                    "    <artifactId>micronaut-data-hibernate-jpa</artifactId>\n" +
                    "    <scope>compile</scope>\n" +
                    "</dependency>\n");
            setGradle("implementation(\"io.micronaut.data:micronaut-data-hibernate-jpa\")");
        }});

        features.put("mongo-sync", new Feature(){{
            setName("mongo-sync");
            setMaven("<dependency>\n" +
                    "    <groupId>io.micronaut.mongodb</groupId>\n" +
                    "    <artifactId>micronaut-mongo-sync</artifactId>\n" +
                    "    <scope>compile</scope>\n" +
                    "</dependency>\n");
            setGradle("implementation(\"io.micronaut.mongodb:micronaut-mongo-sync\")");
        }});

        features.put("liquibase", new Feature(){{
            setName("liquibase");
            setMaven("<dependency>\n" +
                    "    <groupId>io.micronaut.liquibase</groupId>\n" +
                    "    <artifactId>micronaut-liquibase</artifactId>\n" +
                    "    <scope>compile</scope>\n" +
                    "</dependency>\n");
            setGradle("implementation(\"io.micronaut.liquibase:micronaut-liquibase\")");
        }});

        features.put("embed.mongo", new Feature(){{
            setName("embed.mongo");
            setMaven("<dependency>\n" +
                    "    <groupId>de.flapdoodle.embed</groupId>\n" +
                    "    <artifactId>de.flapdoodle.embed.mongo</artifactId>\n" +
                    "    <version>2.0.1</version>\n" +
                    "    <scope>test</scope>\n" +
                    "</dependency>\n");
            setGradle("testImplementation(\"de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.0.1\")");
        }});

        features.put("h2", new Feature(){{
            setName("h2");
            setMaven("<dependency>\n" +
                    "    <groupId>com.h2database</groupId>\n" +
                    "    <artifactId>h2</artifactId>\n" +
                    "    <scope>runtime</scope>\n" +
                    "</dependency>\n");
            setGradle("runtimeOnly(\"com.h2database:h2\")");
        }});

        features.put("graphql", new Feature(){{
            setName("graphql");
            setMaven("<dependency>\n" +
                    "      <groupId>io.micronaut.graphql</groupId>\n" +
                    "      <artifactId>micronaut-graphql</artifactId>\n" +
                    "      <scope>compile</scope>\n" +
                    "</dependency>");
            setGradle("implementation(\"io.micronaut.graphql:micronaut-graphql\")");
        }});


        return features;
    }

}
