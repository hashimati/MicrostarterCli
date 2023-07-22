package io.hashimati.microcli.domains;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hashimati.microcli.utils.Visitor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Objects;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.utils.GeneratorUtils.generateFromTemplate;


public class EntityRelation {
    private final  String ONE_TO_ONE = "OneToOne",
            ONE_TO_MANY = "OneToMany",
            MANY_TO_MANY="ManyToMany";

    @NotEmpty
    private String e1Package;
    @NotEmpty
    private String e2Package;

    @NotEmpty
    private String e1;
    @NotEmpty
    private String e2;
    private String e1Table;
    private String e2Table;

    @NotNull
    private EntityRelationType relationType;

    //------------------------------ One To Many ---------------------------
    private final String e1OneToManyTemplate = "\t@OneToMany(mappedBy = \"${e1Object}\", cascade = CascadeType.ALL, " +
            "orphanRemoval = true)\n" +
            "    @OnDelete(action = OnDeleteAction.CASCADE)\n" +
            "    ${declaration}";
    private final String e2OneToManyTemplate = "\t@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)\n" +
            "     @JoinColumn(name = \"${e1}_id\", nullable = false)\n" +
            "     @OnDelete(action = OnDeleteAction.CASCADE)\n" +
            "     @JsonIgnore\n" +
            "     ${declaration}";

    private final String e1OneToManyJavaDeclarationTemplate = "\tprivate List<${e2}> ${e2list}s;";

    private final String e2OneToManyJavaDeclarationTemplate ="\tprivate ${e1} ${e1Object};";

    private final String e1OneToManyKotlinDeclarationTemplate = "var ${e2list}s: List<${e2}>;";
    private final String e2OneToManyKotlinDeclarationTemplate ="var ${e1Object}: ${e1};";

    private final String e1OneToManyGroovyDeclarationTemplate = "\tList<${e2}> ${e2list}s; ";
    private final String e2OneToManyGroovyDeclarationTemplate ="\t${e1} ${e1Object}";
//-----------------------------------------------------------------------------------
    //------------------------------- One TO One Templates---------------------------

    private final String e1OneToOneTemplate  =e1OneToManyTemplate.replace("OneToMany", "OneToOne");
    private final String e2OneToOneTemplate  =e2OneToManyTemplate.replace("ManyToOne", "OneToOne");

    //Todo define the declarations statements.
    private final String e1OneToOneJavaDeclarationTemplate = "\tprivate ${e2} ${e2Object};";
    private final String e2OneToOneJavaDeclarationTemplate ="\tprivate ${e1} ${e1Object};";

    private final String e1OneToOneKotlinDeclarationTemplate = "var ${e2Object}: ${e2}";
    private final String e2OneToOneKotlinDeclarationTemplate ="var ${e1Object}: ${e1};";

    private final String e1OneToOneGroovyDeclarationTemplate = "\t${e2} ${e2Object} ;";
    private final String e2OneToOneGroovyDeclarationTemplate ="\t${e1} ${e1Object}";



    //-------------------------------------------------------------------------------


    //---------------------------jdbc one-to one template

    private final String relationOneToMany = "ONE_TO_MANY",
            relationManyToOne ="MANY_TO_ONE",
            relationOneToOne ="ONE_TO_ONE",
            relationManyToMany = "MANY_TO_MANY";
    private final String jdbcE1AnnotationTemplate =  "    @Relation(mappedBy = \"${e2attribute}\", value = Relation.Kind.${relation})\n";
    private final String jdbcE2AnnotationTemplate =  "    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @MappedProperty(\"${mappedColumn}\")";
    private final String joinTemplate = "@Join(\"${mappedColumn}\"";

