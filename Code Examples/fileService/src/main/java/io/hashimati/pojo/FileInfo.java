package io.hashimati.pojo;

import java.util.Objects;

public class FileInfo {

    private String id;
    private String name;
    private String path;

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileInfo(String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }
}
