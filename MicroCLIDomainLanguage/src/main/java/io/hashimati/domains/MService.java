package io.hashimati.domains;

import java.util.ArrayList;

public class MService {



    private ConfigurationInfo configurationInfo;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<EntityRelation> relations = new ArrayList<>();


    private String name;
    private String description;
    private String path;
    private String type; // AUTH, NORMAL, GATEWAY, DISCOVERY.


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
