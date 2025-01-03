package io.hashimati.microcli.config;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.hashimati.microcli.domains.ProjectInfo;
import io.micronaut.context.annotation.Factory;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;

import jakarta.inject.Singleton;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

@Factory
public class FeaturesFactory {


    @Singleton
    public static HashMap<String, Feature> features(ProjectInfo projectInfo) throws FileNotFoundException {


        if(projectInfo == null) return null;
        HashMap<String, Feature> features = new HashMap<>();
        features.putIfAbsent("flyway", new Feature(){{
            setName("flyway");
            setGradle("    implementation(\"io.micronaut.flyway:micronaut-flyway\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.flyway</groupId>\n" +
                    "\t\t<artifactId>micronaut-flyway</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});

        features.put("jdbc-hikari", new Feature(){{
            setName("jdbc-hikari");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.sql</groupId>\n" +
                    "\t\t<artifactId>micronaut-jdbc-hikari</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.sql:micronaut-jdbc-hikari\")");
        }});




        features.put("data-jdbc", new Feature(){{
            setName("data-jdbc");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-jdbc</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.data:micronaut-data-jdbc\")");

           if(projectInfo.getSourceLanguage().equalsIgnoreCase(JAVA_LANG))
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-processor\")");
           else if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
           {
               setAnnotationGradle("    compileOnly(\"io.micronaut.data:micronaut-data-processor\")");

           }
           else if(projectInfo.getSourceLanguage().equalsIgnoreCase(KOTLIN_LANG))
           {
               setAnnotationGradle("    kapt(\"io.micronaut.data:micronaut-data-processor\")");

           }

            setAnnotationMaven(
                    "                <path>\n" +
                    "                  <groupId>io.micronaut.data</groupId>\n" +
                    "                  <artifactId>micronaut-data-processor</artifactId>\n" +
                    "                  <version>${micronaut.data.version}</version>\n" +
                            "              <exclusions>\n" +
                            "                <exclusion>\n" +
                            "                  <groupId>io.micronaut</groupId>\n" +
                            "                  <artifactId>micronaut-inject</artifactId>\n" +
                            "                </exclusion>\n" +
                            "              </exclusions>\n" +
                    "                </path>");
//            setVersionProperties("<micronaut.data.version>3.7.2</micronaut.data.version>");
//            getMavenProperties().putIfAbsent("micronaut.data.version", "3.7.2");

        }});


        //rdbc-core
        features.put("data-r2dbc", new Feature(){{


            setName("data-r2dbc");
            setGradle("    implementation(\"io.micronaut.data:micronaut-data-r2dbc\")");


            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-r2dbc</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-processor\")");
            setAnnotationMaven(
                    "                <path>\n" +
                            "                  <groupId>io.micronaut.data</groupId>\n" +
                            "                  <artifactId>micronaut-data-processor</artifactId>\n" +
                            "                  <version>${micronaut.data.version}</version>\n" +
                            "              <exclusions>\n" +
                            "                <exclusion>\n" +
                            "                  <groupId>io.micronaut</groupId>\n" +
                            "                  <artifactId>micronaut-inject</artifactId>\n" +
                            "                </exclusion>\n" +
                            "              </exclusions>\n" +
                            "                </path>");
            getMavenProperties().putIfAbsent("micronaut.data.version", "3.7.2");

        }});

        //rdbc-core
        features.put("r2dbc", new Feature(){{
            setName("r2dbc");
            setGradle("    implementation(\"io.micronaut.r2dbc:micronaut-r2dbc-core\")");

            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.r2dbc</groupId>\n" +
                    "\t\t<artifactId>micronaut-r2dbc-core</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            getMavenProperties().putIfAbsent("micronaut.data.version", "3.7.2");


        }});
        features.put("mongo-reactive", new Feature(){{
            setName("mongo-reactive");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.mongodb</groupId>\n" +
                    "\t\t<artifactId>micronaut-mongo-reactive</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.testcontainers</groupId>\n" +
                    "\t\t<artifactId>spock</artifactId>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>");


            setGradle("    implementation(\"io.micronaut.mongodb:micronaut-mongo-reactive\")");
            setTestContainerGradle("    testImplementation(\"org.testcontainers:mongodb\")");
        }});

        features.put("data-jpa", new Feature(){{
            setName("data-jpa");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-hibernate-jpa</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.data:micronaut-data-hibernate-jpa\")");

            if(projectInfo.getSourceLanguage().equalsIgnoreCase(JAVA_LANG))
                setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-processor\")");
            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                setAnnotationGradle("    compileOnly(\"io.micronaut.data:micronaut-data-processor\")");

            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(KOTLIN_LANG))
                setAnnotationGradle("    kapt(\"io.micronaut.data:micronaut-data-processor\")");

