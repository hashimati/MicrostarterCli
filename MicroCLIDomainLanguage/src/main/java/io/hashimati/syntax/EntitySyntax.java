package io.hashimati.syntax;

import java.util.ArrayList;

public class EntitySyntax extends Syntax{

    public  EntitySyntax(String sentence){
        super(sentence);
    }
    private String name;
    private ArrayList<String> attributesDeclarationsStr = new ArrayList<>();

    private ArrayList<AttributeDeclarationSyntax> attributesDeclarations = new ArrayList<>();


    private boolean pagination;
    private boolean records;
    private boolean noendpoints;

    private boolean isValid;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getAttributesDeclarationsStr() {
        return attributesDeclarationsStr;
    }

    public void setAttributesDeclarationsStr(ArrayList<String> attributesDeclarationsStr) {
        this.attributesDeclarationsStr = attributesDeclarationsStr;
    }

    public boolean isPagination() {
        return pagination;
    }

    public void setPagination(boolean pagination) {
        this.pagination = pagination;
    }

    public boolean isRecords() {
        return records;
    }

    public void setRecords(boolean records) {
        this.records = records;
    }

    public boolean isNoendpoints() {
        return noendpoints;
    }

    public void setNoendpoints(boolean noendpoints) {
        this.noendpoints = noendpoints;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public String toString() {
        return super.toString();

    }

    public ArrayList<AttributeDeclarationSyntax> getAttributesDeclarations() {
        return attributesDeclarations;
    }

    public void setAttributesDeclarations(ArrayList<AttributeDeclarationSyntax> attributesDeclarations) {
        this.attributesDeclarations = attributesDeclarations;
    }
}
