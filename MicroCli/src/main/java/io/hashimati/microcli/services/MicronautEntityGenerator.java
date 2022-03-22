package io.hashimati.microcli.services;

import com.google.googlejavaformat.java.FormatterException;
import groovy.lang.Tuple2;
import groovy.lang.Tuple3;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.constants.ProjectConstants;
import io.hashimati.microcli.domains.*;
import io.hashimati.microcli.exceptions.NotImplementedException;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.micronaut.core.naming.NameUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.domains.EntityRelationType.OneToMany;
import static io.hashimati.microcli.domains.EntityRelationType.OneToOne;
import static io.hashimati.microcli.services.TemplatesService.*;


/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
@Singleton
public class MicronautEntityGenerator
{



    private String path;
    @Inject
    private SqlSchemaGenerator sqlSchemaGenerator;

    @Inject
    private TemplatesService templatesService;

    public void setPath(String path) {
        this.path = path;
    }

    // @PostConstruct
    @Deprecated
    public void test() throws IOException, ClassNotFoundException, FormatterException {


        Entity entity = new Entity(){{
            setName("Ahmed");
            setCollectionName("Ahmed");
            setDatabaseType("mysql");
            setEntityPackage("com.ahmed.ahl");
            setRepoPackage("com.ahmed.ah1.repo");
            setServicePackage("com.ahmed.ah1.services");
            setRestPackage("com.ahmed.ah1.resources");


            getAttributes().add(new EntityAttribute(){{
                setName("ie");
                setType(ProjectConstants.EntityAttributeType.INTEGER);
                setConstraints(new EntityConstraints(){{
                    setMin(Long.valueOf(32));
                    setMax(Long.valueOf(55555));
                }});
            }});

            setFrameworkType("jdbc");
            setCollectionName("Ahmeds");
        }};
        EntityRelation entityRelation = new EntityRelation();
        entityRelation.setE1("Ahmed");
        entityRelation.setE1Package("com.ahmed.ahl");
        entityRelation.setE2("Ali");
        entityRelation.setE2Package("com.ahmed.ah1");
        entityRelation.setRelationType(OneToOne);
        ArrayList<EntityRelation> relations = new ArrayList<>();
        relations.add(entityRelation);
        System.out.println("-----------------java-------");

        System.out.println(generateEntity(entity, relations, "java"));
        System.out.println("***");
        System.out.println(generateRepository(entity, "java", null));
        System.out.println("***");
        System.out.println(generateService(entity, "java"));
        System.out.println("***");
        System.out.println(generateController(entity, "java"));
        System.out.println("-----------------groovy-------");
        System.out.println(generateEntity(entity, relations ,GROOVY_LANG));
        System.out.println("***");
        System.out.println(generateRepository(entity, "groovy", null));
        System.out.println("***");
        System.out.println(generateService(entity, "groovy"));
        System.out.println("***");
        System.out.println(generateController(entity, "groovy"));

        System.out.println("--------------kotlin----------");
        System.out.println(generateEntity(entity,relations ,"kotlin"));
        System.out.println("***");
        System.out.println(generateRepository(entity, "kotlin", null));
        System.out.println("***");
        System.out.println(generateService(entity, "kotlin"));
        System.out.println("***");
        System.out.println(generateController(entity, "kotlin"));

        System.out.println("--------------------end entity test----");
    }


