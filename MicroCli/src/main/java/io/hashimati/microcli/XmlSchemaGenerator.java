package io.hashimati.microcli;

import groovy.text.SimpleTemplateEngine;

import io.hashimati.domains.Entity;
import io.hashimati.domains.EntityAttribute;
import io.hashimati.domains.EntityConstraints;
import io.hashimati.domains.EntityRelation;
import io.hashimati.utils.DataTypeMapper;
import reactor.util.function.Tuple2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


@Singleton
public class XmlSchemaGenerator  {
//
//
//    @Inject
//    private TemplatesService templatesService;
//
//
//    public String generateAttribute(Entity entity) throws IOException, ClassNotFoundException {
//
//        //<column name="${columnName}" type="${type}"/>
//
//        String databaseType = entity.getDatabaseType();
//        String template = templatesService.getLiquibaseTemplates().get("columns");
//
//        HashMap<String, String> mapper = DataTypeMapper.liquibaseMapper;
//
//        SimpleTemplateEngine templateEngine = new SimpleTemplateEngine();
//
//        StringBuilder declaration = new StringBuilder(templateEngine.createTemplate(template).make(new HashMap<String, String>(){{
//            put("columnName", "id");
//            put("type", mapper.get("long"));
//        }}).toString()+"\n");
//        declaration.append(templateEngine.createTemplate(template).make(new HashMap<String, String>(){{
//            put("columnName", "date_created");
//            put("type", mapper.get("date"));
//        }}).toString()+"\n");
//
//        declaration.append(templateEngine.createTemplate(template).make(new HashMap<String, String>(){{
//            put("columnName", "date_updated");
//            put("type", mapper.get("date"));
//        }}).toString()+"\n");
//
//        for (EntityAttribute attribute : entity.getAttributes()) {
//            if(attribute.isArray()) continue;;
//            String attributeName = attribute.getName().toLowerCase();
//            String attributeType = attribute.getType().toLowerCase();
//            String mappedDataType = mapper.get(attributeType);
//
//            EntityConstraints constraints = attribute.getConstraints();
//
//            String max = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? "(" +constraints.getMax() +")":""):"";
//
//            String maxLength = !attributeType.equalsIgnoreCase("date")?(constraints.isEnabled()? ((constraints.getMaxSize() >0)?"(" + constraints.getMaxSize() + ")":""):""):"";
//            String maxx = attributeType.equalsIgnoreCase("string")?maxLength:max;
//            maxx = maxx.contains("-")?"":maxx;
//            String dec =mappedDataType +maxx;
//            declaration.append(templateEngine.createTemplate(template).make(new HashMap<String, String>(){{
//                put("columnName", attributeName);
//                put("type", dec);
//            }}));
//        }
//        System.gc();
////        declaration.deleteCharAt(declaration.lastIndexOf(","));
//        return declaration.toString();
//    }
//
//    public ArrayList<Tuple2<String, String>> generateFiles() {
//        return null;
//    }
//
//    public String generateConstraints(Entity entity,ArrayList<EntityRelation> relations, HashMap<String, String> collectionMapper) {
//       return "";
//    }
//
//
//    public String generateTable(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper) {
//        return null;
//    }
}
