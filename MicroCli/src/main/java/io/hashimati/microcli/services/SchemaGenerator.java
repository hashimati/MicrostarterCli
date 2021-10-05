package io.hashimati.microcli.services;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import groovy.lang.Tuple2;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityRelation;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface SchemaGenerator {

    public String generateTable(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper);
    public String generateAttribute(Entity entity) throws IOException, ClassNotFoundException;


    public ArrayList<Tuple2<String, String>>  generateFiles();
}
