package io.hashimati.lang.utils;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PatternUtils {

    public static List<String> getPatternsFromText(String pattern, String text){

        return Pattern.compile(pattern)
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }
}