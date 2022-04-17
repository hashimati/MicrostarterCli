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

    private String name, entityPackage,repoPackage, servicePackage,restPackage, clientPackage,exceptionPackage, exceptionHandlerPackage,
    graphqlpackage, functionPackage, lambdaPackage, oraclePackage, azurePackage, googlePackage,





    reactiveFramework,


    //database type refer to the database
    //collectionname is refering to tht table name, entity name or collection name.
    databaseType, collectionName, databaseName; // SQL, Mongo, Cassandra, Neo4J;
    private boolean gorm;
    private String dialect;
    //possiblevalue = [jpa, jdbc, normal].
    private String frameworkType;

    private boolean jaxRs;
    private boolean pageable;
    private ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();
    private int liquibaseSequence;

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
}