    public String generateE1OneToManyTemplate(String language, String backend){

        System.out.println("For once I'm here " + backend);
        HashMap<String, String> binder = new HashMap<String, String> ();
        HashMap<String, String> declarationBinder = new HashMap<String, String>();
//private List<${e2}> ${e2list}s = new ArrayList<${e2}>
        declarationBinder.put("e2", e2);
        declarationBinder.put("e2list", e2.toLowerCase());

        String declarationStatement = "";
        switch(language){
            case JAVA_LANG:
                declarationStatement = generateFromTemplate(e1OneToManyJavaDeclarationTemplate,declarationBinder );
                break;
            case GROOVY_LANG:
                declarationStatement = generateFromTemplate(e1OneToManyGroovyDeclarationTemplate,declarationBinder );
                break;
            case KOTLIN_LANG:
                declarationStatement = generateFromTemplate(e1OneToManyKotlinDeclarationTemplate,declarationBinder );
                break;
            default:
                declarationStatement = generateFromTemplate(e1OneToManyJavaDeclarationTemplate,declarationBinder );
                break;
        }


//       " @OneToMany(mappedBy = \"${e1Object}\", cascade = CascadeType.ALL, " +
//                "orphanRemoval = true)\n" +
//                "    @OnDelete(action = OnDeleteAction.CASCADE)\n" +
//                "    ${declaration}";
        // ${declaration}, ${e1Object}
        if(backend.equalsIgnoreCase("jdbc")) {
//            private final String jdbcE1AnnotationTemplate =  "    @Relation(mappedBy = \"${e2attribute}\", value = Relation.Kind.${relation})\n";

            String e2Attri = e1.toLowerCase();
            if(e1.toLowerCase().equalsIgnoreCase(e2.toLowerCase()) && e1Package.toLowerCase().equals(e2Package.toLowerCase()))
            {
                e2Attri = e1.toLowerCase()+"2";
            }
            binder.put("e2attribute", e2Attri);
            binder.put("relation", relationOneToMany);

            return generateFromTemplate(jdbcE1AnnotationTemplate, binder)+"\n"+ declarationStatement;
        }
        binder.put("e1Object", e1.toLowerCase());
        binder.put("declaration", declarationStatement);
        return generateFromTemplate(e1OneToManyTemplate, binder);
    }

    public String generateE2OneToManyTemplate(String language , String backend){
        HashMap<String, String> binder = new HashMap<String, String> ();
        HashMap<String, String> declarationBinder = new HashMap<String, String>();
//var ${e1Object}: ${e1};
        declarationBinder.put("e1", e1);
        declarationBinder.put("e1Object", e1.equals(e2)?e1.toLowerCase()+2:e1.toLowerCase());

        String declarationStatement = "";
        switch(language){
            case JAVA_LANG:
                declarationStatement = generateFromTemplate(e2OneToManyJavaDeclarationTemplate,declarationBinder );
                break;
            case GROOVY_LANG:
                declarationStatement = generateFromTemplate(e2OneToManyGroovyDeclarationTemplate,declarationBinder );
                break;
            case KOTLIN_LANG:
                declarationStatement = generateFromTemplate(e2OneToManyKotlinDeclarationTemplate,declarationBinder );
                break;
            default:
                declarationStatement = generateFromTemplate(e2OneToManyJavaDeclarationTemplate,declarationBinder );
                break;
        }
//
//        @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)\n" +
//        "     @JoinColumn(name = \"${e1}_id\", nullable = false)\n" +
//                "     @OnDelete(action = OnDeleteAction.CASCADE)\n" +
//                "     @JsonIgnore\n" +
//                "     ${declaration}";
        if(backend.equalsIgnoreCase("jdbc")) {
//            private final String jdbcE1AnnotationTemplate =  "    @Relation(mappedBy = \"${e2attribute}\", value = Relation.Kind.${relation})\n";

            binder.put("mappedColumn", e1.toLowerCase());

            return generateFromTemplate(jdbcE2AnnotationTemplate, binder)+"\n"+ declarationStatement;
        }
        binder.put("e1", e1);
        binder.put("declaration", declarationStatement);
        return generateFromTemplate(e2OneToManyTemplate, binder);

    }

//========================================

    public String generateE1OneToOneTemplate(String language, String backend){
        HashMap<String, String> binder = new HashMap<String, String> ();
        HashMap<String, String> declarationBinder = new HashMap<String, String>();

        declarationBinder.put("e2", e2);
        declarationBinder.put("e2Object", e2.toLowerCase());

        String declarationStatement = "";
        switch(language){
            case JAVA_LANG:
                declarationStatement = generateFromTemplate(e1OneToOneJavaDeclarationTemplate,declarationBinder );
                break;
            case GROOVY_LANG:
                declarationStatement = generateFromTemplate(e1OneToOneGroovyDeclarationTemplate,declarationBinder );
                break;
            case KOTLIN_LANG:
                declarationStatement = generateFromTemplate(e1OneToOneKotlinDeclarationTemplate,declarationBinder );
                break;
            default:
                declarationStatement = generateFromTemplate(e1OneToOneJavaDeclarationTemplate,declarationBinder );
                break;
        }
        if(backend.equalsIgnoreCase("jdbc")) {
//            private final String jdbcE1AnnotationTemplate =  "    @Relation(mappedBy = \"${e2attribute}\", value = Relation.Kind.${relation})\n";

            String e2Attri = e1.toLowerCase();
            if(e1.toLowerCase().equalsIgnoreCase(e2.toLowerCase()) && e1Package.toLowerCase().equals(e2Package.toLowerCase()))
            {
                e2Attri = e1.toLowerCase()+"2";
            }
            binder.put("e2attribute", e2Attri);
            binder.put("relation", relationOneToOne);

            return generateFromTemplate(jdbcE1AnnotationTemplate, binder)+"\n"+ declarationStatement;
        }
        // ${declaration}, ${e1Object}
        binder.put("e1Object", e1.toLowerCase());
        binder.put("declaration", declarationStatement);
        return generateFromTemplate(e1OneToOneTemplate, binder);
    }

