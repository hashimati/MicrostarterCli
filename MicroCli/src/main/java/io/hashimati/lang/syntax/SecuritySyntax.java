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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
}
