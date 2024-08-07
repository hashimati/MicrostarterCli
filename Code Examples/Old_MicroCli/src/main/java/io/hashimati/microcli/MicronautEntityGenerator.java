package io.hashimati.microcli;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import groovy.lang.Tuple2;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.constants.ProjectConstants;
import io.hashimati.domains.*;
import io.hashimati.microcli.SqlSchemaGenerator;
import io.hashimati.microcli.TemplatesService;
import io.hashimati.utils.DataTypeMapper;
//import reactor.util.function.Tuple2;
//import reactor.util.function.Tuples;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import static io.hashimati.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.domains.EntityRelationType.OneToMany;
import static io.hashimati.domains.EntityRelationType.OneToOne;


/**
 * @author Ahmed Al Hashmi
 */
@Singleton
public class MicronautEntityGenerator
{


    @Inject
    private SqlSchemaGenerator sqlSchemaGenerator;

    @Inject
    private TemplatesService templatesService;

  // @PostConstruct
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
        System.out.println(generateRepository(entity, "java"));
        System.out.println("***");
        System.out.println(generateService(entity, "java"));
        System.out.println("***");
        System.out.println(generateController(entity, "java"));
        System.out.println("-----------------groovy-------");
        System.out.println(generateEntity(entity, relations ,GROOVY_LANG));
        System.out.println("***");
        System.out.println(generateRepository(entity, "groovy"));
        System.out.println("***");
        System.out.println(generateService(entity, "groovy"));
        System.out.println("***");
        System.out.println(generateController(entity, "groovy"));

        System.out.println("--------------kotlin----------");
        System.out.println(generateEntity(entity,relations ,"kotlin"));
        System.out.println("***");
        System.out.println(generateRepository(entity, "kotlin"));
        System.out.println("***");
        System.out.println(generateService(entity, "kotlin"));
        System.out.println("***");
        System.out.println(generateController(entity, "kotlin"));

        System.out.println("--------------------end entity test----");
    }

    public String generateEntity(Entity entity, ArrayList<EntityRelation> relations, String language) throws IOException,
            ClassNotFoundException, FormatterException {

        String declarrationSperator = language.equalsIgnoreCase(KOTLIN_LANG)? ",": "\n";
        Set<EntityRelation> entityRelations = getRelations(entity, relations);
        String attributesDeclaration ="";
        String importedPackages = "";
        boolean containDate = false;

        if(entity.getAttributes()!= null)
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

                    attributeDeclaration += eA.getConstraints().getDateValidationExepression();
                } else if (eA.isClass()) {
                    attributeDeclaration += eA.getConstraints().getNotNullExpression();
                }
            }
                else if(eA.isArray()) {
                    attributeDeclaration += eA.getConstraints().getCollectionSizeExpression();
                    attributeDeclaration += eA.getConstraints().getNotBlankExpression();

                }

            }
            attributeDeclaration +=eA.getDeclaration(language) + declarrationSperator;
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

                        importedPackages +=(importedPackages.contains(r.getE1Package()+"."+r.getE1())? "": "\nimport " + r.getE1Package()+"."+r.getE1()+ ";");
                        attributesDeclaration += "\n" + r.generateE1OneToOneTemplate(language, entity.getFrameworkType())+declarrationSperator;
                    }
                    if(entity.getName().equals(r.getE2()) && entity.getEntityPackage().equals(r.getE2Package()))
                    {


                        importedPackages +=(importedPackages.contains(r.getE2Package()+"."+r.getE2())? "": "\nimport" +
                                " " + r.getE2Package()+"."+r.getE2()+ ";");
                        attributesDeclaration += "\n" + r.generateE2OneToOneTemplate(language, entity.getFrameworkType())+declarrationSperator;
                    }
                    break;
                case OneToMany:
                    if(entity.getName().equals(r.getE1()) && entity.getEntityPackage().equals(r.getE1Package()))
                    {

                        importedPackages +=(importedPackages.contains(r.getE1Package()+"."+r.getE1())? "": "\nimport " + r.getE1Package()+"."+r.getE1()+ ";");
                        attributesDeclaration += "\n" + r.generateE1OneToManyTemplate(language, entity.getFrameworkType())+declarrationSperator;
                    }
                    if(entity.getName().equals(r.getE2()) && entity.getEntityPackage().equals(r.getE2Package()))
                    {

                        importedPackages +=(importedPackages.contains(r.getE2Package()+"."+r.getE2())? "": "\nimport " + r.getE2Package()+"."+r.getE2()+ ";");
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
       // boolean isNormal = false;
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
             //   isNormal = true;
                isJdbc = false;
                isJpa = false;

                entityAnnotation = "";
                break;

        }

        if(language.equalsIgnoreCase(KOTLIN_LANG))
            attributesDeclaration =attributesDeclaration.trim().isEmpty()?"": ", " + attributesDeclaration;
        binder.put("entityAnnotation",entityAnnotation );
        binder.put("tableAnnotation","" );
        binder.put("entitypackage", entity.getEntityPackage());
        binder.put("jpa", isJpa);
        binder.put("jdbc", isJdbc);
        binder.put("normal", (isJpa == false && isJdbc == false));

        binder.put("collectionName", entity.getCollectionName()); 
        binder.put("className",entity.getName() );
        binder.put("instances", attributesDeclaration);
        binder.put("importedPackages",importedPackages );
        binder.put("containDate", containDate);

        String templatePath= getTemplatPath(TemplatesService.ENTITY, language.toLowerCase());


        String entityTemplate  =templatesService.loadTemplateContent(templatePath);

        String result = new SimpleTemplateEngine().createTemplate(entityTemplate).make(binder).toString();
        if(!language.equalsIgnoreCase(JAVA_LANG))
            result = result.replace(";", "");