    public String generateE2OneToOneTemplate(String language, String backend){
        HashMap<String, String> binder = new HashMap<String, String> ();
        HashMap<String, String> declarationBinder = new HashMap<String, String>();

        declarationBinder.put("e1", e1);
        declarationBinder.put("e1Object", e1.equals(e2)?e1.toLowerCase()+2:e1.toLowerCase());

        String declarationStatement = "";
        switch(language){
            case JAVA_LANG:
                declarationStatement = generateFromTemplate(e2OneToOneJavaDeclarationTemplate,declarationBinder );
                break;
            case GROOVY_LANG:
                declarationStatement = generateFromTemplate(e2OneToOneGroovyDeclarationTemplate,declarationBinder );
                break;
            case KOTLIN_LANG:
                declarationStatement = generateFromTemplate(e2OneToOneKotlinDeclarationTemplate,declarationBinder );
                break;
            default:
                declarationStatement = generateFromTemplate(e2OneToOneJavaDeclarationTemplate,declarationBinder );
                break;
        }
        if(backend.equalsIgnoreCase("jdbc")) {
//            private final String jdbcE1AnnotationTemplate =  "    @Relation(mappedBy = \"${e2attribute}\", value = Relation.Kind.${relation})\n";
            String e2Attri = (getRelationType() == EntityRelationType.OneToOne)? e1.toLowerCase() :e1.toLowerCase();
            if(e1.toLowerCase().equalsIgnoreCase(e2.toLowerCase()) && e1Package.toLowerCase().equals(e2Package.toLowerCase()))
            {
                e2Attri = e1.toLowerCase()+"2";
            }

            binder.put("mappedColumn", e2Attri);

            return generateFromTemplate(jdbcE2AnnotationTemplate, binder)+"\n"+ declarationStatement;
        }
        // ${declaration}, ${e1Object}
        binder.put("e1Object", e1.toLowerCase());
        binder.put("declaration", declarationStatement);
        return generateFromTemplate(e2OneToOneTemplate, binder);
    }


    public String getE1Package() {
        return e1Package;
    }

    public void setE1Package(String e1Package) {
        this.e1Package = e1Package;
    }

    public String getE2Package() {
        return e2Package;
    }

    public void setE2Package(String e2Package) {
        this.e2Package = e2Package;
    }

    public EntityRelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(EntityRelationType relationType) {
        this.relationType = relationType;
    }

    public String getE1() {
        return e1;
    }

    public void setE1(String e1) {
        this.e1 = e1;
    }

    public String getE2() {
        return e2;
    }

    public void setE2(String e2) {
        this.e2 = e2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityRelation)) return false;
        EntityRelation that = (EntityRelation) o;
        return getE1Package().equals(that.getE1Package()) &&
                getE2Package().equals(that.getE2Package()) &&
                getE1().equals(that.getE1()) &&
                getE2().equals(that.getE2()) &&
                getRelationType() == that.getRelationType();
    }

    @Override
    public String toString() {
        return "EntityRelation{" +
                "e1='" + e1 + '\'' +
                ", e2='" + e2 + '\'' +
                ", relationType=" + relationType +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getE1Package(), getE2Package(), getE1(), getE2(), getRelationType());
    }

    public boolean areEntitiesEqual() {
        return this.e1.equals(this.e2) && e1Package.equals(e2Package);

    }

    public String getE2Table() {
        return e2Table;
    }

    public void setE2Table(String e2Table) {
        this.e2Table = e2Table;
    }

    public String getE1Table() {
        return e1Table;
    }

    public void setE1Table(String e1Table) {
        this.e1Table = e1Table;
    }

    public EntityRelation visit(Visitor<EntityRelation> visitor)
    {
        return visitor.visit(this);
    }

    @JsonIgnore
    public String getE1ToE2RelationGorm()
    {
        return e2.toLowerCase() + ":" + e2;
    }

    @JsonIgnore
    public String getE2ToE1RelationGorm()
    {
        return e1.toLowerCase() + ":" + e1;
    }
}
