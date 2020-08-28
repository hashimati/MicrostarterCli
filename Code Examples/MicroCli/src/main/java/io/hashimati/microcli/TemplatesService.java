package io.hashimati.microcli;


import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Singleton
public class TemplatesService {



    private HashMap<String, String> javaTemplates= new HashMap<>()
            , groovyTemplates = new HashMap<>(),
            kotlinTemplates = new HashMap<>(),
            sqlEntityTemplates = new HashMap<>(), 
            properties = new HashMap<>();

    public static final String
    CLIENT = "client",
            CONTROLLER= "controller",
            JDBC_REPOSITORY="jdbcRepository",
            ENTITY = "entity",
            EXCEPTION_HANDLER="entityExceptionHandler",
            GENERAL_EXCEPTION ="entityGeneralException",
            REPOSITORY_TEST = "EntityRepositoryTest",
            ENUM = "enum",
            MONGO_CONTROLLER = "mongocontroller",
            MONGO_REPOSITORY = "mongorepository",
            MONGO_SERVICE = "mongoservice",
            NEO4J_CONTROLLER = "neo4jcontroller",
            NEO4J_REPOSITORY = "neo4jrepository",
            NEO4J_SERVICE = "neo4jservice",
            RANDOMIZER = "Randomizer",
            REPOSITORY = "repository",
            SERVICE = "service";
    public static String ATTRIBUTE = "attribute",
    CONSTRAINT  ="constraint",
   CREATE= "create";


    //=======================

    public static final String OPENAPI_yml = "OPENAPI",
    H2_JDBC_yml = "h2",
    ORACLE_JDBC_yml = "oracle",
            POSTGRES_JDBC_yml = "postgres",
            MARIADB_JDBC_yml = "mariadb",
            MYSQL_JDBC_yml = "mysql",
            SQLSERVER_JDBC_yml = "sqlserver",
            ORACLE_JDBC_TEST_yml = "oracle_test",
            POSTGRES_JDBC_TEST_yml = "postgres_test",
            MARIADB_JDBC_TEST_yml = "mariadb_test",
            MYSQL_JDBC_TEST_yml = "mysql_test",
            SQLSERVER_TEST_JDBC_yml = "sqlserver_test",
            JPA_yml = "jpa",
    MONGODB_yml = "MONGODB",
    CASSANDRA_yml = "CASSANDRA",
    NEO4J_yml = "NEO4J";




