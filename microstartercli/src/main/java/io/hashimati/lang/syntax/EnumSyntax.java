package io.hashimati.lang.syntax;

import java.util.ArrayList;

public class EnumSyntax extends Syntax{

    private String name;
    private ArrayList<String> enums = new ArrayList<>();
    public EnumSyntax(String sentence) {
        super(sentence);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEnums() {
        return enums;
    }

    public void setEnums(ArrayList<String> enums) {
        this.enums = enums;
    }
}
