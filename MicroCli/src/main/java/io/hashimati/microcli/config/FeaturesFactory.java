package io.hashimati.microcli.config;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.micronaut.context.annotation.Factory;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;

import javax.inject.Singleton;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

@Factory
public class FeaturesFactory {


    @Singleton
    public static HashMap<String, Feature> features() throws FileNotFoundException {

        ProjectInfo projectInfo = MicronautProjectValidator.getProjectInfo();
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
                    "                </path>");
            setVersionProperties("<micronaut.data.version>3.0.0</micronaut.data.version>");
            getMavenProperties().putIfAbsent("micronaut.data.version", "3.0.0");

        }});


        //rdbc-core
        features.put("data-r2dbc", new Feature(){{


            setName("data-r2dbc");
            setGradle("    implementation(\"io.micronaut.r2dbc:micronaut-data-r2dbc\")");


            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.r2dbc</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-r2dbc</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-processor\")");
            setAnnotationMaven(
                    "                <path>\n" +
                            "                  <groupId>io.micronaut.data</groupId>\n" +
                            "                  <artifactId>micronaut-data-processor</artifactId>\n" +
                            "                  <version>${micronaut.data.version}</version>\n" +
                            "                </path>");
            getMavenProperties().putIfAbsent("micronaut.data.version", "3.0.0");

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
            getMavenProperties().putIfAbsent("micronaut.data.version", "3.0.0");


        }});
        features.put("mongo-reactive", new Feature(){{
            setName("mongo-reactive");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.mongodb</groupId>\n" +
                    "\t\t<artifactId>micronaut-mongo-reactive</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");

            setGradle("    implementation(\"io.micronaut.mongodb:micronaut-mongo-reactive\")");
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
                    "                </path>");
            setVersionProperties("<micronaut.data.version>3.0.0</micronaut.data.version>");
            getMavenProperties().putIfAbsent("micronaut.data.version", "3.0.0");
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
                    "      <groupId>mysql</groupId>\n" +
                    "      <artifactId>mysql-connector-java</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");
            setGradle("    runtimeOnly(\"mysql:mysql-connector-java\")");
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

            setDepndencyManagement("<dependency>\n" +
                    "        <groupId>org.testcontainers</groupId>\n" +
                    "        <artifactId>testcontainers-bom</artifactId>\n" +
                    "        <version>1.14.3</version>\n" +
                    "        <type>pom</type>\n" +
                    "        <scope>import</scope>\n" +
                    "      </dependency>");
            setTestContainerGradle("    testImplementation(platform(\"org.testcontainers:testcontainers-bom:1.14.3\"))");
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
                    "              <version>1.18.12</version>\n" +
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
            setGradle("    implementation(\"com.graphql-java-kickstart:graphql-java-tools:11.0.1\")");
            getMaven().add("\t<dependency>\n"+
                    "\t\t<groupId>>com.graphql-java-kickstart</groupId>\n" +
                    "\t\t<artifactId>graphql-java-tools</artifactId>\n"+
                    "\t\t<scope>compile</scope>\n"+
                    "\t\t<version>11.0.1</version>\n"+
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
            setGradle("     implementation(\"org.jasypt:jasypt:1.9.3\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>org.jasypt</groupId>\n" +
                    "\t\t<artifactId>jasypt</artifactId>\n" +
                    "\t\t<version>1.9.3</version>\n" +
                    "\t</dependency>\n");
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



        features.put("reactor", new Feature(){{

            setName("reactor");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.reactor</groupId>\n" +
                    "\t\t<artifactId>micronaut-reactor</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.reactor:micronaut-reactor\")");
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
            setGradle("    rewrite(\"org.openrewrite.recipe:rewrite-micronaut:1.0.0\")");
            getMavenProperties().putIfAbsent("openrewrite.maven.plugin.version", "4.9.0");
            getMavenProperties().putIfAbsent("rewrite-micronaut.version", "1.0.0");
            setGradleTask("rewrite {\n" +
                    "    activeRecipe(\"org.openrewrite.java.micronaut.Micronaut2to3Migration\")\n" +
                    "}");
            getGradlePlugins().add("    id(\"org.openrewrite.rewrite\") version \"5.7.0\"");

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
        features.put("tracing-jaeger", new Feature(){{
            setName("tracing-jaeger");

            setGradle("    implementation(\"io.micronaut:micronaut-tracing\")" +
                    "    implementation(\"io.micronaut:micronaut-tracing\")");
            getMaven().add("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut</groupId>\n" +
                    "\t\t<artifactId>micronaut-tracing</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            getMaven().add(" \t<dependency>\n" +
                    "\t\t<groupId>io.jaegertracing</groupId>\n" +
                    "\t\t<artifactId>jaeger-thrift</artifactId>\n" +
                    "\t\t<scope>runtime</scope>\n" +
                    "\t</dependency>");


        }});


        return features;
    }


}
