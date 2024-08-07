package io.hashimati.microcli.services;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import io.micronaut.runtime.event.annotation.EventListener;

import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

@Singleton
public class TemplatesService {



    private HashMap<String, String> javaTemplates = new HashMap<>(), groovyTemplates = new HashMap<>(),
            kotlinTemplates = new HashMap<>(),
            sqlEntityTemplates = new HashMap<>(),
            properties = new HashMap<>(),
            graphqlTemplates = new HashMap<>(),
            liquibaseTemplates = new HashMap<>(),
            flywayTemplates = new HashMap<>(),
            distributedTracingTemplates = new HashMap<>(),
            cacheTemplates = new HashMap<>(),
            micrometersTemplates = new HashMap<>(),
            securityTemplates = new HashMap<>(),
            securityControllerTemplates = new HashMap<>(),
            securityAPIKeyTemplates = new HashMap<>(),
            securityDomainsTemplates = new HashMap<>(), 
            securityRepositoryTemplates = new HashMap<>(), 
            securityServicesTemplates = new HashMap<>(), 
            securityEventsTemplates= new HashMap<>(), 
            securityUtilsTemplates = new HashMap<>(),
            securityRefreshTokenTemplates= new HashMap<>(),
            securityPropertiesTemplates = new HashMap<>(),
            securityLiquibase = new HashMap<>(),
            flutter = new HashMap<>();


    public static final String LOGBACK_PATH = "micronaut/logs/logback.xml";
    public static final String OPEN_API_PATH = "micronaut/components/openapi.properties";
    public static final String
            CLIENT = "client",
            CONTROLLER = "controller",
            GRPC_ENDPOINT = "GRPC_ENDPOINT",
            JDBC_REPOSITORY = "jdbcRepository",
            DATA_MONGODB_REPOSITORY = "DATA_MONGODB_REPOSITORY",
            JOIN_ANNOTATION = "joinAnnotation",
            JOIN_METHODS = "joinMethods",
            ENTITY = "entity",
            ENTITY_RECORD = "entity_record",
            EXCEPTION_HANDLER = "entityExceptionHandler",
            GENERAL_EXCEPTION = "entityGeneralException",
            REPOSITORY_TEST = "EntityRepositoryTest",
            ENUM = "enum",
            MONGO_CONTROLLER = "mongocontroller",
            MONGO_REPOSITORY = "mongorepository",
            MONGO_SERVICE = "mongoservice",
            MONGO_CLIENT = "mongoclient",
            MONGODB_CONFIGURATION = "MongodbConfiguration",
            NEO4J_CONTROLLER = "neo4jcontroller",
            NEO4J_REPOSITORY = "neo4jrepository",
            NEO4J_SERVICE = "neo4jservice",
            RANDOMIZER = "Randomizer",
            REPOSITORY = "repository",
            SERVICE = "service",
            EUREKA_SERVER = "eurekaServer",
            CONFIG_SERVER = "configServer",
            GATEWAY = "gateway",
            FIND_BY_DATA_REPO= "FIND_BY_DATA_REPO",
            FIND_BY_REACTIVE_REPO = "FIND_BY_R2DBC_REPO",
            FIND_BY_MONGODB_REPO = "FIND_BY_MONGODB_REPO",
            FIND_BY_SERVICE ="FIND_BY_SERVICE",
            FIND_BY_CONTROLLER = "FIND_BY_CONTROLLER",
            FIND_BY_CLIENT ="FIND_BY_CLIENT",
            FIND_BY_GRAPHQL = "FIND_BY_GRAPHQL",
            FIND_ALL_BY_DATA_REPO= "FIND_ALL_BY_DATA_REPO",
            FIND_ALL_BY_REACTIVE_REPO = "FIND_ALL_BY_R2DBC_REPO",
            FIND_ALL_BY_MONGODB_REPO = "FIND_ALL_BY_MONGODB_REPO",
            FIND_All_BY_SERVICE ="FIND_All_BY_SERVICE",
            FIND_All_BY_CONTROLLER = "FIND_All_BY_CONTROLLER",
            FIND_All_BY_CLIENT ="FIND_All_BY_CLIENT",
            FIND_All_BY_GRAPHQL = "FIND_All_BY_GRAPHQL",
            UPDATE_BY_DATA_REPO = "UPDATE_BY_DATA_REPO",
            UPDATE_BY_MONOGO_REPO ="UPDATE_BY_MONOGO_REPO",
            UPDATE_BY_REACTIVE_REPO = "UPDATE_BY_REACTIVE_REPO",
            UPDATE_BY_SERVICE = "UPDATE_BY_SERVICE",

            UPDATE_BY_CONTROLLER = "UPDATE_BY_CONTROLLER",
            UPDATE_BY_CLIENT = "UPDATE_BY_CLIENT",
            UPDATE_BY_GRAPHQL = "UPDATE_BY_GRAPHQL",

            LAMBDA_FUNCTION_SAVE_REQUEST = "lambda_save_request",
            LAMBDA_FUNCTION_UPDATE_REQUEST = "lambda_update_request",
            LAMBDA_FUNCTION_DELETE_REQUEST = "lambda_delete_request",
            LAMBDA_FUNCTION_FIND_REQUEST = "lambda_find_request",
            LAMBDA_FUNCTION_FINDALL_REQUEST = "lambda_findall_request",

            AZURE_FUNCTION_SAVE_REQUEST = "AZURE_save_request",
            AZURE_FUNCTION_UPDATE_REQUEST = "AZURE_update_request",
            AZURE_FUNCTION_DELETE_REQUEST = "AZURE_delete_request",
            AZURE_FUNCTION_FIND_REQUEST = "AZURE_find_request",
            AZURE_FUNCTION_FINDALL_REQUEST = "AZURE_findall_request",


            GOOGLE_FUNCTION_SAVE_REQUEST = "GOOGLE_save_request",
            GOOGLE_FUNCTION_UPDATE_REQUEST = "GOOGLE_update_request",
            GOOGLE_FUNCTION_DELETE_REQUEST = "GOOGLE_delete_request",
            GOOGLE_FUNCTION_FIND_REQUEST = "GOOGLE_find_request",
            GOOGLE_FUNCTION_FINDALL_REQUEST = "GOOGLE_findall_request",

            ORACLE_FUNCTION_SAVE_REQUEST = "ORACLE_save_request",
            ORACLE_FUNCTION_UPDATE_REQUEST = "ORACLE_update_request",
            ORACLE_FUNCTION_DELETE_REQUEST = "ORACLE_delete_request",
            ORACLE_FUNCTION_FIND_REQUEST = "ORACLE_find_request",
            ORACLE_FUNCTION_FINDALL_REQUEST = "ORACLE_findall_request",

            R2DBC_CONTROLLER = "r2dbc_controller",
            R2DBC_SERVICE = "r2dbc_service",
            R2DBC_REPOSITORY = "r2dbc_repository",
            R2DBC_CLIENT = "r2dbc_client",

