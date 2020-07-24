package io.hashimati.microcli;


import io.hashimati.domains.Entity;
import io.hashimati.domains.EntityRelation;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface SchemaGenerator {

    public String generateTable(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper);
    public String generateAttribute(Entity entity) throws IOException, ClassNotFoundException;


    public ArrayList<Tuple2<String, String>>  generateFiles();
}
