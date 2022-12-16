package io.hashimati.lang.syntax;

import java.util.ArrayList;
import java.util.HashSet;

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
    private boolean propagate ;
    private HashSet<String> services = new HashSet<>();
    private HashSet<String> roles = new HashSet<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<String> roles) {
        this.roles = roles;
    }



    public HashSet<String> getServices() {
        return services;
    }

    public void setServices(HashSet<String> services) {
        this.services = services;
    }

    public boolean isPropagate() {
        return propagate;
    }

    public void setPropagate(boolean propagate) {
        this.propagate = propagate;
    }
}
