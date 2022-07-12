package io.hashimati.parsers.keywords;

import java.util.Arrays;
import java.util.List;

public class Keywords {

    public static List<String> VALIDATION_KEYWORDS = Arrays.asList(
            "entity",
            "config",
            "min\\s*\\(\\s*\\d+\\s*\\)",
            "max\\s*\\(\\s*\\d+\\s*\\)",
            "size\\(\\d+\\s*\\,\\s*\\d+\\)",
            "required",
            "nullable",
            "notnull",
            "regex\\s*\\(\\s*[\\w* \\W* \\S* \\s*]*\\s*\\)"
            );
}