//        else
//            result  =new Formatter().formatSource(result);

        return result;
    }
    public String generateRepository(Entity entity, String language) throws IOException, ClassNotFoundException {

        HashMap<String, String> binder = new HashMap<>();

        if(!entity.getDatabaseType().toLowerCase().equalsIgnoreCase("mongodb")){
            String repositoryTemplate ="";

            if(entity.getFrameworkType().equalsIgnoreCase("jpa")) {
                binder.put("entityRepositoryPackage", entity.getRepoPackage());
                binder.put("importEntity", entity.getEntityPackage() + "." + entity.getName());
                binder.put("className", entity.getName());
                binder.put("methods", "");
                String templatePath= getTemplatPath(TemplatesService.REPOSITORY, language.toLowerCase());

                repositoryTemplate = templatesService.loadTemplateContent(templatePath);

            }
            else if(entity.getFrameworkType().equalsIgnoreCase("jdbc"))
            {
                binder.put("entityRepositoryPackage", entity.getRepoPackage());
                binder.put("importEntity", entity.getEntityPackage() + "." + entity.getName());
                binder.put("className", entity.getName());
                binder.put("dialect", DataTypeMapper.dialectMapper.get(entity.getDatabaseType().toLowerCase()));
                binder.put("methods", "");
                String templatePath= getTemplatPath(TemplatesService.JDBC_REPOSITORY, language.toLowerCase());


                repositoryTemplate = templatesService.loadTemplateContent(templatePath);
            }
            return new SimpleTemplateEngine().createTemplate(repositoryTemplate).make(binder).toString();
        }
        else if(entity.getDatabaseType().toLowerCase().equalsIgnoreCase("mongodb"))
        {
            binder.put("entityRepositoryPackage", entity.getRepoPackage());
            binder.put("entityPackage", entity.getEntityPackage());
            binder.put("entityClass", entity.getName());
            binder.put("databaseName", entity.getDatabaseName());
            binder.put("collectionName", entity.getCollectionName());
            String templatePath= getTemplatPath(TemplatesService.MONGO_REPOSITORY, language.toLowerCase());

            String repositoryTemplate =templatesService.loadTemplateContent( templatePath);
            return new SimpleTemplateEngine().createTemplate(repositoryTemplate).make(binder).toString();
        }
        return "";
    }
    public String generateEntityException(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, String> binder = new HashMap<>();
        binder.put("exceptionPackage", entity.getExceptionPackage() );
        binder.put("className", entity.getName());

        String templatePath= getTemplatPath(TemplatesService.GENERAL_EXCEPTION, language.toLowerCase());


        String  exceptionTemplate = templatesService.loadTemplateContent(templatePath);

        return new SimpleTemplateEngine().createTemplate(exceptionTemplate).make(binder).toString();
    }

    private String getTemplatPath(String key, String language) {
       String templatePath = "";
       switch (language)
        {
            case "java":
                templatePath =  templatesService.getJavaTemplates().get(key);
                break;
            case "groovy":
                templatePath =  templatesService.getGroovyTemplates().get(key);
                break;
            case "kotlin":
                templatePath =  templatesService.getKotlinTemplates().get(key);
                break;
        }
        return templatePath;
   }

    public String generateEntityExceptionHandler(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, String> binder = new HashMap<>();
        binder.put("exceptionHanderPackage", entity.getExceptionHandlerPackage() );
        binder.put("excptionPacage", entity.getExceptionPackage());
        binder.put("className", entity.getName());

        String templatePath= getTemplatPath(TemplatesService.EXCEPTION_HANDLER, language.toLowerCase());

        String  exceptionTemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(exceptionTemplate).make(binder).toString();
    }
    public String generateEntityRepositoryTest(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, String> binder = new HashMap<>();
        binder.put("className",entity.getName() );
        binder.put("entityName", entity.getName().toLowerCase());
        binder.put("repositoryPackage",entity.getRepoPackage() );
        binder.put("entityPackage", entity.getEntityPackage());
        binder.put("defaultPackage", entity.getEntityPackage().replace(".domains", ""));


        String templatePath= getTemplatPath(TemplatesService.REPOSITORY_TEST, language.toLowerCase());

        String  entityRepositoryTesttemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(entityRepositoryTesttemplate).make(binder).toString();
    }
    public String generateRandomizer(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, String> binder = new HashMap<>();
        binder.put("packageName", entity.getEntityPackage().replace(".domains", ""));


        String templatePath= getTemplatPath(TemplatesService.RANDOMIZER, language.toLowerCase());

        String  exceptionTemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(exceptionTemplate).make(binder).toString();
    }

    public String generateService(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, String> binder = new HashMap<>();
        binder.put("servicePackage", entity.getServicePackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("repoPackage", entity.getRepoPackage()+"."+entity.getName()+"Repository");
        binder.put("entityName", entity.getName().toLowerCase());
        binder.put("className", entity.getName());
        String serviceTemplate = "";
        String templatePath="";

        switch (entity.getDatabaseType().toLowerCase())
        {
            case "mongodb":

                 templatePath= getTemplatPath(TemplatesService.MONGO_SERVICE, language.toLowerCase());

                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
            default:

                templatePath= getTemplatPath(TemplatesService.SERVICE, language.toLowerCase());
                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
        }

        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }
    public String generateController(Entity entity, String language) throws IOException, ClassNotFoundException {
        HashMap<String, String> binder = new HashMap<>();
        binder.put("controllerPackage", entity.getRestPackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("servicePackage", entity.getServicePackage()+"."+entity.getName()+"Service");
        binder.put("entityName", entity.getName().toLowerCase());
        binder.put("entities", entity.getName().toLowerCase());

        binder.put("className", entity.getName());
        String serviceTemplate ;
        String templatePath="";
        switch (entity.getDatabaseType().toLowerCase())
        {
            case "mongodb":
                templatePath= getTemplatPath(TemplatesService.MONGO_CONTROLLER, language.toLowerCase());
                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
            default:
                templatePath= getTemplatPath(TemplatesService.CONTROLLER, language.toLowerCase());

                serviceTemplate = templatesService.loadTemplateContent(templatePath);
                break;
        }
        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }
    public String generateClient(Entity entity, String language) throws IOException, ClassNotFoundException {



        HashMap<String, String> binder = new HashMap<>();
        binder.put("clientPackage", entity.getClientPackage() );
        binder.put("entityPackage", entity.getEntityPackage()+"." + entity.getName());
        binder.put("entityName", entity.getName().toLowerCase());
        binder.put("entities", entity.getName().toLowerCase());

        binder.put("className", entity.getDatabaseType().equalsIgnoreCase("mongodb")? "Single<"+entity.getName()+">":
                entity.getName());
        binder.put("classNameA", entity.getName());
        String templatePath= getTemplatPath(TemplatesService.CLIENT, language.toLowerCase());

        String  serviceTemplate = templatesService.loadTemplateContent(templatePath);


        return new SimpleTemplateEngine().createTemplate(serviceTemplate).make(binder).toString();
    }


    public String generateEnum(EnumClass enumClass, String language) throws IOException, ClassNotFoundException {


        String templatePath= getTemplatPath(TemplatesService.ENUM, language.toLowerCase());

        String template = templatesService.loadTemplateContent(templatePath);

//        enumPackage, className, options
        HashMap<String, String> map = new HashMap<>();
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
                language)));
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


//    @Inject
//    private GeneratorUtils generatorUtils;
//    public File generateEntityFilesOnly(DomainsRequest domainsRequest) throws Exception {
//        return generatorUtils.generateZip(generateEntityFiles(domainsRequest), projectRequest.getArtifact(),
//                false);
//    }
}