    private String generateEntityGorm(Entity entity, ArrayList<EntityRelation> relations, String language)  throws IOException,
            ClassNotFoundException
    {
        String attributesDeclaration ="";

        String importedPackages = entity.getEntitiesImport(entity.getEntityPackage().replace(".domains", ""));
        boolean containDate = false;

        StringBuilder contraints = new StringBuilder("");
        if(entity.getAttributes()!= null && !entity.getAttributes().isEmpty())
            for(EntityAttribute eA: entity.getAttributes())
            {

                if(eA.isDate())
                {
                    containDate = true;
                }

                String attributeDeclaration = "";




                if(eA.getConstraints() != null)
                    if(eA.getConstraints().isEnabled()) {

                        StringBuilder attrContraint = new StringBuilder(eA.getName()).append(" ");
                        if(eA.isArray() == false){
                            if (eA.isString()) {
                                attrContraint.append(eA.getConstraints().getSizeExpressionGorm()).append(", ");
                                attrContraint.append(eA.getConstraints().getNotBlankExpressionGorm()).append(", ");
                                attrContraint.append( eA.getConstraints().getNotNullExpressionGorm()).append(", ");
                                if(!eA.getConstraints().getPatternExpressionGorm().trim().isEmpty())
                                    attrContraint.append(eA.getConstraints().getPatternExpressionGorm()).append(", ");
                                if(!eA.getConstraints().getEmailExpressionGorm().trim().isEmpty())
                                    attrContraint.append(eA.getConstraints().getEmailExpressionGorm()).append(", ");
                                attrContraint.append( eA.getConstraints().getUniqueExperessionGorm());

                            } else if (eA.isInteger() || eA.isByte() || eA.isShort() || eA.isLong()) {
                                if(! eA.getConstraints().getMinExpressionGorm().trim().isEmpty())
                                    attrContraint.append( eA.getConstraints().getMinExpressionGorm()).append(", ");
                                if(! eA.getConstraints().getMaxExpressionGorm().trim().isEmpty())
                                    attrContraint.append( eA.getConstraints().getMaxExpressionGorm()).append(", ");

                                attrContraint.append(eA.getConstraints().getUniqueExperessionGorm());

                            } else if (eA.isDouble() || eA.isFloat()) {
                                if(! eA.getConstraints().getMinDecimalExpressionGorm().trim().isEmpty())
                                    attrContraint.append( eA.getConstraints().getMinDecimalExpressionGorm()).append(", ");
                                if(! eA.getConstraints().getMaxDecimalExpressionGorm().trim().isEmpty())
                                    attrContraint.append( eA.getConstraints().getMaxDecimalExpressionGorm()).append(", ");
                                attrContraint.append(eA.getConstraints().getUniqueExperessionGorm()).append(", ");

                            } else if (eA.isDate()) {
                                attrContraint.append(eA.getConstraints().getNotNullExpressionGorm());

                                //      attributeDeclaration += eA.getConstraints().getDateValidationExepression();
                            } else if (eA.isClass()) {
                                attrContraint.append(eA.getConstraints().getNotNullExpressionGorm());
                            }
                        }
                        else if(eA.isArray()) {
//                            attributeDeclaration += eA.getConstraints().getCollectionSizeExpression();
                            attrContraint.append(eA.getConstraints().getNotBlankExpressionGorm());

                        }
                        attrContraint.trimToSize();
                     //   attrContraint.replace(attrContraint.lastIndexOf(","), attrContraint.lastIndexOf(","), "").append("\n");
                        contraints.append(attrContraint.toString());
                    }
                attributeDeclaration +=eA.getDeclaration(language,false);
                if(!eA.isPremetive() && eA.getTypePackage()!= null )
                {
                    importedPackages += eA.getPackageSyntax(language)+"\n";
                }

                attributesDeclaration +=   attributeDeclaration +"\n";

            }

        HashMap<String, String> binder = new HashMap<>();
            binder.put("entitypackage", entity.getEntityPackage());
            binder.put("className", entity.getName());
            binder.put("instances", attributesDeclaration);
            binder.put("constraints", contraints.toString());
            binder.put("db", entity.getDatabaseName());
            binder.put("collection", entity.getCollectionName());
            binder.put("storeType", entity.getDatabaseType().equalsIgnoreCase("mongodb")?"collection":"table");
        String templatePath= getTemplatPath(GORM_ENTITY, language.toLowerCase());


        String entityTemplate  =templatesService.loadTemplateContent(templatePath);


        String result = new SimpleTemplateEngine().createTemplate(entityTemplate).make(binder).toString();

        return result;
    }
    public String generateEntity(Entity entity, ArrayList<EntityRelation> relations, String language) throws IOException,
            ClassNotFoundException {

        if(language.equalsIgnoreCase(GROOVY_LANG) && entity.isGorm())
            return generateEntityGorm(entity, relations, language);
        String declarrationSperator = language.equalsIgnoreCase(KOTLIN_LANG) || (language.equalsIgnoreCase(JAVA_LANG) && entity.isJavaRecord())? ",": "\n";
        Set<EntityRelation> entityRelations = getRelations(entity, relations);
        String attributesDeclaration ="";
        String importedPackages = entity.getEntitiesImport(entity.getEntityPackage().replace(".domains", ""));
        boolean containDate = false;

        if(entity.getAttributes()!= null && !entity.getAttributes().isEmpty())
        for(EntityAttribute eA: entity.getAttributes())
        {

            if(eA.isDate())
            {
                containDate = true;
            }

            String attributeDeclaration = "";




            if(eA.getConstraints() != null)
            if(eA.getConstraints().isEnabled()) {
                if(eA.isArray() == false){
                if (eA.isString()) {
                    attributeDeclaration += eA.getConstraints().getSizeExpression();
                    attributeDeclaration += eA.getConstraints().getNotBlankExpression();
                    attributeDeclaration += eA.getConstraints().getNotNullExpression();
                    attributeDeclaration += eA.getConstraints().getPatternExpression();
                    attributeDeclaration += eA.getConstraints().getEmailExpression();
                    attributeDeclaration += (entity.getFrameworkType().equalsIgnoreCase("jpa") ? eA.getConstraints().getUniqueExperession() : "");
                } else if (eA.isInteger() || eA.isByte() || eA.isShort() || eA.isLong()) {
                    attributeDeclaration += eA.getConstraints().getMaxExpression();
                    attributeDeclaration += eA.getConstraints().getMinExpression();
                    attributeDeclaration += (entity.getFrameworkType().equalsIgnoreCase("jpa") ? eA.getConstraints().getUniqueExperession() : "");

                } else if (eA.isDouble() || eA.isFloat()) {
                    attributeDeclaration += eA.getConstraints().getDecimalMaxExpression();
                    attributeDeclaration += eA.getConstraints().getDecimalMinExpression();
                    attributeDeclaration += (entity.getFrameworkType().equalsIgnoreCase("jpa") ? eA.getConstraints().getUniqueExperession() : "");

                } else if (eA.isDate()) {
                    attributeDeclaration += eA.getConstraints().getNotNullExpression();

              //      attributeDeclaration += eA.getConstraints().getDateValidationExepression();
                } else if (eA.isClass()) {
                    attributeDeclaration += eA.getConstraints().getNotNullExpression();
                }
            }
                else if(eA.isArray()) {
                    attributeDeclaration += eA.getConstraints().getCollectionSizeExpression();
                    attributeDeclaration += eA.getConstraints().getNotBlankExpression();

                }

            }
            attributeDeclaration +=eA.getDeclaration(language, entity.isJavaRecord()) + declarrationSperator;
            if(!eA.isPremetive() && eA.getTypePackage()!= null )
            {
               importedPackages += eA.getPackageSyntax(language)+"\n";


            }

            attributesDeclaration +=   attributeDeclaration +"\n";
        }
        if(entityRelations != null)
        for(EntityRelation r: entityRelations)
        {
            switch(r.getRelationType())
            {
                case OneToOne:
                    if(entity.getName().equals(r.getE1()) && entity.getEntityPackage().equals(r.getE1Package()))
                    {

                        importedPackages +=(importedPackages.contains(r.getE1Package()+"."+r.getE2())? "": "\nimport " + r.getE2Package()+"."+r.getE2()+ ";");
                        attributesDeclaration += "\n" + r.generateE1OneToOneTemplate(language, entity.getFrameworkType())+declarrationSperator;
                    }
                    if(entity.getName().equals(r.getE2()) && entity.getEntityPackage().equals(r.getE2Package()))
                    {


                        importedPackages +=(importedPackages.contains(r.getE1Package()+"."+r.getE1())? "": "\nimport" +
                                " " + r.getE2Package()+"."+r.getE2()+ ";");
                        attributesDeclaration += "\n" + r.generateE2OneToOneTemplate(language, entity.getFrameworkType())+declarrationSperator;
                    }
                    break;
                case OneToMany:
                    if(entity.getName().equals(r.getE1()) && entity.getEntityPackage().equals(r.getE1Package()))
                    {

                        importedPackages +=(importedPackages.contains(r.getE1Package()+"."+r.getE2())? "": "\nimport " + r.getE2Package()+"."+r.getE2()+ ";");
                        attributesDeclaration += "\n" + r.generateE1OneToManyTemplate(language, entity.getFrameworkType())+declarrationSperator;
                    }
                    if(entity.getName().equals(r.getE2()) && entity.getEntityPackage().equals(r.getE2Package()))
                    {

                        importedPackages +=(importedPackages.contains(r.getE2Package()+"."+r.getE1())? "": "\nimport " + r.getE1Package()+"."+r.getE1()+ ";");
                        attributesDeclaration += "\n" + r.generateE2OneToManyTemplate(language, entity.getFrameworkType())+declarrationSperator;
                    }
                    break;
//                case ManyToMany:
//
//                    break;

            }
        }
        if((attributesDeclaration = attributesDeclaration.trim()).endsWith(","))
        {
            attributesDeclaration = attributesDeclaration.substring(0, attributesDeclaration.lastIndexOf(','));
        }
        HashMap<String, Object> binder = new HashMap<String, Object>();
        String entityAnnotation= "";
        boolean isJpa = false;
        boolean isJdbc = false;
        boolean isNormal = false;
        switch (entity.getDatabaseType().toLowerCase())
        {
            case "mysql":
            case "oracle":
            case "postgres":
            case "h2":
            case "mariadb":
                if(entity.getFrameworkType().equalsIgnoreCase("jpa"))
                    isJpa = true;
                else
                    isJdbc = true;
                break;
            default:
                isJdbc = false;
                isJpa = false;
                isNormal = isJdbc & isJpa;
                entityAnnotation = "";
                break;

        }

        if(language.equalsIgnoreCase(KOTLIN_LANG) || entity.isJavaRecord()) {
            attributesDeclaration = (attributesDeclaration.trim().isEmpty() ? "" : ", " + attributesDeclaration).replace("\n", " ");

        }
        binder.put("entityAnnotation",entityAnnotation );
        binder.put("tableAnnotation","" );
        binder.put("entitypackage", entity.getEntityPackage());
        binder.put("jpa", isJpa && !entity.isNoEndpoints());
        binder.put("jdbc", isJdbc && !entity.isNoEndpoints());
        binder.put("mongo", entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml) && !entity.isNoEndpoints());
        binder.put("normal", (isJpa == false && isJdbc == false) && !entity.isNoEndpoints());
        binder.put("openApi", MicronautProjectValidator.getProjectInfo(path).getApplicationType().equalsIgnoreCase("default") && !entity.isNoEndpoints());
        binder.put("collectionName", entity.getCollectionName()); 
        binder.put("className",entity.getName() );
        binder.put("instances", attributesDeclaration.replaceAll("(?m)^[ \t]*\r?\n", ""));
        binder.put("importedPackages",importedPackages );
        binder.put("containDate", containDate);
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("principle", entity.isSecurityEnabled());
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));

        String templatePath= getTemplatPath(entity.isJavaRecord()?TemplatesService.ENTITY_RECORD:TemplatesService.ENTITY, language.toLowerCase()).replaceAll("(?m)^[ \t]*\r?\n", "");


        String entityTemplate  =templatesService.loadTemplateContent(templatePath);


        //This is to clean up the empty lines
        if(entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData() ) {
            if(language.equalsIgnoreCase(JAVA_LANG))
            {
                entityTemplate = entityTemplate.replace(" <% if(jpa) out.print '@Id '%>\n" +
                        "    <% if(jpa) out.print '@GeneratedValue(strategy = GenerationType.SEQUENCE) '%>\n" +
                        "    <% if(jpa) out.print '@EqualsAndHashCode.Exclude '%>\n" +
                        "    <% if(jpa) out.println 'private Long id;'%>\n" +
                        "    <% if(jdbc) out.print '@Id '%>\n" +
                        "    <% if(jdbc) out.print '@GeneratedValue(GeneratedValue.Type.AUTO) ' %>\n" +
                        "    <% if(jdbc) out.print '@EqualsAndHashCode.Exclude ' %>\n" +
                        "    <% if(jdbc) out.println 'private Long id;'%>\n" +
                        "    <% if(normal) out.println 'private String id;' %>\n" +
                        "    ${instances}\n" +
                        "    <% if(jdbc) out.print '@DateCreated ' %>\n" +
                        "    <% if(jdbc) out.println 'private Date dateCreated;' %>\n" +
                        "    <% if(jdbc) out.print  '@DateUpdated ' %>\n" +
                        "    <% if(jdbc) out.println 'private Date dateUpdated;' %>\n" +
                        "    <% if(jpa) out.print '@DateCreated ' %>\n" +
                        "    <% if(jpa) out.println 'private Date dateCreated;' %>\n" +
                        "    <% if(jpa) out.print '@DateUpdated ' %>\n" +
                        "    <% if(jpa) out.println 'private Date dateUpdated;' %>\n" +
                        "}", " <% if(jpa) out.print '@Id '%>\n" +
                        "    <% if(normal) out.println 'private String id;' %>\n" +
                        "    ${instances}\n" +

                        "}").replace("<% if(jpa) out.print \"@Entity(name =\\\"${collectionName}\\\")\"%>\n" +
                        "<% if(jdbc) out.print \"@MappedEntity(value = \\\"${collectionName}\\\", namingStrategy = Raw.class)\"%>", "")
                        ;
            }
            else if(language.equalsIgnoreCase(GROOVY_LANG))
            {

                entityTemplate = entityTemplate.replace("    <% if(jpa) out.print '@Id'%>\n" +
                        "    <% if(jpa) out.print '@GeneratedValue(strategy = GenerationType.SEQUENCE)' %>\n" +
                        "    <% if(jpa) out.print 'long id'%>\n" +
                        "    <% if(jdbc) out.print '@Id'%>\n" +
                        "    <% if(jdbc) out.print '@GeneratedValue(GeneratedValue.Type.AUTO)' %>\n" +
                        "    <% if(jdbc) out.print 'long id'%>\n" +
                        "    <% if(normal) out.print 'String id;'%>\n" +
                        "    ${instances}\n" +
                        "    <% if(jdbc) out.print '@DateCreated' %>\n" +
                        "    <% if(jdbc) out.print 'Date dateCreated;' %>\n" +
                        "\n" +
                        "    <% if(jdbc) out.print '@DateUpdated' %>\n" +
                        "    <% if(jdbc) out.print 'Date dateUpdated;' %>\n" +
                        "    <% if(jpa) out.print '@DateCreated' %>\n" +
                        "    <% if(jpa) out.print 'Date dateCreated;' %>\n" +
                        "\n" +
                        "    <% if(jpa) out.print '@DateUpdated' %>\n" +
                        "    <% if(jpa) out.print 'Date dateUpdated;' %>\n" +
                        "}","    <% if(normal) out.println 'String id' %>\n" +
                        "    ${instances}\n" +
                        "}").replace("<% if(jpa) out.print \"@Entity(name=\\\"${collectionName}\\\")\"%>\n" +
                        "<% if(jdbc) out.print \"@MappedEntity(value = \\\"${collectionName}\\\", namingStrategy = Raw.class)\"%>\n", "");
            }
            else if(language.equalsIgnoreCase(KOTLIN_LANG)){
                entityTemplate.replace("<% if(jpa) out.print \"@Entity(name=\\\"${collectionName}\\\")\"%>\n" +
                        "<% if(jdbc) out.print \"@MappedEntity(value = \\\"${collectionName}\\\", namingStrategy = Raw.class)\"%>\n","");

            }
        }
        //This is temp resolution for R2DBC, The complete if statement should be deleted after finding the resolution.
        if(entity.getFrameworkType().equalsIgnoreCase("r2dbc"))
        {
            entityTemplate = entityTemplate.replaceAll("Date dateCreated", "String dateCreated");

            entityTemplate = entityTemplate.replaceAll("Date dateUpdated", "String dateUpdated");

        }
        String result = new SimpleTemplateEngine().createTemplate(entityTemplate).make(binder).toString();
        if(!language.equalsIgnoreCase(JAVA_LANG))
            result = result.replace(";", "");
