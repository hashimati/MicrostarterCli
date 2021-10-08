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
import java.util.List;
import lombok.Data;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Data
public class Feature {
    private String name,
    gradle,
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
    ArrayList<String> maven = new ArrayList<>();
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


    public List<Dependency> getMavenDependency()
    {

        return maven.stream().map(x-> getMavenDependency(x)).collect(Collectors.toList());

        //return getMavenDependency(this.maven);
    }

}
