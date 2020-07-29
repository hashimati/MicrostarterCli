package io.hashimati.config;

import io.hashimati.domains.EntityConstraints;
import io.hashimati.utils.Visitor;
import lombok.Data;

@Data
public class Feature {

    private String name,
    maven, gradle,
    annotationMaven,
    annotationGradle,
    testGradle,
    testGradleAnnotation;


    public Feature visit(Visitor<Feature> visitor)
    {
        return visitor.visit(this);
    }
}
