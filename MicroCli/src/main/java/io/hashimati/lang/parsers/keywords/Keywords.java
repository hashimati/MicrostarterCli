package io.hashimati.lang.parsers.keywords;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import java.util.Set;

public class Keywords {

    public final static Set<String> VALIDATION_KEYWORDS = Set.of(
            "min\\s*\\(\\s*\\d+\\s*\\)",
            "max\\s*\\(\\s*\\d+\\s*\\)",
            "size\\(\\d+\\s*\\-\\s*\\d+\\)",
            "required",
            "nullable",
            "notnull",
            "email",
            "regex\\([.\\w\\{ \\} \\[\\]\\(\\)\\.\\:\\'\\<\\>\\\" \\,\\?\\\\ \\*\\+]*\\)"
            );

    public final static Set<String> DATA_TYPE_KEYWORDS = Set.of(
            "String",
            "char",
            "byte",
            "short",
            "int",
            "long",
            "float",
            "BigInteger",
            "BigDecimal",
            "double",
            "boolean");


    public final static Set<String> RelationShips = Set.of(
            "OneToOne",
            "OneToMany",
            "ManyToMany"
    );

    public final static Set<String> OBJECT_KEYWORDS = Set.of(
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
            "framework",
            "dao"

    );
}
