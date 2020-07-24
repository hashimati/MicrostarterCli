package io.hashimati.microcli;

import groovy.text.SimpleTemplateEngine;
import io.hashimati.domains.*;
import io.hashimati.utils.DataTypeMapper;
import lombok.SneakyThrows;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.hashimati.microcli.TemplatesService.CONSTRAINT;
import static io.hashimati.microcli.TemplatesService.CREATE;


@Singleton
public class SqlSchemaGenerator implements SchemaGenerator {


    @Inject
    private TemplatesService templatesService;
    @SneakyThrows
    @Override
    public String generateTable(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) {

        String template = templatesService.getSqlEntityTemplates().get(CREATE);
//
//        ${attributes}
//        ${constrains}

        StringBuilder declaration = new StringBuilder(generateAttribute(entity));

        Tuple2<String,String> constraints =generateConstraints(entity, relations, erMapper);
        if((constraints.getT1()+constraints.getT2()).isEmpty())
            declaration.deleteCharAt(declaration.lastIndexOf(","));
        else
            declaration.append(constraints.getT1()).append(constraints.getT2());


//        declaration.deleteCharAt(declaration.lastIndexOf(","));
        HashMap<String, String> map = new HashMap<>();
        map.put("attributes", declaration.toString());

//        map.put("constrains", constraints);
        map.put("talbeName", entity.getCollectionName());

        System.gc();

        return new SimpleTemplateEngine().createTemplate(template).make(map).toString();
    }

    @Override
    public String generateAttribute(Entity entity) {
        String databaseType = entity.getDatabaseType();
        HashMap<String, String> mapper = getDatabaseDataMapper(databaseType);

        StringBuilder declaration = new StringBuilder("id "+ mapper.get("long") +" PRIMARY KEY,\n");
        declaration.append("\tdate_created " + mapper.get("date") + " NOT NULL,\n");
        declaration.append("\tdate_updated " + mapper.get("date") + " NOT NULL,\n");
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

    //    @Override
    public Tuple2<String, String> generateConstraints(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) throws IOException, ClassNotFoundException {
            //CONSTRAINT ${name} ${constraintType} (${attribute}) REFERENCES ${entity}(id)

        HashMap<String, String> mapper = getDatabaseDataMapper(entity
        .getDatabaseType());
        String template = templatesService.getSqlEntityTemplates().get(CONSTRAINT);
        StringBuilder constraints = new StringBuilder("");
        StringBuilder attributes = new StringBuilder("");
        for (EntityRelation relation : relations) {

            if(relation.getE2().equals(entity.getName()) && relation.getE2Package().equals(entity.getEntityPackage())){
                String attributeName = relation.getRelationType() == EntityRelationType.OneToOne && !relation.areEntitiesEqual()? "id":relation.getE1().toLowerCase();

                HashMap<String,String> map = new HashMap<>();
                map.put("name", "FK_"+entity.getName()+relation.getE1());
                map.put("constraintType", "FOREIGN KEY");

                map.put("attribute",  attributeName);
                map.put("entity", erMapper.get(relation.getE1()));

                attributes.append( "\t"+attributeName + " " +mapper.get("long") + ",\n"  );



                String constraintDeclaration = new SimpleTemplateEngine().createTemplate(template).make(map).toString() + ",";
                constraints.append(constraintDeclaration);
            }


        }

        System.gc();
        return Tuples.of(attributes.toString(), constraints.lastIndexOf(",")>=0? constraints.deleteCharAt(constraints.lastIndexOf(",")).toString(): constraints.toString());
    }

    @Override
    public ArrayList<Tuple2<String, String>> generateFiles() {
        return null;
    }

}