//        else
//            result  =new Formatter().formatSource(result);

        result = result.replace("    \n" +
                "    \n", "");
        return result;//.replaceAll("(?m)^[ \t]*\r?\n", "");
    }
    public String generateRepository(Entity entity, String language, List<EntityRelation> relations) throws IOException, ClassNotFoundException {

        HashMap<String, Object> binder = new HashMap<>();

        String methods = "";
        // Creating extra methods
        Tuple3<String, String, String> findMethodTemplates =  getFindUpdateDeleteMethodsTemplates(entity, language);
        for(EntityAttribute ea : entity.getAttributes())
        {
            HashMap<String, Object> attributeBinder = new HashMap<>(){{
                put("Entity", entity.getName());
                put("Attribute", NameUtils.capitalize(ea.getName()));
                put("type",DataTypeMapper.wrapperMapper.get(ea.getType().toLowerCase()));
                put("attr", NameUtils.camelCase(ea.getName()));
                put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                put("entityClass", entity.getName());
                put("bsonType", DataTypeMapper.bsonMapper.get(ea.getType().toLowerCase()));
                put("moreImports", "");
            }};
            if(ea.isFindByMethod())
                methods  = new StringBuilder().append(methods).append("\n").append(new SimpleTemplateEngine().createTemplate(findMethodTemplates.getV1()).make(attributeBinder).toString()).toString();
            if(ea.isFindAllMethod())
                methods  = new StringBuilder().append(methods).append("\n").append(new SimpleTemplateEngine().createTemplate(findMethodTemplates.getV2()).make(attributeBinder).toString()).toString();
        }

        // end creating extra methods.
        if(!entity.getUpdateByMethods().isEmpty()){

            var update = entity.getUpdateByMethods();
            for(String u : update.keySet())
            {
                EntityAttribute query = entity.getAttributeByName(u);
                String updates = entity.getUpdateByMethods().get(u).stream()
                        .map(x->entity.getAttributeByName(x))
                        .map(x-> x.getDeclaration(language, false).replace("private","").replace(";", "").trim())
                        .reduce((x,y)-> x + ", "+y).orElse("");

                String appendUpdates = "";
                if(entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData())
                {
                    //.append("name", new BsonString(name))
                    final String template = ".append(\"${attr}\", new ${bson}(${attr}))";
                    appendUpdates = entity.getUpdateByMethods().get(u).stream()
                            .map(x->entity.getAttributeByName(x))
                            .map(x->{
                                HashMap<String , String> bind = new HashMap<>(){{
                                    put("attr", x.getName());
                                    put("bson", DataTypeMapper.bsonMapper.get(x.getType().toLowerCase()));

                                }};
                                try {
                                    return new SimpleTemplateEngine().createTemplate(template).make(bind).toString();
                                } catch (ClassNotFoundException e) {
                                    return "";
                                } catch (IOException e) {
                                    return "";
                                }
                            }).reduce((x,y) -> x + y).orElse("");
                }
                HashMap<String, Object> ubinder = new HashMap<>(){{
                    put("Attribute", NameUtils.capitalize(u) );
                    put("type",DataTypeMapper.wrapperMapper.get(query.getType().toLowerCase()));
                    put("updates", updates );
                    put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                    put("attribute", u);
                    put("queryBsonDocument",DataTypeMapper.bsonMapper.get(query.getType().toLowerCase() ));
                    put("block", "");
                }};
                ubinder. put("appendUpdates", appendUpdates);
                methods  = new StringBuilder().append(methods).append("\n").append(new SimpleTemplateEngine().createTemplate(findMethodTemplates.getV3()).make(ubinder).toString()).toString();
            }
        }



        if(relations != null)
        {
            String annotationTemplate = templatesService.loadTemplateContent(getTemplatPath(JOIN_ANNOTATION, language));
            String joinMethodsTemplate = templatesService.loadTemplateContent(getTemplatPath(JOIN_METHODS, language));



            String annotations = relations.stream().map(x->{
                if(x.getRelationType() == OneToOne){
                    return GeneratorUtils.generateFromTemplate(annotationTemplate, new HashMap<String, String>(){{
                        put("attribute", x.getE2().toLowerCase());
                    }});

                }
                else if(x.getRelationType() == OneToMany)
                {
                    return GeneratorUtils.generateFromTemplate(annotationTemplate, new HashMap<String, String>(){{
                        put("attribute", x.getE2().toLowerCase()+ "s");
                    }});

                }
                else
                    return "";
            }).reduce("", (x, y)-> x + "\n" + y);

            methods +="\n"+ GeneratorUtils.generateFromTemplate(joinMethodsTemplate, new HashMap<String, String>(){{
                put("joinAnnotation", annotations);
                put("className", entity.getName());

            }});


        }

        if(!entity.getDatabaseType().toLowerCase().equalsIgnoreCase("mongodb") || entity.isMnData()){
            String repositoryTemplate ="";
            if(entity.getFrameworkType().equalsIgnoreCase("jpa")) {

                binder.put("entityRepositoryPackage", entity.getRepoPackage());
                binder.put("importEntity", entity.getEntityPackage() + "." + entity.getName());
                binder.put("className", entity.getName());
                binder.put("methods", methods);
                binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                binder.put("micrometer", entity.isMicrometer());
                binder.put("moreImports", "");
                String templatePath= getTemplatPath(TemplatesService.REPOSITORY, language.toLowerCase());


                repositoryTemplate = templatesService.loadTemplateContent(templatePath);



            }
            else if(entity.getFrameworkType().equalsIgnoreCase("gorm")) {

                //   if(language.equalsIgnoreCase(GROOVY_LANG) && entity.isGorm()){

                    String templatePath = getTemplatPath(GORM_REPOSITORY, language.toLowerCase());

                    binder.put("repositoryPackage",entity.getRepoPackage() );
                    binder.put("entityPackage", entity.getEntityPackage());
                    binder.put("entityClass", entity.getName());
                    binder.put("storeType", "table");
                binder.put("methods", methods);


                binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                binder.put("micrometer", entity.isMicrometer());
                binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
                binder.put("moreImports", "");
                repositoryTemplate = templatesService.loadTemplateContent(templatePath);
                    return new SimpleTemplateEngine().createTemplate(repositoryTemplate).make(binder).toString();

              //  }
            }
            else if(entity.getFrameworkType().equalsIgnoreCase("jdbc"))
            {
                binder.put("entityRepositoryPackage", entity.getRepoPackage());
                binder.put("importEntity", entity.getEntityPackage() + "." + entity.getName());
                binder.put("className", entity.getName());
                binder.put("dialect", DataTypeMapper.dialectMapper.get(entity.getDatabaseType().toLowerCase()));
                binder.put("methods", methods);
                binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                binder.put("micrometer", entity.isMicrometer());
                binder.put("moreImports", "");
                String templatePath= getTemplatPath(TemplatesService.JDBC_REPOSITORY, language.toLowerCase());


                repositoryTemplate = templatesService.loadTemplateContent(templatePath);
            }
            else if(entity.getFrameworkType().equalsIgnoreCase("r2dbc"))
            {
                binder.put("entityRepositoryPackage", entity.getRepoPackage());
                binder.put("importEntity", entity.getEntityPackage() + "." + entity.getName());
                binder.put("className", entity.getName());
                binder.put("dialect", DataTypeMapper.dialectMapper.get(entity.getDatabaseType().toLowerCase()));
                binder.put("methods", methods);
                binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                binder.put("micrometer", entity.isMicrometer());
                binder.put("moreImports", "");
                String templatePath= getTemplatPath(R2DBC_REPOSITORY, language.toLowerCase());


                repositoryTemplate = templatesService.loadTemplateContent(templatePath);
            }
            else {
                binder.put("entityRepositoryPackage", entity.getRepoPackage());
                binder.put("importEntity", entity.getEntityPackage() + "." + entity.getName());
                binder.put("className", entity.getName());
                binder.put("methods", methods);
                binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                binder.put("micrometer", entity.isMicrometer());
                binder.put("moreImports", "");
                String templatePath= getTemplatPath(DATA_MONGODB_REPOSITORY, language.toLowerCase());
                repositoryTemplate = templatesService.loadTemplateContent(templatePath);

            }
            return new SimpleTemplateEngine().createTemplate(repositoryTemplate).make(binder).toString();
        }
        else if(entity.getDatabaseType().toLowerCase().equalsIgnoreCase("mongodb")) {

            if(language.equalsIgnoreCase(GROOVY_LANG) && entity.isGorm()){

                String templatePath = getTemplatPath(GORM_REPOSITORY, language.toLowerCase());

//                binder.put("importDomains", entity.getEntityPackage()+"."+ entity.getName());
                binder.put("repositoryPackage",entity.getRepoPackage() );
                binder.put("entityPackage", entity.getEntityPackage());
                binder.put("entityClass", entity.getName());
                binder.put("storeType", "collection");
                binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                binder.put("micrometer", entity.isMicrometer());
                binder.put("methods", methods);
                binder.put("moreImports", "");
                binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
                String repositoryTemplate = templatesService.loadTemplateContent(templatePath);
                return new SimpleTemplateEngine().createTemplate(repositoryTemplate).make(binder).toString();

            }
            else
            {
                binder.put("entityRepositoryPackage", entity.getRepoPackage());
                binder.put("entityPackage", entity.getEntityPackage());
                binder.put("projectPackage", entity.getEntityPackage().replace(".domains", ""));
                binder.put("methods", methods);
                binder.put("entityClass", entity.getName());
                binder.put("entityName", NameUtils.camelCase(entity.getName()));
                binder.put("databaseName", entity.getDatabaseName());
                binder.put("collectionName", entity.getCollectionName());
                binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                binder.put("micrometer", entity.isMicrometer());
                binder.put("moreImports", "");
                String templatePath = getTemplatPath(MONGO_REPOSITORY, language.toLowerCase());

                String repositoryTemplate = templatesService.loadTemplateContent(templatePath);
                return new SimpleTemplateEngine().createTemplate(repositoryTemplate).make(binder).toString();
            }
        }
        return "";
    }

    private Tuple3<String, String, String> getFindUpdateDeleteMethodsTemplates(Entity entity, String language) {



        String findtemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language,FIND_BY_DATA_REPO));
        String findAlltemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language,FIND_ALL_BY_DATA_REPO));
        String updateTemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, UPDATE_BY_DATA_REPO));

        if(entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData()){
            findtemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language,FIND_BY_MONGODB_REPO));
            findAlltemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language,FIND_ALL_BY_MONGODB_REPO));
            updateTemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, UPDATE_BY_MONOGO_REPO));

        }
        else {

            if(entity.getFrameworkType().equalsIgnoreCase("r2dbc")){
                findtemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language,FIND_BY_R2DBC_REPO));
                findAlltemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language,FIND_ALL_BY_R2DBC_REPO));

            }
            else {
                findtemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_BY_DATA_REPO));
                findAlltemplate = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_ALL_BY_DATA_REPO));
            }
        }

       return Tuple3.tuple(findtemplate, findAlltemplate, updateTemplate);
    }

    public String generateEntityException(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, Object> binder = new HashMap<>();
        binder.put("exceptionPackage", entity.getExceptionPackage() );
        binder.put("className", entity.getName());
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("micrometer", entity.isMicrometer());
        String templatePath= getTemplatPath(TemplatesService.GENERAL_EXCEPTION, language.toLowerCase());


        String  exceptionTemplate = templatesService.loadTemplateContent(templatePath);

        return new SimpleTemplateEngine().createTemplate(exceptionTemplate).make(binder).toString();
    }

    private String getTemplatPath(String key, String language) {

        if ("groovy".equals(language)) {
            return templatesService.getGroovyTemplates().get(key);
        } else if ("kotlin".equals(language)) {
            return templatesService.getKotlinTemplates().get(key);
        } else if ("java".equals(language)) {
            return templatesService.getJavaTemplates().get(key);
        }
        else
            return templatesService.getJavaTemplates().get(key);
   }

    public String generateEntityExceptionHandler(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, Object> binder = new HashMap<>();
        binder.put("exceptionHanderPackage", entity.getExceptionHandlerPackage() );
        binder.put("excptionPacage", entity.getExceptionPackage());
        binder.put("className", entity.getName());
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("micrometer", entity.isMicrometer());
        String templatePath= getTemplatPath(TemplatesService.EXCEPTION_HANDLER, language.toLowerCase());

        String  exceptionTemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(exceptionTemplate).make(binder).toString();
    }
    public String generateEntityRepositoryTest(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, Object> binder = new HashMap<>();
        binder.put("className",entity.getName() );
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("repositoryPackage",entity.getRepoPackage() );
        binder.put("entityPackage", entity.getEntityPackage());
        binder.put("defaultPackage", entity.getEntityPackage().replace(".domains", ""));
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("micrometer", entity.isMicrometer());

        String templatePath= getTemplatPath(TemplatesService.REPOSITORY_TEST, language.toLowerCase());

        String  entityRepositoryTesttemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(entityRepositoryTesttemplate).make(binder).toString();
    }
    public String generateRandomizer(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, Object> binder = new HashMap<>();
        binder.put("packageName", entity.getEntityPackage().replace(".domains", ""));
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));


        String templatePath= getTemplatPath(TemplatesService.RANDOMIZER, language.toLowerCase());

        String  exceptionTemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(exceptionTemplate).make(binder).toString();
    }



    private String generateServiceGorm(Entity entity, String language) throws IOException, ClassNotFoundException {
        //Todo maybe to be deleted
       String templatePath= getTemplatPath(GORM_SERVICE, language.toLowerCase());

       String serviceTemplate = templatesService.loadTemplateContent(templatePath);

       String updates = entity.getAttributes().stream()
                .map(x->
                        new StringBuilder().append("\t\tobj.").append(x.getName()).append("=").append(NameUtils.camelCase(entity.getName(), true)).append(".").append(x.getName()).toString()
                ).reduce("", (x, y)-> new StringBuilder(x).append("\n").append(y).toString());
        HashMap<String, Object> binder = new HashMap<>();
        binder.put("servicePackage", entity.getServicePackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("repoPackage", entity.getRepoPackage()+"."+entity.getName()+"Repository");
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("className", entity.getName());
        binder.put("updates", updates);
        binder.put("methods", "");
        binder.put("cached", entity.isCached());
        binder.put("tableName", entity.getCollectionName()) ;
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("micrometer", entity.isMicrometer());
        binder.put("moreImports", "");
        binder.put("principle", entity.isSecurityEnabled());
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }
    public String generateService(Entity entity, String language) throws IOException, ClassNotFoundException {

        if(language.equalsIgnoreCase(GROOVY_LANG) && entity.isGorm()) {
            return generateServiceGorm(entity, language);
        }

        String methods = "";
        var findUpdateTemplates = getFindUpdateTemplates(language,"service");

        String returnType = entity.getName();
        String updateReturnType = "Long";
        String returnTypeList = "Iterable<"+entity.getName() + ">";
        String blocking = ".orElse(null)";

        if(entity.getFrameworkType().equalsIgnoreCase("r2dbc") || (entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData()&& entity.getReactiveFramework().equalsIgnoreCase("reactor")))
        {
            returnType = "Mono<"+ entity.getName() + ">";
            returnTypeList = "Flux<"+ entity.getName() + ">";
            updateReturnType = "Mono<Long>";
            blocking = "";
        }
        else if (entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData()){
            returnType = "Single<"+ entity.getName() + ">";
            returnTypeList = "Flowable<"+ entity.getName() + ">";
            updateReturnType = "Single<Long>";
            blocking = "";
        }
        for(EntityAttribute ea : entity.getAttributes()) {


            HashMap<String, Object> sBinder = new HashMap<String, Object>() {{
                put("servicePackage", entity.getServicePackage());
                put("entityPackage", entity.getEntityPackage() + "." + entity.getName());
                put("repoPackage", entity.getRepoPackage() + "." + entity.getName() + "Repository");
                put("entityName", NameUtils.camelCase(entity.getName(), true));
                put("className", entity.getName());

                put("cached", entity.isCached());
                put("tableName", entity.getCollectionName());
                put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                put("micrometer", entity.isMicrometer());
                put("Attribute", NameUtils.capitalize(ea.getName()));
                put("attributeType", DataTypeMapper.wrapperMapper.get(ea.getType()));
                put("moreImports", "");

            }};
            sBinder.put("principle", entity.isSecurityEnabled());
            sBinder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));

            sBinder.put("blocking", blocking);
            sBinder.put("returnType", returnType);
            sBinder.put("returnTypeList", returnTypeList);
            if (ea.isFindAllMethod())
            {
                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV2()).make(sBinder)).toString();
            }
            if(ea.isFindByMethod()){

                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV1()).make(sBinder)).toString();
            }

        }
        if(!entity.getUpdateByMethods().isEmpty()){

            var update = entity.getUpdateByMethods();
            for(String u : update.keySet())
            {
                EntityAttribute query = entity.getAttributeByName(u);
                String updates = entity.getUpdateByMethods().get(u).stream()
                        .map(x->entity.getAttributeByName(x))
                        .map(x-> x.getDeclaration(language, false).replace("private","").replace(";", "").trim())
                        .reduce((x,y)-> x + ", "+y).orElse("");

                String updatesVariables = entity.getUpdateByMethods().get(u).stream()
                        .reduce((x,y)-> x + ", "+y).orElse("");
                HashMap<String, Object> ubinder = new HashMap<>(){{
                    put("micrometer", entity.isMicrometer());
                    put("servicePackage", entity.getServicePackage() );
                    put("entityName", NameUtils.camelCase(entity.getName(), true));
                    put("Attribute", NameUtils.capitalize(u) );
                    put("type",DataTypeMapper.wrapperMapper.get(query.getType().toLowerCase()));
                    put("updates", updates );
                    put("updatesVariables", updatesVariables);
                    put("block", "");
                }};
                ubinder.put("principle", entity.isSecurityEnabled());
                ubinder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
                ubinder.put("returnType", updateReturnType);
                methods  = new StringBuilder().append(methods).append("\n").append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV3()).make(ubinder).toString()).toString();
            }
        }



        HashMap<String, Object> binder = new HashMap<>();
        binder.put("servicePackage", entity.getServicePackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("repoPackage", entity.getRepoPackage()+"."+entity.getName()+"Repository");
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("className", entity.getName());
        binder.put("methods", methods);
        binder.put("idType", entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml)? "String": (language.equalsIgnoreCase(KOTLIN_LANG)? "Long":"long"));
        binder.put("cached", entity.isCached());
        binder.put("transactional", entity.isMnData() && !entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml));
        binder.put("tableName", entity.getCollectionName()) ;
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("micrometer", entity.isMicrometer());
        binder.put("moreImports", "");
        binder.put("principle", entity.isSecurityEnabled());
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
        String serviceTemplate = "";
        String templatePath="";

        switch (entity.getDatabaseType().toLowerCase())
        {
            case "mongodb":

                 templatePath= getTemplatPath(entity.isMnData()?SERVICE:TemplatesService.MONGO_SERVICE, language.toLowerCase());
                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
            default:
                String templateKey = (entity.getFrameworkType().equalsIgnoreCase("r2dbc"))?   R2DBC_SERVICE : SERVICE;

                templatePath= getTemplatPath(templateKey, language.toLowerCase());
                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
        }

        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }

    private Tuple3<String, String, String> getFindUpdateTemplates(String language, String type) {
        String find = "";
        String findAll = "";
        String update = "";
        switch (type) {
            case "service":
                find = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_BY_SERVICE));
                findAll = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_All_BY_SERVICE));
                update = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, UPDATE_BY_SERVICE));
            break;
            case "controller":
                find = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_BY_CONTROLLER));
                findAll = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_All_BY_CONTROLLER));
                update = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, UPDATE_BY_CONTROLLER));
                break;
            case "client":
                find = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_BY_CLIENT));
                findAll = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_All_BY_CLIENT));
                update = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, UPDATE_BY_CLIENT));

                break;
            case "graphql":
                find = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_BY_GRAPHQL));
                findAll = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, FIND_All_BY_GRAPHQL));
                update = templatesService.loadTemplateContent(templatesService.getKeyByLanguage(language, UPDATE_BY_GRAPHQL));
                break;


        }
        return Tuple2.tuple(find, findAll, update);

    }

    public String generateControllerGorm(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, Object> binder = new HashMap<>();
        binder.put("controllerPackage", entity.getRestPackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("servicePackage", entity.getServicePackage()+"."+entity.getName()+"Service");
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("jaeger", entity.isTracingEnabled());
        binder.put("micrometer", entity.isMicrometer());
        binder.put("methods", "");
        binder.put("className", entity.getName());
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("moreImports", "");
        binder.put("principle", entity.isSecurityEnabled());
        binder.put("jaxrs", entity.isJaxRs());
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
        String templatePath = getTemplatPath(GORM_CONTROLLER, language.toLowerCase());

        String controllerTemplate = templatesService.loadTemplateContent(templatePath);

        return new SimpleTemplateEngine().createTemplate(controllerTemplate).make(binder).toString();


    }

    public String generateController(Entity entity, String language) throws IOException, ClassNotFoundException {
        if(language.equalsIgnoreCase(GROOVY_LANG) && entity.isGorm())
        {
            return generateControllerGorm(entity, language);

        }
        String methods = "";
        var findUpdateTemplates = getFindUpdateTemplates(language,"controller");

        String returnType = entity.getName();
        String updateReturnType = "Long";
        String returnTypeList = "Iterable<"+entity.getName() + ">";
        if(entity.getFrameworkType().equalsIgnoreCase("r2dbc") || (entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData() && entity.getReactiveFramework().equalsIgnoreCase("reactor")))
        {
            returnType = "Mono<"+ entity.getName() + ">";
            returnTypeList = "Flux<"+ entity.getName() + ">";
            updateReturnType = "Mono<Long>";
        }
        else if(entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData() ){
            returnType = "Single<"+ entity.getName() + ">";
            returnTypeList = "Flowable<"+ entity.getName() + ">";
            updateReturnType = "Single<Long>";

        }
        for(EntityAttribute ea : entity.getAttributes()) {


            HashMap<String, Object> sBinder = new HashMap<String, Object>() {{
                put("controllerPackage", entity.getRestPackage());
                put("servicePackage", entity.getRestPackage());
                put("entityPackage", entity.getEntityPackage() + "." + entity.getName());
                put("repoPackage", entity.getRepoPackage() + "." + entity.getName() + "Repository");
                put("entityName", NameUtils.camelCase(entity.getName(), true));
                put("className", entity.getName());
                put("jaeger", entity.isTracingEnabled());
                put("jaxrs", entity.isJaxRs());
                put("cached", entity.isCached());
                put("tableName", entity.getCollectionName());
                put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                put("micrometer", entity.isMicrometer());
                put("Attribute", NameUtils.capitalize( ea.getName()));
                put("attributeType", DataTypeMapper.wrapperMapper.get(ea.getType()));
                put("moreImports", "");
                put("principle", entity.isSecurityEnabled());
                put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));


            }};
            sBinder.put("returnType", returnType);
            sBinder.put("returnTypeList", returnTypeList);
            if (ea.isFindAllMethod())
            {
                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV2()).make(sBinder)).toString();
            }
            if(ea.isFindByMethod()){

                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV1()).make(sBinder)).toString();
            }

        }
        if(!entity.getUpdateByMethods().isEmpty()){

            var update = entity.getUpdateByMethods();
            for(String u : update.keySet())
            {
                EntityAttribute query = entity.getAttributeByName(u);
                String updates = entity.getUpdateByMethods().get(u).stream()
                        .map(x->entity.getAttributeByName(x))
                        .map(x-> x.getDeclaration(language, false).replace("private","").replace(";", "").trim())
                        .reduce((x,y)-> x + ", "+y).orElse("");

                String updatesVariables = entity.getUpdateByMethods().get(u).stream()
                        .map(x-> {

                            String template = "body.get${key}()";
                            if(entity.isJavaRecord())
                            {
                                template = "body.${key}()";
                            }
                            if(language.equalsIgnoreCase(KOTLIN_LANG))
                            {
                                template = "body.${key}";
                            }

                            var b = new HashMap<String, String>(){{
                                put("key",language.equalsIgnoreCase(KOTLIN_LANG) || entity.isJavaRecord()? x: NameUtils.capitalize(x));
                            }};
                            try {
                                return new SimpleTemplateEngine().createTemplate(template).make(b).toString();
                            } catch (ClassNotFoundException e) {
                                return "";
                            } catch (IOException e) {
                                return "";
                            }
                        })
                        .reduce((x,y)-> x + ", "+y).orElse("");
                HashMap<String, Object> ubinder = new HashMap<>(){{
                    put("micrometer", entity.isMicrometer());
                    put("servicePackage", entity.getServicePackage() );
                    put("entityName", NameUtils.camelCase(entity.getName(), true));
                    put("Attribute", NameUtils.capitalize(u) );
                    put("type",DataTypeMapper.wrapperMapper.get(query.getType().toLowerCase()));
                    put("updates", updates );
                    put("className", entity.getName());
                    put("jaeger", entity.isTracingEnabled());
                    put("controllerPackage", entity.getRestPackage());
                    put("updatesVariables", updatesVariables);
                    put("block", "");
                }};
                ubinder.put("jaxrs", entity.isJaxRs());
                ubinder.put("principle", entity.isSecurityEnabled());
                ubinder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
                ubinder.put("updates",entity.getUpdateByMethods().get(u).stream()
                       .reduce((x,y)-> x + ", "+y).orElse("") );
                ubinder.put("returnType", updateReturnType);
                methods  = new StringBuilder().append(methods).append("\n").append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV3()).make(ubinder).toString()).toString();
            }
        }



        HashMap<String, Object> binder = new HashMap<>();
        binder.put("controllerPackage", entity.getRestPackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("servicePackage", entity.getServicePackage()+"."+entity.getName()+"Service");
        binder.put("entityName",NameUtils.camelCase(entity.getName(), true));
        binder.put("entities", NameUtils.camelCase(entity.getName(), true));
        binder.put("micrometer", entity.isMicrometer());
        binder.put("jaeger", entity.isTracingEnabled());
        binder.put("methods", methods);
        binder.put("idType", entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml)? "String": (language.equalsIgnoreCase(KOTLIN_LANG)? "Long":"long"));
        binder.put("className", entity.getName());
        binder.put("moreImports", "");
        binder.put("jaxrs", entity.isJaxRs());
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("principle", entity.isSecurityEnabled());
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
        String serviceTemplate ;
        String templatePath="";
        switch (entity.getDatabaseType().toLowerCase())
        {
            case "mongodb":
                templatePath= getTemplatPath(entity.isMnData()?TemplatesService.CONTROLLER:TemplatesService.MONGO_CONTROLLER, language.toLowerCase());
                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
            default:
                String templateKey = (entity.getFrameworkType().equalsIgnoreCase("r2dbc"))?   R2DBC_CONTROLLER :TemplatesService.CONTROLLER;


                templatePath= getTemplatPath(templateKey, language.toLowerCase());

                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
        }
        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }

    public String generateClientGorm(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, Object> binder = new HashMap<>();
        binder.put("clientPackage", entity.getClientPackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("className",  entity.getName());
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("methods", "");
        binder.put("moreImports", "");
        binder.put("principle", entity.isSecurityEnabled());
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
        String templatePath= getTemplatPath(GORM_CLIENT, language.toLowerCase());
        String  serviceTemplate = templatesService.loadTemplateContent(templatePath);
        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();

    }
    public String generateClient(Entity entity, String language) throws IOException, ClassNotFoundException {


        if(entity.isGorm() && language.equalsIgnoreCase(GROOVY_LANG))
            return generateClientGorm(entity, language);
        String methods = "";
        var findUpdateTemplates = getFindUpdateTemplates(language,"client");

        String returnType = entity.getName();
        String updateReturnType = "Long";
        String returnTypeList = "Iterable<"+entity.getName() + ">";
        if(entity.getFrameworkType().equalsIgnoreCase("r2dbc") || (entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData() && entity.getReactiveFramework().equalsIgnoreCase("reactor")))
        {
            returnType = "Mono<"+ entity.getName() + ">";
            returnTypeList = "Flux<"+ entity.getName() + ">";
            updateReturnType = "Mono<Long>";
        }
        else if(entity.getDatabaseType().equalsIgnoreCase("mongodb")  && !entity.isMnData()){
            returnType = "Single<"+ entity.getName() + ">";
            returnTypeList = "Flowable<"+ entity.getName() + ">";
            updateReturnType = "Single<Mono>";
        }
        for(EntityAttribute ea : entity.getAttributes()) {


            HashMap<String, Object> sBinder = new HashMap<String, Object>() {{
                put("controllerPackage", entity.getRestPackage());
                put("servicePackage", entity.getRestPackage());
                put("entityPackage", entity.getEntityPackage() + "." + entity.getName());
                put("repoPackage", entity.getRepoPackage() + "." + entity.getName() + "Repository");
                put("entityName", NameUtils.camelCase(entity.getName(), true));
                put("className", entity.getName());
                put("jaeger", entity.isTracingEnabled());
                put("cached", entity.isCached());
                put("tableName", entity.getCollectionName());
                put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                put("micrometer", entity.isMicrometer());
                put("Attribute", NameUtils.capitalize( ea.getName()));
                put("attributeType", DataTypeMapper.wrapperMapper.get(ea.getType()));
                put("moreImports", "");
            }};
            sBinder.put("returnType", returnType);
            sBinder.put("returnTypeList", returnTypeList);
            sBinder.put("principle", entity.isSecurityEnabled());
            sBinder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));

            if (ea.isFindAllMethod())
            {
                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV2()).make(sBinder)).toString();
            }
            if(ea.isFindByMethod()){

                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV1()).make(sBinder)).toString();
            }

        }

        if(!entity.getUpdateByMethods().isEmpty()){

            var update = entity.getUpdateByMethods();
            for(String u : update.keySet())
            {
                EntityAttribute query = entity.getAttributeByName(u);
                String updates = entity.getUpdateByMethods().get(u).stream()
                        .map(x->entity.getAttributeByName(x))
                        .map(x-> x.getDeclaration(language, false).replace("private","").replace(";", "").trim())
                        .reduce((x,y)-> x + ", "+y).orElse("");


                HashMap<String, Object> ubinder = new HashMap<>(){{
                    put("entityName", NameUtils.camelCase(entity.getName(), true));
                    put("Attribute", NameUtils.capitalize(u) );
                    put("type",DataTypeMapper.wrapperMapper.get(query.getType().toLowerCase()));
                    put("className", entity.getName());
                    put("block", "");
                }};
                ubinder.put("principle", entity.isSecurityEnabled());
                ubinder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
                ubinder.put("returnType", updateReturnType);
                methods  = new StringBuilder().append(methods).append("\n").append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV3()).make(ubinder).toString()).toString();
            }
        }



        HashMap<String, Object> binder = new HashMap<>();
        binder.put("clientPackage", entity.getClientPackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("entities", NameUtils.camelCase(entity.getName(), true));
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("methods", methods);
        binder.put("idType", entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml)? "String": (language.equalsIgnoreCase(KOTLIN_LANG)? "Long":"long"));
        binder.put("className",  entity.getName());
        binder.put("classNameA", entity.getName());
        binder.put("moreImports", "");
        binder.put("principle", entity.isSecurityEnabled());
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
        String key = (entity.getFrameworkType().equalsIgnoreCase("r2dbc"))?   R2DBC_CLIENT : CLIENT;

        if("MongoDB".equalsIgnoreCase(entity.getDatabaseType()) && !entity.isMnData())
            key = TemplatesService.MONGO_CLIENT;
        String templatePath= getTemplatPath(key, language.toLowerCase());


        String  serviceTemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }


    public String generateGraphQLFactory(HashSet<Entity> entities, String language) throws IOException, ClassNotFoundException {

       List<Entity > gqEntities = entities.stream().filter(x-> x.isGraphQl()).collect(Collectors.toList());

        String QueryResolvers =(!language.equalsIgnoreCase(KOTLIN_LANG))? gqEntities.stream().map(x-> x.getName()+"QueryResolver" + " " +x.getName().toLowerCase()+"QueryResolver")
                .reduce("" ,(x, y) -> x+", " +y).replaceFirst(",", "")
                //else
                :gqEntities.stream().map(x->  "" +x.getName().toLowerCase()+"QueryResolver " + ":" + x.getName()+"QueryResolver")
                .reduce("" ,(x, y) -> x+", " +y).replaceFirst(",", "")
                ;

        String schemafiles = gqEntities.stream().map(x-> "\""+x.getName() + ".graphqls\"")
                .reduce("", (x,y) -> x+ "," + y);
        String resolverObject = gqEntities.stream().map(x->x.getName().toLowerCase()+"QueryResolver")
                .reduce("", (x,y) -> x + ","+ y).replaceFirst(",", "");;


        HashMap<String, Object> binder = new HashMap<>();
        binder.put("pack", gqEntities.get(0).getGraphqlpackage() );


        binder.put("QueryResolvers", QueryResolvers);
        binder.put("schemafiles",  schemafiles);
        binder.put("resolverObject", resolverObject);
        binder.put("micrometer", entities.stream().anyMatch(Entity::isMicrometer));

        String key = TemplatesService.GRAPHQL_QUERY_FACOTRY;
        String templatePath= getTemplatPath(key, language.toLowerCase());

        String  serviceTemplate = templatesService.loadTemplateContent(templatePath);

        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }

    public String generateGraphQLResolver(Entity entity, String language) throws IOException, ClassNotFoundException {



        //Todo implement methods.


        String methods = "";
        var findUpdateTemplates = getFindUpdateTemplates(language,"graphql");

        String returnType = entity.getName();
        String returnTypeList = "Iterable<"+entity.getName() + ">";
        String updateReturnType = "Long";

        String blockSingle = "";
        String blockIter = "";
        if(entity.getFrameworkType().equalsIgnoreCase("r2dbc") || (entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData() && entity.getReactiveFramework().equalsIgnoreCase("reactor")))
        {
            blockSingle = ".block()";
            blockIter = ".toIterable()";
        }
        else if (entity.getDatabaseType().equalsIgnoreCase("mongodb") && !entity.isMnData()){
            blockSingle = ".getBlocking()";
            blockIter = ".blockingIterable()";
        }
        boolean principal = entity.isSecurityEnabled();
        for(EntityAttribute ea : entity.getAttributes()) {


            HashMap<String, Object> sBinder = new HashMap<String, Object>() {{
                put("servicePackage", entity.getServicePackage());
                put("entityPackage", entity.getEntityPackage() + "." + entity.getName());
                put("repoPackage", entity.getRepoPackage() + "." + entity.getName() + "Repository");
                put("entityName", NameUtils.camelCase(entity.getName(), true));
                put("className", entity.getName());
                put("pack", entity.getGraphqlpackage());
                put("cached", entity.isCached());
                put("tableName", entity.getCollectionName());
                put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
                put("micrometer", entity.isMicrometer());
                put("Attribute", NameUtils.capitalize(ea.getName()));
                put("attributeType", DataTypeMapper.wrapperMapper.get(ea.getType()));
                put("moreImports", "");


            }};
            sBinder.put("blockSingle", blockSingle);
            sBinder.put("blockIter", blockIter);
            sBinder.put("returnType", returnType);
            sBinder.put("returnTypeList", returnTypeList);
            sBinder.put("principal", principal);
            sBinder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));
            if (ea.isFindAllMethod())
            {
                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV2()).make(sBinder)).toString();
            }
            if(ea.isFindByMethod()){

                methods = new StringBuilder().append(methods).append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV1()).make(sBinder)).toString();
            }

        }


