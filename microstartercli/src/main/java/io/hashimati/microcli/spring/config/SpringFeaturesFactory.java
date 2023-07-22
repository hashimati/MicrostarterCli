package io.hashimati.microcli.spring.config;

import io.hashimati.microcli.config.Feature;
import io.micronaut.context.annotation.Factory;

import jakarta.inject.Singleton;
import java.io.FileNotFoundException;
import java.util.HashMap;

@Factory
public class SpringFeaturesFactory {


    @Singleton
    public static HashMap<String, Feature> dependencies() throws FileNotFoundException {


        HashMap<String, Feature> features = new HashMap<>();
        features.put("org.codehaus.groovy",new Feature(){{

            setName("org.codehaus.groovy");
            setGradle("\timplementation 'org.codehaus.groovy:groovy'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.codehaus.groovy</groupId>\n" +
                    "\t\t<artifactId>groovy</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        features.putIfAbsent("org.jetbrains.kotlin", new Feature(){{
            setName("org.jetbrains.kotlin");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.jetbrains.kotlin</groupId>\n" +
                    "\t\t<artifactId>kotlin-maven-allopen</artifactId>\n" +
                    "\t\t<version>${kotlin.version}</version>\n" +
                    "\t\t</dependency>");
            setGradle("\timplementation(\"org.jetbrains.kotlin:kotlin-reflect\")\n" +
                    "\timplementation(\"org.jetbrains.kotlin:kotlin-stdlib-jdk8\")\n");
        }});
        features.put("web", new Feature(){{
            setName("web");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-web'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-web</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        features.put("webFlux", new Feature(){{
            setName("webFlux");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-webflux'\n\ttestImplementation 'io.projectreactor:reactor-test'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-webflux</artifactId>\n" +
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.projectreactor</groupId>\n" +
                    "\t\t<artifactId>reactor-test</artifactId>\n" +
                    "\t\t<scope>test</scope>\n"+
                    "\t\t</dependency>");

        }});


        //Security
        features.put("security", new Feature(){{
            setName("security");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-security'");
            setGradle("\ttestImplementation 'org.springframework.security:spring-security-test'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-security</artifactId>\n" +
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.security</groupId>\n" +
                    "\t\t<artifactId>spring-security-test</artifactId>\n" +
                    "\t\t<scope>test</scope>\n"+
                    "\t\t</dependency>");
        }});


