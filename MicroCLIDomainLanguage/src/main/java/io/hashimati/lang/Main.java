package io.hashimati.lang;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import io.hashimati.lang.parsers.utils.Validator;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {


    public static void main(String... args)
    {
        System.out.println(new Validator().isBracketBalanced("{[{}]}"));


        Pattern p = Pattern.compile("\\s*entity\\s*\\w*\\{[\\s* ^\\} ^\\{ ^\\! ^\\@ ^\\# ^\\$ ^\\% ^\\^ ^\\& ^\\* ^\\( ^\\) ^\\[ ^\\]  \\w* \\:]*\\s*\\}");
        Matcher matcher = p.matcher("   entity hello{ \nhello    :    Str   ing } \n entity ahmed{}");


        List<String> list  = matcher.results().map(MatchResult::group).collect(Collectors.toList());
        System.out.println(list.size());
        list.forEach(System.out::println);
    }
}
