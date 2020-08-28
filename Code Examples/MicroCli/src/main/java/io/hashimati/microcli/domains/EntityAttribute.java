package io.hashimati.microcli.domains;


import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.Visitor;
import io.micronaut.core.util.StringUtils;

import static io.hashimati.microcli.constants.ProjectConstants.EntityAttributeType.*;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

public class EntityAttribute {
    private String name, type, typePackage;
    private boolean premetive =true;

    private boolean array=false;
    private boolean jdbc;
    private boolean jpa;

    private String backendDataRun;
    private EntityConstraints constraints;

    public EntityAttribute()
    {}
    public boolean isString(){

        return this.type.toString().trim().equalsIgnoreCase(STRING);
    }
    public boolean isInteger(){
        return this.type.toString().trim().equalsIgnoreCase(INTEGER);

    }
    public boolean isDate(){
        return this.type.toString().trim().equalsIgnoreCase(DATE);

    }
    public boolean isDouble(){
        return this.type.toString().trim().equalsIgnoreCase(DOUBLE);

    }
    public boolean isFloat(){

        return this.type.toString().trim().equalsIgnoreCase(FLOAT);

    }
    public boolean isByte(){
        return this.type.toString().trim().equalsIgnoreCase(BYTE);

    }
    public boolean isShort(){
        return this.type.toString().trim().equalsIgnoreCase(SHORT);

    }
    public boolean isLong(){
        return this.type.toString().trim().equalsIgnoreCase(LONG);

    }
    public boolean isChar(){
        return this.type.toString().trim().equalsIgnoreCase(CHAR);

    }
    public boolean isBigInteger()
    {
        return this.type.toString().trim().equalsIgnoreCase(BIG_INTEGER);

    }
    public boolean isBoolean()
    {
        return this.type.toString().trim().equalsIgnoreCase(BOOLEAN);

    }
    public boolean isBigDouble()
    {
        return this.type.toString().trim().equalsIgnoreCase(BIG_DECIMAL);

    }
    public boolean isClass()
    {
        return !isString()
                || !isPermetiveDataType();
    }

    private boolean isPermetiveDataType() {
        return isByte() || isInteger() || isShort() || isChar() || isDouble() || isFloat();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(String typePackage) {
        this.typePackage = typePackage;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType()
    {
        return this.type;
    }


    public String getDeclaration(String lang)
    {
        String constraintsDeclaration="";
//        if(constraints != null)
//        {
//            if(constraints.isEnabled())
//            {
//                if(isString())
//                {
//                    if(!constraints.isNullable())
//                    {
//                        constraintsDeclaration +="\t" +constraints.getNotNullExpression();
//
//                    }
//                    if(constraints.getMaxSize()!=null && constraints.getMinSize() !=null)
//                    {
//                        constraintsDeclaration +=  constraints.getSizeExpression();
//
//                    }
//                    if(constraints.getPattern() != null)
//                    {
//                        constraintsDeclaration +="\t" + constraints.getPatternExpression();
//
//                    }
//                    if(constraints.isEmail())
//                    {
//                        constraintsDeclaration += constraints.getEmailExpression();
//                    }
//                    if(constraints.isNotBlank())
//                    {
//                        constraintsDeclaration += constraints.getNotBlankExpression() ;
//
//                    }
//                }
//
//                else if(isInteger())
//                {
//                    if(constraints.getMin() != null)
//                    {
//                        constraintsDeclaration += constraints.getMinExpression();
//                    }
//                    if(constraints.getMax() != null) {
//                        constraintsDeclaration += constraints.getMaxExpression();
//                    }
//                }
//
//            }
//
//        }


        String elementCollectionAnnotation = "\t@ElementCollection(fetch = FetchType.EAGER)\n";
        if(array)
        {

            type = DataTypeMapper.wrapperMapper.getOrDefault(type.toLowerCase(), type);
            if(lang.equalsIgnoreCase(KOTLIN_LANG))
            {
                type = StringUtils.capitalize(type);
            }


            String collection;
            if(jdbc)
                collection = (lang.equalsIgnoreCase(KOTLIN_LANG)) ? "MutableSetx" :"Set";
            else
                collection = collection=(lang.equalsIgnoreCase(KOTLIN_LANG)) ? "MutableList" :"List";

            type = collection +"<" +type +">";
        }
        switch (lang)
        {
            case JAVA_LANG:

                return ((jpa && array)?elementCollectionAnnotation:"")+"\tprivate " +type + " " + name +";\n";


            case GROOVY_LANG:
                return ((jpa && array)?elementCollectionAnnotation:"")+"\t" +type + " " + name +";\n";
            case KOTLIN_LANG:
                return ((jpa && array)?elementCollectionAnnotation:"")+"\t" +"var" + " " + name + ":" + StringUtils.capitalize(type) +"\n";
        }
        return "";
    }
    public String getPackageSyntax(String lang)
    {
        if(this.getType().equalsIgnoreCase(BIG_DECIMAL))
        {
            return "import " + BIG_DECIMAL_CLASS + ";";
        }
        if(this.getType().equalsIgnoreCase(BIG_DECIMAL))
        {
            return "import " +BIG_INTEGER_CLASS + ";";
        }
        switch (lang)
        {
            case JAVA_LANG:
            case GROOVY_LANG:
            case KOTLIN_LANG:
                return "import " + getTypePackage() + ";";
        }
        return "";
    }

    public boolean isPremetive() {
        return premetive;
    }

    public void setPremetive(boolean premetive) {
        this.premetive = premetive;
    }

    public EntityConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(EntityConstraints constraints) {
        this.constraints = constraints;
    }

    @Override
    public String toString() {
        return "EntityAttribute{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", typePackage='" + typePackage + '\'' +
                ", premetive=" + premetive +
                ", array=" + array +
                ", constraints=" + constraints +
                '}';
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public boolean isJpa() {
        return jpa;
    }

    public void setJpa(boolean jpa) {
        this.jpa = jpa;
    }


    public String getBackendDataRun() {
        return backendDataRun;
    }

    public void setBackendDataRun(String backendDataRun) {
        this.backendDataRun = backendDataRun;
    }

    public boolean isJdbc() {
        return jdbc;
    }

    public void setJdbc(boolean jdbc) {
        this.jdbc = jdbc;
    }
    public EntityAttribute visit(Visitor<EntityAttribute> visitor)
    {
        return visitor.visit(this);
    }
}
