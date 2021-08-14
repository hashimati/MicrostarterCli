package io.hashimati.microcli.services;

/**
 * @author Ahmed Al Hashmi
 */
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Singleton
public class TemplatesService {


    private HashMap<String, String> javaTemplates = new HashMap<>(), groovyTemplates = new HashMap<>(),
            kotlinTemplates = new HashMap<>(),
            sqlEntityTemplates = new HashMap<>(),
            properties = new HashMap<>(),
            graphqlTemplates = new HashMap<>(),
            liquibaseTemplates = new HashMap<>(),
            flywayTemplates = new HashMap<>(),

            cacheTemplates = new HashMap<>();

    public static final String
            CLIENT = "client",
            CONTROLLER = "controller",
            JDBC_REPOSITORY = "jdbcRepository",
            JOIN_ANNOTATION = "joinAnnotation",
            JOIN_METHODS = "joinMethods",
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

            R2DBC_CONTROLLER = "r2dbc_controller",
            R2DBC_SERVICE = "r2dbc_service",
            R2DBC_REPOSITORY = "r2dbc_repository",
            R2DBC_CLIENT = "r2dbc_client",

            GORM_ENTITY="gorm_entity",
            GORM_REPOSITORY = "gorm_repository",
            GORM_SERVICE = "gorm_service",
            GORM_CONTROLLER = "gorm_controller",
            GORM_CLIENT = "gorm_client",
            GRAPHQL_QUERY_FACOTRY = "graphql_query_factory",
            GRAPHQL_QUERY_RESOLVER = "graphql_query_resolver",
            GRAPHQL_REACTIVE_QUERY_RESOLVER = "graphql_reactive_query_resolver",
            SECURITY_AUTHENTICATION_PROVIDER = "authentication_provider_user_password",
            SECURITY_REACTIVE_AUTHENTICATION_PROVIDER = "reactive_authentication_provider_user_password",
            SECURITY_LOGIN_EVENT= "login_event",
            SECURITY_LOGIN_STATUS = "login_status",
            SECURITY_PASSWORD_ENCODER = "password_encoder",
            SECURITY_ROLES = "sec_roles",
            SECURITY_USER= "sec_user",
            SECURITY_USER_REPOSITORY= "sec_user_repository",
            SECURITY_USER_SERVICE= "sec_user_service",
            SECURITY_USER_CONTROLLER= "sec_user_controller",
            SECURITY_USER_MONGO_REPOSITORY= "sec_user_mongo_repository",
            SECURITY_USER_MONGO_SERVICE= "sec_user_mongo_service",
            SECURITY_USER_MONGO_CONTROLLER= "sec_user_mongo_controller",


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
                    MQTT_CLIENT = "mqtt_client",
                    MQTT_LISTENER = "mqtt_listener",
            GCP_PUB_SUB_CLIENT = "gcpPubSub_client",
            GCP_PUB_SUB_LISTENER = "gcpPubSub_listener",
                    GRAPHQL_ENUM = "graphql_enum",
                    GRAPHQL_SCHEMA = "graphql_schema",
                    GRAPHQL_TYPE = "graphql_type",
                    GRAPHQL_QUERY = "graphql_query",
                    GRAPHQL_DATA = "graphql_data",
                    GRAPHQL_MUTATION = "graphql_mutation",
                    GRAPHQL_QUERY_METHOD= "graphql_query_method",

            ///liquiBase
            LIQUIBASE_CATALOG = "catalog",
                    LIQUIBASE_COLUMN = "column",
                    LIQUIBASE_ADD_COLUMN = "add_column",
                    LIQUIBASE_DROP_COLUMN = "drop_column",
                    LIQUIBASE_constrain = "constrain",
                    LIQUIBASE_FOREIGNKEY= "foreign_key",
                    LIQUIBASE_INCLUDE= "include",
                    LIQUIBASE_SCHEMA = "schema",
                    LIQUIBASE_TABLE="table",
                    LIQUIBASE_DROP_TABLE = "drop_table",
            //Flyway
            FLYWAY_CATALOG = "catalog",
            FLYWAY_COLUMN = "column",
            FLYWAY_ADD_COLUMN = "add_column",
            FLYWAY_DROP_COLUMN = "drop_column",
            FLYWAY_constrain = "constrain",
            FLYWAY_FOREIGNKEY= "foreign_key",
            FLYWAY_TABLE="table",
            FLYWAY_DROP_TABLE = "drop_table",
            FLYAWAY_YML = "flyway_yml",

