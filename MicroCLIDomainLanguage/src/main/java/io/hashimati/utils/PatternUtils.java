package io.hashimati.utils;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PatternUtils {

    public static List<String> getPatternsFromtext(String pattern, String text){

        return Pattern.compile(pattern)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}