package io.hashimati.microcli.domains;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hashimati.microcli.utils.Visitor;
import io.micronaut.core.naming.NameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

//@Data
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
//@Builder

public class Entity
{

    private String name,idType, entityPackage,repoPackage, servicePackage,restPackage, clientPackage,exceptionPackage, exceptionHandlerPackage, microstreamPackage,
    graphqlpackage, functionPackage, lambdaPackage, oraclePackage, azurePackage, googlePackage,





    reactiveFramework,


    //database type refer to the database
    //collectionname is refering to tht table name, entity name or collection name.
    databaseType, collectionName, databaseName; // SQL, Mongo, Cassandra, Neo4J;

    private boolean isLombok = false;
    private boolean gorm;
    private String dialect;
    //possiblevalue = [jpa, jdbc, normal].
    private String frameworkType;


    private short microstreamChannelCount = 4;
    private boolean jaxRs;
    private boolean spring;
    private boolean micronaut;
    private boolean pageable;
    private ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
    private int liquibaseSequence;
    private boolean nonBlocking;
    private boolean mnData;
    private boolean isGraphQl;
    private boolean cached;
    private boolean micrometer;
    private boolean tracingEnabled;
    private boolean securityEnabled;
    private String securityStrategy;
    private boolean noEndpoints;
    private String javaVersion;
    private boolean javaRecord;
    private String fileServiceType; // filesystem, aws, gcp, azure
    private String microstreamRoot, microstreamRootClass, microstreamPath;
    private HashMap<String, HashSet<String>> updateByMethods = new HashMap<String, HashSet<String>>();
    private ArrayList<URL> urls = new ArrayList<>();

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

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
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


    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public ArrayList<EntityAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<EntityAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", entityPackage='" + entityPackage + '\'' +
                ", repoPackage='" + repoPackage + '\'' +
                ", servicePackage='" + servicePackage + '\'' +
                ", restPackage='" + restPackage + '\'' +
                ", clientPackage='" + clientPackage + '\'' +
                ", exceptionPackage='" + exceptionPackage + '\'' +
                ", exceptionHandlerPackage='" + exceptionHandlerPackage + '\'' +
                ", graphqlpackage='" + graphqlpackage + '\'' +
                ", functionPackage='" + functionPackage + '\'' +
                ", lambdaPackage='" + lambdaPackage + '\'' +
                ", oraclePackage='" + oraclePackage + '\'' +
                ", azurePackage='" + azurePackage + '\'' +
                ", googlePackage='" + googlePackage + '\'' +
                ", reactiveFramework='" + reactiveFramework + '\'' +
                ", databaseType='" + databaseType + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", gorm=" + gorm +
                ", dialect='" + dialect + '\'' +
                ", frameworkType='" + frameworkType + '\'' +
                ", attributes=" + attributes +
                ", liquibaseSequence=" + liquibaseSequence +
                ", isGraphQl=" + isGraphQl +
                ", cached=" + cached +
                ", micrometer=" + micrometer +
                ", tracingEnabled=" + tracingEnabled +
                '}';
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getClientPackage() {
        return clientPackage;
    }

    public void setClientPackage(String clientPackage) {
        this.clientPackage = clientPackage;
    }

    public String getFrameworkType() {
        return frameworkType;
    }

    public void setFrameworkType(String frameworkType) {
        this.frameworkType = frameworkType;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
            Entity entity = (Entity) o;
        return name.equals(entity.name) &&
                entityPackage.equals(entity.entityPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, entityPackage);
    }



    public Entity visit(Visitor<Entity> visitor)
    {
        return visitor.visit(this);
    }

    public void setPackages(String defaultPackage) {

        this.setEntityPackage(new StringBuilder().append(defaultPackage).append(".domains").toString());
        this.setRepoPackage(new StringBuilder().append(defaultPackage).append(".repositories").toString());
        this.setServicePackage(new StringBuilder().append(defaultPackage).append(".services").toString());
        this.setRestPackage(new StringBuilder().append(defaultPackage).append(".controllers").toString());
        this.setMicrostreamPackage(new StringBuilder().append(defaultPackage).append(".microstream").toString());
        this.setClientPackage(new StringBuilder().append(defaultPackage).append(".clients").toString());
        this.setGraphqlpackage(new StringBuilder().append(defaultPackage).append(".graphqls").toString());
        this.setFunctionPackage(new StringBuilder().append(defaultPackage).append(".function").toString());
        this.setLambdaPackage(String.format("%s.aws", getFunctionPackage()));

        setOraclePackage(String.format("%s.oracle", getFunctionPackage()));
        setGooglePackage(String.format("%s.google", getFunctionPackage()));
        setAzurePackage((String.format("%s.azure", getFunctionPackage())));
    }
    @JsonIgnore
    public String getEntitiesImport(String defaultPackage)
    {
        if(this.getAttributes().isEmpty())
        {
            return "";
        }
        return this.getAttributes().stream().map(

                x->{
                    if(x.isEnumuration())
                return new StringBuilder().append("import ").append(defaultPackage).append(".enums.").append(x.getType()).append(";").toString();
                    else return "";
                }
        ).reduce((x, y) -> x + "\n"+y).get();
    }

