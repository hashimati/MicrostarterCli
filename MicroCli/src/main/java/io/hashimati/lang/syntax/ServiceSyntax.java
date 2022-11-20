package io.hashimati.lang.syntax;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */

import java.util.ArrayList;

public class ServiceSyntax extends Syntax{




    public  ServiceSyntax(String sentence){
        super(sentence);

    }
    private String name = "demo", language = "JAVA", build = "GRADLE", reactive = "reactor", framework = "micronaut";

    private String views;
    private String annotation;
    private String testFramework;
    private boolean graphql;

    private boolean grpc;
    private String port="8080";
    private String database = "h2",Package ="io.demo", databaseName = "demo", dao= "jdbc", jaxRS, tracing, cache, messaging, metrics, file;
    private String migrationTool = "none";
    private String awsSecret = "aws_secret", awsKey = "aws_key";
    private ArrayList<String> dependencies = new ArrayList<>();
    private ArrayList<String> features = new ArrayList<>();


    private SecuritySyntax securitySyntax;
    private ArrayList<EntitySyntax> entities = new ArrayList<>();

    private ArrayList<EnumSyntax> enums = new ArrayList<>();
    private ArrayList<RelationshipSyntax> relationships = new ArrayList<>();


    @Override
    public String toString() {
        return super.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getReactive() {
        return reactive;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getJaxRS() {
        return jaxRS;
    }

    public void setJaxRS(String jaxRS) {
        this.jaxRS = jaxRS;
    }

    public String getTracing() {
        return tracing;
    }

    public void setTracing(String tracing) {
        this.tracing = tracing;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getMessaging() {
        return messaging;
    }

    public void setMessaging(String messaging) {
        this.messaging = messaging;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setReactive(String reactive) {
        this.reactive = reactive;
    }

    public ArrayList<EntitySyntax> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<EntitySyntax> entities) {
        this.entities = entities;
    }

    public boolean isGraphql() {
        return graphql;
    }

    public void setGraphql(boolean graphql) {
        this.graphql = graphql;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(ArrayList<String> dependencies) {
        this.dependencies = dependencies;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }

    public String getTestFramework() {
        return testFramework;
    }

    public void setTestFramework(String testFramework) {
        this.testFramework = testFramework;
    }

    public String getMigrationTool() {
        return migrationTool;
    }

    public void setMigrationTool(String migrationTool) {
        this.migrationTool = migrationTool;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAwsKey() {
        return awsKey;
    }

    public void setAwsKey(String awsKey) {
        this.awsKey = awsKey;
    }

    public String getAwsSecret() {
        return awsSecret;
    }

    public void setAwsSecret(String awsSecret) {
        this.awsSecret = awsSecret;
    }

    public ArrayList<EnumSyntax> getEnums() {
        return enums;
    }

    public void setEnums(ArrayList<EnumSyntax> enums) {
        this.enums = enums;
    }

    public ArrayList<RelationshipSyntax> getRelationships() {
        return relationships;
    }

    public void setRelationships(ArrayList<RelationshipSyntax> relationships) {
        this.relationships = relationships;
    }

    public boolean isGrpc() {
        return grpc;
    }

    public void setGrpc(boolean grpc) {
        this.grpc = grpc;
    }

    public SecuritySyntax getSecuritySyntax() {
        return securitySyntax;
    }

    public void setSecuritySyntax(SecuritySyntax securitySyntax) {
        this.securitySyntax = securitySyntax;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}