//        if(!entity.getUpdateByMethods().isEmpty()){
//
//            var update = entity.getUpdateByMethods();
//            for(String u : update.keySet())
//            {
//                EntityAttribute query = entity.getAttributeByName(u);
//                String updates = entity.getUpdateByMethods().get(u).stream()
//                        .map(x->entity.getAttributeByName(x))
//                        .map(x-> x.getDeclaration(language).replace("private","").replace(";", "").trim())
//                        .reduce((x,y)-> x + ", "+y).orElse("");
//
//                String updatesVariables = entity.getUpdateByMethods().get(u).stream()
//                        .map(x-> {
//
//                            String template = "body.get${key}()";
//
//                            var b = new HashMap<String, String>(){{
//                                put("key",NameUtils.capitalize(x));
//                            }};
//                            try {
//                                return new SimpleTemplateEngine().createTemplate(template).make(b).toString();
//                            } catch (ClassNotFoundException e) {
//                                return "";
//                            } catch (IOException e) {
//                                return "";
//                            }
//                        })
//                        .reduce((x,y)-> x + ", "+y).orElse("");
//                HashMap<String, Object> ubinder = new HashMap<>(){{
//                    put("pack", entity.getEntityPackage());
//                    put("micrometer", entity.isMicrometer());
//                    put("servicePackage", entity.getServicePackage() );
//                    put("entityName", NameUtils.camelCase(entity.getName(), true));
//                    put("Attribute", NameUtils.capitalize(u) );
//                    put("type",DataTypeMapper.wrapperMapper.get(query.getType().toLowerCase()));
//                    put("updates", updates );
//                    put("className", entity.getName());
//                    put("jaeger", entity.isTracingEnabled());
//                    put("controllerPackage", entity.getRestPackage());
//                    put("updatesVariables", updatesVariables);
//                }};
//                ubinder.put("block", blockSingle);
//                ubinder.put("returnType", updateReturnType);
//                methods  = new StringBuilder().append(methods).append("\n").append(new SimpleTemplateEngine().createTemplate(findUpdateTemplates.getV3()).make(ubinder).toString()).toString();
//            }
//        }
//


        HashMap<String, Object> binder = new HashMap<>();
        binder.put("pack", entity.getGraphqlpackage() );
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("className",  entity.getName());
//        binder.put("idType", entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml)? "String": (language.equalsIgnoreCase(KOTLIN_LANG)? "Long":"long"));
        binder.put("domainPackage", entity.getEntityPackage());
        binder.put("servicePackage", entity.getServicePackage());
        binder.put("reactor", entity.getReactiveFramework().equalsIgnoreCase("reactor"));
        binder.put("micrometer", entity.isMicrometer()) ;
        binder.put("methods", methods);
        binder.put("moreImports", "");
        binder.put("principal", entity.isSecurityEnabled());
        String idType  =language.equalsIgnoreCase(KOTLIN_LANG)? "Long": "long";
        binder.put("idType",entity.getDatabaseType().toLowerCase().contains("mongodb")? "String":idType);
        binder.put("header", entity.getSecurityStrategy().equalsIgnoreCase("jwt"));

        String key = (entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml) && !entity.isMnData() && !entity.isGorm())? TemplatesService.GRAPHQL_REACTIVE_QUERY_RESOLVER : TemplatesService.GRAPHQL_QUERY_RESOLVER;

        String templatePath= getTemplatPath(key, language.toLowerCase());

        String  serviceTemplate = templatesService.loadTemplateContent(templatePath);

        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }
    public String generateEnum(EnumClass enumClass, String language) throws IOException, ClassNotFoundException {


        String templatePath= getTemplatPath(TemplatesService.ENUM, language.toLowerCase());

        String template = templatesService.loadTemplateContent(templatePath);

//        enumPackage, className, options
        HashMap<String, Object> map = new HashMap<>();
        map.put(
                "enumPackage", enumClass.getEnumPackage()
        );
        map.put("className", enumClass.getName());
        StringBuilder options = new StringBuilder("");
        for (String value : enumClass.getValues()) {
            options.append(value).append(", ");
        }
        options.deleteCharAt(options.lastIndexOf(","));
        map.put("options", options.toString());
        return new SimpleTemplateEngine().createTemplate(template).make(map).toString();


    }


    //to extract relations
    private Set<EntityRelation> getRelations(Entity e, ArrayList<EntityRelation> relations)
    {

        if(relations == null) return null;
        return relations.stream().filter(x->{
            return (x.getE1().equals(e.getName()) && x.getE1Package().equals(e.getEntityPackage())) ||
                    (x.getE2().equals(e.getName()) && x.getE2Package().equals(e.getEntityPackage()));
        }).collect(Collectors.toSet());
    }


    public ArrayList<Tuple2<String, String>> generateEntityFiles(DomainsRequest domainsRequest) throws IOException, ClassNotFoundException, FormatterException {


        String rootPath = "src/main/"+domainsRequest.getLanguage().toLowerCase() + "/";//
        ArrayList<Tuple2<String, String>> result = new ArrayList<Tuple2<String, String>>();
        for(Entity e : domainsRequest.getEntities()) {
            result.addAll(generateEntityFiles(
                    e,
                    domainsRequest.getEntityRelations(),
                    domainsRequest.getLanguage()
            , rootPath));
        }
        if(!domainsRequest.getDatabaseType().toLowerCase().contains("h2") && !domainsRequest.getDatabaseType().toLowerCase().contains("mongo"))
        {
            result.add(generateSqlFiles(domainsRequest));
        }
        if(!domainsRequest.getEnumClasses().isEmpty())
            result.addAll(generateEnumFiles(domainsRequest));
        return result;

    }

    public Tuple2<String, String> generateSqlFiles(DomainsRequest domainsRequest){
        String rootPath = "src/main/resources/sql/";//
        String filename  = "SCHEMA_GENERATOR.sql";

        StringBuilder result = new StringBuilder("");
        for (Entity entity : domainsRequest.getEntities()) {
            result.append(sqlSchemaGenerator.generateTable(entity, domainsRequest.getEntityRelations(), domainsRequest.getErMapper()));
            result.append("\n\n");
        }
        return

                //Tuples.of
                  Tuple2.tuple(rootPath+filename, result.toString());
    }
    public ArrayList<Tuple2<String, String>> generateEnumFiles(DomainsRequest  domainsRequest) throws IOException, ClassNotFoundException {
        String rootPath = "src/main/"+domainsRequest.getLanguage().toLowerCase() + "/";
        ArrayList<Tuple2<String, String>> result = new ArrayList<Tuple2<String, String>>();

        String fileExtension =".java";
        switch (domainsRequest.getLanguage().toLowerCase())
        {
            case JAVA_LANG:
                fileExtension =".java";
                break;
            case GROOVY_LANG:
                fileExtension =".groovy";
                break;
            case KOTLIN_LANG:
                fileExtension = ".kt";
                break;
            default:
                fileExtension = ".java";
                break;
        }
        for (EnumClass enumClass : domainsRequest.getEnumClasses()) {

            result.add(Tuple2.tuple(rootPath + (domainsRequest.getPackage()+".domains.enums").replace(".", "/") + "/" + enumClass.getName()+"."+fileExtension, generateEnum(enumClass, domainsRequest.getLanguage().toLowerCase())));
        }

        return result;
    }
    public ArrayList<Tuple2<String, String>> generateEntityFiles(Entity entity,
                                                                 ArrayList<EntityRelation> entityRelations,
                                                                 String language, String rootPath) throws IOException,
            ClassNotFoundException, FormatterException {
        String fileExtension = ".java";
        switch (language)
        {
            case JAVA_LANG:
                fileExtension =".java";
                break;
            case GROOVY_LANG:
                fileExtension =".groovy";
                break;
            case KOTLIN_LANG:
                fileExtension = ".kt";
                break;
            default:
                fileExtension = ".java";
                break;
        }
        ArrayList<Tuple2<String, String>> result = new ArrayList<Tuple2<String, String>>();
        result.add(Tuple2.tuple(rootPath+entity.getEntityPackage().replace(".", "/")+ "/"+ entity.getName() + fileExtension, generateEntity(entity,
                entityRelations,
                language )));
        result.add(Tuple2.tuple(rootPath+entity.getRepoPackage().replace(".", "/")+ "/"+entity.getName()+
                        "Repository" + fileExtension,
                generateRepository(entity,
                language, null)));
        result.add(Tuple2.tuple(rootPath+entity.getServicePackage().replace(".", "/")+ "/"+ entity.getName() +"Service" + fileExtension, generateService(entity,
                language )));
        result.add(Tuple2.tuple(rootPath+entity.getRestPackage().replace(".", "/")+ "/"+ entity.getName() +"Controller" +fileExtension,
                generateController(entity,
                language )));
        result.add(Tuple2.tuple(rootPath+entity.getClientPackage().replace(".", "/")+ "/"+ entity.getName() +"Client" +fileExtension,
                generateClient(entity,
                        language )));


        if(language.equalsIgnoreCase(JAVA_LANG))
        {
            result.add(Tuple2.tuple(rootPath+entity.getExceptionPackage().replace(".", "/")+ "/"+ entity.getName() +"GeneralException" +fileExtension,
                    generateEntityException(entity,
                            language )));
            result.add(Tuple2.tuple(rootPath+entity.getExceptionHandlerPackage().replace(".", "/")+ "/"+ entity.getName() +"ExceptionHandler" +fileExtension,
                    generateEntityExceptionHandler(entity,
                            language )));

            if(entity.getFrameworkType().equalsIgnoreCase("jdbc") || entity.getFrameworkType().equalsIgnoreCase("jpa"))
            {
                result.add(Tuple2.tuple(rootPath.replaceFirst("main", "test")+entity.getRepoPackage().replace(".", "/")+ "/"+ entity.getName() +"RepositoryTest" +fileExtension,
                        generateEntityRepositoryTest(entity,
                                language )));

                result.add(Tuple2.tuple(rootPath.replaceFirst("main", "test")+entity.getEntityPackage().replace("domains", "utils").replace(".", "/")+ "/"+ "Randomizer" +fileExtension,
                        generateRandomizer(entity,
                                language )));
            }
        }



        return result;

    }


    public String generateFromTemplate(Entity entity, String lang, HashMap<String, String> binder, String templateKey ) throws IOException, ClassNotFoundException {
        return new SimpleTemplateEngine()
                .createTemplate(
                        templatesService.loadTemplateContent(getTemplatPath(templateKey, lang.toLowerCase())))
                .make(binder).toString();

    }
    public String generateTestController(Entity entity , String language, String testFramework) throws IOException, ClassNotFoundException{

        HashMap<String, Object> binder = new HashMap<String, Object>();
        binder.putIfAbsent("controllerPackage", entity.getRestPackage());
        binder.put("entityPackage", entity.getEntityPackage());
        binder.put("defaultPackage", entity.getEntityPackage().replace(".domains", ""));
        binder.put("className", entity.getName());
        binder.put("entityName", NameUtils.camelCase(entity.getName(), true));
        binder.put("afterBeforeMethods", "");
        binder.put("moreImports", "");
        binder.put("header", entity.isSecurityEnabled() && entity.getSecurityStrategy().toLowerCase().contains("jwt"));
        binder.put("basic", entity.isSecurityEnabled() && !entity.getSecurityStrategy().toLowerCase().contains("jwt"));

        String keyTemplate = TemplatesService.CONTROLLER_UNIT_TEST;
        switch(testFramework.toLowerCase())
        {
            case "spock":
                keyTemplate = TemplatesService.CONTROLLER_SPOCK_TEST;
                language = GROOVY_LANG;
                break;
            case "kotlintest":
                keyTemplate = TemplatesService.CONTROLLER_KOTLIN_TEST;
                language = KOTLIN_LANG;
                break;
            default:
                keyTemplate = TemplatesService.CONTROLLER_UNIT_TEST;
        }
        return new SimpleTemplateEngine()
                .createTemplate(
                        templatesService.loadTemplateContent(getTemplatPath(keyTemplate, language.toLowerCase())))
                .make(binder).toString();
    }

    public String generateEnumGraphQL(EnumClass enumClass) throws IOException, ClassNotFoundException {

        HashMap<String, String> map = new HashMap<>();

        map.put("className", enumClass.getName());
        StringBuilder options = new StringBuilder("");
        for (String value : enumClass.getValues()) {
            options.append(value).append(", ");
        }
        options.deleteCharAt(options.lastIndexOf(","));
        map.put("options", options.toString());
       String template =  templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(GRAPHQL_ENUM));


       return new SimpleTemplateEngine()
                .createTemplate(template).make(map).toString();
    }


    public String generateGraphQLSchema(Entity entity) throws IOException, ClassNotFoundException
    {


        HashMap<String, String> map = new HashMap<>();
        map.put("className", entity.getName());
        if(entity.getDatabaseType().equalsIgnoreCase(MONGODB_yml))
            map.put("idType", "String");
        else
            map.put("idType", "Int");
       String attributesDeclaration = "";
       if(!entity.getAttributes().isEmpty()) {
           attributesDeclaration=  entity.getAttributes().stream()
                   .map(x->x.graphQLDeclaration())
                   .reduce("" , (x, y) ->x+y);
           attributesDeclaration = attributesDeclaration.substring(0, attributesDeclaration.lastIndexOf(","));
       }
        map.put("attributes", attributesDeclaration);
        String template =  templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(GRAPHQL_SCHEMA));


        return new SimpleTemplateEngine()
                .createTemplate(template).make(map).toString();
    }
    public String generateGraphQLQuery(HashSet<Entity> entities) throws IOException, ClassNotFoundException {

        List<Entity> gqEntities = entities.stream().filter(x -> x.isGraphQl()).collect(Collectors.toList());



        String methodTemplate =  templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(GRAPHQL_QUERY_METHOD));
        String methods = gqEntities.stream().map(x->{

            HashMap<String, String> map = new HashMap<>();
            String findAllTemplate =  templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(FIND_All_BY_GRAPHQL));
            String findByTemplate =  templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(FIND_BY_GRAPHQL));
            String findOne = "", findAll = "";
            for(EntityAttribute ea : x.getAttributes())
            {
               // String findMethods ="";
                HashMap<String, Object> sBinder = new HashMap<>();

                sBinder.put("className", x.getName());
                sBinder.put("Attribute", NameUtils.capitalize(ea.getName()));
                sBinder.put("Type", DataTypeMapper.graphqlMapper.get(ea.getType().toLowerCase()));


                if(ea.isFindByMethod())
                {
                    try {
                        findOne += new SimpleTemplateEngine().createTemplate(findByTemplate).make(sBinder).toString();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(ea.isFindAllMethod())
                {
                    try {
                        findAll += new SimpleTemplateEngine().createTemplate(findAllTemplate).make(sBinder).toString();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            map.put("className", x.getName());
            if(x.getDatabaseType().equalsIgnoreCase(MONGODB_yml))
                map.put("idType", "String");
            else
                map.put("idType", "Int");

            try {
                return new SimpleTemplateEngine()
                        .createTemplate(methodTemplate).make(map).toString() + findOne + findAll;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }).reduce("", (x,y)->x+ "\n" + y);


        String queryTemplates =  templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(GRAPHQL_QUERY));

        HashMap<String, String> map = new HashMap<>();

        map.put("methods", methods);


        String mutationMethodTemplate =  templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(GRAPHQL_MUTATION));
        String updateTemplate =templatesService.loadTemplateContent(templatesService.getGraphqlTemplates().get(UPDATE_BY_GRAPHQL));

        String mutationMethods = gqEntities.stream().map(x->{

            HashMap<String, String> map2 = new HashMap<>();
            map2.put("className", x.getName());
            map2.put("entityName", x.getName().toLowerCase());
            if(x.getDatabaseType().equalsIgnoreCase(MONGODB_yml))
                map2.put("idType", "String");
            else
                map2.put("idType", "Int");
            try {


                return new SimpleTemplateEngine()
                        .createTemplate(mutationMethodTemplate).make(map2).toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }).reduce("", (x,y)-> new StringBuilder().append(x).append("\n").append(y).toString());

//        mutationMethods += "\n" + gqEntities.stream().filter(x->!x.getUpdateByMethods().isEmpty())
//                        .map(entity->{
//                            String entityUpdateMthods =
//                                    entity.getUpdateByMethods().keySet().stream().map(
//                                            e->{
//                                                HashMap<String, String> binder = new HashMap<>(){{
//                                                    put("className", entity.getName());
//                                                    put("Type", DataTypeMapper.graphqlMapper.get(entity.getAttributeByName(e).getType().toLowerCase()));
//                                                    put("Attribute", NameUtils.capitalize(e));
//                                                }};
//                                                try {
//                                                    return new SimpleTemplateEngine().createTemplate(updateTemplate).make(binder).toString();
//                                                } catch (ClassNotFoundException ex) {
//                                                    return "";
//                                                } catch (IOException ex) {
//                                                    return "";
//                                                }
//                                            }
//                                    ).reduce((x,y) -> x+y).orElse("");
//
//                            return entityUpdateMthods;
//                        }).reduce((x,y)->x + y).orElse("");
//

        map.put("mutationMethods", mutationMethods);
        return new SimpleTemplateEngine()
                .createTemplate(queryTemplates).make(map).toString();




    }

    /**
     * All possible keys should be called
     * @param entity
     * @param language
     * @param templateKey : SAVE, DELETE UPDATE, FIND, FIND ALL.
     * @return the generate Template
     * @throws Exception
     */
    public String generateFunction(Entity entity, String language,String templateKey ) throws Exception {

        HashMap<String, String> binder = new HashMap<String, String>();
        binder.putIfAbsent("pack", entity.getFunctionPackage());
        binder.put("inputImport", entity.getEntityPackage() + "." + entity.getName());
        binder.put("Input", entity.getName());
        binder.put("Output", entity.getName());
        binder.put("block", entity.getReactiveFramework().equalsIgnoreCase("reactor")?".block": "getBlocking()");
        return generateFromTemplate(entity, language, binder, templateKey);
    }
    public String generateAzureFunction(Entity entity, String language) throws Exception {
        throw new NotImplementedException("Not Implemented");
    }
    public String generateGoogleFunction(Entity entity, String language) throws Exception {
        throw new NotImplementedException("Not Implemented");
    }
    public String generateOracleFunction(Entity entity, String language) throws Exception {
        throw new NotImplementedException("Not Implemented");
    }
//    @Inject
//    private GeneratorUtils generatorUtils;
//    public File generateEntityFilesOnly(DomainsRequest domainsRequest) throws Exception {
//        return generatorUtils.generateZip(generateEntityFiles(domainsRequest), projectRequest.getArtifact(),
//                false);
//    }
}