            setAnnotationMaven("                <path>\n" +
                    "                  <groupId>io.micronaut.data</groupId>\n" +
                    "                  <artifactId>micronaut-data-processor</artifactId>\n" +
                    "                  <version>${micronaut.data.version}</version>\n" +
                    "              <exclusions>\n" +
                    "                <exclusion>\n" +
                    "                  <groupId>io.micronaut</groupId>\n" +
                    "                  <artifactId>micronaut-inject</artifactId>\n" +
                    "                </exclusion>\n" +
                    "              </exclusions>\n" +
                    "                </path>");
            setVersionProperties("<micronaut.data.version>3.7.2</micronaut.data.version>");
            getMavenProperties().putIfAbsent("micronaut.data.version", "3.7.2");
        }});

        features.put("mongo-sync", new Feature(){{
            setName("mongo-sync");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.mongodb</groupId>\n" +
                    "\t\t<artifactId>micronaut-mongo-sync</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.mongodb:micronaut-mongo-sync\")");
            setTestGradle("    testImplementation(\"org.testcontainers:mongodb\")");
            setTestMaven("\t<dependency>\n" +
                    "\t\t<groupId>org.testcontainers</groupId>\n" +
                    "\t\t<artifactId>mongodb</artifactId>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("data-mongodb", new Feature(){{

            setName("data-mongodb");
            setGradle("    implementation(\"io.micronaut.data:micronaut-data-mongodb\")\n");
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-document-processor\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-mongodb</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setAnnotationMaven("\t<path>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-document-processor</artifactId>\n" +
                    "\t\t<version>${micronaut.data.version}</version>\n" +
                    "              <exclusions>\n" +
                    "                <exclusion>\n" +
                    "                  <groupId>io.micronaut</groupId>\n" +
                    "                  <artifactId>micronaut-inject</artifactId>\n" +
                    "                </exclusion>\n" +
                    "              </exclusions>\n" +
                    "\t</path>");

        }});

        features.put("data-mongodb-reactive", new Feature(){{

            setName("data-mongodb-reactive");
            setGradle("    implementation(\"io.micronaut.data:micronaut-data-mongodb\")\n" +
                    "    runtimeOnly(\"org.mongodb:mongodb-driver-reactivestreams\")");
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-document-processor\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-mongodb</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setAnnotationMaven("\t<path>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-document-processor</artifactId>\n" +
                    "\t\t<version>${micronaut.data.version}</version>\n" +
                    "              <exclusions>\n" +
                    "                <exclusion>\n" +
                    "                  <groupId>io.micronaut</groupId>\n" +
                    "                  <artifactId>micronaut-inject</artifactId>\n" +
                    "                </exclusion>\n" +
                    "              </exclusions>\n" +
                    "\t</path>");

        }});
        features.put("cassandra", new Feature(){{
            setName("cassandra");
            setGradle("    implementation(\"io.micronaut.cassandra:micronaut-cassandra\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.cassandra</groupId>\n" +
                    "\t\t<artifactId>micronaut-cassandra</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("redis-lettuce", new Feature(){{
            setName("redis-lettuce");
            setGradle("    implementation(\"io.micronaut.redis:micronaut-redis-lettuce\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.redis</groupId>\n" +
                    "\t\t<artifactId>micronaut-redis-lettuce</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("liquibase", new Feature(){{
            setName("liquibase");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.liquibase</groupId>\n" +
                    "\t\t<artifactId>micronaut-liquibase</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.liquibase:micronaut-liquibase\")");
        }});

        features.put("embed.mongo", new Feature(){{
            setName("embed.mongo");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>de.flapdoodle.embed</groupId>\n" +
                    "\t\t<artifactId>de.flapdoodle.embed.mongo</artifactId>\n" +
                    "\t\t<version>2.0.1</version>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    testImplementation(\"de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.0.1\")");

            setGradleTask("rewrite {\n" +
                    "    activeRecipe(\"org.openrewrite.java.micronaut.Micronaut2to3Migration\")\n" +
                    "}");
            setPlugin(new Plugin(){{
                setGroupId("org.openrewrite.maven");
                setArtifactId("rewrite-maven-plugin</artifactId");
                setVersion("${openrewrite.maven.plugin.version}");
                setConfiguration( "<configuration>\n" +
                        "<activeRecipes>\n" +
                        "<recipe>org.openrewrite.java.micronaut.Micronaut2to3Migration</recipe>\n" +
                        "</activeRecipes>\n" +
                        "<activeStyles>\n" +
                        "</activeStyles>\n" +
                        "</configuration>\n");
                addDependency(new Dependency(){{
                    setGroupId("org.openrewrite.recipe");
                    setArtifactId("rewrite-micronaut");
                    setVersion("${rewrite-micronaut.version}");
                }});
            }});
        }});

        features.put("h2", new Feature(){{
            setName("h2");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.h2database</groupId>\n" +
                    "\t\t<artifactId>h2</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>\n");
            setTestMaven("\t<dependency>\n" +
                    "\t\t<groupId>com.h2database</groupId>\n" +
                    "\t\t<artifactId>h2</artifactId>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    runtimeOnly(\"com.h2database:h2\")");
            setTestGradle("    testRuntimeOnly(\"com.h2database:h2\")");

            setRdbcMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.r2dbc</groupId>\n" +
                    "\t\t<artifactId>r2dbc-h2</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");
            setRdbcGradle("    runtimeOnly(\"io.r2dbc:r2dbc-h2\")");
            setTestRdbcGradle("    testRuntimeOnly(\"io.r2dbc:r2dbc-h2\")");
            setTestRdbcMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.r2dbc</groupId>\n" +
                    "\t\t<artifactId>r2dbc-h2</artifactId>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>");
        }});


        features.put("mysql", new Feature(){{
            setName("mysql");
            getMaven().add("  <dependency>\n" +
                    "      <groupId>com.mysql</groupId>\n" +
                    "      <artifactId>mysql-connector-j</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");

            setGradle("    runtimeOnly(\"com.mysql:mysql-connector-j\")");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:mysql\")");
            setTestMaven(" <dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>mysql</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");

            setRdbcMaven("\t<dependency>\n" +
                    "\t\t<groupId>dev.miku</groupId>\n" +
                    "\t\t<artifactId>r2dbc-mysql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");
            setRdbcGradle("    runtimeOnly(\"dev.miku:r2dbc-mysql\")");

        }});

        features.put("testcontainers", new Feature(){{
            setName("testcontainers");




            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.testcontainers</groupId>\n" +
                    "\t\t<artifactId>mongodb</artifactId>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>");
            setTestContainerGradle("    testImplementation(\"org.testcontainers:testcontainers\")");
        }});
        features.put("junit-jupiter", new Feature(){{
            setName("junit-jupiter");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.testcontainers</groupId>\n" +
                            "\t\t<artifactId>junit-jupiter</artifactId>\n" +
                            "\t\t<scope>test</scope>\n" +
                            "\t</dependency>");
            setGradle("    testImplementation(\"org.testcontainers:junit-jupiter\")");
        }});
        features.put("testcontainers-spock", new Feature(){{
            setName("testcontainers-spock");
            setGradle("    testImplementation(\"org.testcontainers:spock\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.testcontainers</groupId>\n" +
                    "\\t\t<artifactId>spock</artifactId>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("postgres", new Feature(){{
            setName("postgres");
            getMaven().add("    <dependency>\n" +
                    "      <groupId>org.postgresql</groupId>\n" +
                    "      <artifactId>postgresql</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");

            getMaven().add(" <dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>postgresql</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");
            setGradle("    runtimeOnly(\"org.postgresql:postgresql\")");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:postgresql\")");

            setRdbcGradle("    runtimeOnly(\"io.r2dbc:r2dbc-postgresql\")");
            setRdbcMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.r2dbc</groupId>\n" +
                    "\t\t<artifactId>r2dbc-postgresql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("mariadb", new Feature(){{
            setName("mariadb");
            getMaven().add(" <dependency>\n" +
                    "      <groupId>org.mariadb.jdbc</groupId>\n" +
                    "      <artifactId>mariadb-java-client</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");
            setTestMaven("<dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>mariadb</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");
            setGradle("    runtimeOnly(\"org.mariadb.jdbc:mariadb-java-client\")");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:mariadb\")");
            setRdbcGradle("    runtimeOnly(\"org.mariadb:r2dbc-mariadb\")");
            setRdbcMaven("\t<dependency>\n" +
                    "\t\t<groupId>org.mariadb</groupId>\n" +
                    "\t\t<artifactId>r2dbc-mariadb</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");
        }});


        //rdbc not supported
        features.put("oracle", new Feature(){{
            setName("oracle");
            getMaven().add("<dependency>\n" +
                    "      <groupId>com.oracle.ojdbc</groupId>\n" +
                    "      <artifactId>ojdbc8</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");
            setTestMaven(" <dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>oracle-xe</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");
            setGradle("    runtimeOnly(\"com.oracle.ojdbc:ojdbc8\")");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:oracle-xe\")");
        }});

        features.put("sqlserver", new Feature(){{
            setName("sqlserver");
            getMaven().add("   <dependency>\n" +
                    "      <groupId>com.microsoft.sqlserver</groupId>\n" +
                    "      <artifactId>mssql-jdbc</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");
            setGradle("    runtimeOnly(\"com.microsoft.sqlserver:mssql-jdbc\")");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:mssqlserver\")");

            setTestMaven("  <dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>mssqlserver</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");

            setRdbcMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.r2dbc</groupId>\n" +
                    "\t\t<artifactId>r2dbc-mssql</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");
            setRdbcGradle("    runtimeOnly(\"io.r2dbc:r2dbc-mssql\")");
        }});

        features.put("graphql", new Feature(){{
            setName("graphql");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.graphql</groupId>\n" +
                    "\t\t<artifactId>micronaut-graphql</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.graphql:micronaut-graphql\")");
        }});
        features.put("openapi", new Feature(){{
            setName("openapi");
            getMaven().add("    <dependency>\n" +
                    "      <groupId>io.swagger.core.v3</groupId>\n" +
                    "      <artifactId>swagger-annotations</artifactId>\n" +
                    "      <scope>compile</scope>\n" +
                    "    </dependency>");
            setGradle("    implementation(\"io.swagger.core.v3:swagger-annotations\")");
            if(projectInfo.getSourceLanguage().equalsIgnoreCase(JAVA_LANG))
                setAnnotationGradle("    annotationProcessor(\"io.micronaut.openapi:micronaut-openapi\")");
            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                setAnnotationGradle("    compileOnly(\"io.micronaut.openapi:micronaut-openapi\")");


            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(KOTLIN_LANG))
                setAnnotationGradle("    kapt(\"io.micronaut.openapi:micronaut-openapi\")");

            getMavenCompileArgs().add("\t\t<arg>-Amicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop</arg>\n");
            setAnnotationMaven(
                    "            <path>\n" +
                    "              <groupId>io.micronaut.openapi</groupId>\n" +
                    "              <artifactId>micronaut-openapi</artifactId>\n" +
                    "              <version>${micronaut.openapi.version}</version>\n" +
                    "            </path>");
        }});


        features.put("lombok", new Feature(){{
            setName("lombok");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.projectlombok</groupId>\n" +
                    "\t\t<artifactId>lombok</artifactId>\n" +
                    "\t\t<version>1.18.12</version>\n" +
                    "\t\t<scope>provided</scope>\n" +
                    "\t</dependency>");
            setAnnotationMaven(
                    "            <path>\n" +
                    "              <groupId>org.projectlombok</groupId>\n" +
                    "              <artifactId>lombok</artifactId>\n" +
                    "              <version>${lombok.version}</version>\n" +
                    "            </path>");
            setGradle("    compileOnly(\"org.projectlombok:lombok\")");
            setAnnotationGradle("\n    annotationProcessor(\"org.projectlombok:lombok\")");
//            setTestGradleAnnotation("    testAnnotationProcessor 'org.projectlombok:lombok:1.18.12'");
//            setTestGradle("    testCompileOnly 'org.projectlombok:lombok:1.18.12'\n");

        }});

        features.put("rabbitmq", new Feature(){{
            setName("rabbitmq");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.rabbitmq</groupId>\n" +
                    "\t\t<artifactId>micronaut-rabbitmq</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");

            setGradle("    implementation(\"io.micronaut.rabbitmq:micronaut-rabbitmq\")");
        }});

        features.put("kafka", new Feature(){{
            setName("kafka");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.kafka</groupId>\n" +
                    "\t\t<artifactId>micronaut-kafka</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");

            setGradle("    implementation(\"io.micronaut.kafka:micronaut-kafka\")");
        }});

        features.put("nats", new Feature(){{
            setName("nats");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.nats</groupId>\n" +
                    "\t\t<artifactId>micronaut-nats</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\tt</dependency\n");

            setGradle("    implementation(\"io.micronaut.nats:micronaut-nats\")");
        }});

        features.put("graphql", new Feature(){{
            setName("graphql");
            setGradle("    implementation(\"io.micronaut.graphql:micronaut-graphql\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.graphql</groupId>\n" +
                    "\t\t<artifactId>micronaut-graphql</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        //	compile 'com.graphql-java:graphql-java-tools:5.2.4'
        features.put("graphql-java-tools", new Feature(){{
            setName("qraphql-java-tools");
            setGradle("    implementation(\"com.graphql-java-kickstart:graphql-java-tools:13.0.0\")");
            getMaven().add("\t<dependency>\n"+
                    "\t\t<groupId>com.graphql-java-kickstart</groupId>\n" +
                    "\t\t<artifactId>graphql-java-tools</artifactId>\n"+
                    "\t\t<scope>compile</scope>\n"+
                    "\t\t<version>13.0.0</version>\n"+
                    "\t</dependency>");
        }});


        features.put("graphql-spqr", new Feature(){{
            setName("graphql-spqr");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.leangen.graphql</groupId>\n" +
                    "\t\t<artifactId>spqr</artifactId>\n" +
                    "\t\t<version>0.10.0</version>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.leangen.graphql:spqr:0.10.0\")");
        }});
        features.put("jasypt", new Feature(){{
            setName("jasypt");
//            setGradle("     implementation(\"org.jasypt:jasypt:1.9.3\")");
            setGradle("         implementation(\"org.apache.camel:camel-jasypt:4.2.0\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.apache.camel</groupId>\n" +
                    "\t\t<artifactId>camel-jasypt</artifactId>\n" +
                    "\t\t<version>4.2.0</version>\n" +
                    "\t</dependency>\n");
//            getMaven().add("\t<dependency>\n" +
//                    "\t\t<groupId>org.jasypt</groupId>\n" +
//                    "\t\t<artifactId>jasypt</artifactId>\n" +
//                    "\t\t<version>1.9.3</version>\n" +
//                    "\t</dependency>\n");
        }});
        features.put("security-jwt", new Feature(){{
            setName("security-jwt");
            setGradle("    implementation(\"io.micronaut.security:micronaut-security-jwt\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.security</groupId>\n" +
                    "\t\t<artifactId>micronaut-security-jwt</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("security", new Feature(){{
            setName("security");
            setGradle("    implementation(\"io.micronaut.security:micronaut-security\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.security</groupId>\n" +
                    "\t\t<artifactId>micronaut-security</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});
        features.put("security-annotations", new Feature(){{
            setName("security-annotations");
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.security:micronaut-security-annotations\")");
            setAnnotationMaven("\t<path>\n" +
                    "\t\t<groupId>io.micronaut.security</groupId>\n" +
                    "\t\t<artifactId>micronaut-security-annotations</artifactId>\n" +
                    "\t\t<version>${micronaut.security.version}</version>\n" +
                    "\t</path>");
        }});
        features.put("security-session", new Feature(){{
            setName("security-session");
            setGradle("    implementation(\"io.micronaut.security:micronaut-security-session\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.security</groupId>\n" +
                    "\t\t<artifactId>micronaut-security-session</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("security-oauth2", new Feature(){{
            setName("security-oauth2");
            setGradle("    implementation(\"io.micronaut.security:micronaut-security-oauth2\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.security</groupId>\n" +
                    "\t\t<artifactId>micronaut-security-oauth2</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("security-ldap", new Feature(){{
            setName("security-ldap");
            setGradle("    implementation(\"io.micronaut.security:micronaut-security-ldap\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.security</groupId>\n" +
                    "\t\t<artifactId>micronaut-security-ldap</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});


        features.put("security-annotations", new Feature(){{
            setName("security-annotations");
            if(projectInfo.getSourceLanguage().equalsIgnoreCase(JAVA_LANG))

                setAnnotationGradle("    annotationProcessor(\"io.micronaut.security:micronaut-security-annotations\")");
            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                setAnnotationGradle("    compileOnly(\"io.micronaut.security:micronaut-security-annotations\")");
            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(KOTLIN_LANG))
                setAnnotationGradle("    kapt(\"io.micronaut.security:micronaut-security-annotations\")");
            setAnnotationMaven("\t\t\t<path>\n" +
                    "\t\t\t\t<groupId>io.micronaut.security</groupId>\n" +
                    "\t\t\t\t<artifactId>micronaut-security-annotations</artifactId>\n" +
                    "\t\t\t\t<version>${micronaut.security.version}</version>\n" +
                    "\t\t\t</path>");
        }});



        features.put("aws-sdk-v2", new Feature(){{
            setName("aws-sdk-v2");
            setGradle("    implementation(\"io.micronaut.aws:micronaut-aws-sdk-v2\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.aws</groupId>\n" +
                    "\t\t<artifactId>micronaut-aws-sdk-v2</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("gcp-pubsub", new Feature(){{
            setName("gcp-pubsub");
            setGradle("    implementation(\"io.micronaut.gcp:micronaut-gcp-pubsub\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.gcp</groupId>\n" +
                    "\t\t<artifactId>micronaut-gcp-pubsub</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("mongo-gorm",
                new Feature(){{

                    setName("mongo-gorm");
                    setGradle("    implementation(\"io.micronaut.groovy:micronaut-mongo-gorm\")");
                    getMaven().add("    <dependency>\n" +
                            "      <groupId>io.micronaut.groovy</groupId>\n" +
                            "      <artifactId>micronaut-mongo-gorm</artifactId>\n" +
                            "      <scope>compile</scope>\n" +
                            "    </dependency>");


        }});

        features.put("neo4j-bolt",new Feature(){{
            setName("neo4j-bolt");
            setGradle("    implementation(\"io.micronaut.neo4j:micronaut-neo4j-bolt\")");
            getMaven().add("    <dependency>\n" +
                    "      <groupId>io.micronaut.neo4j</groupId>\n" +
                    "      <artifactId>micronaut-neo4j-bolt</artifactId>\n" +
                    "      <scope>compile</scope>\n" +
                    "    </dependency>");
        }} );
        features.put("neo4j-gorm", new Feature(){{
            setName("neo4j-gorm");
            setGradle("    implementation(\"io.micronaut.groovy:micronaut-neo4j-gorm\")");
            getMaven().add("    <dependency>\n" +
                    "      <groupId>io.micronaut.groovy</groupId>\n" +
                    "      <artifactId>micronaut-neo4j-gorm</artifactId>\n" +
                    "      <scope>compile</scope>\n" +
                    "    </dependency>");
        }});

        features.put("tomcat-jdbc", new Feature(){{
            setName("tomcat-jdbc");
            getMaven().add(" <dependency>\n" +
                    "      <groupId>org.apache.tomcat</groupId>\n" +
                    "      <artifactId>tomcat-jdbc</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>");
            setGradle("    runtimeOnly(\"org.apache.tomcat:tomcat-jdbc\")");
        }});
        features.put("hibernate-gorm",new Feature(){{
            setName("hibernate-gorm");
            setGradle("        implementation(\"io.micronaut.groovy:micronaut-hibernate-gorm\")");
            getMaven().add(" <dependency>\n" +
                    "      <groupId>io.micronaut.groovy</groupId>\n" +
                    "      <artifactId>micronaut-hibernate-gorm</artifactId>\n" +
                    "      <scope>compile</scope>\n" +
                    "    </dependency>");
        }} );
        features.put("hibernate-validator", new Feature(){{
            setName("hibernate-validator");
            setGradle("    implementation(\"io.micronaut.beanvalidation:micronaut-hibernate-validator\")");
            getMaven().add("  <dependency>\n" +
                    "      <groupId>io.micronaut.beanvalidation</groupId>\n" +
                    "      <artifactId>micronaut-hibernate-validator</artifactId>\n" +
                    "      <scope>compile</scope>\n" +
                    "    </dependency>" );
        }});

        features.put("mqttv5", new Feature(){{
            setName("mqttv5");
            setGradle("    implementation(\"io.micronaut.mqtt:micronaut-mqttv5\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.mqtt</groupId>\n" +
                    "\t\t<artifactId>micronaut-mqttv5</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});
        features.put("mqttv3", new Feature(){{
            setName("mqttv3");
            setGradle("    implementation(\"io.micronaut.mqtt:micronaut-mqttv3\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.mqtt</groupId>\n" +
                    "\t\t<artifactId>micronaut-mqttv5</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});



        features.put("reactor", new Feature(){{

            setName("reactor");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.reactor</groupId>\n" +
                    "\t\t<artifactId>micronaut-reactor</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.reactor:micronaut-reactor\")");
        }});


        features.put("retry", new Feature(){{
            setName("retry");
            setGradle("    implementation(\"io.micronaut:micronaut-retry\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut</groupId>\n" +
                    "\t\t<artifactId>micronaut-retry</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});

        features.put("micronaut-validation", new Feature(){{
            setName("micronaut-validation");
            setGradle("    implementation(\"io.micronaut.validation:micronaut-validation\")\n" +
                    "    implementation(\"jakarta.validation:jakarta.validation-api\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.validation</groupId>\n" +
                    "\t\t<artifactId>micronaut-validation</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>jakarta.validation</groupId>\n" +
                    "\t\t<artifactId>jakarta.validation-api</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("reactor-http-client", new Feature(){{

            setName("reactor-http-client");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.reactor</groupId>\n" +
                    "\t\t<artifactId>micronaut-reactor-http-client</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.reactor:micronaut-reactor-http-client\")");
        }});

        features.put("rxjava2", new Feature(){{

            setName("rxjava2");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.rxjava2</groupId>\n" +
                    "\t\t<artifactId>micronaut-rxjava2</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.rxjava2:micronaut-rxjava2\")");
        }});

        features.put("rxjava2-http-client", new Feature(){{

            setName("rxjava2-http-client");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.rxjava2</groupId>\n" +
                    "\t\t<artifactId>micronaut-rxjava2-http-client</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.rxjava2:micronaut-rxjava2-http-client\")");
        }});
        features.put("rxjava2-http-server-netty", new Feature(){{

            setName("rxjava2-http-server-netty");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.rxjava2</groupId>\n" +
                    "\t\t<artifactId>micronaut-rxjava2-http-server-netty</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.rxjava2:micronaut-rxjava2-http-server-netty\")");
        }});

        features.put("rxjava3", new Feature(){{

            setName("rxjava3");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.rxjava3</groupId>\n" +
                    "\t\t<artifactId>micronaut-rxjava3</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.rxjava3:micronaut-rxjava3\")");
        }});

        features.put("rxjava3-http-client", new Feature(){{

            setName("rxjava3-http-client");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.rxjava3</groupId>\n" +
                    "\t\t<artifactId>micronaut-rxjava3-http-client</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.rxjava3:micronaut-rxjava3-http-client\")");
        }});

        features.put("cache-caffeine", new Feature(){{
            setName("cache-caffeine");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.cache</groupId>\n" +
                    "\t\t<artifactId>micronaut-cache-caffeine</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.cache:micronaut-cache-caffeine\")");
        }});


        //Management
        features.put("management", new Feature(){{
            setName("management");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut</groupId>\n" +
                    "\t\t<artifactId>micronaut-management</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut:micronaut-management\")");
        }});

        features.put("micrometer", new Feature(){{
            setName("micrometer");
            setGradle("    implementation(\"io.micronaut.micrometer:micronaut-micrometer-core\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.micrometer</groupId>\n" +
                    "\t\t<artifactId>micronaut-micrometer-core</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});

        features.put("micrometer-graphite", new Feature(){{
            setName("micrometer-graphite");
            setGradle("    implementation(\"io.micronaut.micrometer:micronaut-micrometer-registry-graphite\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.micrometer</groupId>\n" +
                    "\t\t<artifactId>micronaut-micrometer-registry-graphite</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("micrometer-prometheus", new Feature(){{
            setName("micrometer-prometheus");
            setGradle("    implementation(\"io.micronaut.micrometer:micronaut-micrometer-registry-prometheus\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.micrometer</groupId>\n" +
                    "\t\t<artifactId>micronaut-micrometer-registry-prometheus</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("micrometer-influx", new Feature(){{
            setName("micrometer-influx");
            setGradle("    implementation(\"io.micronaut.micrometer:micronaut-micrometer-registry-influx\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.micrometer</groupId>\n" +
                    "\t\t<artifactId>micronaut-micrometer-registry-influx</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("micrometer-statsd", new Feature(){{
            setName("micrometer-statsd");
            setGradle("    implementation(\"io.micronaut.micrometer:micronaut-micrometer-registry-statsd\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.micrometer</groupId>\n" +
                    "\t\t<artifactId>micronaut-micrometer-registry-statsd</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("openrewrite", new Feature(){{
            setName("openrewrite");
            setGradle("    rewrite(\"org.openrewrite.recipe:rewrite-micronaut:1.3.0\")");
            getMavenProperties().putIfAbsent("openrewrite.maven.plugin.version", "4.9.0");
            getMavenProperties().putIfAbsent("rewrite-micronaut.version", "1.0.0");
            setGradleTask("rewrite {\n" +
                    "    activeRecipe(\"org.openrewrite.java.micronaut.Micronaut2to3Migration\")\n" +
                    "}");
            getGradlePlugins().add("    id(\"org.openrewrite.rewrite\") version \"5.12.0\"");

        }});

        features.put("graalvm", new Feature(){{
            setName("graalvm");
            setGradle("    compileOnly(\"org.graalvm.nativeimage:svm\")");
        }});

        features.put("acme", new Feature(){{
            setName("acme");
            setGradle("    implementation(\"io.micronaut.acme:micronaut-acme\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t <groupId>io.micronaut.acme</groupId>\n" +
                    "\t\t<artifactId>micronaut-acme</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("aws-v2-sdk", new Feature(){{
            setName("aws-v2-sdk");
            setGradle("    implementation(\"io.micronaut.aws:micronaut-aws-sdk-v2\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.aws</groupId>\n" +
                    "\t\t<artifactId>micronaut-aws-sdk-v2</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
//        features.put("tracing", new Feature(){{
//            setName("tracing");
//            setGradle("    implementation(\"io.micronaut:micronaut-tracing\")\n" );
//            getMaven().add("\t<dependency>\n" +
//                    "\t\t<groupId>io.micronaut</groupId>\n" +
//                    "\t\t<artifactId>micronaut-tracing</artifactId>\n" +
//                    "\t\t<scope>compile</scope>\n" +
//                    "\t</dependency>\n");
//        }});
        features.put("tracing-jaeger", new Feature(){{
            setName("tracing-jaeger");
            setGradle("        implementation(\"io.micronaut.tracing:micronaut-tracing-jaeger\")\n");

            getMaven().add(" \t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.tracing</groupId>\n" +
                    "\t\t<artifactId>micronaut-tracing-jaeger</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");


        }});
        features.put("gcp-cloud-trace", new Feature(){{
            setName("gcp-cloud-trace");

            setGradle("        implementation(\"io.micronaut.gcp:micronaut-gcp-tracing\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.gcp</groupId>\n" +
                    "\t\t<artifactId>micronaut-gcp-tracing</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("tracing-zipkin", new Feature(){{
            setName("tracing-zipkin");

            setGradle("    implementation(\"io.micronaut.tracing:micronaut-tracing-zipkin\")");

            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.tracing</groupId>\n" +
                    "\t\t<artifactId>micronaut-tracing-zipkin</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});
        features.put("aws-s3", new Feature(){{
            setName("aws-s3");

            setGradle("    implementation (\"com.amazonaws:aws-java-sdk-s3:1.12.225\")");

            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>com.amazonaws</groupId>\n" +
                    "\t\t<artifactId>aws-java-sdk-s3</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n"+
                    "\t\t<version>1.12.225</version>\n"+
                    "\t</dependency>");

        }});

        features.put("jax-rs", new Feature(){{
            setName("jax-rs");
            setGradle("    implementation(\"io.micronaut.jaxrs:micronaut-jaxrs-server\")");
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.jaxrs:micronaut-jaxrs-processor\")");
            setTestGradle("    testAnnotationProcessor(\"io.micronaut.jaxrs:micronaut-jaxrs-processor\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.jaxrs</groupId>\n" +
                    "\t\t<artifactId>micronaut-jaxrs-server</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setAnnotationMaven(" <path>\n" +
                    "              <groupId>io.micronaut.jaxrs</groupId>\n" +
                    "              <artifactId>micronaut-jaxrs-processor</artifactId>\n" +
                    "              <version>${micronaut.jaxrs.version}</version>\n" +
                    "            </path>");
        }});

        features.put("grpc", new Feature(){{
            setName("grpc");
            setGradlePlugins(
                    new ArrayList<String>(){{
                        add("    id(\"com.google.protobuf\") version \"0.8.15\"");
                    }}
            );
            setGradle("    implementation(\"io.micronaut.grpc:micronaut-grpc-runtime\")");
            setGradleTask("sourceSets {\n" +
                    "    main {\n" +
                    "        java {\n" +
                    "            srcDirs(\"build/generated/source/proto/main/grpc\")\n" +
                    "            srcDirs(\"build/generated/source/proto/main/java\")\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n\n" +
                    "protobuf {\n" +
                    "    protoc { artifact = \"com.google.protobuf:protoc:3.20.1\" }\n" +
                    "    plugins {\n" +
                    "        grpc { artifact = \"io.grpc:protoc-gen-grpc-java:1.46.0\" }\n" +
                    "    }\n" +
                    "    generateProtoTasks {\n" +
                    "        all()*.plugins { grpc {} }\n" +
                    "    }\n" +
                    "}");
            setMaven(new ArrayList<String>(){{

                add("\t<dependency>\n" +
                        "\t\t<groupId>io.micronaut.grpc</groupId>\n" +
                        "\t\t<artifactId>micronaut-grpc-runtime</artifactId>\n" +
                        "\t\t<scope>compile</scope>\n" +
                        "\t</dependency>");
            }});
            setPlugin(new Plugin(){{
                setGroupId("com.github.os72");
                setArtifactId("protoc-jar-maven-plugin");
            }});

        }});
        features.put("microstream", new Feature(){{
            setName("microstream");
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.microstream:micronaut-microstream-annotations\")");
            setGradle("    implementation(\"io.micronaut.microstream:micronaut-microstream\")\n"+
                    "    implementation(\"io.micronaut.microstream:micronaut-microstream-annotations\")");
            setAnnotationMaven("\t\t<path>\n" +
                    "\t\t\t<groupId>io.micronaut.microstream</groupId>\n" +
                    "\t\t\t<artifactId>micronaut-microstream-annotations</artifactId>\n" +
                    "\t\t\t<version>${micronaut.microstream.version}</version>\n" +
                    "\t\t</path>");
            getMaven().add("\t <dependency>\n" +
                    "\t\t<groupId>io.micronaut.microstream</groupId>\n" +
                    "\t\t<artifactId>micronaut-microstream</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            getMaven().add("\t <dependency>\n" +
                    "\t\t<groupId>io.micronaut.microstream</groupId>\n" +
                    "\t\t<artifactId>micronaut-microstream-annotations</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});


        features.put("spring", new Feature(){
            {
                setName("spring");
                setAnnotationGradle(" annotationProcessor(\"io.micronaut.spring:micronaut-spring-annotation\")");
                setTestGradleAnnotation("testAnnotationProcessor(\"io.micronaut.spring:micronaut-spring-annotation\")");
                setAnnotationMaven("\t<path>\n" +
                        "\t\t<groupId>io.micronaut.spring</groupId>\n" +
                        "\t\t<artifactId>micronaut-spring-annotation</artifactId>\n" +
                        "\t\t<version>${micronaut.spring.version}</version>\n" +
                        "\t</path>");
            }
        });
        features.put("spring-boot", new Feature(){
            {
                setName("spring-boot");
                setAnnotationGradle("\tannotationProcessor(\"io.micronaut.spring:micronaut-spring-boot-annotation\")\n");
                setTestGradleAnnotation("\ttestAnnotationProcessor(\"io.micronaut.spring:micronaut-spring-boot-annotation\")\n");
                setGradle("\timplementation(\"org.springframework.boot:spring-boot-starter\")\n\truntimeOnly(\"io.micronaut.spring:micronaut-spring-boot\")\n");
                getMaven().add("\t<dependency>\n" +
                        "\t\t<groupId>org.springframework.boot</groupId>\n" +
                        "\t\t<artifactId>spring-boot-starter</artifactId>\n" +
                        "\t\t<scope>compile</scope>\n" +
                        "\t</dependency>");

            }
        });


        features.put("spring-web", new Feature(){
            {
                setName("spring-web");
                setAnnotationGradle("\tannotationProcessor(\"io.micronaut.spring:micronaut-spring-web-annotation\")\n");
                setGradle("\timplementation(\"org.springframework.boot:spring-boot-starter-web\")\n\truntimeOnly(\"io.micronaut.spring:micronaut-spring-web\")\n");
                setTestGradleAnnotation("\ttestAnnotationProcessor(\"io.micronaut.spring:micronaut-spring-web-annotation\")\n");
                getMaven().add("\t<dependency>\n" +
                                                "\t\t<groupId>org.springframework.boot</groupId>\n" +
                                                "\t\t<artifactId>spring-boot-starter-web</artifactId>\n" +
                                                "\t\t<scope>compile</scope>\n" +

                        "\t</dependency>");
                getMaven().add("\t<dependency>\n" +
                        "\t\t<groupId>io.micronaut.spring</groupId>\n" +

                        "\t\t<artifactId>micronaut-spring-web</artifactId>\n" +

                        "\t\t<scope>runtime</scope>\n" +

                        "\t</dependency>");
                setAnnotationMaven("\t<path>\n" +
                        "\t\t<groupId>io.micronaut.spring</groupId>\n" +
                        "\t\t<artifactId>micronaut-spring-web-annotation</artifactId>\n" +
                        "\t\t<version>${micronaut.spring.version}</version>\n" +
                        "\t</path>");
            }
        });

        features.put("views-thymeleaf", new Feature(){{
            setName("views-thymeleaf");
            setGradle("\timplementation(\"io.micronaut.views:micronaut-views-thymeleaf\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.views</groupId>\n" +
                    "\t\t<artifactId>micronaut-views-thymeleaf</artifactId>\n" +

                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});
        features.put("views-velocity", new Feature(){{
            setName("views-velocity");
            setGradle("\timplementation(\"io.micronaut.views:micronaut-views-velocity\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.views</groupId>\n" +
                    "\t\t<artifactId>micronaut-views-velocity</artifactId>\n" +

                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});

        features.put("views-freemarker", new Feature(){{
            setName("views-velocity");
            setGradle("\timplementation(\"io.micronaut.views:micronaut-views-freemarker\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.views</groupId>\n" +
                    "\t\t<artifactId>micronaut-views-freemarker</artifactId>\n" +

                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});
        features.put("email-template", new Feature(){{
            setName("email-template");
            setGradle("\timplementation(\"io.micronaut.email:micronaut-email-template\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.email</groupId>\n" +
                    "\t\t<artifactId>micronaut-email-template</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});


        features.put("discovery-eureka", new Feature(){{
            setName("discovery-eureka");
            setGradle("\timplementation(\"io.micronaut.discovery:micronaut-discovery-client\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.discovery</groupId>\n" +
                    "\t\t<artifactId>micronaut-discovery-client</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});
        features.put("discovery-consul", new Feature(){{
            setName("discovery-consul");
            setGradle("\timplementation(\"io.micronaut.discovery:micronaut-discovery-client\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.discovery</groupId>\n" +
                    "\t\t<artifactId>micronaut-discovery-client</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});
        features.put("discovery-kubernetes", new Feature(){{
            setName("discovery-kubernetes");
            setGradle("\timplementation(\"io.micronaut.kubernetes:micronaut-kubernetes-discovery-client\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.kubernetes</groupId>\n" +
                    "\t\t<artifactId>micronaut-kubernetes-discovery-client</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("object-storage-aws", new Feature(){{
            setName("object-storage-aws");
            setGradle("\timplementation(\"io.micronaut.objectstorage:micronaut-object-storage-aws\")\n" +
                    "\timplementation(\"io.micronaut.aws:micronaut-aws-sdk-v2\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.objectstorage</groupId>\n" +
                    "\t\t<artifactId>micronaut-object-storage-aws</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("object-storage-gcp", new Feature(){{
            setName("object-storage-gcp");
            setGradle("\timplementation(\"io.micronaut.objectstorage:micronaut-object-storage-gcp\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.objectstorage</groupId>\n" +
                    "\t\t<artifactId>micronaut-object-storage-gcp</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});

        features.put("object-storage-oracle-cloud", new Feature(){{
            setName("object-storage-oracle-cloud");
            setGradle("\timplementation(\"io.micronaut.objectstorage:micronaut-object-storage-oracle-cloud\")\n" +
                    "\timplementation(\"io.micronaut.oraclecloud:micronaut-oraclecloud-sdk\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.objectstorage</groupId>\n" +
                    "\t\t<artifactId>micronaut-object-storage-oracle-cloud</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.oraclecloud</groupId>\n" +
                    "\t\t<artifactId>micronaut-oraclecloud-sdk</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
        }});
        features.put("object-storage-azure", new Feature(){{
            setName("object-storage-azure");
            setGradle("\timplementation(\"io.micronaut.objectstorage:micronaut-object-storage-azure\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.objectstorage</groupId>\n" +
                    "\t\t<artifactId>micronaut-object-storage-azure</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});

        features.put("yaml", new Feature(){{
            setName("yaml");
            setGradle("\truntimeOnly(\"org.yaml:snakeyaml\")\n");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.yaml</groupId>\n" +
                    "\t\t<artifactId>snakeyaml</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");
        }});
        return features;
    }
}
