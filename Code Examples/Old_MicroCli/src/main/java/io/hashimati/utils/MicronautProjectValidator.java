package io.hashimati.utils;

import io.hashimati.config.Feature;
import io.hashimati.config.FeaturesFactory;
import io.hashimati.domains.ProjectInfo;
import io.hashimati.microcli.TemplatesService;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import static io.hashimati.microcli.TemplatesService.H2_JDBC_yml;

public class MicronautProjectValidator {

    public static ProjectInfo projectInfo = null;
    public static boolean isMavenOrGradle() throws FileNotFoundException {
        return isMaven() || isGradle();
    }
    public static boolean isMaven() throws FileNotFoundException {
      return getProjectInfo().getBuildTool().equalsIgnoreCase("maven");
    }
    public static boolean isGradle() throws FileNotFoundException {
        return getProjectInfo().getBuildTool().equalsIgnoreCase("gradle");

    }

    public static boolean isValidProject() throws FileNotFoundException {
        return getProjectInfo() != null;
    }



    public static boolean isGradleContainDependency(String dependency)
    {
            return false;
    }
    public static boolean isProjectContainsMnDataJDBC() throws FileNotFoundException {

        return getProjectInfo().getFeatures().contains("data-jdbc");


    }
    public static boolean isProjectContainsMnDataJPA() throws FileNotFoundException {

        return getProjectInfo().getFeatures().contains("data-hibernate-jpa");


    }
    public static boolean isProjectContainsMnDataHikari() throws FileNotFoundException {

        return getProjectInfo().getFeatures().contains("jdbc-hikari");


    }
    public static boolean isProjectContainsReactiveMongo() throws FileNotFoundException {

        return getProjectInfo().getFeatures().contains("mongo-reactive");


    }
    public static boolean isProjectContainsGraphQl() throws FileNotFoundException{
        return getProjectInfo().getFeatures().contains("graphql");

    }
    public static boolean isProjectContainsOpenAPI() throws FileNotFoundException {
        return getProjectInfo().getFeatures().contains("openapi");
    }
    public static String getGradleDependencies () throws IOException{

        String content = getGradleFileContent();
//        Pattern pattern = Pattern.compile("dependencies \\{(.*?)\\}\n" +
//                "\n" +
//                "test.classpath");
//        Matcher matcher = pattern.matcher(content);
//        StringBuilder result = new StringBuilder("");
//        while(matcher.find()) {
//            result.append( matcher.group(1));
//        }
//        return result.toString();


        //It's not the best way but It's ok on desktop. It's working if the project generate by Micronaut Launch.
        //This code needs to be changed.
        String from  = "dependencies {", to ="}\n" +
                "\n" +
                "test.classpath";
        int fromIndex = content.indexOf(from )+ from.length();
        int toIndex = content.indexOf(to);
        return content.substring(fromIndex, toIndex);
    }

    public static String getMavenDependencies() throws FileNotFoundException {

        String pomContent = getPomFileContent();


        String from  = "<dependencies>", to ="</dependencies>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        return pomContent.substring(fromIndex, toIndex);
    }

    public static String getAppNameFromMaven() throws FileNotFoundException {

        String pomContent = getPomFileContent();


        String from  = "<artifactId>", to ="</artifactId>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        return pomContent.substring(fromIndex, toIndex);
    }

    public static String getAppNameFromGradle() throws FileNotFoundException {
        String fileContent = GeneratorUtils.getFileContent(new File("settings.gradle"));

        String from = "rootProject.name=\"";
        return fileContent.substring(from.length(),fileContent.indexOf("\"", from.length()));
    }

    //note: This is not the best way to implement this method.
    public static boolean updateMavenDependencies(String newDependencies) throws IOException
    {


        String pomContent = getPomFileContent();


        String from  = "<dependencies>", to ="</dependencies>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        String dependencies =  pomContent.substring(fromIndex, toIndex);
        String newDep = dependencies +"\n"+ newDependencies+ "\n";
        pomContent = pomContent.replace(dependencies, newDep);
        GeneratorUtils.dumpContentToFile("pom.xml", pomContent);
        return true;
    }

