package io.hashimati.parsers.keywords;

import java.util.Set;

public class Keywords {

    public static Set<String> VALIDATION_KEYWORDS = Set.of(
            "min\\s*\\(\\s*\\d+\\s*\\)",
            "max\\s*\\(\\s*\\d+\\s*\\)",
            "size\\(\\d+\\s*\\,\\s*\\d+\\)",
            "required",
            "nullable",
            "notnull",
            "regex\\s*\\(\\s*[\\w* \\W* \\S* \\s*]*\\s*\\)"
            );
    public static Set<String> OBJECT_KEYWORDS = Set.of(
            "entity",
            "services",
            "security",
            "config",
            "enum",
            "client",
            "microservices",
            "package",
            "build",
            "language",
            "port",
            "reactive",
            "graphql",
            "metrics",
            "file",
            "cache",
            "tracing",
            "messaging",

            "pagination",


    )
}
