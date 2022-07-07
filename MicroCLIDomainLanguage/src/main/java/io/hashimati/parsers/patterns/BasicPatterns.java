package io.hashimati.parsers.patterns;

public class BasicPatterns {

    public static String bodyPattern = "[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*";
   public static String invalidBody = "[\\W* \\w*]*\\s*";
    public static String ENTITY_PATTERN = "\\s*entity\\s*\\w*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String SERVICE_PATTERN = "\\s*service\\s*\\w*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String SECURITY_PATTERN = "\\s*security\\s*\\w*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    public static String ENUM_PATTERN = "\\s*enum\\s*\\w*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}";
    //Services Commands

    public static String PACKAGE_PATTERNS = "\\s*package [\\w*.]*\\w*\\s*;";
    public static String BUILD_PATTERNS = "\\s*build \\w*\\s*;";
    public static String LANGUAGE_PATTERNS = "\\s*language \\w*\\s*;";

    //Configurations's commands.
    public static String REACTIVE_COMMAND_PATTERN = "\\s*reactive \\w*\\s*;";
    public static String DATABASE_COMMAND_PATTERN = "\\s*database \\w*\\s*;";
    public static String DATABASE_NAME_COMMAND_PATTERN = "\\s*databaseName \\w*\\s*;";
    public static String JAX_RS_COMMAND_PATTERN = "\\s*jaxRS\\s*;";
    public static String CACHE_COMMAND_PATTERN = "\\s*cache \\w*\\s*;";
    public static String MESSAGING_COMMAND_PATTERN = "\\s*messaging \\w*\\s*;";
    public static String TRACING_COMMAND_PATTERN = "\\s*tracing \\w*\\s*;";
    public static String GRAPHQL_COMMAND_PATTERN = "\\s*graphql\\s*;";
    public static String METRICS_COMMAND_PATTERN = "\\s*metrics \\w*\\s*;";
    public static String FILE_COMMAND_PATTERN = "\\s*file \\w*\\s*;";
    public static String VALIDATE_LINE_PATTERN = "\\s*[\\w\\s]*\\s*;\\s*";


    //entity's patterns
    public static String ATTRIBUTE_DECLARATION = "\\s*\\w*\\s*\\:\\s*\\w*\\s*[\\w ]*\\s*[;|,]{1}";
    public static String PAGENATION_COMMAND_PATTERN = "\\s*pagination\\s*;";
    public static String RECORDS_COMMAND_PATTERN = "\\s*records\\s*;";


}