    //note: this is not the best way to implement this method.
    public static boolean updateMavenPathAnnotation(String annotation) throws IOException{


        String pomContent = getPomFileContent();


        String from  = "<annotationProcessorPaths>", to ="</annotationProcessorPaths>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        StringBuilder annotations =  new StringBuilder(pomContent.substring(fromIndex, toIndex)).append("\n"+ annotation+ "\n");
        pomContent = pomContent.replace(pomContent.substring(fromIndex, toIndex), annotations.toString());
        GeneratorUtils.dumpContentToFile("pom.xml", pomContent);
        return true;
    }
    public static boolean updateMavenPathDepndencyManagement(String annotation) throws IOException{


        String pomContent = getPomFileContent();


        String from  = "<annotationProcessorPaths>", to ="</annotationProcessorPaths>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        StringBuilder annotations =  new StringBuilder(pomContent.substring(fromIndex, toIndex)).append("\n"+ annotation+ "\n");
        pomContent = pomContent.replace(pomContent.substring(fromIndex, toIndex), annotations.toString());
        GeneratorUtils.dumpContentToFile("pom.xml", pomContent);
        return true;
    }
    public static boolean updateGradlewDependencies(String newDependencies, int index) throws IOException
    {
        String gradleContent = getGradleFileContent();

        if(gradleContent.contains(newDependencies.trim()))
            return true;


        String prefix = "";
        if(newDependencies.trim().startsWith("annotationProcessor")){

            prefix = "annotationProcessor";
        }
        else if(newDependencies.trim().startsWith("compileOnly")){

            prefix = "compileOnly";
        } else if(newDependencies.trim().startsWith("compile")){

            prefix = "compile";
        } else if(newDependencies.trim().startsWith("implementation")){
            prefix = "implementation";
        } else if(newDependencies.trim().startsWith("runtimeOnly")){
            prefix = "runtimeOnly";
        }else if(newDependencies.trim().startsWith("runtime")){
            prefix = "runtime";
        } else if(newDependencies.trim().startsWith("testAnnotationProcessor")){
            prefix = "testAnnotationProcessor";
        }else if(newDependencies.trim().startsWith("testRuntimeOnly")){
            prefix = "testRuntimeOnly";
        }else if(newDependencies.trim().startsWith("testCompileOnly")){
            prefix = "testCompileOnly";
        }else if(newDependencies.trim().startsWith("testCompile")){
            prefix = "testCompile";
        }else if(newDependencies.trim().startsWith("testRuntimeOnly")){
            prefix = "testRuntimeOnly";
        }else if(newDependencies.trim().startsWith("testRuntime")){
            prefix = "testRuntime";
        }

        if(index<0 || !gradleContent.contains(prefix) || prefix.isEmpty())
        {
            String from  = "dependencies {", to ="}\n" +
                    "\n" +
                    "test.classpath";
            int fromIndex = gradleContent.indexOf(from )+ from.length();
            int toIndex = gradleContent.indexOf(to);
            String dependencies= gradleContent.substring(fromIndex, toIndex);
            String newDep = index <= 0? newDependencies + "\n" + dependencies :dependencies+"\n"+newDependencies;
            gradleContent = gradleContent.replace(dependencies, newDep);
            GeneratorUtils.dumpContentToFile("build.gradle", gradleContent);

            return true;
        }
        else if(gradleContent.contains(prefix)) {
            int lastIndexOfPrefix = gradleContent.lastIndexOf(prefix);
            System.out.println(prefix);
            System.out.println(lastIndexOfPrefix);
            String replaceString =      gradleContent.substring(lastIndexOfPrefix,
                    gradleContent.indexOf("\n", lastIndexOfPrefix));


            gradleContent = gradleContent.replace(replaceString, replaceString+"\n" + newDependencies);
            GeneratorUtils.dumpContentToFile("build.gradle", gradleContent);

            return true;
        }
//        else
//        {
//            String from  = "dependencies {", to ="}\n" +
//                "\n" +
//                "test.classpath";
//            int fromIndex = gradleContent.indexOf(from )+ from.length();
//            int toIndex = gradleContent.indexOf(to);
//            String dependencies= gradleContent.substring(fromIndex, toIndex);
//            String newDep = index <= 0? newDependencies + "\n" + dependencies + "\n" :dependencies+"\n"+newDependencies+"\n";
//            gradleContent = gradleContent.replace(dependencies, newDep);
//            return true;
//        }
        return false;
    }


