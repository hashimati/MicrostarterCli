package io.hashimati.config;


import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.util.HashMap;

@Factory
public class FeaturesFactory {


    @Singleton
    public static HashMap<String, Feature> features(){

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

        features.put("data-hibernate-jpa", new Feature(){{
            setName("data-hibernate-jpa");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>io.micronaut.data</groupId>\n" +
                    "\t\t<artifactId>micronaut-data-hibernate-jpa</artifactId>\n" +
                    "\t\t<scope>compile</scope>\n" +
                    "\t</dependency>\n");
            setGradle("    implementation(\"io.micronaut.data:micronaut-data-hibernate-jpa\")");
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
            setGradle("    runtimeOnly(\"com.h2database:h2\")");
        }});


        features.put("mysql", new Feature(){{
            setName("mysql");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>mysql</groupId>\n" +
                    "\t\t<artifactId>mysql-connector-java</artifactId>\n" +
                    "\t\t<version>8.0.17</version>\n" +
                    "\t</dependency>\n");
            setGradle("    compile group: 'mysql', name: 'mysql-connector-java', version: '8.0.17'");
        }});


        features.put("postgres", new Feature(){{
            setName("postgres");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>org.postgresql</groupId>\n" +
                    "\t\t<artifactId>postgresql</artifactId>\n" +
                    "\t\t<version>42.2.14</version>\n" +
                    "\t</dependency>\n");
            setGradle("    compile group: 'org.postgresql', name: 'postgresql', version: '42.2.14'\n");
        }});

        features.put("mariadb", new Feature(){{
            setName("mariadb");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>org.mariadb.jdbc</groupId>\n" +
                    "\t\t<artifactId>mariadb-java-client</artifactId>\n" +
                    "\t\t<version>2.6.2</version>\n" +
                    "\t</dependency>\n");
            setGradle("    compile group: 'org.mariadb.jdbc', name: 'mariadb-java-client', version: '2.6.2'\n'");
        }});

        features.put("oracle", new Feature(){{
            setName("oracle");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>com.oracle.database.jdbc</groupId>\n" +
                    "\t\t<artifactId>ojdbc10</artifactId>\n" +
                    "\t\t<version>19.7.0.0</version>\n" +
                    "\t</dependency>\n");
            setGradle("    compile group: 'com.oracle.database.jdbc', name: 'ojdbc10', version: '19.7.0.0'");
        }});

        features.put("sql server-jre_8", new Feature(){{
            setName("Sql Server-JRE_8");
            setMaven("\t<dependency>\n" +
                    "\t\tgroupId>com.microsoft.sqlserver</groupId>\n" +
                    "\t\t<artifactId>mssql-jdbc</artifactId>\n" +
                    "\t\t<version>8.3.1.jre8-preview</version>\n" +
                    "\t</dependency>\n");
            setGradle("    compile group: 'com.microsoft.sqlserver', name: 'mssql-jdbc', version: '8.3.1.jre8-preview'\n");
        }});

        features.put("sql server-jre_11", new Feature(){{
            setName("Sql Server-JRE_11");
            setMaven("\t<dependency>\n" +
                    "\t\tgroupId>com.microsoft.sqlserver</groupId>\n" +
                    "\t\t<artifactId>mssql-jdbc</artifactId>\n" +
                    "\t\t<version>8.3.1.jre11-preview</version>\n" +
                    "\t</dependency>\n");
            setGradle("    compile group: 'com.microsoft.sqlserver', name: 'mssql-jdbc', version: '8.3.1.jre11-preview'\n");
        }});
        features.put("sql server-jre_14", new Feature(){{
            setName("Sql Server-JRE_14");
            setMaven("\t<dependency>\n" +
                    "\t\tgroupId>com.microsoft.sqlserver</groupId>\n" +
                    "\t\t<artifactId>mssql-jdbc</artifactId>\n" +
                    "\t\t<version>8.3.1.jre14-preview</version>\n" +
                    "\t</dependency>\n");
            setGradle("compile group: 'com.microsoft.sqlserver', name: 'mssql-jdbc', version: '8.3.1.jre14-preview'\n");
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


        features.put("lombok", new Feature(){{
            setName("lombok");
            setMaven("\t<dependency>\n" +
                    "\t\t<groupId>org.projectlombok</groupId>\n" +
                    "\t\t<artifactId>lombok</artifactId>\n" +
                    "\t\t<version>1.18.12</version>\n" +
                    "\t\t<scope>provided</scope>\n" +
                    "\t</dependency>");
            setAnnotationMaven("<path>\n" +
                    "\t\t<groupId>org.projectlombok</groupId>\n" +
                    "\t\t<artifactId>lombok</artifactId>\n" +
                    "\t\t<version>1.18.12</version>\n" +
                    "\t</path>");
            setGradle("    compileOnly 'org.projectlombok:lombok:1.18.12'\n");
            setAnnotationGradle("    annotationProcessor 'org.projectlombok:lombok:1.18.12'");
            setTestGradleAnnotation("testAnnotationProcessor 'org.projectlombok:lombok:1.18.12'");
            setTestGradle("testCompileOnly 'org.projectlombok:lombok:1.18.12'\n");

        }});



        return features;
    }

}
