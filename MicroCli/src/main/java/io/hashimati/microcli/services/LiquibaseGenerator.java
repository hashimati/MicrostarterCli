package io.hashimati.microcli.services;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

        StringBuilder declaration = new StringBuilder(templateEngine.createTemplate(columnTemplate).make(new HashMap<String, String>(){{
            put("columnName", "id");
            put("type", mapper.get("long"));
            put("constraints", "");
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

            String max = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? "(" +constraints.getMax() +")":""):"";

            String maxLength = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? ((constraints.getMaxSize() >0)?"(" + constraints.getMaxSize() + ")":""):""):"";
           String maxx = attributeType.equalsIgnoreCase("string")?maxLength:max;
            maxx = maxx.contains("-")?"":maxx;
            String dec =mappedDataType +maxx;

            StringBuilder constraintsBuilder = new StringBuilder();
            if(constraints.isUnique())
                constraintsBuilder.append("unique=\"true\" ");
            if(constraints.isNullable())
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
        }});
    }

    public String generateChangeSet(List<Entity> entityList, ArrayList<EntityRelation> relations, HashMap<String, String> erMappper) throws  IOException, ClassNotFoundException{

        String changeSetTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_SCHEMA));

        StringBuilder content = new StringBuilder("").append(
        entityList.stream().map(x-> {
            try {
                return generateTable(x, relations, erMappper);
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


    
}
