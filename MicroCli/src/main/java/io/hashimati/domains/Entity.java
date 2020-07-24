package io.hashimati.domains;

import io.hashimati.utils.Visitor;

import java.util.ArrayList;
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

    //database type refer to the database
    //collectionname is refering to tht table name, entity name or collection name.
    databaseType, collectionName, databaseName; // SQL, Mongo, Cassandra, Neo4J;

    private String dialect;
    //possiblevalue = [jpa, jdbc, normal].
    private String frameworkType;

    private ArrayList<EntityAttribute> attributes = new ArrayList<EntityAttribute>();


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
                ", databaseType='" + databaseType + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", attributes=" + attributes +
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

}
