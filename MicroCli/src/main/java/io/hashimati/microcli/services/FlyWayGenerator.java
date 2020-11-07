package io.hashimati.microcli.services;


import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.text.Template;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.domains.EntityConstraints;
import io.hashimati.microcli.domains.EntityRelation;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class FlyWayGenerator
{


    @Inject
    private TemplatesService templatesService;


    public String generateAttribute(Entity entity)  {
        String databaseType = entity.getDatabaseType();
        HashMap<String, String> mapper = getDatabaseDataMapper(databaseType);


//        AtomicReference<StringBuilder> declaration = new AtomicReference<>(new StringBuilder("id "+ mapper.get("long") +" PRIMARY KEY,\n")
//                .append("\tdate_created " + mapper.get("date") + " NOT NULL,\n")
//                .append("\tdate_updated " + mapper.get("date") + " NOT NULL,\n"));
//        entity.getAttributes().parallelStream().forEach(attribute->{
//            if(!attribute.isArray()) {
//                String attributeName = attribute.getName().toLowerCase();
//                String attributeType = attribute.getType().toLowerCase();
//                String mappedDataType = mapper.get(attributeType);
//
//                EntityConstraints constraints = attribute.getConstraints();
//
//                String notNull = constraints.isEnabled() ? (constraints.isRequired() || constraints.isNotBlank() || constraints.isNotEmpty() || constraints.isNotempty() ? "NOT NULL" : "") : "";
//                String unique = constraints.isEnabled() ? (constraints.isUnique() ? "UNIQUE" : "") : "";
//                String max = !attributeType.equalsIgnoreCase("date") ? (constraints.isEnabled() ? "(" + constraints.getMax() + ")" : "") : "";
//
//                String maxLength = !attributeType.equalsIgnoreCase("date") ? (constraints.isEnabled() ? ((constraints.getMaxSize() > 0) ? "(" + constraints.getMaxSize() + ")" : "") : "") : "";
//                String maxx = attributeType.equalsIgnoreCase("string") ? maxLength : max;
//                maxx = maxx.contains("-") ? "" : maxx;
//                String dec = "\t" + attributeName + " " + mappedDataType + maxx + " " + notNull + " " + unique + " ,\n";
//                declaration.get().append((dec));
//            }
//        });





        StringBuilder declaration = new StringBuilder("id "+ mapper.get("long") +" PRIMARY KEY,\n")
                .append("\tdate_created " + mapper.get("date") + " NOT NULL,\n")
                .append("\tdate_updated " + mapper.get("date") + " NOT NULL,\n");


        //No parallel because the order is matter.
        for (EntityAttribute attribute : entity.getAttributes()) {
            if(attribute.isArray()) continue;;
            String attributeName = attribute.getName().toLowerCase();
            String attributeType = attribute.getType().toLowerCase();
            String mappedDataType = mapper.get(attributeType);

            EntityConstraints constraints = attribute.getConstraints();

            String notNull =  constraints.isEnabled()? (constraints.isRequired() || constraints.isNotBlank() || constraints.isNotEmpty() || constraints.isNotempty()?"NOT NULL":""):"";
            String unique =  constraints.isEnabled()? (constraints.isUnique()? "UNIQUE":""):"";
            String max = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? "(" +constraints.getMax() +")":""):"";

            String maxLength = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? ((constraints.getMaxSize() >0)?"(" + constraints.getMaxSize() + ")":""):""):"";
            String maxx = attributeType.equalsIgnoreCase("string")?maxLength:max;
            maxx = maxx.contains("-")?"":maxx;
            String dec = "\t" +attributeName + " " + mappedDataType +maxx+" " +notNull + " " + unique+ " ,\n";
            declaration.append((dec));

        }
        System.gc();
//        declaration.deleteCharAt(declaration.lastIndexOf(","));
        return declaration.toString();
    }
    public Tuple2<String, String> createTable(Entity entity,int changeSetId){

        String template =templatesService.loadTemplateContent(templatesService.getFlywayTemplates().get(TemplatesService.FLYWAY_TABLE));
        String attributes = generateAttribute(entity);
        String content = GeneratorUtils.generateFromTemplate(template,
                new HashMap<String, String>(){
                    {
                        put("talbeName", entity.getCollectionName());
                        put("attributes", attributes);

                    }});
        String fileName = new StringBuilder().append("V").append(String.valueOf(changeSetId)).append("__datebase-change.sql").toString();
        return Tuple.tuple(fileName, content);
    }
    public Tuple2<String, String> dropTable(String entity, int changeSetId){
        String template =templatesService.loadTemplateContent(templatesService.getFlywayTemplates().get(TemplatesService.FLYWAY_DROP_TABLE));

        String content = GeneratorUtils.generateFromTemplate(template,
                new HashMap<String, String>(){
                    {
                        put("talbeName", entity);

                    }});
        String fileName = new StringBuilder().append("V").append(String.valueOf(changeSetId)).append("__datebase-change.sql").toString();
        return Tuple.tuple(fileName, content);
    }
    public Tuple2<String, String> addColumns(Entity entity, HashSet<EntityAttribute> attributes , int changeSetId){
        String template =templatesService.loadTemplateContent(templatesService.getFlywayTemplates().get(TemplatesService.FLYWAY_ADD_COLUMN));

        return null;
    }
    public Tuple2<String, String> addRelationship(Entity entity, EntityRelation relation, int changeSetId){
        String template =templatesService.loadTemplateContent(templatesService.getFlywayTemplates().get(TemplatesService.FLYWAY_TABLE));

        return null;
    }
    public Tuple2<String, String> dropColumn(String entity, HashSet<String> attributes, int changeSetId ){
        String template =templatesService.loadTemplateContent(templatesService.getFlywayTemplates().get(TemplatesService.FLYWAY_DROP_COLUMN));

        return null;
    }

    private HashMap<String, String> getDatabaseDataMapper(String databaseType) {
        HashMap<String, String> mapper = null;
        switch (databaseType.toLowerCase())
        {
            case "mysql":
                mapper = DataTypeMapper.mysqlMapper;
                break;

            case "mariadb":
                mapper = DataTypeMapper.mysqlMapper;
                break;
            case "postgres":
                mapper = DataTypeMapper.postgresMapper;
                break;

            case "oracle":
                mapper = DataTypeMapper.oracleMapper;
                break;

            case "sqlserver":
            case "sql_server":
                mapper = DataTypeMapper.mssqlMapper;

                break;

        }
        return mapper;
    }

}