            GENERAL_REACTIVE_REPOSITORY ="GENERAL_REACTIVE_REPOSITORY",
                    GENERAL_REACTIVE_SERVICE = "GENERAL_REACTIVE_SERVICE",
                    GENERAL_REACTIVE_CLIENT = "GENERAL_REACTIVE_CLIENT",
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
            SECURITY_JWT_PROPERTIES = "security_jwt_properties",
                    SECURITY_JWT_PROPAGATION_PROPERTIES = "security_jwt_propagation_properties",
            SECURITY_SESSION_PROPERTIES = "security_session_properties",
            SECURITY_INTERCEPT_URL ="SECURITY_INTERCEPT_URL",
            SECURITY_INTERCEPT_URL_PATTERN = "SECURITY_INTERCEPT_URL_PATTERN",
            SECURITY_LIQUIBASE_CONFIG = "Liquibase_config",
            SECURITY_LIQUIBASE_SCHEMA = "liquibase_schema",
            LOGIN_EVENT_LISTENER = "login_event_Listerner",
            LOGIN_EVENT_PUBLISHER = "login_event_publisher",
            EVENT_PUBLISHER = "EVENT_PUBLISHER",
            EVENT_LISTENER = "EVENT_LISTENER",

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

            CAFFEINE_YML ="caffeine_yml",

            DISTRIBUTED_TRACING_ZIPKIN = "zipkin",
                    DISTRIBUTED_TRACING_JAEGER = "jaeger",
            FILE_SYSTEM_SERVICE = "FILE_SYSTEM_SERVICE",
            FILE_CONTROLLER_METHODS = "FILE_CONTROLLER_METHODS",
                    FILE_CLIENT_METHODS = "FILE_CLIENT_METHODS",
            FILE_SERVICE_METHODS = "FILE_SERVICE_METHODS",
                    FILE_SERVICE_METHODS_AWS = "FILE_SERVICE_METHODS_AWS",
                    FILE_SERVICE_METHODS_AZURE = "FILE_SERVICE_METHODS_AZURE",
                    FILE_SERVICE_METHODS_GCP = "FILE_SERVICE_METHODS_GCP",

    AWS_S3_SERVICE = "AWS_S3_SERVICE",
            AWS_CONFIGURATION = "AWS_CONFIGURATION",
            AMAZON_CLIENT = "AMAZON_CLIENT",
                    AWS_CONFIGURATION_FACTORY = "AWS_CONFIGURATION_FACTORY",
            AWS_CONFIGURATION_PROPERTIES = "AWS_CONFIGURATION_PROPERTIES",

            MICROSTREAM_ROPOSITORY = "MICROSTREAM_REPOSITORY",
            MICROSTREAM_REPOSITORY_IMPL = "MICROSTREAM_REPOSITORY_IMPL",
            MICROSTREAM_ROOT_DATA = "MICROSTREAM_ROOT",
                    OPENAPI_PROPERTIES = "OPENAPI_PROPERTIES";

            public static String FLUTTER_ENTITY = "FLUTTER_ENTITY",
                                 FLUTTER_SERVICE = "FLUTTER_SERVICE",
                                FLUTTER_CONTROLLER = "FLUTTER_CONTROLLER",
                                FLUTTER_MODEL = "FLUTTER_MODEL",
                                FLUTTER_REPOSITORY = "FLUTTER_REPOSITORY",
                                FLUTTER_CLIENT = "FLUTTER_CLIENT",
                                FLUTTER_ENUM = "FLUTTER_ENUM",
                                FLUTTER_EXCEPTION = "FLUTTER_EXCEPTION",
                                FLUTTER_LOGGING = "FLUTTER_LOGGING_PAGE",
                                FLUTTER_ENTITY_PAGE = "FLUTTER_ENTITY_PAGE",
                                FLUTTER_RANDOMIZER = "FLUTTER_RANDOMIZER",
                                FLUTTER_CONTROLLER_UNIT_TEST = "FLUTTER_CONTROLLER_UNIT_TEST";
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
            MDB_yml = "mdb",
            MDB_COLLECTION_YML = "mdb_collection",
            MICROSTREAM_YML = "MICROSTREAM_YML",




    MONGODB_yml = "MONGODB",
    CASSANDRA_yml = "CASSANDRA",
    NEO4J_yml = "NEO4J",
    KAFKA_yml = "KAFKA",
    MQTT_yml = "mqtt",

    RABBITMQ_yml = "RABBITMQ",
    NATS_yml = "NATS",
    GRAPHQL_yml = "GRAPHQL",
    JWT_yml = "JWT",
    LIQUIBASE_yml= "liquibase",


    MICROMETERS_yml = "micrometers",
    GRAPHITE_yml = "graphite",
    PROMETHEUS_yml = "prometheus",
    STATSD_yml = "statsd",
    INFLUX_yml = "influx",
    PROMETHEUS_JOB_YML = "prometheus.yml",
     APIKEY = "APIKEY",
     APIKEY_REPOSITORY = "APIKEY_REPOSITORY",
     APIKEY_SERVICE = "APIKEY_SERVICE",
     APIKEY_CONTROLLER = "APIKEY_CONTROLLER",
     APIKEY_TOKEN_READER = "APIKEY_TOKEN_READER",
     APIKEY_TOKEN_VALIDATOR = "APIKEY_TOKEN_VALIDATOR",
     APIKEY_TOKEN_GENERATOR = "APIKEY_TOKEN_GENERATOR",
    APIKEY_LIQUIBASE = "APIKEY_LIQUIBASE",
    //Security Constants
    USER_CONTROLLER = "usercontroller",
    LOGIN_EVENT = "loginevent",
    REFRESHTOKEN = "refreshtoken",
    LOGIN_STATUS = "loginstatus",
    ROLES = "roles",
    USER = "user",
    REFRESH_TOKEN_REPOSITORY = "refreshtokenRepository",
    USER_REPOSITORY = "userrepository",
    CUSTOM_REFRESH_TOKEN = "customer_refresh_token_Persistenance",
    USER_SERVICE = "userservice",
    AUTHENTICATION_PROVIDER= "authenticationprovider",
    PASSWORD_ENCODER = "password_encoder",
    PASSWORD_ENCODER_SERVICE = "password_encoder_service",
    SECURITY_CLIENT = "security_client",
    SECURITY_FACTORY = "security_factory",
    SECURITY_CODERANDOMIZER = "code_randomizer",
    VIEW_CONFIG = "view",
    MQTT ="MQTT";



