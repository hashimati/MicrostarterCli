package io.hashimati.microcli.constants;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ProjectConstants {
    public static class LanguagesConstants{
        public final static  String JAVA_LANG="java",
                KOTLIN_LANG="kotlin",
                GROOVY_LANG="groovy";
        public final static List<String> LANGUAGES = Arrays.asList(JAVA_LANG, KOTLIN_LANG, GROOVY_LANG);
    }

    public static class BuildConstats{
        public final static String GRADLE= "gradle",
        MAVEN = "maven";
        public final static List<String> BUILDS = Arrays.asList(GRADLE, MAVEN);
    }
    public static class ProfileConstants{
        public static final String SKELETON = "skeleton";
        public static final String TEMPLATES = "templates";
        public static final String FEATURES = "features";

    }
    public static class Extensions{
        public final  static String JAVA="java",
                KOTLIN="kt",
                GROOVY ="groovy",
                gradle = ".gradle",
                gradle_kotlin=".gradle_kts",
                XML = ".xml";
    }

    public static class FrameWorkConstant{
        public final static String MICRONAUT="micronaut",
                SPRING="spring",
                GRPC="grpc",
                GENERAL="general";
    }

    public static class MicronautProfileConstants {
        public final static String SERVICE= "service"
                ,KAFKA = "kafka", BASE = "base",
                FUNCTION="function",
                RABBITMQ="rabbitmq",
                FUNCTION_AWS = "function-aws",
                GRPC = "grpc",
                FUNCTION_AWS_ALEXA= "function-aws-alexa"
                        ,CLI = "cli", CONFIGURATION="configuration";
        public static final String FEDERATION = "federation";
        public static final List<String> PROFILES = Arrays.asList(SERVICE, FUNCTION, FUNCTION_AWS,FUNCTION_AWS_ALEXA,
                GRPC);
    }
    public static class MicronautProfilesFolders{

        public static final String FEATURES ="features",
        SKELETON= "skeleton",
        TEMPLATES="templates";

    }
    public static class MicronautSkeletonFolders{
        public static final String MAVEN_BUILD="maven-build",
        GRADLE_BUILD="gradle-build",
        SRC ="src";
    }

    public static class GradleFilesConstants{
        public static final String BUILD="build.gradle";
    }

    public static class SrcFolders{

        public static final String RESOURCES = "resources",
        SRC = "src",
        MAIN = "main",
        TEST= "test",
        //RESOURCES_TEST = "resources",
        JAVA_DIR = "java",
        KOTLIN_DIR= "kotlin",
        GROOVY_DIR="groovy",
        APPLICATION = "application.yml",
        APPLICATION_TEST = "application-test.yml",
        BOOTSTRAP = "bootstrap.yml",
        FUNCTION_YML = "function.yml",
        PACKAGE_DIR = "@defaultPackage.path@",
        PROJECT_CLASS_NAME_CONSTANT = "@project.className@",
        APPLICATION_YML_FULL = "/src/main/resources/application.yml",
                APPLICATION_YML_TEST_FULL = "/src/main/resources/application-test.yml",
                BOOTSTRAP_YML_FULL = "/src/main/resources/bootstrap.yml",
                BOOTSTRAP_YML_TEST_FULL = "/src/test/resources/bootstrap.yml",
        FUNCTION_YML_FULL = "/src/main/resources/function.yml",
        FUNCTION_YML_TEST_FULL = "/src/test/resources/function.yml";

    }


    public static class ServiceTemplates{
        public static final String CLIENT= "Client",
        CONTROLLER ="Controller",
        CONTROLLER_TEST = "ControllerTest",
        WEBSOCKET_CLIENT = "WebsocketClient",
        WEBSOCKET_SERVER = "WebsocketServer",
        CONTROLLER_SPEC ="ControllerSpec",
                PROFILE = "SERVICE";
        public static final List<String> LIST = Arrays.asList(
                CLIENT,
                CONTROLLER ,
                CONTROLLER_TEST,
                WEBSOCKET_CLIENT ,
                WEBSOCKET_SERVER ,
                CONTROLLER_SPEC
        );
    }
    public static class BaseTemplates{
        public static final String BEAN = "Bean",
        JOB= "Job",
        TEST = "Test",
        SPEC = "Spec",
        PROFILE = "BASE";
        public static final List<String> LIST = Arrays.asList(
                JOB,
                TEST,
                SPEC
        );
    }
    public static class CliTemplates{
        public static final String COMMAND="Command",
        COMMAND_TEST="CommmandTest",
        COMMAND_SPEC="CommandSpec";
        public static final List<String> LIST= Arrays.asList(
                COMMAND, COMMAND_TEST, COMMAND_SPEC
        );
    }
    public static class KafkaTemplates{
        public final static String LISTENER = "Listener",
        PRODUCER = "Producer",
                PROFILE = "KAFKA";
        public static final List<String> LIST = Arrays.asList(
            LISTENER,
                PRODUCER
        );
    }
    public static class RabbitmqTemplates{
        public final static String LISTENER = "Listener",
                PRODUCER = "Producer",
                PROFILE = "RABBITMQ";
        public static final List<String> LIST = Arrays.asList(
                LISTENER,
                PRODUCER
        );
    }
    public static class GRPCTemplates{
        public final static String SERVICE = "Service",
        SERVICE_PROTO = "service.proto",ROFILE = "GRPC";
        public static final List<String> LIST = Arrays.asList(
                SERVICE,
                SERVICE_PROTO
        );
    }

    public static class EntityAttributeType{
            public final static String STRING= "String",
            INTEGER = "int",
            LONG = "long",
            SHORT = "short",
            DOUBLE = "double",
            BOOLEAN = "boolean",
            BYTE = "byte",
            TIMESTAMPS= "Timestamps",
            DATE = "Date",
            CHAR = "char",
            FLOAT = "float",
            BIG_INTEGER = "BigInteger",
            BIG_DECIMAL ="BigDecimal",
            CLASS = "class",
            BIG_INTEGER_CLASS = "java.math.BigInteger",
            BIG_DECIMAL_CLASS = "java.math.BigDecimal",
            DATE_SQL = "java.sql.Date",
            DATE_NOSQL = "java.util.Date";


            public static HashMap<String, String> JAVA_TYPE_KEYWORDS= new HashMap<String, String>(){{
                put(INTEGER, "int");
                put(STRING, "String");
                put(LONG, "long");
                put(SHORT ,"short") ;
                put(DOUBLE, "double");
                put(BOOLEAN, "boolean");
                put(BYTE , "byte");
                put(CHAR , "char");
                put( FLOAT , "float");
                put(BIG_INTEGER , "BigInteger");
                put(BIG_DECIMAL ,"BigDecimal");
            }};
            public static HashMap<String, String> GROOVY_TYPE_KEYWORDS= new HashMap<String, String>(){{
                put(INTEGER, "int");
                put(STRING, "String");
                put(LONG, "long");
                put(SHORT ,"short") ;
                put(DOUBLE, "double");
                put(BOOLEAN, "boolean");
                put(BYTE , "byte");
                put(CHAR , "char");
                put( FLOAT , "float");
                put(BIG_INTEGER , "BigInteger");
                put(BIG_DECIMAL ,"BigDecimal");
            }};
            public static HashMap<String, String> KOTLIN_TYPE_KEYWORDS= new HashMap<String, String>(){{
                put(INTEGER, "Int");
                put(STRING, "String");
                put(LONG, "Long");
                put(SHORT ,"Short") ;
                put(DOUBLE, "Double");
                put(BOOLEAN, "Boolean");
                put(BYTE , "Byte");
                put(CHAR , "Char");
                put( FLOAT , "Float");
                put(BIG_INTEGER , "BigInteger");
                put(BIG_DECIMAL ,"BigDecimal");
            }};
        public static HashMap<String, String> SQL_TYPE_KEYWORDS= new HashMap<String, String>(){{
            put(INTEGER, "INT");
            put(STRING, "VARCHAR");
            put(LONG, "BIGINT");
            put(SHORT ,"SMALLINT") ;
            put(DOUBLE, "DECIMAL");
            put(BOOLEAN, "BOOL");
            put(BYTE , "SMALLINT");
            put(CHAR , "CHAR(1)");
            put( FLOAT , "FLOAT");
            put(BIG_INTEGER , "BigInteger");
            put(BIG_DECIMAL ,"BigDecimal");
            put(DATE_SQL, "TIMESTAMP");
        }};

        public static  List<String> getNumbric(){
                return Arrays.asList(LONG,INTEGER,SHORT, BYTE, FLOAT, DOUBLE, BIG_INTEGER, BIG_DECIMAL);
            }
            public static  List<String> getAvailableDataType(){
                    List<String> result = Arrays.asList(STRING, CHAR,BOOLEAN, CLASS);
                    result.addAll(getNumbric());
                    return result;
                }
            public static final String JAVA_KEYWORD[] = { "abstract", "assert", "boolean",
                    "break", "byte", "case", "catch", "char", "class", "const",
                    "continue", "default", "do", "double", "else", "extends", "false",
                    "final", "finally", "float", "for", "goto", "if", "implements",
                    "import", "instanceof", "int", "interface", "long", "native",
                    "new", "null", "package", "private", "protected", "public",
                    "return", "short", "static", "strictfp", "super", "switch",
                    "synchronized", "this", "throw", "throws", "transient", "true",
                    "try", "void", "volatile", "while" };

            public static boolean isJavaKeyword(String keyword) {
                return (Arrays.binarySearch(JAVA_KEYWORD, keyword) >= 0);
            }
            public static final String KOTLEN_KEYWORDS[] ={
                "as", 	"class", 	"break", 	"continue", 	"do", 	"else",
                "for", 	"fun", 	"false", 	"if", 	"in", 	"interface",
                "super", 	"return", 	"object", 	"package",	"null", 	"is",
                "try", 	"throw", 	"true", 	"this", 	"typeof", 	"typealias",
                "when", 	"while", 	"val", 	"var"
            };
            public static boolean isKotlinKeyword(String keyword) {
                return (Arrays.binarySearch(KOTLEN_KEYWORDS, keyword) >= 0);
            }


            public static final String GROOVY_KEYWORDS[] ={
                    "as", "assert","break", "case","catch","class","const",
                    "continue","def","default","do","else","enum","extends",
                    "false","finally","for","goto","if", "implements", "import", "in",
                    "instanceof", "interface","new","null","package","return","super",
                    "switch","this","throw","throws","trait","true", "try", "while"
            };
            public static boolean isGroovyKeyword(String keyword) {
                return (Arrays.binarySearch(GROOVY_KEYWORDS, keyword) >= 0);
            }
        }




        public static class ViewFramework{
            public static final String THYMELEAF = "Thymeleaf",
                    HANDLEBARS = "Handlebars",
                    VELOCITY = "Velocity",
                    FREEMARKER = "FREEMARKER";

            public static final String THYMELEAF_GRADLE = "runtime 'org.thymeleaf:thymeleaf:3.0.11.RELEASE'",
                    HANDLEBARS_GRADLE = "runtime 'com.github.jknack:handlebars:4.1.0'",
                    VELOCITY_GRADLE = "runtime 'org.apache.velocity:velocity-engine-core:2.0'",
                    FREEMARKER_GRADLE = "runtime 'org.freemarker:freemarker:2.3.28'";

            public static final String THYMELEAF_MAVEN = "\n" +
                    "\n" +
                    "<dependency>\n" +
                    "    <groupId>org.thymeleaf</groupId>\n" +
                    "    <artifactId>thymeleaf</artifactId>\n" +
                    "    <version>3.0.11.RELEASE</version>\n" +
                    "    <scope>runtime</scope>\n" +
                    "</dependency>\n" +
                    "\n",
                    HANDLEBARS_MAVEN = "\n" +
                            "<dependency>\n" +
                            "    <groupId>com.github.jknack</groupId>\n" +
                            "    <artifactId>handlebars</artifactId>\n" +
                            "    <version>4.1.0</version>\n" +
                            "    <scope>runtime</scope>\n" +
                            "</dependency>\n" +
                            "\n",
                    VELOCITY_MAVEN = "\n" +
                            "\n" +
                            "<dependency>\n" +
                            "    <groupId>org.apache.velocity</groupId>\n" +
                            "    <artifactId>velocity-engine-core</artifactId>\n" +
                            "    <version>2.0</version>\n" +
                            "    <scope>runtime</scope>\n" +
                            "</dependency>\n" +
                            "\n",
                    FREEMARKER_MAVEN = "\n" +
                            "\n" +
                            "<dependency>\n" +
                            "    <groupId>org.freemarker</groupId>\n" +
                            "    <artifactId>freemarker</artifactId>\n" +
                            "    <version>2.3.28</version>\n" +
                            "    <scope>runtime</scope>\n" +
                            "</dependency>\n" +
                            "\n";


            public static final String VIEW_MAVEN="\n" +
                    "<dependency>\n" +
                    "    <groupId>io.micronaut.views</groupId>\n" +
                    "    <artifactId>micronaut-views</artifactId>\n" +
                    "</dependency>\n",
                    VIEW_GRADLE ="implementation 'io.micronaut.views:micronaut-views'";

            public static final String VIEW_PROPERTIES = "---\n" +
                    "micronaut:\n" +
                    "  views:\n" +
                    "    ${framework}:\n" +
                    "      enabled: true\n",
            STATIC_PATH= "---\n" +
                    "micronaut:\n" +
                    "  router:\n" +
                    "    static-resources:\n" +
                    "      default:\n" +
                    "        mapping: \"/**\"\n" +
                    "      '*':\n" +
                    "        paths:\n" +
                    "        - \"classpath:static\"";
        }

        public static class EntityObject{

            public static final String ENTITY = "Entity",
            SERVICE = "Service",
            REPOSITORY = "Repository",
            CONTROLLER = "Controller",
            TEST = "Test",
            METHOD = "Method",
            INSTANCE_VAR= "Instance_Var";
        }


        public static class EntityTemplates{

            public static final String ENTITY_JAVA = "package ${entitypackage}\n" +
                    "\n" +
                    "\n" +
                    "import javax.persistence.*;\n" +
                    "import lombok.*;\n" +
                    "\n" +
                    "@Data\n" +
                    "@ToString\n" +
                    "@NoArgsConstructor\n" +
                    "@AllArgsConstructor\n" +
                    "@EqualsAndHashCode\n" +
                    "@Builder\n" +
                    "\n" +
                    "${entityAnnotation}\n" +
                    "${tableAnnotation}\n" +//@Table(name="${tablename}")
                    "public class ${className}{\n" +
                    "\t${instances}\n" +
                    "}\n",
                    ENTITY_GROOVY = "package ${entitypackage}\n" +
                            "\n" +
                            "import javax.persistence.Entity\n" +
                            "import javax.persistence.GeneratedValue\n" +
                            "import javax.persistence.Id\n" +
                            "\n" +
                            "${entityAnnotation}\n" +
                            "${tableAnnotation}\n" +//@Table(name="${tablename}")
                            "class ${className}{\n" +
                            "    @Id\n" +
                            "    @GeneratedValue\n" +
                            "    Long id\n" +
                            "   ${instances}\n" +
                            "\n" +
                            "  ${contructor}\n" +
                            "\n" +
                            "  ${defaultConstructor}\n" +
                            "}",
                    ENTITY_KOTLIN = "package ${entitypackage}\n" +
                            "\n" +
                            "import javax.persistence.Entity\n" +
                            "import javax.persistence.GeneratedValue\n" +
                            "import javax.persistence.Id\n" +
                            "\n" +
                            "${entityAnnotation}\n" +
                            "${tableAnnotation}\n" +//@Table(name="${tablename}")
                            "data class ${className}(@Id\n" +
                            "                @GeneratedValue\n" +
                            "\t\tvar id: Long,\n" +
                            "              \t${instances})";


        }



        public static class PathsTemplate{

            public  static String ENTITY_PATH = "/src/main/${lang}/${defaultPackage}/domains",
                    REPOSITORY_PATH = "/src/main/${lang}/${defaultPackage}/repositories",
            SERVICES_PATH = "/src/main/${lang}/${defaultPackage}/services",
            CONTROLLER_PATH= "/src/main/${lang}/${defaultPackage}/controllers",
                    GRAPHQL_PATH= "/src/main/${lang}/${defaultPackage}/graphqls",

            GENERAL_PATH = "/src/main/${lang}/${defaultPackage}",
            EXCEPTION_HANDLER_PATH = "/src/main/${lang}/${defaultPackage}/exceptions/handlers",
            GENERAL_EXCEPTION = "/src/main/${lang}/${defaultPackage}/exceptions",
                    ENUMS="/src/main/${lang}/${defaultPackage}/enums";



        }

        public static final String MicronautServiceInfoTemplate = "micronaut:\n" +
                "  application:\n" +
                "    name: ${servicename}\n" +
                "  server:\n" +
                "    port: ${port}\n";




}


