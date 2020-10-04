package io.hashimati.microcli.services;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.domains.EntityConstraints;
import io.hashimati.microcli.domains.EntityRelation;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.GeneratorUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.XMLFormatter;


@Singleton
public class LiquibaseGenerator {


    @Inject
    private TemplatesService templatesService;


    public String generateAttribute(Entity entity) throws IOException, ClassNotFoundException {

        //<column name="${columnName}" type="${type}"/>

        String databaseType = entity.getDatabaseType();
        String columnTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_COLUMN));
        String constraintTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_constrain));


        HashMap<String, String> mapper = DataTypeMapper.liquibaseMapper;

        SimpleTemplateEngine templateEngine = new SimpleTemplateEngine();


        StringBuilder idConstraintsBuilder = new StringBuilder()
                .append("unique=\"true\" ")
                .append("nullable=\"false\" ")
                .append("primaryKey=\"true\" ");
        StringBuilder declaration = new StringBuilder(templateEngine.createTemplate(columnTemplate).make(new HashMap<String, String>(){{
            put("columnName", "id");
            put("type", mapper.get("long"));
            put("constraints", templateEngine.createTemplate(constraintTemplate).make(new HashMap<String, String>(){{
                put("constraints", idConstraintsBuilder.toString());}}).toString());
        }}).toString()+"\n");
        declaration.append(templateEngine.createTemplate(columnTemplate).make(new HashMap<String, String>(){{
            put("columnName", "date_created");
            put("type", mapper.get("date"));
            put("constraints", "");
        }}).toString()+"\n");

        declaration.append(templateEngine.createTemplate(columnTemplate).make(new HashMap<String, String>(){{
            put("columnName", "date_updated");
            put("type", mapper.get("date"));
            put("constraints", "");
        }}).toString()+"\n");

        for (EntityAttribute attribute : entity.getAttributes()) {
            if(attribute.isArray()) continue;;
            String attributeName = attribute.getName().toLowerCase();
            String attributeType = attribute.getType().toLowerCase();
            String mappedDataType = mapper.get(attributeType);

            EntityConstraints constraints = attribute.getConstraints();
            String max ="";
            System.out.println(constraints == null);
            if(constraints != null && constraints.getMax() != null)
             max = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? "(" +constraints.getMax() +")":""):"";

            String maxLength = "(255)";
            if(constraints != null && constraints.getMaxSize() != null)
             maxLength = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? ((constraints.getMaxSize() >0)?"(" + constraints.getMaxSize() + ")":""):""):"";
           String maxx = attributeType.equalsIgnoreCase("string")?maxLength:max;
            maxx = maxx.contains("-")?"":maxx;
            String dec =mappedDataType +maxx;

            StringBuilder constraintsBuilder = new StringBuilder();
            if(constraints != null && constraints.isUnique())
                constraintsBuilder.append("unique=\"true\" ");
            if(constraints != null && constraints.isNullable())
                constraintsBuilder.append("nullable=\"true\" ");

            declaration.append(templateEngine.createTemplate(columnTemplate).make(new HashMap<String, String>(){{
                put("columnName", attributeName);
                put("type", dec);
                put("constraints", templateEngine.createTemplate(constraintTemplate).make(new HashMap<String, String>(){{
                    put("constraints", constraintsBuilder.toString());
                }}).toString());
            }}));
        }
        System.gc();
//        declaration.deleteCharAt(declaration.lastIndexOf(","));
        return declaration.toString();
    }

    public ArrayList<Tuple2<String, String>> generateFiles() {
        return null;
    }

    public String generateTable(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) throws IOException, ClassNotFoundException {
        //todo
        String entityColumns = generateAttribute(entity);

        String tableTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_TABLE));

        return GeneratorUtils.generateFromTemplate(tableTemplate, new HashMap<String, String>(){{
            put("columns", entityColumns);
            put("tableName", entity.getCollectionName());
        }});
    }

    public String generateChangeSet(HashSet<Entity> entityList, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) throws  IOException, ClassNotFoundException{

        String changeSetTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_SCHEMA));


        StringBuilder content = new StringBuilder("").append(
        entityList.stream().map(x-> {
            try {

                return generateTable(x, relations, erMapper);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return "";
            }

        }).reduce("", (x, y) -> new StringBuilder().append(x).append("\n").append(y).toString()));

        //todo add foreign key

                
        return GeneratorUtils.generateFromTemplate(changeSetTemplate, new HashMap<String, String>(){{
            put("tables", content.toString());
            put("foreignKey", "");
        }});
    }
    public String generateConstraints(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> collectionMapper) {
       return "";
    }

    public Tuple2<String, String> generateCatalog()
    {


        return Tuple.tuple(System.getProperty("user.dir") +"/src/main/resources/db/liquibase-changelog.xml",templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_CATALOG)));


    }
    public Tuple2<String, String> generateSchema(HashSet<Entity> entities, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) throws IOException, ClassNotFoundException {
        StringBuilder filePath = new StringBuilder(System.getProperty("user.dir") ).append("/src/main/resources/db/changelog/");
        String date = "01-create-schema.xml"; //new SimpleDateFormat("DD-MM-YYYY").format(new Date());
        String content = generateChangeSet(entities, relations, erMapper);

        return Tuple.tuple(filePath.append(date).toString(), content);


    }


    
}