            CAFFEINE_YML ="caffeine_yml";




    public static String ATTRIBUTE ="attribute",
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
            H2_GORM_yml = "h2_gorm",
            ORACLE_GORM_yml = "oracle_gorm",
            POSTGRES_GORM_yml = "postgres_gorm",
            MARIADB_GORM_yml = "mariadb_gorm",
            MYSQL_GORM_yml = "mysql_gorm",
            SQLSERVER_GORM_yml = "sqlserver_gorm",
            ORACLE_JDBC_TEST_yml = "oracle_test",
            POSTGRES_JDBC_TEST_yml = "postgres_test",
            MARIADB_JDBC_TEST_yml = "mariadb_test",
            MYSQL_JDBC_TEST_yml = "mysql_test",
            SQLSERVER_TEST_JDBC_yml = "sqlserver_test",
            H2_R2DBC_yml = "h2_r2dbc",
            POSTGRES_R2DBC_yml = "postgres_r2dbc",
            MARIADB_R2DBC_yml = "mariadb_r2dbc",
            MYSQL_R2DBC_yml = "mysql_r2dbc",
            SQLSERVER_R2DBC_yml = "sqlserver_r2dbc",

            H2_R2DBC_TEST_yml = "h2_r2dbc_test",
            POSTGRES_R2DBC_TEST_yml = "postgres_r2dbc_test",
            MARIADB_R2DBC_TEST_yml = "mariadb_r2dbc_test",
            MYSQL_R2DBC_TEST_yml = "mysql_r2dbc_test",
            SQLSERVER_R2DBC_TEST_yml = "sqlserver_r2dbc_test",
            JPA_yml = "jpa",




    MONGODB_yml = "MONGODB",
    CASSANDRA_yml = "CASSANDRA",
    NEO4J_yml = "NEO4J",
    KAFKA_yml = "KAFKA",
    MQTT_yml = "mqtt",

    RABBITMQ_yml = "RABBITMQ",
    NATS_yml = "NATS",
    GRAPHQL_yml = "GRAPHQL",
    JWT_yml = "JWT",
    LIQUIBASE_yml= "liquibase";




