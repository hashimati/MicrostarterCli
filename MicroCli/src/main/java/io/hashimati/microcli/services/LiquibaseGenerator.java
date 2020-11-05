package io.hashimati.microcli.services;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.text.SimpleTemplateEngine;
import groovy.text.XmlTemplateEngine;
import io.hashimati.microcli.domains.*;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.XMLFormatter;
import io.micronaut.core.naming.NameUtils;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;
/**
 * @author Ahmed Al Hashmi
 */
import static io.hashimati.microcli.domains.EntityRelationType.OneToOne;
import static io.hashimati.microcli.utils.GeneratorUtils.generateFromTemplate;


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
            put("columnName", "dateCreated");
            put("type", mapper.get("date"));
            put("constraints", "");
        }}).toString()+"\n");

        declaration.append(templateEngine.createTemplate(columnTemplate).make(new HashMap<String, String>(){{
            put("columnName", "dateUpdated");
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


        return generateFromTemplate(tableTemplate, new HashMap<String, String>(){{
            put("columns", entityColumns);
            put("tableName", entity.getCollectionName());
        }});
    }

    public Tuple2<String, String> generateAddColumnChangeSet(Entity entity, ArrayList<EntityAttribute> attributes, HashMap<String, String> erMapper, int changeSetId) throws Exception {
        //todo
        Entity e = new Entity();
        e.setName(entity.getName());
        e.setAttributes(attributes);
        String entityColumns = generateAttribute(entity);

        String tableTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_ADD_COLUMN));


        String columnsChangeSet = generateFromTemplate(tableTemplate, new HashMap<String, String>(){{
            put("columns", entityColumns);
            put("tableName", entity.getCollectionName());
        }});

        String changeSetTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_SCHEMA));

        String content = generateFromTemplate(changeSetTemplate, new HashMap<String, String>(){{
            put("tables","");
            put("foreignKey", columnsChangeSet);
            put("id", String.valueOf(changeSetId));
            put("username", NameUtils.capitalize(System.getProperty("user.name")));

        }});
        StringBuilder filePath = new StringBuilder(System.getProperty("user.dir") ).append("/src/main/resources/db/changelog/");
        String date = MessageFormat.format("db.changelog-{0}.xml", changeSetId); // new StringBuilder().append(changeSetId).append("-create-schema.xml").toString(); //new SimpleDateFormat("DD-MM-YYYY").format(new Date());

        //todo XML FORMATTER
        return Tuple.tuple(filePath.append(date).toString(), XMLFormatter.format(content));
    }
    public String generateChangeSet(HashSet<Entity> entityList, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper, int changeSetId) throws  IOException, ClassNotFoundException{

        String changeSetTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_SCHEMA));


        StringBuilder content = new StringBuilder("").append(
        entityList.stream().filter(x->x.getLiquibaseSequence()== changeSetId).map(x-> {
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

                
        return generateFromTemplate(changeSetTemplate, new HashMap<String, String>(){{
            put("tables", content.toString());
            put("foreignKey", "");
            put("id", String.valueOf(changeSetId));
            put("username", NameUtils.capitalize(System.getProperty("user.name")));
        }});
    }
    public String generateConstraints(Entity entity, ArrayList<EntityRelation> relations, HashMap<String, String> collectionMapper) {
       return "";
    }

    public Tuple2<String, String> generateCatalog()
    {


        return Tuple.tuple(System.getProperty("user.dir") +"/src/main/resources/db/liquibase-changelog.xml",templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_CATALOG)));


    }
    public Tuple2<String, String> generateSchema(HashSet<Entity> entities, ArrayList<EntityRelation> relations, HashMap<String, String> erMapper, int changeSetId) throws Exception {
        StringBuilder filePath = new StringBuilder(System.getProperty("user.dir") ).append("/src/main/resources/db/changelog/");
        String date = MessageFormat.format("db.changelog-{0}.xml", changeSetId); // new StringBuilder().append(changeSetId).append("-create-schema.xml").toString(); //new SimpleDateFormat("DD-MM-YYYY").format(new Date());
        String content = generateChangeSet(entities, relations, erMapper, changeSetId);

        //todo XML FORMATTER
        return Tuple.tuple(filePath.append(date).toString(), XMLFormatter.format(content));
    }

    public Tuple2<String, String> generateForeignKey(Entity e1, Entity e2, EntityRelation relation, int changeSetId) throws Exception {


        String template = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_FOREIGNKEY));
        HashMap<String, String> map = new HashMap<>()
        {{

            put("baseColumnName", relation.getE1().toLowerCase());
            put("baseTable",relation.getE2Table() );
            put("constraintName", MessageFormat.format("{0}_{1}", relation.getE1().toLowerCase(), relation.getE2().toLowerCase()));
            put("referencedTable", relation.getE1Table());
            put("unique", relation.getRelationType()== OneToOne?"true":"false");

        }};
        String foreignKey = generateFromTemplate(template, map);



        String changeSetTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_SCHEMA));

        String content = generateFromTemplate(changeSetTemplate, new HashMap<String, String>(){{
            put("tables","");
            put("foreignKey", foreignKey);
            put("id", String.valueOf(changeSetId));
            put("username", NameUtils.capitalize(System.getProperty("user.name")));

        }});


        StringBuilder filePath = new StringBuilder(System.getProperty("user.dir") ).append("/src/main/resources/db/changelog/");
        String date = MessageFormat.format("db.changelog-{0}.xml", changeSetId);
        return Tuple.tuple(filePath.append(date).toString(), XMLFormatter.format(content));
    }




    public String generateXMLTemplate(String template, HashMap<String, String> map) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
        return new XmlTemplateEngine().createTemplate(template).make(map).toString();
    }

    public Tuple2<String, String> generateDeleteColumnChangeSet(Entity entity, HashSet<String> attributes, HashMap<String, String> mapper, int changeSetId) throws Exception {
            //todo
        Entity e = new Entity();
        e.setName(entity.getName());

        StringBuilder dropColumnStatements= new StringBuilder();

        String dropColumnTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_DROP_COLUMN));
        for (String a : attributes) {

            HashMap<String, String> map = new HashMap<>(){{
                put("tableName", entity.getCollectionName());
                put("columnName", a);
            }};
            dropColumnStatements.append(generateFromTemplate(dropColumnTemplate, map)).append("\n");


        }
        String changeSetTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_SCHEMA));

        String content = generateFromTemplate(changeSetTemplate, new HashMap<String, String>(){{
            put("tables","");
            put("foreignKey", dropColumnStatements.toString());
            put("id", String.valueOf(changeSetId));
            put("username", NameUtils.capitalize(System.getProperty("user.name")));

        }});
        StringBuilder filePath = new StringBuilder(System.getProperty("user.dir") ).append("/src/main/resources/db/changelog/");
        String date = MessageFormat.format("db.changelog-{0}.xml", changeSetId); // new StringBuilder().append(changeSetId).append("-create-schema.xml").toString(); //new SimpleDateFormat("DD-MM-YYYY").format(new Date());

        //todo XML FORMATTER
        return Tuple.tuple(filePath.append(date).toString(), XMLFormatter.format(content));
    }
    public Tuple2<String, String> generateDropTableChangeSet(Entity entity, HashMap<String, String> mapper, int changeSetId) throws Exception {
        //todo
        Entity e = new Entity();
        e.setName(entity.getName());

        StringBuilder dropColumnStatements= new StringBuilder();

        String dropTableTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_DROP_TABLE));

        String dropTableStatement = generateFromTemplate(dropTableTemplate, new HashMap<String, String> (){{
            put("tableName", entity.getCollectionName());

        }});

        String changeSetTemplate = templatesService.loadTemplateContent(templatesService.getLiquibaseTemplates().get(TemplatesService.LIQUIBASE_SCHEMA));

        String content = generateFromTemplate(changeSetTemplate, new HashMap<String, String>(){{
            put("tables",dropTableStatement);
            put("foreignKey", "");
            put("id", String.valueOf(changeSetId));
            put("username", NameUtils.capitalize(System.getProperty("user.name")));

        }});
        StringBuilder filePath = new StringBuilder(System.getProperty("user.dir") ).append("/src/main/resources/db/changelog/");
        String date = MessageFormat.format("db.changelog-{0}.xml", changeSetId); // new StringBuilder().append(changeSetId).append("-create-schema.xml").toString(); //new SimpleDateFormat("DD-MM-YYYY").format(new Date());

        //todo XML FORMATTER
        return Tuple.tuple(filePath.append(date).toString(), XMLFormatter.format(content));
    }

}