    @EventListener
    public void loadTemplates(StartupEvent event) throws IOException {


        javaTemplates.put(CLIENT,"micronaut/entityTemplates/java/client.txt" );
        javaTemplates.put(CONTROLLER, "micronaut/entityTemplates/java/controller.txt");
        javaTemplates.put(ENTITY,"micronaut/entityTemplates/java/entity.txt");
        javaTemplates.put(EXCEPTION_HANDLER,"micronaut/entityTemplates/java/entityExceptionHandler.txt");
        javaTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/java/entityGeneralException.txt");
        javaTemplates.put(ENUM,"micronaut/entityTemplates/java/enum.txt" );
        javaTemplates.put(JDBC_REPOSITORY,"micronaut/entityTemplates/java/jdbcRepository.txt");
        javaTemplates.put(MONGO_CONTROLLER,"micronaut/entityTemplates/java/mongocontroller.txt" );
        javaTemplates.put(MONGO_REPOSITORY,"micronaut/entityTemplates/java/mongorepository.txt");
        javaTemplates.put(MONGO_SERVICE, "micronaut/entityTemplates/java/mongoservice.txt");
        javaTemplates.put(NEO4J_CONTROLLER,"micronaut/entityTemplates/java/neo4jcontroller.txt");
        javaTemplates.put(NEO4J_REPOSITORY,"micronaut/entityTemplates/java/neo4jrepository.txt");
        javaTemplates.put(NEO4J_SERVICE, "micronaut/entityTemplates/java/neo4jservice.txt");
        javaTemplates.put(REPOSITORY, "micronaut/entityTemplates/java/repository.txt");
        javaTemplates.put(SERVICE, "micronaut/entityTemplates/java/service.txt");

        groovyTemplates.put(CLIENT,"micronaut/entityTemplates/groovy/client.txt" );
        groovyTemplates.put(CONTROLLER, "micronaut/entityTemplates/groovy/controller.txt");
        groovyTemplates.put(ENTITY,"micronaut/entityTemplates/groovy/entity.txt");
        groovyTemplates.put(EXCEPTION_HANDLER,"micronaut/entityTemplates/groovy/entityExceptionHandler.txt");
        groovyTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/groovy/entityGeneralException.txt");
        groovyTemplates.put(ENUM,"micronaut/entityTemplates/groovy/enum.txt" );
        groovyTemplates.put(JDBC_REPOSITORY,"micronaut/entityTemplates/groovy/jdbcRepository.txt");
        groovyTemplates.put(MONGO_CONTROLLER,"micronaut/entityTemplates/groovy/mongocontroller.txt" );
        groovyTemplates.put(MONGO_REPOSITORY,"micronaut/entityTemplates/groovy/mongorepository.txt");
        groovyTemplates.put(MONGO_SERVICE, "micronaut/entityTemplates/groovy/mongoservice.txt");
        groovyTemplates.put(NEO4J_CONTROLLER,"micronaut/entityTemplates/groovy/neo4jcontroller.txt");
        groovyTemplates.put(NEO4J_REPOSITORY,"micronaut/entityTemplates/groovy/neo4jrepository.txt");
        groovyTemplates.put(NEO4J_SERVICE, "micronaut/entityTemplates/groovy/neo4jservice.txt");
        groovyTemplates.put(REPOSITORY, "micronaut/entityTemplates/groovy/repository.txt");
        groovyTemplates.put(SERVICE, "micronaut/entityTemplates/groovy/service.txt");


        kotlinTemplates.put(CLIENT,"micronaut/entityTemplates/kotlin/client.txt" );
        kotlinTemplates.put(CONTROLLER, "micronaut/entityTemplates/kotlin/controller.txt");
        kotlinTemplates.put(ENTITY,"micronaut/entityTemplates/kotlin/entity.txt");
        kotlinTemplates.put(EXCEPTION_HANDLER,"micronaut/entityTemplates/kotlin/entityExceptionHandler.txt");
        kotlinTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/kotlin/entityGeneralException.txt");
        kotlinTemplates.put(ENUM,"micronaut/entityTemplates/kotlin/enum.txt" );
        kotlinTemplates.put(JDBC_REPOSITORY,"micronaut/entityTemplates/kotlin/jdbcRepository.txt");
        kotlinTemplates.put(MONGO_CONTROLLER,"micronaut/entityTemplates/kotlin/mongocontroller.txt" );
        kotlinTemplates.put(MONGO_REPOSITORY,"micronaut/entityTemplates/kotlin/mongorepository.txt");
        kotlinTemplates.put(MONGO_SERVICE, "micronaut/entityTemplates/kotlin/mongoservice.txt");
        kotlinTemplates.put(NEO4J_CONTROLLER,"micronaut/entityTemplates/kotlin/neo4jcontroller.txt");
        kotlinTemplates.put(NEO4J_REPOSITORY,"micronaut/entityTemplates/kotlin/neo4jrepository.txt");
        kotlinTemplates.put(NEO4J_SERVICE, "micronaut/entityTemplates/kotlin/neo4jservice.txt");
        kotlinTemplates.put(REPOSITORY, "micronaut/entityTemplates/kotlin/repository.txt");
        kotlinTemplates.put(SERVICE, "micronaut/entityTemplates/kotlin/service.txt");

        sqlEntityTemplates.put(ATTRIBUTE, "micronaut/entityTemplates/sql/attribute_template.txt");
        sqlEntityTemplates.put(CONSTRAINT,"micronaut/entityTemplates/sql/constraint_template.sql");
        sqlEntityTemplates.put(CREATE,"micronaut/entityTemplates/sql/create_template.sql");


        properties.put(OPENAPI_yml, "micronaut/entityTemplates/OpenAPI.yml");
//        properties.put(H2_JDBC_yml, "micronaut/entityTemplates/");
        properties.put(ORACLE_JDBC_yml, "micronaut/entityTemplates/oracle_jdbc_properties.txt");
        properties.put(H2_JDBC_yml, "micronaut/entityTemplates/h2_jdbc_properties.txt");
        properties.put(MARIADB_JDBC_yml, "micronaut/entityTemplates/mariadb_jdbc_properties.txt");
        properties.put(POSTGRES_JDBC_yml, "micronaut/entityTemplates/postgres_jdbc_proper_TESTties.txt");
        properties.put(MYSQL_JDBC_yml, "micronaut/entityTemplates/mysql_jdbc_properties.txt");
        properties.put(SQLSERVER_JDBC_yml, "micronaut/entityTemplates/sqlserver_jdbc_properties.txt");

        properties.put(ORACLE_JDBC_TEST_yml, "micronaut/entityTemplates/oracle_jdbc_test_properties.txt");
        properties.put(MARIADB_JDBC_TEST_yml, "micronaut/entityTemplates/mariadb_jdbc_test_properties.txt");
        properties.put(POSTGRES_JDBC_TEST_yml, "micronaut/entityTemplates/postgres_jdbc_test_properties.txt");
        properties.put(MYSQL_JDBC_TEST_yml, "micronaut/entityTemplates/mysql_jdbc_test_properties.txt");
        properties.put(SQLSERVER_TEST_JDBC_yml, "micronaut/entityTemplates/sqlserver_jdbc_test_properties.txt");



        properties.put(JPA_yml, "micronaut/entityTemplates/jpa_properties.txt");
        properties.put(MONGODB_yml, "micronaut/entityTemplates/mongodb_properties.txt");
        properties.put(CASSANDRA_yml, "micronaut/entityTemplates/cassandra_properties.txt");
        properties.put(NEO4J_yml, "micronaut/entityTemplates/neo4j_properties.txt");
    }

