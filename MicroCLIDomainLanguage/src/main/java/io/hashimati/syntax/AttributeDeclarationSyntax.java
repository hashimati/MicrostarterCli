package io.hashimati.syntax;

import java.util.ArrayList;
import java.util.HashSet;

public class AttributeDeclarationSyntax extends Syntax{
    private String type,
    name;
    private HashSet<String> constraints = new HashSet<>();

    public AttributeDeclarationSyntax(String sentence) {
        super(sentence);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(HashSet<String> constraints) {
        this.constraints = constraints;
    }
}
