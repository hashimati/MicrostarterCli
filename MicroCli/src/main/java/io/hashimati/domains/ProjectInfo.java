package io.hashimati.domains;

import io.hashimati.utils.Visitor;
import lombok.Data;

import java.util.HashSet;

@Data
public class ProjectInfo {

    private String applicationType,
    defaultPackage,
    testFramework,
    sourceLanguage,
    buildTool;

    private HashSet<String> features;
    public ProjectInfo visit(Visitor<ProjectInfo> visitor)
    {
        return visitor.visit(this);
    }
}
