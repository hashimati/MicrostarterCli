package io.hashimati.microcli.services;


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


    private HashMap<String, String> javaTemplates = new HashMap<>(), groovyTemplates = new HashMap<>(),
            kotlinTemplates = new HashMap<>(),
            sqlEntityTemplates = new HashMap<>(),
            properties = new HashMap<>(),
            graphqlTemplates = new HashMap<>();

    public static final String
            CLIENT = "client",
            CONTROLLER = "controller",
            JDBC_REPOSITORY = "jdbcRepository",
            ENTITY = "entity",
            EXCEPTION_HANDLER = "entityExceptionHandler",
            GENERAL_EXCEPTION = "entityGeneralException",
            REPOSITORY_TEST = "EntityRepositoryTest",
            ENUM = "enum",
            MONGO_CONTROLLER = "mongocontroller",
            MONGO_REPOSITORY = "mongorepository",
            MONGO_SERVICE = "mongoservice",
            MONGO_CLIENT = "mongoclient",
            NEO4J_CONTROLLER = "neo4jcontroller",
            NEO4J_REPOSITORY = "neo4jrepository",
            NEO4J_SERVICE = "neo4jservice",
            RANDOMIZER = "Randomizer",
            REPOSITORY = "repository",
            SERVICE = "service",
            GRAPHQL_QUERY_FACOTRY = "graphql_query_factory",
            GRAPHQL_QUERY_RESOLVER = "graphql_query_resolver",
            CONTROLLER_UNIT_TEST = "controller_unit",
            CONTROLLER_SPOCK_TEST = "controller_spock",
            CONTROLLER_KOTLIN_TEST = "controller_kotlintest",
            //COMP is stands for Component.
            COMP_CONTROLLER = "comp_controller",
            COMP_Singleton = "comp_singleton",
            COMP_REPOSITORY= "comp_repository",
            COMP_CLIENT = "comp_client",
            COMP_JOB= "comp_job",
            COMP_WEBSOCKET = "comp_websocket",
            COMP_WEBSOCKET_CLIENT= "comp_websocket_client",

            RABBITMQ_CLIENT = "rabbitMQ_client",
            RABBITMQ_LISTENER = "rabbitMQ_listener",
            KAFKA_CLIENT = "kafka_client",
            KAFKA_LISTENER = "kafaka_listener",
            NATS_CLIENT = "nats_client",
            NATS_LISTENER = "nats_listener",
                    GRAPHQL_ENUM = "graphql_enum",
                    GRAPHQL_SCHEMA = "graphql_schema",
                    GRAPHQL_TYPE = "graphql_type",
                    GRAPHQL_QUERY = "graphql_query",
                    GRAPHQL_DATA = "graphql_query",
                    GRAPHQL_MUTATION = "graphql_query";




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
    NEO4J_yml = "NEO4J",
    KAFKA_yml = "KAFKA",
    RABBITMQ_yml = "RABBITMQ",
    NATS_yml = "NATS",
            GRAPHQL_yml = "GRAPHQL";




    @EventListener
    public void loadTemplates(StartupEvent event) {


        javaTemplates.put(CLIENT, "micronaut/entityTemplates/java/client.txt");
        javaTemplates.put(CONTROLLER, "micronaut/entityTemplates/java/controller.txt");
        javaTemplates.put(ENTITY, "micronaut/entityTemplates/java/entity.txt");
        javaTemplates.put(EXCEPTION_HANDLER, "micronaut/entityTemplates/java/entityExceptionHandler.txt");
        javaTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/java/entityGeneralException.txt");
        javaTemplates.put(ENUM, "micronaut/entityTemplates/java/enum.txt");
        javaTemplates.put(JDBC_REPOSITORY, "micronaut/entityTemplates/java/jdbcRepository.txt");
        javaTemplates.put(MONGO_CONTROLLER, "micronaut/entityTemplates/java/mongocontroller.txt");
        javaTemplates.put(MONGO_REPOSITORY, "micronaut/entityTemplates/java/mongorepository.txt");
        javaTemplates.put(MONGO_CLIENT, "micronaut/entityTemplates/java/mongoclient.txt");
        javaTemplates.put(MONGO_SERVICE, "micronaut/entityTemplates/java/mongoservice.txt");
        javaTemplates.put(NEO4J_CONTROLLER, "micronaut/entityTemplates/java/neo4jcontroller.txt");
        javaTemplates.put(NEO4J_REPOSITORY, "micronaut/entityTemplates/java/neo4jrepository.txt");
        javaTemplates.put(NEO4J_SERVICE, "micronaut/entityTemplates/java/neo4jservice.txt");
        javaTemplates.put(REPOSITORY, "micronaut/entityTemplates/java/repository.txt");
        javaTemplates.put(SERVICE, "micronaut/entityTemplates/java/service.txt");
        javaTemplates.put(RANDOMIZER,  "micronaut/entityTemplates/java/Randomizer.txt");
        javaTemplates.put(CONTROLLER_UNIT_TEST,  "micronaut/entityTemplates/java/controllerJunitTest.txt");
        javaTemplates.put(COMP_CONTROLLER,"micronaut/components/java/controller.txt");
        javaTemplates.put(COMP_Singleton,"micronaut/components/java/Service.txt");
        javaTemplates.put(COMP_JOB,"micronaut/components/java/job.txt");
        javaTemplates.put(COMP_CLIENT,"micronaut/components/java/client.txt");
        javaTemplates.put(COMP_REPOSITORY,"micronaut/components/java/repository.txt");
        javaTemplates.put(COMP_WEBSOCKET,"micronaut/components/java/websocket.txt");
        javaTemplates.put(COMP_WEBSOCKET_CLIENT,"micronaut/components/java/websocketClient.txt");
        javaTemplates.put(COMP_CLIENT,"micronaut/components/java/client.txt");
        javaTemplates.put(RABBITMQ_CLIENT,"micronaut/components/java/rabbitmqClient.txt");
        javaTemplates.put(RABBITMQ_LISTENER,"micronaut/components/java/rabbitmqConsumer.txt");
        javaTemplates.put(KAFKA_CLIENT,"micronaut/components/java/kafkaClient.txt");
        javaTemplates.put(KAFKA_LISTENER,"micronaut/components/java/kafkaConsumer.txt");
        javaTemplates.put(NATS_CLIENT,"micronaut/components/java/natsClient.txt");
        javaTemplates.put(NATS_LISTENER,"micronaut/components/java/natsConsumer.txt");
        javaTemplates.put(GRAPHQL_QUERY_FACOTRY, "micronaut/components/java/QueryFactory.txt");
        javaTemplates.put(GRAPHQL_QUERY_RESOLVER,"micronaut/components/java/QueryResolver.txt");



        groovyTemplates.put(CLIENT, "micronaut/entityTemplates/groovy/client.txt");
        groovyTemplates.put(CONTROLLER, "micronaut/entityTemplates/groovy/controller.txt");
        groovyTemplates.put(ENTITY, "micronaut/entityTemplates/groovy/entity.txt");
        groovyTemplates.put(EXCEPTION_HANDLER, "micronaut/entityTemplates/groovy/entityExceptionHandler.txt");
        groovyTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/groovy/entityGeneralException.txt");
        groovyTemplates.put(ENUM, "micronaut/entityTemplates/groovy/enum.txt");
        groovyTemplates.put(JDBC_REPOSITORY, "micronaut/entityTemplates/groovy/jdbcRepository.txt");
        groovyTemplates.put(MONGO_CONTROLLER, "micronaut/entityTemplates/groovy/mongocontroller.txt");
        groovyTemplates.put(MONGO_REPOSITORY, "micronaut/entityTemplates/groovy/mongorepository.txt");
        groovyTemplates.put(MONGO_SERVICE, "micronaut/entityTemplates/groovy/mongoservice.txt");
        groovyTemplates.put(MONGO_CLIENT, "micronaut/entityTemplates/groovy/mongoclient.txt");
        groovyTemplates.put(NEO4J_CONTROLLER, "micronaut/entityTemplates/groovy/neo4jcontroller.txt");
        groovyTemplates.put(NEO4J_REPOSITORY, "micronaut/entityTemplates/groovy/neo4jrepository.txt");
        groovyTemplates.put(NEO4J_SERVICE, "micronaut/entityTemplates/groovy/neo4jservice.txt");
        groovyTemplates.put(REPOSITORY, "micronaut/entityTemplates/groovy/repository.txt");
        groovyTemplates.put(SERVICE, "micronaut/entityTemplates/groovy/service.txt");
        groovyTemplates.put(RANDOMIZER,  "micronaut/entityTemplates/groovy/Randomizer.txt");
        groovyTemplates.put(CONTROLLER_UNIT_TEST,  "micronaut/entityTemplates/groovy/controllerJunitTest.txt");
        groovyTemplates.put(CONTROLLER_SPOCK_TEST,  "micronaut/entityTemplates/groovy/controllerSpockTest.txt");
        groovyTemplates.put(COMP_CONTROLLER,"micronaut/components/groovy/controller.txt");
        groovyTemplates.put(COMP_Singleton,"micronaut/components/groovy/Service.txt");
        groovyTemplates.put(COMP_JOB,"micronaut/components/groovy/job.txt");
        groovyTemplates.put(COMP_CLIENT,"micronaut/components/groovy/client.txt");
        groovyTemplates.put(RABBITMQ_CLIENT,"micronaut/components/groovy/rabbitmqClient.txt");
        groovyTemplates.put(RABBITMQ_LISTENER,"micronaut/components/groovy/rabbitmqConsumer.txt");
        groovyTemplates.put(KAFKA_CLIENT,"micronaut/components/groovy/kafkaClient.txt");
        groovyTemplates.put(KAFKA_LISTENER,"micronaut/components/groovy/kafkaConsumer.txt");
        groovyTemplates.put(COMP_REPOSITORY,"micronaut/components/groovy/repository.txt");
        groovyTemplates.put(COMP_WEBSOCKET,"micronaut/components/groovy/websocket.txt");
        groovyTemplates.put(COMP_WEBSOCKET_CLIENT,"micronaut/components/groovy/websocketClient.txt");
        groovyTemplates.put(NATS_CLIENT,"micronaut/components/groovy/natsClient.txt");
        groovyTemplates.put(NATS_LISTENER,"micronaut/components/groovy/natsConsumer.txt");
        groovyTemplates.put(GRAPHQL_QUERY_FACOTRY, "micronaut/components/groovy/QueryFactory.txt");
        groovyTemplates.put(GRAPHQL_QUERY_RESOLVER,"micronaut/components/groovy/QueryResolver.txt");



        kotlinTemplates.put(CLIENT, "micronaut/entityTemplates/kotlin/client.txt");
        kotlinTemplates.put(CONTROLLER, "micronaut/entityTemplates/kotlin/controller.txt");
        kotlinTemplates.put(ENTITY, "micronaut/entityTemplates/kotlin/entity.txt");
        kotlinTemplates.put(EXCEPTION_HANDLER, "micronaut/entityTemplates/kotlin/entityExceptionHandler.txt");
        kotlinTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/kotlin/entityGeneralException.txt");
        kotlinTemplates.put(ENUM, "micronaut/entityTemplates/kotlin/enum.txt");
        kotlinTemplates.put(JDBC_REPOSITORY, "micronaut/entityTemplates/kotlin/jdbcRepository.txt");
        kotlinTemplates.put(MONGO_CONTROLLER, "micronaut/entityTemplates/kotlin/mongocontroller.txt");
        kotlinTemplates.put(MONGO_REPOSITORY, "micronaut/entityTemplates/kotlin/mongorepository.txt");
        kotlinTemplates.put(MONGO_SERVICE, "micronaut/entityTemplates/kotlin/mongoservice.txt");
        kotlinTemplates.put(MONGO_CLIENT, "micronaut/entityTemplates/kotlin/mongoclient.txt");
        kotlinTemplates.put(NEO4J_CONTROLLER, "micronaut/entityTemplates/kotlin/neo4jcontroller.txt");
        kotlinTemplates.put(NEO4J_REPOSITORY, "micronaut/entityTemplates/kotlin/neo4jrepository.txt");
        kotlinTemplates.put(NEO4J_SERVICE, "micronaut/entityTemplates/kotlin/neo4jservice.txt");
        kotlinTemplates.put(REPOSITORY, "micronaut/entityTemplates/kotlin/repository.txt");
        kotlinTemplates.put(SERVICE, "micronaut/entityTemplates/kotlin/service.txt");
        kotlinTemplates.put(RANDOMIZER,  "micronaut/entityTemplates/kotlin/Randomizer.txt");
        kotlinTemplates.put(CONTROLLER_UNIT_TEST,  "micronaut/entityTemplates/kotlin/controllerJunitTest.txt");
        kotlinTemplates.put(COMP_CONTROLLER,"micronaut/components/kotlin/controller.txt");
        kotlinTemplates.put(COMP_Singleton,"micronaut/components/kotlin/Service.txt");
        kotlinTemplates.put(COMP_JOB,"micronaut/components/kotlin/job.txt");
        kotlinTemplates.put(COMP_CLIENT,"micronaut/components/kotlin/client.txt");
        kotlinTemplates.put(RABBITMQ_CLIENT,"micronaut/components/kotlin/rabbitmqClient.txt");
        kotlinTemplates.put(RABBITMQ_LISTENER,"micronaut/components/kotlin/rabbitmqConsumer.txt");
        kotlinTemplates.put(KAFKA_CLIENT,"micronaut/components/kotlin/kafkaClient.txt");
        kotlinTemplates.put(KAFKA_LISTENER,"micronaut/components/kotlin/kafkaConsumer.txt");
        kotlinTemplates.put(COMP_REPOSITORY,"micronaut/components/kotlin/repository.txt");
        kotlinTemplates.put(COMP_WEBSOCKET,"micronaut/components/kotlin/websocket.txt");
        kotlinTemplates.put(COMP_WEBSOCKET_CLIENT,"micronaut/components/kotlin/websocketClient.txt");
        kotlinTemplates.put(NATS_CLIENT,"micronaut/components/kotlin/natsClient.txt");
        kotlinTemplates.put(NATS_LISTENER,"micronaut/components/kotlin/natsConsumer.txt");
        kotlinTemplates.put(GRAPHQL_QUERY_FACOTRY, "micronaut/components/kotlin/QueryFactory.txt");
        kotlinTemplates.put(GRAPHQL_QUERY_RESOLVER,"micronaut/components/kotlin/QueryResolver.txt");


        sqlEntityTemplates.put(ATTRIBUTE, "micronaut/entityTemplates/sql/attribute_template.txt");
        sqlEntityTemplates.put(CONSTRAINT, "micronaut/entityTemplates/sql/constraint_template.sql");
        sqlEntityTemplates.put(CREATE, "micronaut/entityTemplates/sql/create_template.sql");


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
        properties.put(KAFKA_yml, "micronaut/entityTemplates/kafka_properties.txt");
        properties.put(RABBITMQ_yml, "micronaut/entityTemplates/rabbtmq_properties.txt");
        properties.put(NATS_yml, "micronaut/entityTemplates/nats_properties.txt");
        properties.put(GRAPHQL_yml, "micronaut/entityTemplates/graphql_properties.txt");

        graphqlTemplates.put(GRAPHQL_ENUM, "micronaut/entityTemplates/graphql/enum.txt");
        graphqlTemplates.put(GRAPHQL_SCHEMA, "micronaut/entityTemplates/graphql/schema.txt");
        graphqlTemplates.put(GRAPHQL_TYPE, "micronaut/entityTemplates/graphql/type.txt");
        graphqlTemplates.put(GRAPHQL_DATA, "micronaut/entityTemplates/graphql/data.txt");
        graphqlTemplates.put(GRAPHQL_MUTATION, "micronaut/entityTemplates/graphql/mutation.txt");
        graphqlTemplates.put(GRAPHQL_QUERY, "micronaut/entityTemplates/graphql/query.txt");
    }

    public void auxLoadTemplatePath(List<String> fileNames, HashMap<String, String> templates, String root)
    {

        fileNames.forEach(x-> {
            templates.putIfAbsent(x.substring(0, x.indexOf('_')), "classpath:"+root+"/" + x);
       });
    }

    public String loadTemplateContent(String path){
        ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();
       StringBuilder template = new StringBuilder();

        try {
         //   System.out.println(path + " " + loader.getResource("classpath:"+path).get() == null);
            Scanner scanner = new Scanner(loader.getResource("classpath:"+path).get().openStream());
            while (scanner.hasNextLine()) {
                template.append(scanner.nextLine()).append("\n");
            }
            return template.toString();
        } catch (Exception e) {
            System.out.println("Cannot read " + path);
            e.printStackTrace();
        }

        return template.toString();
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

    public HashMap<String, String> getGraphqlTemplates(){return graphqlTemplates;}
    public HashMap<String, String> getProperties(){return properties; }
    public HashMap<String, String> getSqlEntityTemplates() {
        return sqlEntityTemplates;
    }
}

