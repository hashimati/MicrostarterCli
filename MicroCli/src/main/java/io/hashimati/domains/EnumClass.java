package io.hashimati.domains;

import io.hashimati.utils.Visitor;

import java.util.HashSet;


public class EnumClass {

    private String name;
    private HashSet<String> values;

    private String enumPackage;
    public HashSet<String> getValues() {
        return values;
    }

    public void setValues(HashSet<String> values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "EnumClass{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }

    public String getEnumPackage() {
        return enumPackage;
    }

    public void setEnumPackage(String enumPackage) {
        this.enumPackage = enumPackage;
    }


    public EnumClass visit(Visitor<EnumClass> visitor)
    {
        return visitor.visit(this);
    }
}
