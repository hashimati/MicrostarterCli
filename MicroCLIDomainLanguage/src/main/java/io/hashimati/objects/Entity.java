package io.hashimati.objects;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Entity {


    private String name, entityPackage,repoPackage, servicePackage,restPackage, clientPackage,exceptionPackage, exceptionHandlerPackage,
            graphqlpackage,

    reactiveFramework,


    //database type refer to the database
    //collectionname is refering to tht table name, entity name or collection name.
    databaseType, collectionName, databaseName; // SQL, Mongo, Cassandra, Neo4J;
    private boolean gorm;
    private String dialect;
    //possiblevalue = [jpa, jdbc, normal].
    private String frameworkType;

    private ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
    private int liquibaseSequence;

    private boolean isGraphQl;
    private boolean cached;
    private boolean micrometer;


    public Entity(String name) {
        this.name = name;
    }

    public Entity() {
    }

    public boolean isEmpty() {
        return name.isEmpty();
    }

    public char charAt(int index) {
        return name.charAt(index);
    }

    public int codePointAt(int index) {
        return name.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return name.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return name.codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return name.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        name.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    @Deprecated(since = "1.1")
    public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
        name.getBytes(srcBegin, srcEnd, dst, dstBegin);
    }

    public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
        return name.getBytes(charsetName);
    }

    public byte[] getBytes(Charset charset) {
        return name.getBytes(charset);
    }

    public byte[] getBytes() {
        return name.getBytes();
    }

    public boolean contentEquals(StringBuffer sb) {
        return name.contentEquals(sb);
    }

    public boolean contentEquals(CharSequence cs) {
        return name.contentEquals(cs);
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return name.equalsIgnoreCase(anotherString);
    }

    public int compareTo(String anotherString) {
        return name.compareTo(anotherString);
    }

    public int compareToIgnoreCase(String str) {
        return name.compareToIgnoreCase(str);
    }

    public boolean regionMatches(int toffset, String other, int ooffset, int len) {
        return name.regionMatches(toffset, other, ooffset, len);
    }

    public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
        return name.regionMatches(ignoreCase, toffset, other, ooffset, len);
    }

    public boolean startsWith(String prefix, int toffset) {
        return name.startsWith(prefix, toffset);
    }

    public boolean startsWith(String prefix) {
        return name.startsWith(prefix);
    }

    public boolean endsWith(String suffix) {
        return name.endsWith(suffix);
    }

    public int indexOf(int ch) {
        return name.indexOf(ch);
    }

    public int indexOf(int ch, int fromIndex) {
        return name.indexOf(ch, fromIndex);
    }

    public int lastIndexOf(int ch) {
        return name.lastIndexOf(ch);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return name.lastIndexOf(ch, fromIndex);
    }

    public int indexOf(String str) {
        return name.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return name.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return name.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return name.lastIndexOf(str, fromIndex);
    }

    public String substring(int beginIndex) {
        return name.substring(beginIndex);
    }

    public String substring(int beginIndex, int endIndex) {
        return name.substring(beginIndex, endIndex);
    }

    public CharSequence subSequence(int beginIndex, int endIndex) {
        return name.subSequence(beginIndex, endIndex);
    }

    public String concat(String str) {
        return name.concat(str);
    }

    public String replace(char oldChar, char newChar) {
        return name.replace(oldChar, newChar);
    }

    public boolean matches(String regex) {
        return name.matches(regex);
    }

    public boolean contains(CharSequence s) {
        return name.contains(s);
    }

    public String replaceFirst(String regex, String replacement) {
        return name.replaceFirst(regex, replacement);
    }

    public String replaceAll(String regex, String replacement) {
        return name.replaceAll(regex, replacement);
    }

    public String replace(CharSequence target, CharSequence replacement) {
        return name.replace(target, replacement);
    }

    public String[] split(String regex, int limit) {
        return name.split(regex, limit);
    }

    public String[] split(String regex) {
        return name.split(regex);
    }

    public String toLowerCase(Locale locale) {
        return name.toLowerCase(locale);
    }

    public String toLowerCase() {
        return name.toLowerCase();
    }

    public String toUpperCase(Locale locale) {
        return name.toUpperCase(locale);
    }

    public String toUpperCase() {
        return name.toUpperCase();
    }

    public String trim() {
        return name.trim();
    }

    public String strip() {
        return name.strip();
    }

    public String stripLeading() {
        return name.stripLeading();
    }

    public String stripTrailing() {
        return name.stripTrailing();
    }

    public boolean isBlank() {
        return name.isBlank();
    }

    public Stream<String> lines() {
        return name.lines();
    }

    public IntStream chars() {
        return name.chars();
    }

    public IntStream codePoints() {
        return name.codePoints();
    }

    public char[] toCharArray() {
        return name.toCharArray();
    }

    public String intern() {
        return name.intern();
    }

    public String repeat(int count) {
        return name.repeat(count);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getRepoPackage() {
        return repoPackage;
    }

    public void setRepoPackage(String repoPackage) {
        this.repoPackage = repoPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getRestPackage() {
        return restPackage;
    }

    public void setRestPackage(String restPackage) {
        this.restPackage = restPackage;
    }

    public String getClientPackage() {
        return clientPackage;
    }

    public void setClientPackage(String clientPackage) {
        this.clientPackage = clientPackage;
    }

    public String getExceptionPackage() {
        return exceptionPackage;
    }

    public void setExceptionPackage(String exceptionPackage) {
        this.exceptionPackage = exceptionPackage;
    }

    public String getExceptionHandlerPackage() {
        return exceptionHandlerPackage;
    }

    public void setExceptionHandlerPackage(String exceptionHandlerPackage) {
        this.exceptionHandlerPackage = exceptionHandlerPackage;
    }

    public String getGraphqlpackage() {
        return graphqlpackage;
    }

    public void setGraphqlpackage(String graphqlpackage) {
        this.graphqlpackage = graphqlpackage;
    }

    public String getReactiveFramework() {
        return reactiveFramework;
    }

    public void setReactiveFramework(String reactiveFramework) {
        this.reactiveFramework = reactiveFramework;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public boolean isGorm() {
        return gorm;
    }

    public void setGorm(boolean gorm) {
        this.gorm = gorm;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getFrameworkType() {
        return frameworkType;
    }

    public void setFrameworkType(String frameworkType) {
        this.frameworkType = frameworkType;
    }

    public ArrayList<EntityAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<EntityAttribute> attributes) {
        this.attributes = attributes;
    }

    public int getLiquibaseSequence() {
        return liquibaseSequence;
    }

    public void setLiquibaseSequence(int liquibaseSequence) {
        this.liquibaseSequence = liquibaseSequence;
    }

    public boolean isGraphQl() {
        return isGraphQl;
    }

    public void setGraphQl(boolean graphQl) {
        isGraphQl = graphQl;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean isMicrometer() {
        return micrometer;
    }

    public void setMicrometer(boolean micrometer) {
        this.micrometer = micrometer;
    }
}