    @EventListener
    public void loadTemplates(StartupEvent event) {

        javaTemplates.put(CLIENT, "micronaut/entityTemplates/java/client.txt");
        javaTemplates.put(CONTROLLER, "micronaut/entityTemplates/java/controller.txt");
        javaTemplates.put(ENTITY, "micronaut/entityTemplates/java/entity.txt");
        javaTemplates.put(EXCEPTION_HANDLER, "micronaut/entityTemplates/java/entityExceptionHandler.txt");
        javaTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/java/entityGeneralException.txt");
        javaTemplates.put(ENUM, "micronaut/entityTemplates/java/enum.txt");
        javaTemplates.put(JDBC_REPOSITORY, "micronaut/entityTemplates/java/jdbcRepository.txt");
        javaTemplates.put(JOIN_ANNOTATION, "micronaut/entityTemplates/java/JdbcJoinAnnotation.txt");
        javaTemplates.put(JOIN_METHODS, "micronaut/entityTemplates/java/JoinRepoMethods.txt");
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
        javaTemplates.put(MQTT_CLIENT,"micronaut/components/java/mqttClient.txt");
        javaTemplates.put(MQTT_LISTENER,"micronaut/components/java/mqttConsumer.txt");
        javaTemplates.put(NATS_CLIENT,"micronaut/components/java/natsClient.txt");
        javaTemplates.put(NATS_LISTENER,"micronaut/components/java/natsConsumer.txt");
        javaTemplates.put(GCP_PUB_SUB_CLIENT,"micronaut/components/java/gcpPubSubClient.txt");
        javaTemplates.put(GCP_PUB_SUB_LISTENER,"micronaut/components/java/gcpPubSubConsumer.txt");
        javaTemplates.put(GRAPHQL_QUERY_FACOTRY, "micronaut/components/java/QueryFactory.txt");
        javaTemplates.put(GRAPHQL_QUERY_RESOLVER,"micronaut/components/java/QueryResolver.txt");
        javaTemplates.put(GRAPHQL_REACTIVE_QUERY_RESOLVER,"micronaut/components/java/Reactive_QueryResolver.txt");
        javaTemplates.put(SECURITY_AUTHENTICATION_PROVIDER, "micronaut/security/java/AuthenictationProviderUserPassword.txt");
        javaTemplates.put(SECURITY_LOGIN_EVENT, "micronaut/security/java/LoginEvent.txt");
        javaTemplates.put(SECURITY_LOGIN_STATUS, "micronaut/security/java/LoginStatus.txt");
        javaTemplates.put(SECURITY_PASSWORD_ENCODER, "micronaut/security/java/PasswordEncoder.txt");
        javaTemplates.put(SECURITY_REACTIVE_AUTHENTICATION_PROVIDER, "micronaut/security/java/reactive/AuthenictationProviderUserPassword.txt");
        javaTemplates.put(SECURITY_ROLES, "micronaut/security/java/Roles.txt");
        javaTemplates.put(SECURITY_USER, "micronaut/security/java/User.txt");
        javaTemplates.put(SECURITY_USER_REPOSITORY, "micronaut/security/java/UserRepository.txt");
        javaTemplates.put(SECURITY_USER_SERVICE, "micronaut/security/java/UserService.txt");
        javaTemplates.put(SECURITY_USER_CONTROLLER, "micronaut/security/java/UserController.txt");
        javaTemplates.put(SECURITY_USER_MONGO_REPOSITORY, "micronaut/security/java/reactive/UserRepository.txt");
        javaTemplates.put(SECURITY_USER_MONGO_SERVICE, "micronaut/security/java/reactive/UserService.txt");
        javaTemplates.put(SECURITY_USER_MONGO_CONTROLLER, "micronaut/security/java/reactive/UserController.txt");
        javaTemplates.put(R2DBC_CONTROLLER, "micronaut/entityTemplates/java/r2dbc/controller.txt");
        javaTemplates.put(R2DBC_SERVICE, "micronaut/entityTemplates/java/r2dbc/service.txt");
        javaTemplates.put(R2DBC_REPOSITORY, "micronaut/entityTemplates/java/r2dbc/jdbcRepository.txt");
        javaTemplates.put(R2DBC_CLIENT, "micronaut/entityTemplates/java/r2dbc/client.txt");

        groovyTemplates.put(CLIENT, "micronaut/entityTemplates/groovy/client.txt");
        groovyTemplates.put(CONTROLLER, "micronaut/entityTemplates/groovy/controller.txt");
        groovyTemplates.put(ENTITY, "micronaut/entityTemplates/groovy/entity.txt");
        groovyTemplates.put(EXCEPTION_HANDLER, "micronaut/entityTemplates/groovy/entityExceptionHandler.txt");
        groovyTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/groovy/entityGeneralException.txt");
        groovyTemplates.put(ENUM, "micronaut/entityTemplates/groovy/enum.txt");
        groovyTemplates.put(JDBC_REPOSITORY, "micronaut/entityTemplates/groovy/jdbcRepository.txt");
        groovyTemplates.put(JOIN_ANNOTATION, "micronaut/entityTemplates/groovy/JdbcJoinAnnotation.txt");
        groovyTemplates.put(JOIN_METHODS, "micronaut/entityTemplates/groovy/JoinRepoMethods.txt");
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
        groovyTemplates.put(MQTT_CLIENT,"micronaut/components/groovy/mqttClient.txt");
        groovyTemplates.put(MQTT_LISTENER,"micronaut/components/groovy/mqttConsumer.txt");
        groovyTemplates.put(COMP_REPOSITORY,"micronaut/components/groovy/repository.txt");
        groovyTemplates.put(COMP_WEBSOCKET,"micronaut/components/groovy/websocket.txt");
        groovyTemplates.put(COMP_WEBSOCKET_CLIENT,"micronaut/components/groovy/websocketClient.txt");
        groovyTemplates.put(NATS_CLIENT,"micronaut/components/groovy/natsClient.txt");
        groovyTemplates.put(NATS_LISTENER,"micronaut/components/groovy/natsConsumer.txt");
        groovyTemplates.put(GCP_PUB_SUB_CLIENT,"micronaut/components/groovy/gcpPubSubClient.txt");
        groovyTemplates.put(GCP_PUB_SUB_LISTENER,"micronaut/components/groovy/gcpPubSubConsumer.txt");
        groovyTemplates.put(GRAPHQL_QUERY_FACOTRY, "micronaut/components/groovy/QueryFactory.txt");
        groovyTemplates.put(GRAPHQL_QUERY_RESOLVER,"micronaut/components/groovy/QueryResolver.txt");
        groovyTemplates.put(GRAPHQL_REACTIVE_QUERY_RESOLVER,"micronaut/components/groovy/Reactive_QueryResolver.txt");
        groovyTemplates.put(SECURITY_AUTHENTICATION_PROVIDER, "micronaut/security/groovy/AuthenictationProviderUserPassword.txt");
        groovyTemplates.put(SECURITY_LOGIN_EVENT, "micronaut/security/groovy/LoginEvent.txt");
        groovyTemplates.put(SECURITY_LOGIN_STATUS, "micronaut/security/groovy/LoginStatus.txt");
        groovyTemplates.put(SECURITY_PASSWORD_ENCODER, "micronaut/security/groovy/PasswordEncoder.txt");
        groovyTemplates.put(SECURITY_REACTIVE_AUTHENTICATION_PROVIDER, "micronaut/security/groovy/reactive/AuthenictationProviderUserPassword.txt");
        groovyTemplates.put(SECURITY_ROLES, "micronaut/security/groovy/Roles.txt");
        groovyTemplates.put(SECURITY_USER, "micronaut/security/groovy/User.txt");
        groovyTemplates.put(SECURITY_USER_REPOSITORY, "micronaut/security/groovy/UserRepository.txt");
        groovyTemplates.put(SECURITY_USER_SERVICE, "micronaut/security/groovy/UserService.txt");
        groovyTemplates.put(SECURITY_USER_CONTROLLER, "micronaut/security/groovy/UserController.txt");
        groovyTemplates.put(SECURITY_USER_MONGO_REPOSITORY, "micronaut/security/groovy/reactive/UserRepository.txt");
        groovyTemplates.put(SECURITY_USER_MONGO_SERVICE, "micronaut/security/groovy/reactive/UserService.txt");
        groovyTemplates.put(SECURITY_USER_MONGO_CONTROLLER, "micronaut/security/groovy/reactive/UserController.txt");
        groovyTemplates.put(GORM_ENTITY, "micronaut/entityTemplates/groovy/gorm/Entity.txt");
        groovyTemplates.put(GORM_REPOSITORY, "micronaut/entityTemplates/groovy/gorm/Repository.txt");
        groovyTemplates.put(GORM_SERVICE, "micronaut/entityTemplates/groovy/gorm/Service.txt");
        groovyTemplates.put(GORM_CONTROLLER, "micronaut/entityTemplates/groovy/gorm/Controller.txt");
        groovyTemplates.put(GORM_CLIENT, "micronaut/entityTemplates/groovy/gorm/Client.txt");
        groovyTemplates.put(R2DBC_CONTROLLER, "micronaut/entityTemplates/groovy/r2dbc/controller.txt");
        groovyTemplates.put(R2DBC_SERVICE, "micronaut/entityTemplates/groovy/r2dbc/service.txt");
        groovyTemplates.put(R2DBC_REPOSITORY, "micronaut/entityTemplates/groovy/r2dbc/jdbcRepository.txt");
        groovyTemplates.put(R2DBC_CLIENT, "micronaut/entityTemplates/groovy/r2dbc/client.txt");

        kotlinTemplates.put(CLIENT, "micronaut/entityTemplates/kotlin/client.txt");
        kotlinTemplates.put(CONTROLLER, "micronaut/entityTemplates/kotlin/controller.txt");
        kotlinTemplates.put(ENTITY, "micronaut/entityTemplates/kotlin/entity.txt");
        kotlinTemplates.put(EXCEPTION_HANDLER, "micronaut/entityTemplates/kotlin/entityExceptionHandler.txt");
        kotlinTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/kotlin/entityGeneralException.txt");
        kotlinTemplates.put(ENUM, "micronaut/entityTemplates/kotlin/enum.txt");
        kotlinTemplates.put(JDBC_REPOSITORY, "micronaut/entityTemplates/kotlin/jdbcRepository.txt");
        kotlinTemplates.put(JOIN_ANNOTATION, "micronaut/entityTemplates/kotlin/JdbcJoinAnnotation.txt");
        kotlinTemplates.put(JOIN_METHODS, "micronaut/entityTemplates/kotlin/JoinRepoMethods.txt");
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
        kotlinTemplates.put(MQTT_CLIENT,"micronaut/components/kotlin/mqttClient.txt");
        kotlinTemplates.put(MQTT_LISTENER,"micronaut/components/kotlin/mqttConsumer.txt");
        kotlinTemplates.put(COMP_REPOSITORY,"micronaut/components/kotlin/repository.txt");
        kotlinTemplates.put(COMP_WEBSOCKET,"micronaut/components/kotlin/websocket.txt");
        kotlinTemplates.put(COMP_WEBSOCKET_CLIENT,"micronaut/components/kotlin/websocketClient.txt");
        kotlinTemplates.put(NATS_CLIENT,"micronaut/components/kotlin/natsClient.txt");
        kotlinTemplates.put(NATS_LISTENER,"micronaut/components/kotlin/natsConsumer.txt");
        kotlinTemplates.put(GCP_PUB_SUB_CLIENT,"micronaut/components/kotlin/gcpPubSubClient.txt");
        kotlinTemplates.put(GCP_PUB_SUB_LISTENER,"micronaut/components/kotlin/gcpPubSubConsumer.txt");
        kotlinTemplates.put(GRAPHQL_QUERY_FACOTRY, "micronaut/components/kotlin/QueryFactory.txt");
        kotlinTemplates.put(GRAPHQL_QUERY_RESOLVER,"micronaut/components/kotlin/QueryResolver.txt");
        kotlinTemplates.put(GRAPHQL_REACTIVE_QUERY_RESOLVER,"micronaut/components/kotlin/Reactive_QueryResolver.txt");
        kotlinTemplates.put(SECURITY_AUTHENTICATION_PROVIDER, "micronaut/security/kotlin/AuthenictationProviderUserPassword.txt");
        kotlinTemplates.put(SECURITY_LOGIN_EVENT, "micronaut/security/kotlin/LoginEvent.txt");
        kotlinTemplates.put(SECURITY_LOGIN_STATUS, "micronaut/security/kotlin/LoginStatus.txt");
        kotlinTemplates.put(SECURITY_PASSWORD_ENCODER, "micronaut/security/kotlin/PasswordEncoder.txt");
        kotlinTemplates.put(SECURITY_REACTIVE_AUTHENTICATION_PROVIDER, "micronaut/security/kotlin/reactive/AuthenictationProviderUserPassword.txt");
        kotlinTemplates.put(SECURITY_ROLES, "micronaut/security/kotlin/Roles.txt");
        kotlinTemplates.put(SECURITY_USER, "micronaut/security/kotlin/User.txt");
        kotlinTemplates.put(SECURITY_USER_REPOSITORY, "micronaut/security/kotlin/UserRepository.txt");
        kotlinTemplates.put(SECURITY_USER_SERVICE, "micronaut/security/kotlin/UserService.txt");
        kotlinTemplates.put(SECURITY_USER_CONTROLLER, "micronaut/security/kotlin/UserController.txt");
        kotlinTemplates.put(SECURITY_USER_MONGO_REPOSITORY, "micronaut/security/kotlin/reactive/UserRepository.txt");
        kotlinTemplates.put(SECURITY_USER_MONGO_SERVICE, "micronaut/security/kotlin/reactive/UserService.txt");
        kotlinTemplates.put(SECURITY_USER_MONGO_CONTROLLER, "micronaut/security/kotlin/reactive/UserController.txt");
        kotlinTemplates.put(R2DBC_CONTROLLER, "micronaut/entityTemplates/kotlin/r2dbc/controller.txt");
        kotlinTemplates.put(R2DBC_SERVICE, "micronaut/entityTemplates/kotlin/r2dbc/service.txt");
        kotlinTemplates.put(R2DBC_REPOSITORY, "micronaut/entityTemplates/kotlin/r2dbc/jdbcRepository.txt");
        kotlinTemplates.put(R2DBC_CLIENT, "micronaut/entityTemplates/kotlin/r2dbc/client.txt");


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

        properties.put(ORACLE_GORM_yml, "micronaut/entityTemplates/oracle_gormproperties.txt");
        properties.put(H2_GORM_yml, "micronaut/entityTemplates/h2_gorm_properties.txt");
        properties.put(MARIADB_GORM_yml, "micronaut/entityTemplates/mariadb_gorm_properties.txt");
        properties.put(POSTGRES_GORM_yml, "micronaut/entityTemplates/postgres_gorm_proper_TESTties.txt");
        properties.put(MYSQL_GORM_yml, "micronaut/entityTemplates/mysql_gorm_properties.txt");
        properties.put(SQLSERVER_GORM_yml, "micronaut/entityTemplates/sqlserver_gorm_properties.txt");

        properties.put(ORACLE_JDBC_TEST_yml, "micronaut/entityTemplates/oracle_jdbc_test_properties.txt");
        properties.put(MARIADB_JDBC_TEST_yml, "micronaut/entityTemplates/mariadb_jdbc_test_properties.txt");
        properties.put(POSTGRES_JDBC_TEST_yml, "micronaut/entityTemplates/postgres_jdbc_test_properties.txt");
        properties.put(MYSQL_JDBC_TEST_yml, "micronaut/entityTemplates/mysql_jdbc_test_properties.txt");
        properties.put(SQLSERVER_TEST_JDBC_yml, "micronaut/entityTemplates/sqlserver_jdbc_test_properties.txt");

        properties.put(H2_R2DBC_yml, "micronaut/entityTemplates/h2_rdbc_properties.yml");
        properties.put(MARIADB_R2DBC_yml, "micronaut/entityTemplates/mariadb_rdbc_properties.yml");
        properties.put(POSTGRES_R2DBC_yml, "micronaut/entityTemplates/postgres_rdbc_properties.yml");
        properties.put(MYSQL_R2DBC_yml, "micronaut/entityTemplates/mysql_rdbc_properties.yml");
        properties.put(SQLSERVER_R2DBC_yml, "micronaut/entityTemplates/sqlserver_rdbc_properties.yml");

        properties.put(MARIADB_R2DBC_TEST_yml, "micronaut/entityTemplates/maria_r2dbc_test.yml");
        properties.put(POSTGRES_R2DBC_TEST_yml, "micronaut/entityTemplates/postgres_r2dbc_test.yml");
        properties.put(MYSQL_R2DBC_TEST_yml, "micronaut/entityTemplates/mysql_r2db_test.yml");
        properties.put(SQLSERVER_R2DBC_TEST_yml, "micronaut/entityTemplates/sqlserver_r2dbc_test.yml");


        properties.put(JPA_yml, "micronaut/entityTemplates/jpa_properties.yml");
        properties.put(MONGODB_yml, "micronaut/entityTemplates/mongodb_properties.txt");
        properties.put(CASSANDRA_yml, "micronaut/entityTemplates/cassandra_properties.txt");
        properties.put(NEO4J_yml, "micronaut/entityTemplates/neo4j_properties.txt");
        properties.put(KAFKA_yml, "micronaut/entityTemplates/kafka_properties.txt");
        properties.put(RABBITMQ_yml, "micronaut/entityTemplates/rabbtmq_properties.txt");
        properties.put(NATS_yml, "micronaut/entityTemplates/nats_properties.txt");
        properties.put(GRAPHQL_yml, "micronaut/entityTemplates/graphql_properties.txt");
        properties.put(JWT_yml, "micronaut/security/jwt_properties.yml");
        properties.put(LIQUIBASE_yml, "micronaut/entityTemplates/sql/liquibase/liquibase_properties.yml");
        properties.put(MQTT_yml, "micronaut/entityTemplates/mqtt_properties.txt");
        properties.put(CAFFEINE_YML , "micronaut/entityTemplates/caffeine_properties.yml");
        graphqlTemplates.put(GRAPHQL_ENUM, "micronaut/entityTemplates/graphql/enum.txt");
        graphqlTemplates.put(GRAPHQL_SCHEMA, "micronaut/entityTemplates/graphql/schema.txt");
        graphqlTemplates.put(GRAPHQL_TYPE, "micronaut/entityTemplates/graphql/type.txt");
        graphqlTemplates.put(GRAPHQL_DATA, "micronaut/entityTemplates/graphql/data.txt");
        graphqlTemplates.put(GRAPHQL_MUTATION, "micronaut/entityTemplates/graphql/mutation.txt");
        graphqlTemplates.put(GRAPHQL_QUERY, "micronaut/entityTemplates/graphql/query.txt");
        graphqlTemplates.put(GRAPHQL_QUERY_METHOD, "micronaut/entityTemplates/graphql/queryMethods.txt");


        liquibaseTemplates.put(LIQUIBASE_FOREIGNKEY,"micronaut/entityTemplates/sql/liquibase/foreignKey_template.xml");
        liquibaseTemplates.put(LIQUIBASE_CATALOG,"micronaut/entityTemplates/sql/liquibase/catalog_template.xml");
        liquibaseTemplates.put(LIQUIBASE_COLUMN,"micronaut/entityTemplates/sql/liquibase/columns_template.xml");
        liquibaseTemplates.put(LIQUIBASE_ADD_COLUMN,"micronaut/entityTemplates/sql/liquibase/addColumn.xml");
        liquibaseTemplates.put(LIQUIBASE_DROP_COLUMN,"micronaut/entityTemplates/sql/liquibase/dropColumn.xml");
        liquibaseTemplates.put(LIQUIBASE_constrain,"micronaut/entityTemplates/sql/liquibase/constraint_template.xml");
        liquibaseTemplates.put(LIQUIBASE_INCLUDE,"micronaut/entityTemplates/sql/liquibase/include_template.xml");
        liquibaseTemplates.put(LIQUIBASE_SCHEMA,"micronaut/entityTemplates/sql/liquibase/schema_template.xml");
        liquibaseTemplates.put(LIQUIBASE_TABLE,"micronaut/entityTemplates/sql/liquibase/table_template.xml");
        liquibaseTemplates.put(LIQUIBASE_DROP_TABLE, "micronaut/entityTemplates/sql/liquibase/dropTable.xml");


//        flywayTemplates.put(FLYWAY_CATALOG,"micronaut/entityTemplates/sql/flyway/" );
//        flywayTemplates.put(FLYWAY_COLUMN ,"micronaut/entityTemplates/sql/flyway/");
        flywayTemplates.put(FLYWAY_ADD_COLUMN ,"micronaut/entityTemplates/sql/flyway/addColumn.sql");
        flywayTemplates.put(FLYWAY_DROP_COLUMN ,"micronaut/entityTemplates/sql/flyway/dropColumn.sql");
        flywayTemplates.put(FLYWAY_constrain, "micronaut/entityTemplates/sql/constraint_template.sql");
        flywayTemplates.put(FLYWAY_FOREIGNKEY, "micronaut/entityTemplates/sql/flyway/constraint_template.sql");
        flywayTemplates.put(FLYWAY_TABLE,"micronaut/entityTemplates/sql/flyway/create_template.sql");
        flywayTemplates.put(FLYWAY_DROP_TABLE, "micronaut/entityTemplates/sql/flyway/dropTable.sql");
        flywayTemplates.put(FLYAWAY_YML, "micronaut/entityTemplates/sql/flyway/FlyWay_properties.yml");

//        cacheTemplates.put(CAFFEINE_YML , "micronaut/entityTemplates/caffine_properties.yml");
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

    public HashMap<String, String> getLiquibaseTemplates() {
        return liquibaseTemplates;
    }
    public HashMap<String, String> getFlywayTemplates() {
        return flywayTemplates;
    }

}