    @EventListener
    public void loadTemplates(StartupEvent event) {

        javaTemplates.put(CLIENT, "micronaut/entityTemplates/java/client.txt");
        javaTemplates.put(CONTROLLER, "micronaut/entityTemplates/java/controller.txt");
        javaTemplates.put(GRPC_ENDPOINT, "micronaut/entityTemplates/java/GrpcEndPoint.txt");
        javaTemplates.put(GRPC_ENDPOINT+"_Records", "micronaut/entityTemplates/java/GrpcEndPoint_Records.txt");
        javaTemplates.put(ENTITY, "micronaut/entityTemplates/java/entity.txt");
        javaTemplates.put(ENTITY_RECORD, "micronaut/entityTemplates/java/entityRecord.txt");
        javaTemplates.put(EXCEPTION_HANDLER, "micronaut/entityTemplates/java/entityExceptionHandler.txt");
        javaTemplates.put(GENERAL_EXCEPTION, "micronaut/entityTemplates/java/entityGeneralException.txt");
        javaTemplates.put(ENUM, "micronaut/entityTemplates/java/enum.txt");
        javaTemplates.put(JDBC_REPOSITORY, "micronaut/entityTemplates/java/jdbcRepository.txt");
        javaTemplates.put(JOIN_ANNOTATION, "micronaut/entityTemplates/java/JdbcJoinAnnotation.txt");
        javaTemplates.put(JOIN_METHODS, "micronaut/entityTemplates/java/JoinRepoMethods.txt");
        javaTemplates.put(MONGO_CONTROLLER, "micronaut/entityTemplates/java/mongocontroller.txt");
        javaTemplates.put(MONGODB_CONFIGURATION, "micronaut/entityTemplates/java/MongodbConfiguration.txt");
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
        javaTemplates.put(SECURITY_AUTHENTICATION_PROVIDER, "micronaut/security/deprecated/java/AuthenictationProviderUserPassword.txt");
        javaTemplates.put(SECURITY_LOGIN_EVENT, "micronaut/security/deprecated/java/LoginEvent.txt");
        javaTemplates.put(SECURITY_LOGIN_STATUS, "micronaut/security/deprecated/java/LoginStatus.txt");
        javaTemplates.put(SECURITY_PASSWORD_ENCODER, "micronaut/security/deprecated/java/PasswordEncoder.txt");
        javaTemplates.put(SECURITY_REACTIVE_AUTHENTICATION_PROVIDER, "micronaut/security/deprecated/java/reactive/AuthenictationProviderUserPassword.txt");
        javaTemplates.put(SECURITY_ROLES, "micronaut/security/deprecated/java/Roles.txt");
        javaTemplates.put(SECURITY_USER, "micronaut/security/deprecated/java/User.txt");
        javaTemplates.put(SECURITY_USER_REPOSITORY, "micronaut/security/deprecated/java/UserRepository.txt");
        javaTemplates.put(SECURITY_USER_SERVICE, "micronaut/security/deprecated/java/UserService.txt");
        javaTemplates.put(SECURITY_USER_CONTROLLER, "micronaut/security/deprecated/java/UserController.txt");
        javaTemplates.put(SECURITY_USER_MONGO_REPOSITORY, "micronaut/security/deprecated/java/reactive/UserRepository.txt");
        javaTemplates.put(SECURITY_USER_MONGO_SERVICE, "micronaut/security/deprecated/java/reactive/UserService.txt");
        javaTemplates.put(SECURITY_USER_MONGO_CONTROLLER, "micronaut/security/deprecated/java/reactive/UserController.txt");
        javaTemplates.put(R2DBC_CONTROLLER, "micronaut/entityTemplates/java/r2dbc/controller.txt");
        javaTemplates.put(R2DBC_SERVICE, "micronaut/entityTemplates/java/r2dbc/service.txt");
        javaTemplates.put(R2DBC_REPOSITORY, "micronaut/entityTemplates/java/r2dbc/jdbcRepository.txt");
        javaTemplates.put(R2DBC_CLIENT, "micronaut/entityTemplates/java/r2dbc/client.txt");
        javaTemplates.put(GENERAL_REACTIVE_REPOSITORY, "micronaut/entityTemplates/java/generalReactiveRepository.txt");
        javaTemplates.put(GENERAL_REACTIVE_SERVICE, "micronaut/entityTemplates/java/generalReactiveService.txt");
        javaTemplates.put(LAMBDA_FUNCTION_FINDALL_REQUEST, "micronaut/functions/aws/java/FindAllRequestHander.java");
        javaTemplates.put(LAMBDA_FUNCTION_SAVE_REQUEST, "micronaut/functions/aws/java/SaveRequestHander.java");
        javaTemplates.put(LAMBDA_FUNCTION_DELETE_REQUEST, "micronaut/functions/aws/java/DeleteRequestHander.java");
        javaTemplates.put(LAMBDA_FUNCTION_FIND_REQUEST, "micronaut/functions/aws/java/FindByIdRequestHander.java");
        javaTemplates.put(LAMBDA_FUNCTION_UPDATE_REQUEST, "micronaut/functions/aws/java/UpdateRequestHander.java");
        javaTemplates.put(FIND_BY_DATA_REPO,"micronaut/entityTemplates/java/methods/Repository/FindBy/data.txt");
        javaTemplates.put(FIND_BY_MONGODB_REPO, "micronaut/entityTemplates/java/methods/Repository/FindBy/mongodb.txt");
        javaTemplates.put(FIND_BY_REACTIVE_REPO, "micronaut/entityTemplates/java/methods/Repository/FindBy/dataReactive.txt");
        javaTemplates.put(FIND_ALL_BY_DATA_REPO,"micronaut/entityTemplates/java/methods/Repository/FindAllBy/data.txt");
        javaTemplates.put(FIND_ALL_BY_MONGODB_REPO, "micronaut/entityTemplates/java/methods/Repository/FindAllBy/mongodb.txt");
        javaTemplates.put(FIND_ALL_BY_REACTIVE_REPO, "micronaut/entityTemplates/java/methods/Repository/FindAllBy/dataReactive.txt");
        javaTemplates.put(FIND_BY_SERVICE, "micronaut/entityTemplates/java/methods/Service/FindBy.txt");
        javaTemplates.put(FIND_BY_SERVICE, "micronaut/entityTemplates/java/methods/Service/FindBy.txt");
        javaTemplates.put(FIND_All_BY_SERVICE, "micronaut/entityTemplates/java/methods/Service/FindAllBy.txt");
        javaTemplates.put(FIND_BY_CONTROLLER, "micronaut/entityTemplates/java/methods/Controllers/FindBy.txt");
        javaTemplates.put(FIND_All_BY_CONTROLLER, "micronaut/entityTemplates/java/methods/Controllers/FindAllBy.txt");
        javaTemplates.put(FIND_BY_GRAPHQL, "micronaut/entityTemplates/java/methods/GraphQL/FindBy.txt");
        javaTemplates.put(FIND_All_BY_GRAPHQL, "micronaut/entityTemplates/java/methods/GraphQL/FindAllBy.txt");
        javaTemplates.put(FIND_BY_CLIENT, "micronaut/entityTemplates/java/methods/Clients/FindBy.txt");
        javaTemplates.put(FIND_All_BY_CLIENT, "micronaut/entityTemplates/java/methods/Clients/FindAllBy.txt");
        javaTemplates.put(UPDATE_BY_DATA_REPO, "micronaut/entityTemplates/java/methods/Repository/UpdateBy/data.txt");
        javaTemplates.put(UPDATE_BY_MONOGO_REPO, "micronaut/entityTemplates/java/methods/Repository/UpdateBy/mongodb.txt");
        javaTemplates.put(UPDATE_BY_REACTIVE_REPO, "micronaut/entityTemplates/java/methods/Repository/UpdateBy/dataReactive.txt");
        javaTemplates.put(UPDATE_BY_SERVICE, "micronaut/entityTemplates/java/methods/Service/UpdateBy.txt");
        javaTemplates.put(UPDATE_BY_CONTROLLER, "micronaut/entityTemplates/java/methods/Controllers/UpdateBy.txt");
        javaTemplates.put(UPDATE_BY_CLIENT, "micronaut/entityTemplates/java/methods/Clients/UpdateBy.txt");
        javaTemplates.put(UPDATE_BY_GRAPHQL, "micronaut/entityTemplates/java/methods/GraphQL/UpdateBy.txt");
        javaTemplates.put(EVENT_LISTENER, "micronaut/components/java/event/EventListener.txt");
        javaTemplates.put(EVENT_PUBLISHER, "micronaut/components/java/event/EventPublisher.txt");
        javaTemplates.put(DATA_MONGODB_REPOSITORY, "micronaut/entityTemplates/java/MongoDataRepository.txt");
        javaTemplates.put(AWS_CONFIGURATION, "micronaut/components/java/FileServices/AwsCredentials.txt");
        javaTemplates.put(AWS_S3_SERVICE, "micronaut/components/java/FileServices/AwsStorageService.txt");
        javaTemplates.put(AWS_CONFIGURATION_FACTORY, "micronaut/components/java/FileServices/AwsFactory.txt");
        javaTemplates.put(FILE_SYSTEM_SERVICE, "micronaut/components/java/FileServices/FileSystem.txt");
        javaTemplates.put(FILE_CONTROLLER_METHODS, "micronaut/entityTemplates/java/methods/Files/Controllers.txt");
        javaTemplates.put(FILE_CLIENT_METHODS, "micronaut/entityTemplates/java/methods/Files/Client.txt");
        javaTemplates.put(FILE_SERVICE_METHODS, "micronaut/entityTemplates/java/methods/Files/Services.txt");
        javaTemplates.put(FILE_SERVICE_METHODS_AWS, "micronaut/entityTemplates/java/methods/Files/Services_aws.txt");
        javaTemplates.put(MICROSTREAM_ROPOSITORY, "micronaut/entityTemplates/java/microstream/Repository.txt");
        javaTemplates.put(MICROSTREAM_ROOT_DATA, "micronaut/components/java/Microsstream/RootClass.txt");
        javaTemplates.put(EUREKA_SERVER, "cloud/eureka/Eureka.java");
        javaTemplates.put(GATEWAY,"cloud/gateways/Gateway.java");
        javaTemplates.put(CONFIG_SERVER, "cloud/config/Config.java");

        groovyTemplates.put(CLIENT, "micronaut/entityTemplates/groovy/client.txt");
        groovyTemplates.put(GRPC_ENDPOINT, "micronaut/entityTemplates/groovy/GrpcEndPoint.txt");
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
        groovyTemplates.put(MONGODB_CONFIGURATION, "micronaut/entityTemplates/groovy/MongodbConfiguration.txt");
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
        groovyTemplates.put(SECURITY_AUTHENTICATION_PROVIDER, "micronaut/security/deprecated/groovy/AuthenictationProviderUserPassword.txt");
        groovyTemplates.put(SECURITY_LOGIN_EVENT, "micronaut/security/deprecated/groovy/LoginEvent.txt");
        groovyTemplates.put(SECURITY_LOGIN_STATUS, "micronaut/security/deprecated/groovy/LoginStatus.txt");
        groovyTemplates.put(SECURITY_PASSWORD_ENCODER, "micronaut/security/deprecated/groovy/PasswordEncoder.txt");
        groovyTemplates.put(SECURITY_REACTIVE_AUTHENTICATION_PROVIDER, "micronaut/security/deprecated/groovy/reactive/AuthenictationProviderUserPassword.txt");
        groovyTemplates.put(SECURITY_ROLES, "micronaut/security/deprecated/groovy/Roles.txt");
        groovyTemplates.put(SECURITY_USER, "micronaut/security/deprecated/groovy/User.txt");
        groovyTemplates.put(SECURITY_USER_REPOSITORY, "micronaut/security/deprecated/groovy/UserRepository.txt");
        groovyTemplates.put(SECURITY_USER_SERVICE, "micronaut/security/deprecated/groovy/UserService.txt");
        groovyTemplates.put(SECURITY_USER_CONTROLLER, "micronaut/security/deprecated/groovy/UserController.txt");
        groovyTemplates.put(SECURITY_USER_MONGO_REPOSITORY, "micronaut/security/deprecated/groovy/reactive/UserRepository.txt");
        groovyTemplates.put(SECURITY_USER_MONGO_SERVICE, "micronaut/security/deprecated/groovy/reactive/UserService.txt");
        groovyTemplates.put(SECURITY_USER_MONGO_CONTROLLER, "micronaut/security/deprecated/groovy/reactive/UserController.txt");
        groovyTemplates.put(GORM_ENTITY, "micronaut/entityTemplates/groovy/gorm/Entity.txt");
        groovyTemplates.put(GORM_REPOSITORY, "micronaut/entityTemplates/groovy/gorm/Repository.txt");
        groovyTemplates.put(GORM_SERVICE, "micronaut/entityTemplates/groovy/gorm/Service.txt");
        groovyTemplates.put(GORM_CONTROLLER, "micronaut/entityTemplates/groovy/gorm/Controller.txt");
        groovyTemplates.put(GORM_CLIENT, "micronaut/entityTemplates/groovy/gorm/Client.txt");
        groovyTemplates.put(R2DBC_CONTROLLER, "micronaut/entityTemplates/groovy/r2dbc/controller.txt");
        groovyTemplates.put(R2DBC_SERVICE, "micronaut/entityTemplates/groovy/r2dbc/service.txt");
        groovyTemplates.put(R2DBC_REPOSITORY, "micronaut/entityTemplates/groovy/r2dbc/jdbcRepository.txt");
        groovyTemplates.put(R2DBC_CLIENT, "micronaut/entityTemplates/groovy/r2dbc/client.txt");
        groovyTemplates.put(GENERAL_REACTIVE_REPOSITORY, "micronaut/entityTemplates/groovy/generalReactiveRepository.txt");
        groovyTemplates.put(GENERAL_REACTIVE_SERVICE, "micronaut/entityTemplates/groovy/generalReactiveService.txt");
        groovyTemplates.put(LAMBDA_FUNCTION_FINDALL_REQUEST, "micronaut/functions/aws/groovy/FindAllRequestHander.groovy");
        groovyTemplates.put(LAMBDA_FUNCTION_SAVE_REQUEST, "micronaut/functions/aws/groovy/SaveRequestHander.groovy");
        groovyTemplates.put(LAMBDA_FUNCTION_DELETE_REQUEST, "micronaut/functions/aws/groovy/DeleteRequestHander.groovy");
        groovyTemplates.put(LAMBDA_FUNCTION_FIND_REQUEST, "micronaut/functions/aws/groovy/FindByIdRequestHander.groovy");
        groovyTemplates.put(LAMBDA_FUNCTION_UPDATE_REQUEST, "micronaut/functions/aws/groovy/UpdateRequestHander.groovy");
        groovyTemplates.put(FIND_BY_DATA_REPO,"micronaut/entityTemplates/groovy/methods/Repository/FindBy/data.txt");
        groovyTemplates.put(FIND_BY_MONGODB_REPO, "micronaut/entityTemplates/groovy/methods/Repository/FindBy/mongodb.txt");
        groovyTemplates.put(FIND_BY_REACTIVE_REPO, "micronaut/entityTemplates/groovy/methods/Repository/FindBy/dataReactive.txt");
        groovyTemplates.put(FIND_ALL_BY_DATA_REPO,"micronaut/entityTemplates/groovy/methods/Repository/FindAllBy/data.txt");
        groovyTemplates.put(FIND_ALL_BY_MONGODB_REPO, "micronaut/entityTemplates/groovy/methods/Repository/FindAllBy/mongodb.txt");
        groovyTemplates.put(FIND_ALL_BY_REACTIVE_REPO, "micronaut/entityTemplates/groovy/methods/Repository/FindAllBy/dataReactive.txt");
        groovyTemplates.put(FIND_BY_SERVICE, "micronaut/entityTemplates/groovy/methods/Service/FindBy.txt");
        groovyTemplates.put(FIND_BY_SERVICE, "micronaut/entityTemplates/groovy/methods/Service/FindBy.txt");
        groovyTemplates.put(FIND_All_BY_SERVICE, "micronaut/entityTemplates/groovy/methods/Service/FindAllBy.txt");
        groovyTemplates.put(FIND_BY_CONTROLLER, "micronaut/entityTemplates/groovy/methods/Controllers/FindBy.txt");
        groovyTemplates.put(FIND_All_BY_CONTROLLER, "micronaut/entityTemplates/groovy/methods/Controllers/FindAllBy.txt");
        groovyTemplates.put(FIND_BY_GRAPHQL, "micronaut/entityTemplates/groovy/methods/GraphQL/FindBy.txt");
        groovyTemplates.put(FIND_All_BY_GRAPHQL, "micronaut/entityTemplates/groovy/methods/GraphQL/FindAllBy.txt");
        groovyTemplates.put(FIND_BY_CLIENT, "micronaut/entityTemplates/groovy/methods/Clients/FindBy.txt");
        groovyTemplates.put(FIND_All_BY_CLIENT, "micronaut/entityTemplates/groovy/methods/Clients/FindAllBy.txt");
        groovyTemplates.put(UPDATE_BY_DATA_REPO, "micronaut/entityTemplates/groovy/methods/Repository/UpdateBy/data.txt");
        groovyTemplates.put(UPDATE_BY_MONOGO_REPO, "micronaut/entityTemplates/groovy/methods/Repository/UpdateBy/mongodb.txt");
        groovyTemplates.put(UPDATE_BY_REACTIVE_REPO, "micronaut/entityTemplates/groovy/methods/Repository/UpdateBy/dataReactive.txt");
        groovyTemplates.put(UPDATE_BY_SERVICE, "micronaut/entityTemplates/groovy/methods/Service/UpdateBy.txt");
        groovyTemplates.put(UPDATE_BY_CONTROLLER, "micronaut/entityTemplates/groovy/methods/Controllers/UpdateBy.txt");
        groovyTemplates.put(UPDATE_BY_CLIENT, "micronaut/entityTemplates/groovy/methods/Clients/UpdateBy.txt");
        groovyTemplates.put(UPDATE_BY_GRAPHQL, "micronaut/entityTemplates/groovy/methods/GraphQL/UpdateBy.txt");
        groovyTemplates.put(EVENT_LISTENER, "micronaut/components/groovy/event/EventListener.txt");
        groovyTemplates.put(EVENT_PUBLISHER, "micronaut/components/groovy/event/EventPublisher.txt");
        groovyTemplates.put(DATA_MONGODB_REPOSITORY, "micronaut/entityTemplates/groovy/MongoDataRepository.txt");
        groovyTemplates.put(AWS_CONFIGURATION, "micronaut/components/groovy/FileServices/AwsCredentials.txt");
        groovyTemplates.put(AWS_S3_SERVICE, "micronaut/components/groovy/FileServices/AwsStorageService.txt");
        groovyTemplates.put(AWS_CONFIGURATION_FACTORY, "micronaut/components/groovy/FileServices/AwsFactory.txt");
        groovyTemplates.put(FILE_SYSTEM_SERVICE, "micronaut/components/groovy/FileServices/FileSystem.txt");
        groovyTemplates.put(FILE_CONTROLLER_METHODS, "micronaut/entityTemplates/groovy/methods/Files/Controllers.txt");
        groovyTemplates.put(FILE_CLIENT_METHODS, "micronaut/entityTemplates/groovy/methods/Files/Client.txt");
        groovyTemplates.put(FILE_SERVICE_METHODS, "micronaut/entityTemplates/groovy/methods/Files/Services.txt");
        groovyTemplates.put(FILE_SERVICE_METHODS_AWS, "micronaut/entityTemplates/groovy/methods/Files/Services_aws.txt");
        groovyTemplates.put(MICROSTREAM_ROPOSITORY, "micronaut/entityTemplates/groovy/microstream/Repository.txt");
        groovyTemplates.put(MICROSTREAM_ROOT_DATA, "micronaut/components/groovy/Microsstream/RootClass.txt");
        groovyTemplates.put(EUREKA_SERVER, "cloud/eureka/Eureka.groovy");
        groovyTemplates.put(GATEWAY,"cloud/gateways/Gateway.groovy");
        groovyTemplates.put(CONFIG_SERVER, "cloud/config/Config.groovy");
        kotlinTemplates.put(CLIENT, "micronaut/entityTemplates/kotlin/client.txt");
        kotlinTemplates.put(CONTROLLER, "micronaut/entityTemplates/kotlin/controller.txt");
        kotlinTemplates.put(GRPC_ENDPOINT, "micronaut/entityTemplates/kotlin/GrpcEndPoint.txt");
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
        kotlinTemplates.put(MONGODB_CONFIGURATION, "micronaut/entityTemplates/kotlin/MongodbConfiguration.txt");
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
        kotlinTemplates.put(SECURITY_AUTHENTICATION_PROVIDER, "micronaut/security/deprecated/kotlin/AuthenictationProviderUserPassword.txt");
        kotlinTemplates.put(SECURITY_LOGIN_EVENT, "micronaut/security/deprecated/kotlin/LoginEvent.txt");
        kotlinTemplates.put(SECURITY_LOGIN_STATUS, "micronaut/security/deprecated/kotlin/LoginStatus.txt");
        kotlinTemplates.put(SECURITY_PASSWORD_ENCODER, "micronaut/security/deprecated/kotlin/PasswordEncoder.txt");
        kotlinTemplates.put(SECURITY_REACTIVE_AUTHENTICATION_PROVIDER, "micronaut/security/deprecated/kotlin/reactive/AuthenictationProviderUserPassword.txt");
        kotlinTemplates.put(SECURITY_ROLES, "micronaut/security/deprecated/kotlin/Roles.txt");
        kotlinTemplates.put(SECURITY_USER, "micronaut/security/deprecated/kotlin/User.txt");
        kotlinTemplates.put(SECURITY_USER_REPOSITORY, "micronaut/security/deprecated/kotlin/UserRepository.txt");
        kotlinTemplates.put(SECURITY_USER_SERVICE, "micronaut/security/deprecated/kotlin/UserService.txt");
        kotlinTemplates.put(SECURITY_USER_CONTROLLER, "micronaut/security/deprecated/kotlin/UserController.txt");
        kotlinTemplates.put(SECURITY_USER_MONGO_REPOSITORY, "micronaut/security/deprecated/kotlin/reactive/UserRepository.txt");
        kotlinTemplates.put(SECURITY_USER_MONGO_SERVICE, "micronaut/security/deprecated/kotlin/reactive/UserService.txt");
        kotlinTemplates.put(SECURITY_USER_MONGO_CONTROLLER, "micronaut/security/deprecated/kotlin/reactive/UserController.txt");
        kotlinTemplates.put(R2DBC_CONTROLLER, "micronaut/entityTemplates/kotlin/r2dbc/controller.txt");
        kotlinTemplates.put(R2DBC_SERVICE, "micronaut/entityTemplates/kotlin/r2dbc/service.txt");
        kotlinTemplates.put(R2DBC_REPOSITORY, "micronaut/entityTemplates/kotlin/r2dbc/jdbcRepository.txt");
        kotlinTemplates.put(R2DBC_CLIENT, "micronaut/entityTemplates/kotlin/r2dbc/client.txt");
        kotlinTemplates.put(GENERAL_REACTIVE_REPOSITORY, "micronaut/entityTemplates/kotlin/generalReactiveRepository.txt");
        kotlinTemplates.put(GENERAL_REACTIVE_SERVICE, "micronaut/entityTemplates/kotlin/generalReactiveService.txt");
        kotlinTemplates.put(LAMBDA_FUNCTION_FINDALL_REQUEST, "micronaut/functions/aws/kotlin/FindAllRequestHander.kt");
        kotlinTemplates.put(LAMBDA_FUNCTION_SAVE_REQUEST, "micronaut/functions/aws/kotlin/SaveRequestHander.kt");
        kotlinTemplates.put(LAMBDA_FUNCTION_DELETE_REQUEST, "micronaut/functions/aws/kotlin/DeleteRequestHander.kt");
        kotlinTemplates.put(LAMBDA_FUNCTION_FIND_REQUEST, "micronaut/functions/aws/kotlin/FindByIdRequestHander.kt");
        kotlinTemplates.put(LAMBDA_FUNCTION_UPDATE_REQUEST, "micronaut/functions/aws/kotlin/UpdateRequestHander.kt");
        kotlinTemplates.put(FIND_BY_DATA_REPO,"micronaut/entityTemplates/kotlin/methods/Repository/FindBy/data.txt");
        kotlinTemplates.put(FIND_BY_MONGODB_REPO, "micronaut/entityTemplates/kotlin/methods/Repository/FindBy/mongodb.txt");
        kotlinTemplates.put(FIND_BY_REACTIVE_REPO, "micronaut/entityTemplates/kotlin/methods/Repository/FindBy/dataReactive.txt");
        kotlinTemplates.put(FIND_ALL_BY_DATA_REPO,"micronaut/entityTemplates/kotlin/methods/Repository/FindAllBy/data.txt");
        kotlinTemplates.put(FIND_ALL_BY_MONGODB_REPO, "micronaut/entityTemplates/kotlin/methods/Repository/FindAllBy/mongodb.txt");
        kotlinTemplates.put(FIND_ALL_BY_REACTIVE_REPO, "micronaut/entityTemplates/kotlin/methods/Repository/FindAllBy/dataReactive.txt");
        kotlinTemplates.put(FIND_BY_SERVICE, "micronaut/entityTemplates/kotlin/methods/Service/FindBy.txt");
        kotlinTemplates.put(FIND_BY_SERVICE, "micronaut/entityTemplates/kotlin/methods/Service/FindBy.txt");
        kotlinTemplates.put(FIND_All_BY_SERVICE, "micronaut/entityTemplates/kotlin/methods/Service/FindAllBy.txt");
        kotlinTemplates.put(FIND_BY_CONTROLLER, "micronaut/entityTemplates/kotlin/methods/Controllers/FindBy.txt");
        kotlinTemplates.put(FIND_All_BY_CONTROLLER, "micronaut/entityTemplates/kotlin/methods/Controllers/FindAllBy.txt");
        kotlinTemplates.put(FIND_BY_GRAPHQL, "micronaut/entityTemplates/kotlin/methods/GraphQL/FindBy.txt");
        kotlinTemplates.put(FIND_All_BY_GRAPHQL, "micronaut/entityTemplates/kotlin/methods/GraphQL/FindAllBy.txt");
        kotlinTemplates.put(FIND_BY_CLIENT, "micronaut/entityTemplates/kotlin/methods/Clients/FindBy.txt");
        kotlinTemplates.put(FIND_All_BY_CLIENT, "micronaut/entityTemplates/kotlin/methods/Clients/FindAllBy.txt");
        kotlinTemplates.put(UPDATE_BY_DATA_REPO, "micronaut/entityTemplates/kotlin/methods/Repository/UpdateBy/data.txt");
        kotlinTemplates.put(UPDATE_BY_MONOGO_REPO, "micronaut/entityTemplates/kotlin/methods/Repository/UpdateBy/mongodb.txt");
        kotlinTemplates.put(UPDATE_BY_REACTIVE_REPO, "micronaut/entityTemplates/kotlin/methods/Repository/UpdateBy/dataReactive.txt");
        kotlinTemplates.put(UPDATE_BY_SERVICE, "micronaut/entityTemplates/kotlin/methods/Service/UpdateBy.txt");
        kotlinTemplates.put(UPDATE_BY_CONTROLLER, "micronaut/entityTemplates/kotlin/methods/Controllers/UpdateBy.txt");
        kotlinTemplates.put(UPDATE_BY_CLIENT, "micronaut/entityTemplates/kotlin/methods/Clients/UpdateBy.txt");
        kotlinTemplates.put(UPDATE_BY_GRAPHQL, "micronaut/entityTemplates/kotlin/methods/GraphQL/UpdateBy.txt");
        kotlinTemplates.put(EVENT_LISTENER, "micronaut/components/kotlin/event/EventListener.txt");
        kotlinTemplates.put(EVENT_PUBLISHER, "micronaut/components/kotlin/event/EventPublisher.txt");
        kotlinTemplates.put(DATA_MONGODB_REPOSITORY, "micronaut/entityTemplates/kotlin/MongoDataRepository.txt");
        kotlinTemplates.put(AWS_CONFIGURATION, "micronaut/components/kotlin/FileServices/AwsCredentials.txt");
        kotlinTemplates.put(AWS_S3_SERVICE, "micronaut/components/kotlin/FileServices/AwsStorageService.txt");
        kotlinTemplates.put(AWS_CONFIGURATION_FACTORY, "micronaut/components/kotlin/FileServices/AwsFactory.txt");
        kotlinTemplates.put(FILE_SYSTEM_SERVICE, "micronaut/components/kotlin/FileServices/FileSystem.txt");
        kotlinTemplates.put(FILE_CONTROLLER_METHODS, "micronaut/entityTemplates/kotlin/methods/Files/Controllers.txt");
        kotlinTemplates.put(FILE_CLIENT_METHODS, "micronaut/entityTemplates/kotlin/methods/Files/Client.txt");
        kotlinTemplates.put(FILE_SERVICE_METHODS, "micronaut/entityTemplates/kotlin/methods/Files/Services.txt");
        kotlinTemplates.put(FILE_SERVICE_METHODS_AWS, "micronaut/entityTemplates/kotlin/methods/Files/Services_aws.txt");
        kotlinTemplates.put(MICROSTREAM_ROPOSITORY, "micronaut/entityTemplates/kotlin/microstream/Repository.txt");
        kotlinTemplates.put(MICROSTREAM_ROOT_DATA, "micronaut/components/kotlin/Microsstream/RootClass.txt");
        kotlinTemplates.put(EUREKA_SERVER,"cloud/eureka/Eureka.kt");
        kotlinTemplates.put(GATEWAY, "cloud/gateways/Gateway.kt");
        kotlinTemplates.put(CONFIG_SERVER, "cloud/config/ConfigServer.kt");

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
        properties.put(MDB_yml, "micronaut/entityTemplates/mdb.yml");
        properties.put(MDB_COLLECTION_YML, "micronaut/entityTemplates/mdb_collections.yml");
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
        properties.put(AWS_CONFIGURATION_PROPERTIES, "micronaut/components/aws_properties.txt");
        properties.put(MICROSTREAM_YML, "micronaut/entityTemplates/microstreams/microstream_properties.txt");
        properties.put(OPENAPI_PROPERTIES , "micronaut/components/openapi.properties");
        properties.put(VIEW_CONFIG, "micronaut/components/view.yml");
        properties.put(MQTT, "micronaut/components/mqtt.yml");


        graphqlTemplates.put(GRAPHQL_ENUM, "micronaut/entityTemplates/graphql/enum.txt");
        graphqlTemplates.put(GRAPHQL_SCHEMA, "micronaut/entityTemplates/graphql/schema.txt");
        graphqlTemplates.put(GRAPHQL_TYPE, "micronaut/entityTemplates/graphql/type.txt");
        graphqlTemplates.put(GRAPHQL_DATA, "micronaut/entityTemplates/graphql/data.txt");
        graphqlTemplates.put(GRAPHQL_MUTATION, "micronaut/entityTemplates/graphql/mutation.txt");
        graphqlTemplates.put(GRAPHQL_QUERY, "micronaut/entityTemplates/graphql/query.txt");
        graphqlTemplates.put(GRAPHQL_QUERY_METHOD, "micronaut/entityTemplates/graphql/queryMethods.txt");
        graphqlTemplates.put(FIND_BY_GRAPHQL, "micronaut/entityTemplates/graphql/findBy.txt");
        graphqlTemplates.put(FIND_All_BY_GRAPHQL, "micronaut/entityTemplates/graphql/findAllBy.txt");
        graphqlTemplates.put(UPDATE_BY_GRAPHQL, "micronaut/entityTemplates/graphql/UpdateBy.txt");
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

//        cacheTemplates.put(CAFFEINE_YML , "micronaut/entityTemplates/caffeine_properties.yml");
        micrometersTemplates.put(MICROMETERS_yml, "micronaut/micrometers/Micrometers.yml");
        micrometersTemplates.put(PROMETHEUS_yml, "micronaut/micrometers/prometheus.yml");
        micrometersTemplates.put(GRAPHITE_yml, "micronaut/micrometers/graphite.yml");
        micrometersTemplates.put(STATSD_yml, "micronaut/micrometers/statsd.yml");
        micrometersTemplates.put(INFLUX_yml, "micronaut/micrometers/influxdb.yml");
        micrometersTemplates.put(PROMETHEUS_JOB_YML, "micronaut/micrometers/prometheusJobConfig.yml");
        securityControllerTemplates.put(USER_CONTROLLER,"micronaut/security/${auth}/${lang}/${db}/controllers/UserController${ext}");
        securityDomainsTemplates.put(LOGIN_EVENT ,"micronaut/security/${auth}/${lang}/${db}/domains/LoginEvent${ext}");
        securityRefreshTokenTemplates.put(REFRESHTOKEN ,"micronaut/security/${auth}/${lang}/${db}/domains/RefreshToken${ext}");
        securityDomainsTemplates.put(LOGIN_STATUS ,"micronaut/security/${auth}/${lang}/${db}/domains/LoginStatus${ext}");
        securityDomainsTemplates.put(ROLES ,"micronaut/security/${auth}/${lang}/${db}/domains/Roles${ext}");
        securityDomainsTemplates.put(USER ,"micronaut/security/${auth}/${lang}/${db}/domains/User${ext}");

        securityAPIKeyTemplates.put(APIKEY, "micronaut/security/api key/${lang}/JDBC/domains/APIKey${ext}");
        securityAPIKeyTemplates.put(APIKEY_REPOSITORY, "micronaut/security/api key/${lang}/JDBC/repository/ApiKeyRepository${ext}");
        securityAPIKeyTemplates.put(APIKEY_SERVICE, "micronaut/security/api key/${lang}/JDBC/service/ApiKeyService${ext}");
        securityAPIKeyTemplates.put(APIKEY_CONTROLLER, "micronaut/security/api key/${lang}/JDBC/controllers/ApiKeyController${ext}");
        securityAPIKeyTemplates.put(APIKEY_TOKEN_READER, "micronaut/security/api key/${lang}/JDBC/token/ApiKeyTokenReader${ext}");
        securityAPIKeyTemplates.put(APIKEY_TOKEN_VALIDATOR, "micronaut/security/api key/${lang}/JDBC/token/ApiKeyTokenValidator${ext}");
        securityAPIKeyTemplates.put(APIKEY_TOKEN_GENERATOR, "micronaut/security/api key/${lang}/JDBC/token/ApiKeyTokenGenerator${ext}");
        securityAPIKeyTemplates.put(APIKEY_LIQUIBASE, "micronaut/security/db/changelog/db.apikeys-1.xml");
        securityRefreshTokenTemplates.put(REFRESH_TOKEN_REPOSITORY,"micronaut/security/${auth}/${lang}/JDBC/repository/RefreshTokenRepository${ext}");
        securityRepositoryTemplates.put(USER_REPOSITORY ,"micronaut/security/${auth}/${lang}/${db}/repository/UserRepository${ext}");
        securityRefreshTokenTemplates.put(CUSTOM_REFRESH_TOKEN ,"micronaut/security/${auth}/${lang}/${db}/services/CustomRefreshTokenPersistence${ext}");
        securityServicesTemplates.put(USER_SERVICE ,"micronaut/security/${auth}/${lang}/${db}/services/UserService${ext}");
        securityServicesTemplates.put(SECURITY_CODERANDOMIZER, "micronaut/security/${auth}/${lang}/${db}/utils/CodeRandomizer${ext}");
        securityTemplates.put(AUTHENTICATION_PROVIDER,"micronaut/security/${auth}/${lang}/${db}/AuthenticationProviderUserPassword${ext}");
        securityTemplates.put(PASSWORD_ENCODER,"micronaut/security/${auth}/${lang}/${db}/PasswordEncoder${ext}");
        securityTemplates.put(PASSWORD_ENCODER_SERVICE ,"micronaut/security/${auth}/${lang}/${db}/PasswordEncoderService${ext}");
        securityTemplates.put( SECURITY_CLIENT ,"micronaut/security/${auth}/${lang}/${db}/SecurityClient${ext}");
        securityTemplates.put(SECURITY_FACTORY ,"micronaut/security/${auth}/${lang}/${db}/SecurityFactory${ext}");
        securityPropertiesTemplates.put(SECURITY_JWT_PROPERTIES, "micronaut/security/jwt_properties.yml");
        securityPropertiesTemplates.put(SECURITY_JWT_PROPAGATION_PROPERTIES, "micronaut/security/propagation.yml");
        securityPropertiesTemplates.put(SECURITY_SESSION_PROPERTIES, "micronaut/security/session_properties.yml");
        securityPropertiesTemplates.put(SECURITY_INTERCEPT_URL, "micronaut/security/InterceptURL.yml");
        securityPropertiesTemplates.put(SECURITY_INTERCEPT_URL_PATTERN, "micronaut/security/InterceptURLPattern.yml");
        securityLiquibase.put(SECURITY_LIQUIBASE_CONFIG, "micronaut/security/db/liquibase-changelog.xml");
        securityLiquibase.put(SECURITY_LIQUIBASE_SCHEMA, "micronaut/security/db/changelog/db.security-1.xml");
        securityEventsTemplates.put(LOGIN_EVENT_PUBLISHER, "micronaut/security/${auth}/${lang}/${db}/event/LoginEventPublisher${ext}");
        securityEventsTemplates.put(LOGIN_EVENT_LISTENER, "micronaut/security/${auth}/${lang}/${db}/event/LoginEventListener${ext}");

        distributedTracingTemplates.put(DISTRIBUTED_TRACING_ZIPKIN, "micronaut/commons/tracing/zipkin.yml");
        distributedTracingTemplates.put(DISTRIBUTED_TRACING_JAEGER, "micronaut/commons/tracing/jaeger.yml");

        flutter.put(FLUTTER_ENTITY, "/flutter/entity.dart");
        flutter.put(FLUTTER_CLIENT, "/flutter/client.dart");
        flutter.put(FLUTTER_SERVICE, "/flutter/service.dart");
        flutter.put(FLUTTER_CONTROLLER, "/flutter/controller.dart");
        flutter.put(FLUTTER_ENUM, "/flutter/enum.dart");
        flutter.put(FLUTTER_EXCEPTION, "/flutter/exception.dart");
        flutter.put(FLUTTER_REPOSITORY, "/flutter/repository.dart");
        flutter.put(FLUTTER_SERVICE, "/flutter/service.dart");
        flutter.put(FLUTTER_LOGGING, "/flutter/logging.dart");
        flutter.put(FLUTTER_ENTITY_PAGE, "/flutter/entity_page.dart");
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

    public String getKeyByLanguage(String lang, String key)
    {
        switch (lang)
        {
            case JAVA_LANG:
                return javaTemplates.get(key);
            case GROOVY_LANG:
                return groovyTemplates.get(key);
            case KOTLIN_LANG:
                return kotlinTemplates.get(key);
        }
        return "";
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


    public HashMap<String, String> getMicrometersTemplates(){
        return micrometersTemplates;
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
    public HashMap<String, String> getSecurityTemplates(){return securityTemplates; }
    public HashMap<String, String> getSecurityControllerTemplates(){return  securityControllerTemplates; }
    public HashMap<String, String> getSecurityDomainsTemplates(){return securityDomainsTemplates; }
    public HashMap<String, String> getSecurityRepositoryTemplates(){return securityRepositoryTemplates; }
    public HashMap<String, String> getSecurityServicesTemplates(){return securityServicesTemplates; }
    public HashMap<String, String> getSecurityEventsTemplates(){return securityEventsTemplates; }
    public HashMap<String, String> getSecurityUtilsTemplates(){return securityUtilsTemplates; }
    public HashMap<String, String> getSecurityRefreshTokenTemplates(){return securityRefreshTokenTemplates; }
    public HashMap<String, String> getSecurityPropertiesTemplates(){return securityPropertiesTemplates; }
    public HashMap<String, String> getSecurityLiquibase(){return securityLiquibase; }

    public HashMap<String, String> getDistributedTracingTemplates() {
        return distributedTracingTemplates;
    }
    public HashMap<String, String > getSecurityApiKeyTemplates(){return securityAPIKeyTemplates; }
}

