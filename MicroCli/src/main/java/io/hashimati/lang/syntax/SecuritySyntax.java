package io.hashimati.lang.syntax;

import java.util.ArrayList;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
public class SecuritySyntax extends Syntax{

    public SecuritySyntax(String sentence) {
        super(sentence);
    }

    private String type;
    private ArrayList<String> roles = new ArrayList<>();

}
