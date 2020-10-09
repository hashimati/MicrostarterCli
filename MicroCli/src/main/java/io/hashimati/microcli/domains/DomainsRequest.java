package io.hashimati.microcli.domains;
/**
 * @author Ahmed Al Hashmi
 */
import io.hashimati.microcli.utils.Visitor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class DomainsRequest {

    private String group, artifact,
            version, build, language, viewFramework,
            javaVersion, profile, testframework;
    private String  databaseName, databaseType;
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<EntityRelation> entityRelations = new ArrayList<>();

    private ArrayList<EnumClass> enumClasses = new ArrayList<>();
    private SecurityModel security;
    private HashMap<String,String> erMapper = new HashMap<>();
    private String dataBackend;
    public String getPackage() {
        return getGroup()+"." + getArtifact().toLowerCase();

    }

    public DomainsRequest visit(Visitor<DomainsRequest> visitor)
    {
        return visitor.visit(this);
    }


}
