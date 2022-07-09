package io.hashimati.parsers.patterns;

public class GrammarPatterns {

    public static String bodyPattern = "[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*";
    public static String invalidBody = "[\\W* \\w*]*\\s*";
    public static String ENTITY_PATTERN = "\\s*entity\\s*\\w*\\s*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String SERVICE_PATTERN = "\\s*service\\s*\\w*\\s*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String MICROSERVICES_PATTERN = "\\s*microservices\\s*\\w*\\s*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String SECURITY_PATTERN = "\\s*security\\s*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String ENUM_PATTERN = "\\s*enum\\s*\\w*\\s*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String CLIENT_PATTERN = "\\s*client\\s*\\w*\\s*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";

    public static  String ENTITY_DECLARATION = "\\s*entity\\s*\\w*\\s*\\{";
    public static  String SERVICE_DECLARATION = "\\s*service\\s*\\w*\\s*\\{";
    public static  String ENUM_DECLARATION = "\\s*enum\\s*\\w*\\s*\\{";
    public static  String MICROSERVICES_DECLARATION = "\\s*microservices\\s*\\w*\\s*\\{";

    //Services Commands
    public static String PACKAGE_PATTERNS = "\\s*package [\\w*.]*\\w*\\s*;";
    public static String BUILD_PATTERNS = "\\s*build \\w*\\s*;";
    public static String LANGUAGE_PATTERNS = "\\s*language \\w*\\s*;";

    //Configurations's commands.
    public static String PORT_COMMAND_PATTERN = "\\s*port \\s*^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$ \\s*;";
    public static String REACTIVE_COMMAND_PATTERN = "\\s*reactive \\s*\\w*\\s*;";
    public static String DATABASE_COMMAND_PATTERN = "\\s*database \\s*\\w*\\s*;";
    public static String DATABASE_NAME_COMMAND_PATTERN = "\\s*databaseName \\s*\\w*\\s*;";
    public static String JAX_RS_COMMAND_PATTERN = "\\s*jaxRS\\s*;";
    public static String CACHE_COMMAND_PATTERN = "\\s*cache \\s*\\w*\\s*;";
    public static String MESSAGING_COMMAND_PATTERN = "\\s*messaging \\s*\\w*\\s*;";
    public static String TRACING_COMMAND_PATTERN = "\\s*tracing \\s*\\w*\\s*;";
    public static String GRAPHQL_COMMAND_PATTERN = "\\s*graphql\\s*;";
    public static String METRICS_COMMAND_PATTERN = "\\s*metrics \\s*\\w*\\s*;";
    public static String FILE_COMMAND_PATTERN = "\\s*file \\s*\\w*\\s*;";
    public static String VALIDATE_LINE_PATTERN = "\\s*[\\w\\s]*\\s*;\\s*";

    //entity's patterns
    public static String ATTRIBUTE_DECLARATION = "\\s*\\w*\\s*\\:\\s*\\w*\\s*[\\w ]*\\s*[;|,]{1}";
    public static String PAGENATION_COMMAND_PATTERN = "\\s*pagination\\s*;";
    public static String RECORDS_COMMAND_PATTERN = "\\s*records\\s*;";
    public static String VALIDATION_SIZE = "\\s*size\\s*\\(\\s*\\d*\\s*,\\s*\\d*\\s*\\)";
    public static String VALIDATION_MIN = "\\s*min\\s*\\(\\s*\\d*\\s*\\)";
    public static String VALIDATION_MAX = "\\s*max\\s*\\(\\s*\\d*\\s*\\)";
}
