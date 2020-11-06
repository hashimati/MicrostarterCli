package io.hashimati.microcli.services;


import groovy.lang.Tuple2;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.domains.EntityRelation;
import org.xml.sax.SAXException;

import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Singleton
public class FlyWayGenerator implements IDataMigrationGenerator
{

    @Override
    public String generateAttribute(Entity entity) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateTable(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Tuple2<String, String> generateAddColumnChangeSet(Entity entity, ArrayList<EntityAttribute> attributes, HashMap<String, String> erMapper, int changeSetId) throws Exception {
        return null;
    }

    @Override
    public String generateChangeSet(HashSet<Entity> entityList, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper, int changeSetId) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateConstraints(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> collectionMapper) {
        return null;
    }

    @Override
    public Tuple2<String, String> generateCatalog() {
        return null;
    }

    @Override
    public Tuple2<String, String> generateSchema(HashSet<Entity> entities, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper, int changeSetId) throws Exception {
        return null;
    }

    @Override
    public Tuple2<String, String> generateForeignKey(Entity e1, Entity e2, EntityRelation relation, int changeSetId) throws Exception {
        return null;
    }

    @Override
    public String generateXMLTemplate(String template, HashMap<String, String> map) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public Tuple2<String, String> generateDeleteColumnChangeSet(Entity entity, HashSet<String> attributes, HashMap<String, String> mapper, int changeSetId) throws Exception {
        return null;
    }

    @Override
    public Tuple2<String, String> generateDropTableChangeSet(Entity entity, HashMap<String, String> mapper, int changeSetId) throws Exception {
        return null;
    }
}