    public String getGraphqlpackage() {
        return graphqlpackage;
    }

    public void setGraphqlpackage(String graphqlpackage) {
        this.graphqlpackage = graphqlpackage;
    }

    public boolean isGraphQl() {
        return isGraphQl;
    }


    public void setGraphQl(boolean graphQl) {
        isGraphQl = graphQl;
    }

    public int getLiquibaseSequence() {
        return liquibaseSequence;
    }

    public void setLiquibaseSequence(int liquibaseSequence) {
        this.liquibaseSequence = liquibaseSequence;
    }

    public boolean isGorm() {
        return gorm;
    }

    public void setGorm(boolean gorm) {
        this.gorm = gorm;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public String getReactiveFramework() {
        return reactiveFramework;
    }

    public void setReactiveFramework(String reactiveFramework) {
        this.reactiveFramework = reactiveFramework;
    }

    public boolean isMicrometer() {
        return micrometer;
    }

    public void setMicrometer(boolean micrometer) {
        this.micrometer = micrometer;
    }

    public boolean isTracingEnabled() {
        return tracingEnabled;
    }

    public void setTracingEnabled(boolean tracingEnabled) {
        this.tracingEnabled = tracingEnabled;
    }

    public String getFunctionPackage() {
        return functionPackage;
    }

    public void setFunctionPackage(String functionPackage) {
        this.functionPackage = functionPackage;
    }

    public String getLambdaPackage() {
        return lambdaPackage;
    }

    public void setLambdaPackage(String lambdaPackage) {
        this.lambdaPackage = lambdaPackage;
    }

    public String getOraclePackage() {
        return oraclePackage;
    }

    public void setOraclePackage(String oraclePackage) {
        this.oraclePackage = oraclePackage;
    }

    public String getAzurePackage() {
        return azurePackage;
    }

    public void setAzurePackage(String azurePackage) {
        this.azurePackage = azurePackage;
    }

    public String getGooglePackage() {
        return googlePackage;
    }

    public void setGooglePackage(String googlePackage) {
        this.googlePackage = googlePackage;
    }

    public HashMap<String, HashSet<String>> getUpdateByMethods() {
        return updateByMethods;
    }

    public void setUpdateByMethods(HashMap<String, HashSet<String>> updateByMethods) {
        this.updateByMethods = updateByMethods;
    }

    public EntityAttribute getAttributeByName(String name){
        return attributes.stream().filter(x->x.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean isMnData() {
        return mnData;
    }

    public void setMnData(boolean mnData) {
        this.mnData = mnData;
    }

    public void setNoEndpoints(boolean noEndpoints) {
        this.noEndpoints = noEndpoints;
    }

    public boolean isNoEndpoints() {
        return noEndpoints;
    }

    public boolean isSecurityEnabled() {
        return securityEnabled;
    }

    public void setSecurityEnabled(boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    public String getSecurityStrategy() {
        return securityStrategy;
    }

    public void setSecurityStrategy(String securityStrategy) {
        this.securityStrategy = securityStrategy;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public boolean isJavaRecord() {
        return javaRecord;
    }

    public void setJavaRecord(boolean javaRecord) {
        this.javaRecord = javaRecord;
    }

    public ArrayList<URL> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<URL> urls) {
        this.urls = urls;
    }

    public boolean isJaxRs() {
        return jaxRs;
    }

    public void setJaxRs(boolean jaxRs) {
        this.jaxRs = jaxRs;
    }

    public boolean isPageable() {
        return pageable;
    }

    public void setPageable(boolean pageable) {
        this.pageable = pageable;
    }

    public boolean isNonBlocking() {
        return nonBlocking;
    }

    public void setNonBlocking(boolean nonBlocking) {
        this.nonBlocking = nonBlocking;
    }

    public String getFileServiceType() {
        return fileServiceType;
    }

    public void setFileServiceType(String fileServiceType) {
        this.fileServiceType = fileServiceType;
    }

    public String getMicrostreamRoot() {
        return microstreamRoot;
    }

    public void setMicrostreamRoot(String microstreamRoot) {
        this.microstreamRoot = microstreamRoot;
    }

    public String getMicrostreamRootClass() {
        return microstreamRootClass;
    }

    public void setMicrostreamRootClass(String microstreamRootClass) {
        this.microstreamRootClass = microstreamRootClass;
    }

    public String getMicrostreamPath() {
        return microstreamPath;
    }

    public void setMicrostreamPath(String microstreamPath) {
        this.microstreamPath = microstreamPath;
    }

    public String getMicrostreamPackage() {
        return microstreamPackage;
    }

    public void setMicrostreamPackage(String microstreamPackage) {
        this.microstreamPackage = microstreamPackage;
    }

    public short getMicrostreamChannelCount() {
        return microstreamChannelCount;
    }

    public void setMicrostreamChannelCount(short microstreamChannelCount) {
        this.microstreamChannelCount = microstreamChannelCount;
    }

    public boolean isLombok() {
        return isLombok;
    }

    public void setLombok(boolean lombok) {
        isLombok = lombok;
    }

    @JsonIgnore
    public String getEmptyConstructor(){
        return String.format("\tpublic %s(){\t}", name);
    }

    @JsonIgnore
    public String getAllArgsConstructor(){
        if(attributes.isEmpty()) return "";

        String parameters = "";
        if(!isNoEndpoints())
         parameters = attributes.stream().map(x->x.getNormalDeclaration()).reduce((x, y) -> x + ", "+y).orElse("");
        else
            parameters =
                    attributes.stream().filter(x->!x.getName().equals("id")).map(x->x.getNormalDeclaration()).reduce((x, y) -> x + ", "+y).orElse("");

        String body = "";
        if(!isNoEndpoints())
            body = attributes.stream().map(x->x.inConstructorInstantiation()).reduce((x, y) ->x+y).orElse("");
        else
            body = attributes.stream().filter(x->!x.getName().equals("id")).map(x->x.inConstructorInstantiation()).reduce((x, y) ->x+y).orElse("");

        return String.format("\tpublic %s(%s){\n%s\t}", name, parameters, body);
    }
    @JsonIgnore
    public String getEqualMethods()
    {
        String o = NameUtils.camelCase(name);
        String equalExpression = "";
        if(!isNoEndpoints())
            equalExpression = attributes.stream().map(x->x.getEqualsObject(o)).reduce((x, y)-> x + " && "+ y).orElse("true");
        else
            equalExpression = attributes.stream().filter(x->!x.getName().equals("id")).map(x->x.getEqualsObject(o)).reduce((x, y)-> x + " && "+ y).orElse("true");

        return String.format("\t@Override\n\tpublic boolean equals(Object o) {\n\t\tif (this == o) return true;\n\t\tif (!(o instanceof %s)) return false;\n\t\t %s %s = (%s) o;\n\t\treturn %s;\n\t}", name, name, o, name, equalExpression);
    }
    @JsonIgnore
    public String getHashCode(){

        String attrbs = "";
        if(!isNoEndpoints())
            attrbs = attributes.stream().map(x ->x.getName()).reduce((x,y) -> x + ", " + y).orElse("");
        else
            attrbs = attributes.stream().filter(x->!x.getName().equals("id")).map(x ->x.getName()).reduce((x,y) -> x + ", " + y).orElse("");

        String result = attrbs.isEmpty()?"0": String.format("Objects.hash(%s)", attrbs);
        return String.format("\t@Override\n\tpublic int hashCode() {\n\t\treturn %s;\n\t}", result);
    }
    @JsonIgnore
    public String getGetters() {
        String result = "";

        if(!isNoEndpoints())
            result = attributes.stream().map(x -> x.getGetterMethodImpl()).reduce((x, y) -> x + "\n" + y).orElse("");

        else
            result = attributes.stream().filter(x->!x.getName().equals("id")).map(x -> x.getGetterMethodImpl()).reduce((x, y) -> x + "\n" + y).orElse("");

        if (this.isMnData() || this.getDatabaseType().equalsIgnoreCase("mongodb"))
        {
            result += "\n\tpublic Date getDateCreated() {\n" +
                    "\t\treturn dateCreated;\n" +
                    "\t}\n\tpublic Date getDateUpdated() {\n" +
                    "\t\treturn dateUpdated;\n" +
                    "\t}\n";
        }
        return result;
    }

    @JsonIgnore
    public String getSetters(){
        String result = "";

        if(!isNoEndpoints())
            result = attributes.stream().map(x -> x.getSetterMethodImpl()).reduce((x, y) -> x + "\n" + y).orElse("");

        else
            result = attributes.stream().filter(x->!x.getName().equals("id")).map(x -> x.getSetterMethodImpl()).reduce((x, y) -> x + "\n" + y).orElse("");
        if(this.isMnData() || this.getDatabaseType().equalsIgnoreCase("mongodb"))
        {
            result += "\n\tpublic void setDateCreated(Date dateCreated) {\n" +
                    "\t\tthis.dateCreated = dateCreated;\n" +
                    "\t}\n\tpublic void setDateUpdated(Date dateUpdated) {\n" +
                    "\t\tthis.dateUpdated = dateUpdated;\n" +
                    "\t}";
        }
        return result;
    }


    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
        attributes.add(new EntityAttribute(){{
            setType(idType);
            setName("id");

        }});
    }

    public boolean isSpring() {
        return spring;
    }

    public void setSpring(boolean spring) {
        this.spring = spring;
    }

    public boolean isMicronaut() {
        return micronaut;
    }

    public void setMicronaut(boolean micronaut) {
        this.micronaut = micronaut;
    }
}