        //data
        //Mongo
        features.put("mongodb", new Feature(){{
            setName("mongodb");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-mongodb'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-mongodb</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        features.put("mongodb-reactive", new Feature(){{
            setName("mongodb-reactive");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-mongodb-reactive'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>\n" +
                    "\t\t</dependency>");
        }});


        //Couchbase
        features.put("couchbase", new Feature(){{
            setName("couchbase");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-couchbase'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-couchbase</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        features.put("couchbase-reactive", new Feature(){{
            setName("couchbase-reactive");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-couchbase-reactive'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-couchbase-reactive</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        //Cassandra
        features.put("cassandra", new Feature(){{
            setName("cassandra");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-cassandra'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-cassandra</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        features.put("cassandra-reactive", new Feature(){{
            setName("cassandra-reactive");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-cassandra-reactive'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-cassandra-reactive</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        //Neo4J
        features.put("neo4j", new Feature(){{
            setName("neo4j");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-neo4j'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-neo4j</artifactId>\n" +
                    "\t\t</dependency>");
        }});

        //JDBC
        features.put("data-jdbc", new Feature(){{
            setName("data-jdbc");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-jdbc'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-jdbc</artifactId>\n" +
                    "\t\t</dependency>");
        }});
        //R2DBC
        features.put("data-r2dbc", new Feature(){{
            setName("data-r2dbc");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-r2dbc'\n" +
                    "\timplementation 'org.springframework:spring-jdbc'\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-r2dbc</artifactId>\n" +
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework</groupId>\n" +
                    "\t\t<artifactId>spring-jdbc</artifactId>\n" +
                    "\t\t</dependency>");
        }});

        //JPA
        features.put("data-jpa", new Feature(){{
            setName("data-jpa");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-jpa'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-jpa</artifactId>\n" +
                    "\t\t</dependency>");
        }});

        //MySQL
        features.put("mysql", new Feature(){{
            setName("mysql");
            setGradle("\truntimeOnly 'mysql:mysql-connector-java'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>mysql</groupId>\n" +
                    "\t\t<artifactId>mysql-connector-java</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});
        features.put("mysql-r2dbc", new Feature(){{
            setName("mysql-r2dbc");
            setGradle("\truntimeOnly 'mysql:mysql-connector-java'\n" +
                    "\truntimeOnly 'dev.miku:r2dbc-mysql'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>dev.miku</groupId>\n" +
                    "\t\t<artifactId>r2dbc-mysql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>mysql</groupId>\n" +
                    "\t\t<artifactId>mysql-connector-java</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});



        //Postgres
        features.put("postgres", new Feature(){{
            setName("postgres");
            setGradle("\truntimeOnly 'org.postgresql:postgresql'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.postgresql</groupId>\n" +
                    "\t\t<artifactId>postgresql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});
        features.put("postgres-r2dbc", new Feature(){{
            setName("postgres-r2dbc");
            setGradle("\truntimeOnly 'org.postgresql:postgresql'\n" +
                    "\truntimeOnly 'io.r2dbc:r2dbc-postgresql'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.r2dbc</groupId>\n" +
                    "\t\t<artifactId>r2dbc-postgresql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.postgresql</groupId>\n" +
                    "\t\t<artifactId>postgresql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});

        //MariaDB
        features.put("mariadb", new Feature(){{
            setName("mariadb");
            setGradle("\truntimeOnly 'org.mariadb.jdbc:mariadb-java-client'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.mariadb.jdbc</groupId>\n" +
                    "\t\t<artifactId>mariadb-java-client</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});
        features.put("mariadb-r2dbc", new Feature(){{
            setName("mariadb-r2dbc");
            setGradle("\truntimeOnly 'org.mariadb.jdbc:mariadb-java-client'" +
                    "\n\truntimeOnly 'org.mariadb:r2dbc-mariadb'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.mariadb</groupId>\n" +
                    "\t\t<artifactId>r2dbc-mariadb</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.mariadb.jdbc</groupId>\n" +
                    "\t\t<artifactId>mariadb-java-client</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});




        //Oracle
        features.put("oracle", new Feature(){{
            setName("data-jdbc");
            setGradle("\truntimeOnly 'com.oracle.database.jdbc:ojdbc8'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.oracle.database.jdbc</groupId>\n" +
                    "\t\t<artifactId>ojdbc8</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});

        //MS SQL
        features.put("sqlserver", new Feature(){{
            setName("sqlserver");
            setGradle("\truntimeOnly 'com.microsoft.sqlserver:mssql-jdbc'");

            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.microsoft.sqlserver</groupId>\n" +
                    "\t\t<artifactId>mssql-jdbc</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});

        features.put("sqlserver-r2dbc", new Feature(){{
            setName("sqlserver-r2dbc");
            setGradle("\truntimeOnly 'com.microsoft.sqlserver:mssql-jdbc'\n" +
                    "\truntimeOnly 'io.r2dbc:r2dbc-mssql'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.r2dbc</groupId>\n" +
                    "\t\t<artifactId>r2dbc-mssql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.microsoft.sqlserver</groupId>\n" +
                    "\t\t<artifactId>mssql-jdbc</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});
        //IBM
        features.put("ibm db2", new Feature(){{
            setName("ibm db2");
            setGradle("\truntimeOnly 'com.ibm.db2:jcc'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.ibm.db2</groupId>\n" +
                    "\t\t<artifactId>jcc</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});

        //H2
        features.put("h2", new Feature(){{
            setName("h2");
            setGradle("\truntimeOnly 'com.h2database:h2'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.h2database</groupId>\n" +
                    "\t\t<artifactId>h2</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});
        features.put("h2-r2dbc", new Feature(){{
            setName("h2-r2dbc");
            setGradle("\truntimeOnly 'com.h2database:h2'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.h2database</groupId>\n" +
                    "\t\t<artifactId>h2</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
            setGradle("\truntimeOnly 'io.r2dbc:r2dbc-h2'");
            getMaven().add(("\t<dependency>\n" +
                    "\t\t<groupId>io.r2dbc</groupId>\n" +
                    "\t\t<artifactId>r2dbc-h2</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>"));
        }});

        //liquibase
        features.put("liquibase", new Feature(){{
            setName("liquibase");
            setGradle("\timplementation 'org.liquibase:liquibase-core'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.liquibase</groupId>\n" +
                    "\t\t<artifactId>liquibase-core</artifactId>\n" +
                    "\t\t</dependency>");
        }});






        //openAPI
        features.put("springdoc", new Feature(){{
            setName("springdoc");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springdoc</groupId>\n" +
                    "\t\t<artifactId>springdoc-openapi-ui</artifactId>\n" +
                    "\t\t<version>1.5.14</version>\n" +
                    "\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springdoc</groupId>\n" +
                    "\t\t<artifactId>springdoc-openapi-data-rest</artifactId>\n" +
                    "\t\t<version>1.5.14</version>\n" +
                    "\t</dependency>");

            setGradle("implementation 'org.springdoc:springdoc-openapi-ui:1.5.14'\n" +
                    "\timplementation 'org.springdoc:springdoc-openapi-data-rest:1.5.14'\n");

        }});
        features.put("springdoc-security", new Feature(){{
            setName("springdoc-security");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springdoc</groupId>\n" +
                    "\t\t<artifactId>springdoc-openapi-security</artifactId>\n" +
                    "\t\t<version>1.5.13</version>\n" +
                    "\t</dependency>");
            setGradle("implementation 'org.springdoc:springdoc-openapi-security:1.5.13'\n");

        }});
        features.put("springdoc-native", new Feature(){{
            setName("springdoc-native");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springdoc</groupId>\n" +
                    "\t\t<artifactId>springdoc-openapi-native</artifactId>\n" +
                    "\t\t<version>1.5.13</version>\n" +
                    "\t</dependency>");
            setGradle("implementation 'org.springdoc:springdoc-openapi-native:1.5.13'\n");

        }});

        features.put("springdoc-kotlin", new Feature(){{
            setName("springdoc-kotlin");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springdoc</groupId>\n" +
                    "\t\t<artifactId>springdoc-openapi-kotlin</artifactId>\n" +
                    "\t\t<version>1.5.13</version>\n" +
                    "\t</dependency>");
            setGradle("implementation'org.springdoc:springdoc-openapi-kotlin:1.5.13'\n");

        }});

        features.put("springdoc-groovy", new Feature(){{
            setName("springdoc-groovy");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springdoc</groupId>\n" +
                    "\t\t<artifactId>springdoc-openapi-groovy</artifactId>\n" +
                    "\t\t<version>1.5.13</version>\n" +
                    "\t</dependency>");
            setGradle("implementation 'org.springdoc:springdoc-openapi-groovy:1.5.13'\n");

        }});



        //Kafka
        features.put("kafka", new Feature(){{
            setName("kafka");
            setGradle("\timplementation 'org.springframework.kafka:spring-kafka'\n" +
                    "\timplementation 'org.springframework.kafka:spring-kafka-test'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.kafka</groupId>\n" +
                    "\t\t<artifactId>spring-kafka</artifactId>\n" +
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.kafka</groupId>\n" +
                    "\t\t<artifactId>spring-kafka-test</artifactId>\n" +
                    "\t\t<scope>test</scope>\n"+
                    "\t\t</dependency>");
        }});

        //rabbitMQ
        features.put("rabbit", new Feature(){{
            setName("rabbit");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-amqp'\n" +
                    "\timplementation 'org.springframework.amqp:spring-rabbit-test'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boot</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-amqp</artifactId>\n" +
                    "\t\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.amqp</groupId>\n" +
                    "\t\t<artifactId>spring-rabbit-test</artifactId>\n" +
                    "\t\t<scope>test</scope>\n"+
                    "\t\t</dependency>");
        }});

        //ActiveMQ
        features.put("mongodb", new Feature(){{
            setName("mongodb");
            setGradle("\timplementation 'org.springframework.boot:spring-boot-starter-data-mongodb'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.springframework.boo</groupId>\n" +
                    "\t\t<artifactId>spring-boot-starter-data-mongodb</artifactId>\n" +
                    "\t\t</dependency>");
        }});


        //Influx
        features.put("influx", new Feature(){{
            setName("influx");
            setGradle("\truntimeOnly 'io.micrometer:micrometer-registry-influx'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micrometer</groupId>\n" +
                    "\t\t<artifactId>micrometer-registry-influx</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});

        //prometheus
        features.put("prometheus", new Feature(){{
            setName("prometheus");
            setGradle("\truntimeOnly 'io.micrometer:micrometer-registry-prometheus'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micrometer</groupId>\n" +
                    "\t\t<artifactId>micrometer-registry-prometheus</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});
        //graphite
        features.put("graphite", new Feature(){{
            setName("graphite");
            setGradle("\truntimeOnly 'io.micrometer:micrometer-registry-graphite'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micrometer</groupId>\n" +
                    "\t\t<artifactId>micrometer-registry-graphite</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n"+
                    "\t\t</dependency>");
        }});

        features.put("jaeger-core", new Feature(){{
setName("jaeger");
            setGradle("implementation 'io.jaegertracing:jaeger-client'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.jaegertracing</groupId>\n" +
                    "\t\t<artifactId>jaeger-core</artifactId>\n" +
                    "\t\t<<version>1.8.1</version>\n"+
                    "\t</dependency>");
        }});
        features.put("jaeger", new Feature(){{
            setGradle("implementation 'io.opentracing.contrib:opentracing-spring-jaeger-cloud-starter:3.3.1'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.opentracing.contrib</groupId>\n" +
                    "\t\t<artifactId>opentracing-spring-jaeger-cloud-starter</artifactId>\n" +
                    "\t\t<version>3.3.1</version>\n"+
                    "\t</dependency>");
        }});
        features.put("jaeger-client", new Feature(){{
            setName("jaeger-client");
            setGradle("implementation 'io.jaegertracing:jaeger-client'");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.jaegertracing</groupId>\n" +
                    "\t\t<artifactId>jaeger-core</artifactId>\n" +
                    "\t\t<<version>1.8.1</version>\n"+
                    "\t</dependency>");
                }});
        //Netflix-dgs
        features.put("netflix-dgs", new Feature(){{
            setName("netflix-dgs");
            setGradle("implementation(platform(\"com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release\"))\n" +
                    "\timplementation 'com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter'\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.netflix.graphql.dgs</groupId>\n" +
                    "\t\t<artifactId>graphql-dgs-spring-boot-starter</artifactId>\n" +
                    "\t</dependency>");
            getMavenDependenciesManagement().add("\t<dependency>\n" +
                    "\t\t<groupId>com.netflix.graphql.dgs</groupId>\n" +
                    "\t\t<artifactId>graphql-dgs-platform-dependencies</artifactId>\n" +
                    "\t\t<version>4.1.0</version>\n" +
                    "\t\t<type>pom</type>\n" +
                    "\t\t<scope>import</scope>\n" +
                    "\t</dependency>");
        }});


        return features;



    }



}
