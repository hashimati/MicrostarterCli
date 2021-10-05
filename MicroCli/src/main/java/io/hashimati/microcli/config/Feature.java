package io.hashimati.microcli.config;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.Visitor;

import lombok.Data;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Feature {

    private String name,
    maven, gradle,
    annotationMaven,
    annotationGradle,
    testGradle,
    testMaven,
    testGradleAnnotation,
    versionProperties,
    testContainerGradle,
    depndencyManagement,
    ymlConfig,
    rdbcGradle, // for database relational dbs only
    rdbcMaven,  // for relational dbs only
    testRdbcGradle,
    testRdbcMaven,
    gradleTask;
    ArrayList<String> gradlePlugins = new ArrayList<>();
    private Plugin plugin;
    private HashMap<String, String> mavenProperties = new HashMap<>();



    public Feature visit(Visitor<Feature> visitor)
    {
        return visitor.visit(this);
    }


    public Dependency getMavenDependency(String maven)
    {
        Dependency dependency = new Dependency();
        dependency.setGroupId(GeneratorUtils.getValueBetweenTag("groupId", maven));
        dependency.setArtifactId(GeneratorUtils.getValueBetweenTag("artifactId", maven));
        dependency.setScope(GeneratorUtils.getValueBetweenTag("scope", maven));
        if(maven.contains("version"))
            dependency.setVersion(GeneratorUtils.getValueBetweenTag("version", maven));
        if(maven.contains("type"))
            dependency.setType(GeneratorUtils.getValueBetweenTag("type", maven));
        return dependency;
    }


    public Dependency getMavenDependency()
    {
        return getMavenDependency(this.maven);
    }

}