    public void auxLoadTemplatePath(List<String> fileNames, HashMap<String, String> templates, String root)
    {

        fileNames.forEach(x-> {
            templates.putIfAbsent(x.substring(0, x.indexOf('_')), "classpath:"+root+"/" + x);
       });
    }

    public String loadTemplateContent(String path){
        ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();
       String template = "";
        try {
            System.out.println(path);
            Scanner scanner = new Scanner(loader.getResource("classpath:"+path).get().openStream());
            while (scanner.hasNextLine()) {
                template += scanner.nextLine() + "\n";
            }
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return template;
    }
    @Deprecated
    public void auxLoadTemplates(List<String> fileNames, HashMap<String, String> templates, String root)
    {
        fileNames.forEach(x->{

            ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();

            String key = x.substring(0, x.indexOf('_'));
            try {
                String template = "";
                Scanner scanner = new Scanner(loader.getResource("classpath:"+root+"/" + x).get().openStream());
                while (scanner.hasNextLine()) {
                    template += scanner.nextLine() + "\n";
                }
                templates.put(key, template);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
    

    public HashMap<String, String> getJavaTemplates() {
        return javaTemplates;
    }

    public HashMap<String, String> getGroovyTemplates() {
        return groovyTemplates;
    }

    public HashMap<String, String> getKotlinTemplates() {
        return kotlinTemplates;
    }

    public HashMap<String, String> getProperties(){return properties; }
    public HashMap<String, String> getSqlEntityTemplates() {
        return sqlEntityTemplates;
    }
}

