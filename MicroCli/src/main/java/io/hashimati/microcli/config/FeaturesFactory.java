package io.hashimati.microcli.config;


import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

@Factory
public class FeaturesFactory {


    @Singleton
    public static HashMap<String, Feature> features() throws FileNotFoundException {

        ProjectInfo projectInfo = MicronautProjectValidator.getProjectInfo();
        HashMap<String, Feature> features = new HashMap<>();
        features.putIfAbsent("flyway", new Feature(){{
            setName("flyway");
            setGradle("    implementation(\"io.micronaut.flyway:micronaut-flyway\")");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.flyway</groupId>\n" +
                    "\t\t<artifactId>micronaut-flyway</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");

        }});

        features.put("jdbc-hikari", new Feature(){{
            setName("jdbc-hikari");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.sql</groupId>\n" +
                    "\t\t<artifactId>micronaut-jdbc-hikari</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.sql:micronaut-jdbc-hikari\")");
        }});

        features.put("data-jdbc", new Feature(){{
            setName("data-jdbc");
            setMaven("\t<dependency>\n" +
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
               setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-processor\")");

           }

            setAnnotationMaven(
                    "                <path>\n" +
                    "                  <groupId>io.micronaut.data</groupId>\n" +
                    "                  <artifactId>micronaut-data-processor</artifactId>\n" +
                    "                  <version>${micronaut.data.version}</version>\n" +
                    "                </path>");
            setVersionProperties("<micronaut.data.version>1.1.3</micronaut.data.version>");
        }});

        features.put("mongo-reactive", new Feature(){{
            setName("mongo-reactive");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.mongodb</groupId>\n" +
                    "\t\t<artifactId>micronaut-mongo-reactive</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");

            setGradle("    implementation(\"io.micronaut.mongodb:micronaut-mongo-reactive\")");
        }});

        features.put("data-jpa", new Feature(){{
            setName("data-jpa");
            setMaven("\t<dependency>\n" +
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
                setAnnotationGradle("    annotationProcessor(\"io.micronaut.data:micronaut-data-processor\")");

            setAnnotationMaven("                <path>\n" +
                    "                  <groupId>io.micronaut.data</groupId>\n" +
                    "                  <artifactId>micronaut-data-processor</artifactId>\n" +
                    "                  <version>${micronaut.data.version}</version>\n" +
                    "                </path>");
            setVersionProperties("<micronaut.data.version>1.1.3</micronaut.data.version>");
        }});

        features.put("mongo-sync", new Feature(){{
            setName("mongo-sync");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.mongodb</groupId>\n" +
                    "\t\t<artifactId>micronaut-mongo-sync</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.mongodb:micronaut-mongo-sync\")");
        }});


        features.put("liquibase", new Feature(){{
            setName("liquibase");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.liquibase</groupId>\n" +
                    "\t\t<artifactId>micronaut-liquibase</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.liquibase:micronaut-liquibase\")");
        }});

        features.put("embed.mongo", new Feature(){{
            setName("embed.mongo");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>de.flapdoodle.embed</groupId>\n" +
                    "\t\t<artifactId>de.flapdoodle.embed.mongo</artifactId>\n" +
                    "\t\t<version>2.0.1</version>\n" +
                    "\t\t<scope>test</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    testImplementation(\"de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.0.1\")");
        }});

        features.put("h2", new Feature(){{
            setName("h2");
            setMaven("\t<dependency>\n" +
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
        }});


        features.put("mysql", new Feature(){{
            setName("mysql");
            setMaven("  <dependency>\n" +
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
            setMaven("    <dependency>\n" +
                    "      <groupId>org.postgresql</groupId>\n" +
                    "      <artifactId>postgresql</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");

            setMaven(" <dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>postgresql</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");
            setGradle("    runtimeOnly(\"org.postgresql:postgresql\")\n");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:postgresql\")");


        }});

        features.put("mariadb", new Feature(){{
            setName("mariadb");
            setMaven(" <dependency>\n" +
                    "      <groupId>org.mariadb.jdbc</groupId>\n" +
                    "      <artifactId>mariadb-java-client</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");
            setTestMaven("<dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>mariadb</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");
            setGradle("    runtimeOnly(\"org.mariadb.jdbc:mariadb-java-client\")\n");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:mariadb\")\n");
        }});

        features.put("oracle", new Feature(){{
            setName("oracle");
            setMaven("<dependency>\n" +
                    "      <groupId>com.oracle.ojdbc</groupId>\n" +
                    "      <artifactId>ojdbc8</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");
            setTestMaven(" <dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>oracle-xe</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");
            setGradle("    runtimeOnly(\"com.oracle.ojdbc:ojdbc8\")\n");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:oracle-xe\")\n");
        }});

        features.put("sqlserver", new Feature(){{
            setName("sqlserver");
            setMaven("   <dependency>\n" +
                    "      <groupId>com.microsoft.sqlserver</groupId>\n" +
                    "      <artifactId>mssql-jdbc</artifactId>\n" +
                    "      <scope>runtime</scope>\n" +
                    "    </dependency>\n");
            setGradle("    runtimeOnly(\"com.microsoft.sqlserver:mssql-jdbc\")\n");
            setTestGradle("    testRuntimeOnly(\"org.testcontainers:mssqlserver\")");

            setTestMaven("  <dependency>\n" +
                    "      <groupId>org.testcontainers</groupId>\n" +
                    "      <artifactId>mssqlserver</artifactId>\n" +
                    "      <scope>test</scope>\n" +
                    "    </dependency>");
        }});

        features.put("graphql", new Feature(){{
            setName("graphql");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.graphql</groupId>\n" +
                    "\t\t<artifactId>micronaut-graphql</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>");
            setGradle("    implementation(\"io.micronaut.graphql:micronaut-graphql\")\n");
        }});
        features.put("openapi", new Feature(){{
            setName("openapi");
            setMaven("    <dependency>\n" +
                    "      <groupId>io.swagger.core.v3</groupId>\n" +
                    "      <artifactId>swagger-annotations</artifactId>\n" +
                    "      <scope>compile</scope>\n" +
                    "    </dependency>");
            setGradle("    implementation(\"io.swagger.core.v3:swagger-annotations\")");
            if(projectInfo.getSourceLanguage().equalsIgnoreCase(JAVA_LANG))
                setAnnotationGradle("    annotationProcessor(\"io.micronaut.configuration:micronaut-openapi\")");
            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG))
                setAnnotationGradle("    compileOnly(\"io.micronaut.configuration:micronaut-openapi\")");

            else if(projectInfo.getSourceLanguage().equalsIgnoreCase(KOTLIN_LANG))
                setAnnotationGradle("    kapt(\"io.micronaut.configuration:micronaut-openapi\")");



            setAnnotationMaven(
                    "            <path>\n" +
                    "              <groupId>io.micronaut.configuration</groupId>\n" +
                    "              <artifactId>micronaut-openapi</artifactId>\n" +
                    "              <version>${micronaut.openapi.version}</version>\n" +
                    "            </path>");
        }});


        features.put("lombok", new Feature(){{
            setName("lombok");
            setMaven("\t<dependency>\n" +
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
            setGradle("    compileOnly 'org.projectlombok:lombok:1.18.12'\n");
            setAnnotationGradle("\n    annotationProcessor 'org.projectlombok:lombok:1.18.12'");
            setTestGradleAnnotation("    testAnnotationProcessor 'org.projectlombok:lombok:1.18.12'");
            setTestGradle("    testCompileOnly 'org.projectlombok:lombok:1.18.12'\n");

        }});



        return features;
    }

}