    private static String getGradleFileContent() throws FileNotFoundException {
        String cwd = System.getProperty("user.dir");
        File  build = new File(cwd + "/build.gradle");
        return GeneratorUtils.getFileContent(build);
    }

    public static String getPomFileContent() throws FileNotFoundException {
        String cwd = System.getProperty("user.dir");
        File  pom = new File(cwd + "/pom.xml");
        return GeneratorUtils.getFileContent(pom);
    }
    public static ProjectInfo getProjectInfo() throws FileNotFoundException{
        if(projectInfo != null) return projectInfo;
        String cwd = System.getProperty("user.dir");
        File  micronautCli = new File(cwd + "/micronaut-cli.yml");
        if(!micronautCli.exists()) return null;
        Yaml yaml = new Yaml();
        String content = GeneratorUtils.getFileContent(micronautCli);
        return (projectInfo = yaml.loadAs(content, ProjectInfo.class));
    }

    public static String getMainPackage() throws IOException {
        return getProjectInfo().getDefaultPackage();
    }

    public static boolean addLombok() throws IOException, XmlPullParserException {
        if(getProjectInfo().getSourceLanguage().equalsIgnoreCase("java"))
        {
            Feature lombok = FeaturesFactory.features().get("lombok");
            if(getProjectInfo().getBuildTool().equalsIgnoreCase("gradle"))
            {
                updateGradlewDependencies(lombok.getAnnotationGradle(), -2);
                updateGradlewDependencies(lombok.getGradle(), 3);
                updateGradlewDependencies(lombok.getTestGradleAnnotation(), 2);
                updateGradlewDependencies(lombok.getTestGradle(),2);
                return true;
            }
            else if(getProjectInfo().getBuildTool().equalsIgnoreCase("maven"))
            {
                MavenProjectUtils.addDependency(lombok, "pom.xml");
                MavenProjectUtils.addAnnotation(lombok, "pom.xml");

                //todo

                return true;
            }
        }
        return false;
    }
    public static boolean addOpenapi() throws IOException, XmlPullParserException {

        if(getProjectInfo().getApplicationType().equalsIgnoreCase("default"))
        {
            Feature openapi = FeaturesFactory.features().get("openapi");
            if(getProjectInfo().getBuildTool().equalsIgnoreCase("gradle"))
            {
                updateGradlewDependencies(openapi.getAnnotationGradle(), 3);
                updateGradlewDependencies(openapi.getGradle(), 3);


                return true;
            }
            else if(getProjectInfo().getBuildTool().equalsIgnoreCase("maven"))
            {

                MavenProjectUtils.addAnnotation(openapi, "pom.xml");
                MavenProjectUtils.addDependency(openapi, "pom.xml");

                //todo
                return true;
            }


        }
        return false;
    }


