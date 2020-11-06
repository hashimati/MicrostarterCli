package io.hashimati.microcli.services;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.text.SimpleTemplateEngine;
import groovy.text.XmlTemplateEngine;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.domains.EntityConstraints;
import io.hashimati.microcli.domains.EntityRelation;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.XMLFormatter;
import io.micronaut.core.naming.NameUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static io.hashimati.microcli.domains.EntityRelationType.OneToOne;
import static io.hashimati.microcli.utils.GeneratorUtils.generateFromTemplate;

public interface IDataMigrationGenerator {


    public String generateAttribute(Entity entity) throws IOException, ClassNotFoundException ;
    public String generateTable(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) throws IOException, ClassNotFoundException ;

    public Tuple2<String, String> generateAddColumnChangeSet(Entity entity, ArrayList<EntityAttribute> attributes, HashMap<String, String> erMapper, int changeSetId) throws Exception ;
    public String generateChangeSet(HashSet<Entity> entityList, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper, int changeSetId) throws  IOException, ClassNotFoundException;
    public String generateConstraints(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> collectionMapper) ;

    public Tuple2<String, String> generateCatalog();
    public Tuple2<String, String> generateSchema(HashSet<Entity> entities, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper, int changeSetId) throws Exception ;

    public Tuple2<String, String> generateForeignKey(Entity e1, Entity e2, EntityRelation relation, int changeSetId) throws Exception ;


    public String generateXMLTemplate(String template, HashMap<String, String> map) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException ;

    public Tuple2<String, String> generateDeleteColumnChangeSet(Entity entity, HashSet<String> attributes, HashMap<String, String> mapper, int changeSetId) throws Exception ;
    public Tuple2<String, String> generateDropTableChangeSet(Entity entity, HashMap<String, String> mapper, int changeSetId) throws Exception ;

}
