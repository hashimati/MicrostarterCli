package io.hashimati.microcli.domains;

/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hashimati.microcli.constants.FieldTypes;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.Visitor;
import io.micronaut.core.naming.NameUtils;
import io.micronaut.core.util.StringUtils;
import org.checkerframework.checker.i18nformatter.qual.I18nChecksFormat;

import static io.hashimati.microcli.constants.ProjectConstants.EntityAttributeType.*;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

public class EntityAttribute {
    private String name, type, typePackage;
    private boolean premetive =true;
    private boolean enumuration = false;

    private boolean array=false;
    private boolean jdbc;
    private boolean jpa;

    private boolean findByMethod;
    private boolean findAllMethod;
    private boolean findByUpdate;

    private String backendDataRun;

    private EntityConstraints constraints;

    private boolean isFile;

    private FieldTypes fieldType;
    public EntityAttribute()
    {}
    @JsonIgnore
    public boolean isString(){

        return this.type.toString().trim().equalsIgnoreCase(STRING);
    }
    @JsonIgnore
    public boolean isInteger(){
        return this.type.toString().trim().equalsIgnoreCase(INTEGER);

    }
    @JsonIgnore
    public boolean isDate(){
        return this.type.toString().trim().equalsIgnoreCase(DATE);

    }

    @JsonIgnore
    public boolean isBigDecimal(){
        return this.type.toString().trim().equalsIgnoreCase(BIG_DECIMAL);

    }
    @JsonIgnore
    public boolean isDouble(){
        return this.type.toString().trim().equalsIgnoreCase(DOUBLE);

    }
    @JsonIgnore
    public boolean isFloat(){

        return this.type.toString().trim().equalsIgnoreCase(FLOAT);

    }
    @JsonIgnore
    public boolean isByte(){
        return this.type.toString().trim().equalsIgnoreCase(BYTE);

    }
    @JsonIgnore
    public boolean isShort(){
        return this.type.toString().trim().equalsIgnoreCase(SHORT);

    }
    @JsonIgnore
    public boolean isLong(){
        return this.type.toString().trim().equalsIgnoreCase(LONG);

    }
    @JsonIgnore
    public boolean isChar(){
        return this.type.toString().trim().equalsIgnoreCase(CHAR);

    }
    @JsonIgnore
    public boolean isBigInteger()
    {
        return this.type.toString().trim().equalsIgnoreCase(BIG_INTEGER);

    }
    @JsonIgnore
    public boolean isBoolean()
    {
        return this.type.toString().trim().equalsIgnoreCase(BOOLEAN);

    }
    @JsonIgnore
    public boolean isBigDouble()
    {
        return this.type.toString().trim().equalsIgnoreCase(BIG_DECIMAL);

    }
    @JsonIgnore
    public boolean isClass()
    {
        return !isString()
                || !isPermetiveDataType();
    }
    @JsonIgnore
    private boolean isPermetiveDataType() {
        return isByte() || isInteger() || isShort() || isChar() || isDouble() || isFloat();
    }

    @JsonIgnore
    public  boolean isFile()
    {
        return isFile;

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

    public String getFinalStaticDeclaration(String lang, String value)
    {
        switch (lang){
            case GROOVY_LANG:
                return "public static final "+ type + " "+ name +" = "+value + "\n\t";
            case KOTLIN_LANG:
                return "const val "+ type + " "+ name +" = "+value + "\n\t";
            case JAVA_LANG:
            default:
                return "public static final "+ type + " "+ name + " = "+value +";\n\t";
        }
    }

    public String getDeclaration(String lang, boolean isRecord)
    {
        String constraintsDeclaration="";


        if(lang.equalsIgnoreCase(JAVA_LANG) && isRecord){
            lang = lang+"Record";
        }
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
                collection = (lang.equalsIgnoreCase(KOTLIN_LANG)) ? "MutableSet" :"Set";
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
            default:
                return ((jpa && array)?elementCollectionAnnotation:"") +type + " " + name +" ";
        }
        //  return "";
    }

    @JsonIgnore

    public String getGetterMethodImpl(){
        return
                String.format("\tpublic %s get%s() { return %s; }\n", this.getType(), NameUtils.capitalize(this.getName()), name);
    }
    @JsonIgnore
    public String getSetterMethodImpl(){
        return String.format("\tpublic void set%s(%s %s) { this.%s = %s; }\n", NameUtils.capitalize(name), getType(), name, name, name);
    }

    public String inConstructorInstantiation(){
        return String.format("\t\tthis.%s = %s;\n",name, name);
    }

    @JsonIgnore
    public String getNormalDeclaration()
    {
        return String.format("%s %s", type, name);

    }
    @JsonIgnore
    public String getEqualsObject(String compare){
        return String.format(" Objects.equals(%s, %s.%s)", name, compare, name);
    }


    @JsonIgnore
    public String graphQLDeclaration()
    {
        String t = type;
        //return String in case of enum. 
        t = DataTypeMapper.graphqlMapper.containsKey(type) ? DataTypeMapper.graphqlMapper.get(type) : "String";
        return new StringBuilder().append("\t").append(name).append(" : ").append(t).append(",\n").toString();
    }
    @JsonIgnore
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

    public boolean isEnumuration() {
        return enumuration;
    }

    public void setEnumuration(boolean enumuration) {
        this.enumuration = enumuration;
    }

    public boolean isFindByMethod() {
        return findByMethod;
    }

    public void setFindByMethod(boolean findByMethod) {
        this.findByMethod = findByMethod;
    }

    public boolean isFindAllMethod() {
        return findAllMethod;
    }

    public void setFindAllMethod(boolean findAllMethod) {
        this.findAllMethod = findAllMethod;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
