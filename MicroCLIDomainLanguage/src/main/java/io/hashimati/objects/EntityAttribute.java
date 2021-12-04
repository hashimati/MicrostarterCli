package io.hashimati.objects;

public class EntityAttribute {
    private String name, type, typePackage;
    private boolean premetive = true;
    private boolean enumuration = false;

    private boolean array = false;
    private boolean jdbc;
    private boolean jpa;

    private String backendDataRun;

    private EntityConstraints constraints;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(String typePackage) {
        this.typePackage = typePackage;
    }

    public boolean isPremetive() {
        return premetive;
    }

    public void setPremetive(boolean premetive) {
        this.premetive = premetive;
    }

    public boolean isEnumuration() {
        return enumuration;
    }

    public void setEnumuration(boolean enumuration) {
        this.enumuration = enumuration;
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public boolean isJdbc() {
        return jdbc;
    }

    public void setJdbc(boolean jdbc) {
        this.jdbc = jdbc;
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
                ", enumuration=" + enumuration +
                ", array=" + array +
                ", jdbc=" + jdbc +
                ", jpa=" + jpa +
                ", backendDataRun='" + backendDataRun + '\'' +
                ", constraints=" + constraints +
                '}';
    }
}