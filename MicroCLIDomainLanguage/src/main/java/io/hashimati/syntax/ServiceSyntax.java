package io.hashimati.syntax;

public class ServiceSyntax extends Syntax{




    public  ServiceSyntax(String sentence){
        super(sentence);

    }
    private String name, language, build, reactive;

    private String port;
    private String database,Package, databaseName, jaxRS, tracing, cache, messaging, metrics, file;


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
}