    public static boolean addExposingSwaggerUI() throws FileNotFoundException {
        if(getProjectInfo().getBuildTool().equalsIgnoreCase("gradle"))
        {
            return addExposingSwaggerUIToGradle();
        }
        else if(getProjectInfo().getBuildTool().equalsIgnoreCase("maven"))
        {
            return addExposingSwaggerUIToMaven();
        }

        return false;
    }
    public  static boolean addExposingSwaggerUIToMaven() throws FileNotFoundException {
        if(getProjectInfo().getSourceLanguage().equalsIgnoreCase("java")){
            String pom = getPomFileContent().replace("<compilerArgs>",
                    "<compilerArgs>\n" + "                    <arg>-J-Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop</arg>");

            return true;
        }
        else if(getProjectInfo().getSourceLanguage().equalsIgnoreCase("kotlin"))
        {
            String pom = getPomFileContent();


            return true;
        }
        else if(getProjectInfo().getSourceLanguage().equalsIgnoreCase("groovy"))
        {
            String index = "<property>\n" +
                    "                  <name>groovy.parameters</name>\n" +
                    "                  <value>true</value>\n" +
                    "                </property>";
            String replace = index+"\n"+
                    "                 <property>\n" +
                    "                  <name>micronaut.openapi.views.spec</name>\n" +
                    "                  <value>rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop</value>\n" +
                    "                </property>\n";

            String pom = getPomFileContent().replace(index, replace);
            GeneratorUtils.dumpContentToFile("pom.xml", pom);

            return true;
        }
        return false;
    }
    public  static boolean addExposingSwaggerUIToGradle() throws FileNotFoundException {

        if(getProjectInfo().getSourceLanguage().equalsIgnoreCase("java")) {
            String gradleContent = getGradleFileContent().replace("options.encoding = \"UTF-8\"",
                    "options.encoding = \"UTF-8\"\n" +
                            "    options.fork = true\n" +
                            "    options.forkOptions.jvmArgs << '-Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop'\n");

            GeneratorUtils.dumpContentToFile("build.gradle", gradleContent);
            return true;
        }
        else if(getProjectInfo().getSourceLanguage().equalsIgnoreCase("kotlin"))
        {
            String gradleContent = getGradleFileContent().replace("arguments {",
                    "    arguments {\n" +
                            "        arg(\"micronaut.openapi.views.spec\", \"redoc.enabled=true,rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop\")\n");
            GeneratorUtils.dumpContentToFile("build.gradle", gradleContent);

            return true;
        }
        else if(getProjectInfo().getSourceLanguage().equalsIgnoreCase("groovy")){

            String gradleContent = getGradleFileContent().replace("-Dgroovy.parameters=true", "-Dgroovy.parameters=true"+",micronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop");

            GeneratorUtils.dumpContentToFile("build.gradle", gradleContent);
            return true;
        }
            return false;
    }
    public static boolean addDependency(Feature... feature) throws IOException {
        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle"))
        {
            return updateGradlewDependencies(Arrays.stream(feature).map(x->x.getGradle() != null? x.getGradle():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(Arrays.stream(feature).map(x->x.getAnnotationGradle() !=null? x.getAnnotationGradle():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(Arrays.stream(feature).map(x->x.getTestGradle() !=null? x.getTestGradle():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(Arrays.stream(feature).map(x->x.getTestGradleAnnotation() !=null? x.getTestGradleAnnotation():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(Arrays.stream(feature).map(x->x.getTestContainerGradle() !=null? x.getTestContainerGradle():"").reduce("", (x, y)->x+"\n"+ y),2);
        }
        else if(projectInfo.getBuildTool().equalsIgnoreCase("maven"))
        {

            return Arrays.stream(feature).map(
                    x->{
                        try {
                            return MavenProjectUtils.addDependency(x, "pom.xml") &&
                                    MavenProjectUtils.addAnnotation(x, "pom.xml")
                                    && MavenProjectUtils.addDependencyToDependecyMgmt(x, "pom.xml")
                                    && MavenProjectUtils.addTestDependency(x, "pom.xml");
                        } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
            ).reduce((x, y)->x && y).get();


//            return updateMavenDependencies(Arrays.stream(feature).map(x->x.getMaven() == null?"": x.getMaven()).reduce("", (x, y)->x+"\n"+ y))
//                   && updateMavenPathAnnotation(Arrays.stream(feature).map(x->x.getAnnotationMaven() == null?"": x.getAnnotationMaven()).reduce("", (x, y)->x+"\n"+ y))
//                    &&  updateMavenDependencies(Arrays.stream(feature).map(x->x.getTestMaven() == null?"": x.getTestMaven()).reduce("", (x, y)->x+"\n"+ y)) ;
        }
        return false;
    }


    public static boolean addingOpenApiToApplicationFile(String appName) throws FileNotFoundException {

        if(projectInfo == null)
            projectInfo = getProjectInfo();

        if(!projectInfo.getApplicationType().equalsIgnoreCase("default"))
            return false;


        String annotations = "import io.swagger.v3.oas.annotations.*;\n" +
                "import io.swagger.v3.oas.annotations.info.*;\n" +
                "\n" +
                "@OpenAPIDefinition(\n" +
                "    info = @Info(\n" +
                "            title = \"demo\",\n" +
                "            version = \"0.0\"\n" +
                "    )\n" +
                ")".replace("demo", appName);

        String ext = projectInfo.getSourceLanguage().equalsIgnoreCase("kotlin")? ".kt": "."+ getProjectInfo().getSourceLanguage().toLowerCase();
        String mainFilePath = "src/main/"+projectInfo.getSourceLanguage()+"/"+ GeneratorUtils.packageToPath(projectInfo.getDefaultPackage())+"/Application"+ext;
        String from = "import io.micronaut.runtime.Micronaut;";
        if(!projectInfo.getSourceLanguage().equalsIgnoreCase("java"))
        {
           annotations = annotations.replace(";","");
           from = from.replace(";", "");
        }

        String content = GeneratorUtils.getFileContent(new File(mainFilePath));
        content = content.replace(from, from+"\n"+annotations );https://instagram.fdmm2-3.fna.fbcdn.net/v/t51.2885-15/e35/p1080x1080/108193602_124165032685632_5795289934719882707_n.jpg?_nc_ht=instagram.fdmm2-3.fna.fbcdn.net&_nc_cat=100&_nc_ohc=g-gfAGEfYzYAX9l9FHG&oh=4fced48addfe0428edbdcd4340ab6c4b&oe=5F4BC600

        GeneratorUtils.dumpContentToFile(mainFilePath, content);
        return false;
    }


    private static TemplatesService templatesService;
    static {
        templatesService = new TemplatesService();
        try {
            templatesService.loadTemplates(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean appendJDBCToProperties(String database, boolean main, boolean testWithH2) throws FileNotFoundException {
        //todo

        String propertiesPath = "src/main/resources/application"+(main?"":"-test")+".yml";

        String propertiesContent = GeneratorUtils.getFileContent(new File(propertiesPath));
        if(!propertiesContent.contains(database.replace("-test", "").toLowerCase())){


            String template = templatesService.loadTemplateContent(templatesService.getProperties().get(database));

            //this scope will be invoked if the user choose to test with H2 instead of TestContainer.
            if(testWithH2 && !main)
            {
                template = templatesService.loadTemplateContent(templatesService.getProperties().get(H2_JDBC_yml));
            }
            if(template.isEmpty())
                return true;

            return GeneratorUtils.dumpContentToFile(propertiesPath, propertiesContent + (propertiesContent.isEmpty()?"":"\n---\n") + template);
            //return GeneratorUtils.appendContentToFile(propertiesPath,propertiesContent);
        }
        return false;
    }
    public static boolean appendJPAToProperties(String database, boolean main)
    {
        //todo
        return false;
    }
    public static boolean appendToProperties(String properties) throws FileNotFoundException {
        //todo
        String propertiesPath = "src/main/resources/application.yml";
        String propertiesContent = GeneratorUtils.getFileContent(new File(propertiesPath));

        if(!propertiesContent.contains(properties))
        {

            return GeneratorUtils.appendContentToFile(propertiesPath, propertiesContent + "\n---\n"+properties);

        }
        return false;
    }

    public static boolean appendToXMLNode(String node, String path) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        String cwd = System.getProperty("user.dir");
        File  pom = new File(cwd + "/pom.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(pom);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/Project/dependencies";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(nodeList.item(i).getTextContent());
        }
        return true;
    }
}





















