package io.hashimati.microcli.utils;
/**
 * @author Ahmed Al Hashmi
 */
import com.esotericsoftware.yamlbeans.YamlReader;
import groovy.lang.Tuple3;
import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.config.FeaturesFactory;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.services.TemplatesService;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;
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
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.GROOVY_LANG;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.KOTLIN_LANG;
import static io.hashimati.microcli.services.TemplatesService.H2_JDBC_yml;
import static io.hashimati.microcli.services.TemplatesService.H2_R2DBC_yml;

public class MicronautProjectValidator {

    public static ProjectInfo projectInfo = null;
    public static boolean isMavenOrGradle(String path) throws FileNotFoundException {
        return isMaven(path) || isGradle(path);
    }
    public static boolean isMaven(String path) throws FileNotFoundException {
      return getProjectInfo(path).getBuildTool().equalsIgnoreCase("maven");
    }
    public static boolean isGradle(String path) throws FileNotFoundException {
        return getProjectInfo(path).getBuildTool().equalsIgnoreCase("gradle");

    }

    public static boolean isValidProject(String path) throws FileNotFoundException {
        if(!path.endsWith("/")) path +="/";
        return getProjectInfo(path) != null && (getProjectInfo(path).getApplicationType().equalsIgnoreCase("default") || getProjectInfo(path).getApplicationType().equalsIgnoreCase("function"));
    }
    public static boolean isApplication(String path) throws FileNotFoundException {
        return getProjectInfo(path).getApplicationType().equalsIgnoreCase("default");
    }
    public static boolean isFunction(String path) throws FileNotFoundException{
        return getProjectInfo( path).getApplicationType().equalsIgnoreCase("function");
    }



    public static boolean isGradleContainDependency(String dependency)
    {
            return false;
    }
    public static boolean isProjectContainsMnDataJDBC(String path) throws FileNotFoundException {

        return getProjectInfo(path).getFeatures().contains("data-jdbc");


    }
    public static boolean isProjectContainsMnDataJPA(String path) throws FileNotFoundException {

        return getProjectInfo( path).getFeatures().contains("data-hibernate-jpa");


    }
    public static boolean isProjectContainsMnDataHikari(String path) throws FileNotFoundException {

        return getProjectInfo(path).getFeatures().contains("jdbc-hikari");


    }
    public static boolean isProjectContainsReactiveMongo(String path) throws FileNotFoundException {

        return getProjectInfo(path).getFeatures().contains("mongo-reactive");


    }
    public static boolean isProjectContainsGraphQl(String path) throws FileNotFoundException{
        return getProjectInfo(path).getFeatures().contains("graphql");

    }
    public static boolean isProjectContainsOpenAPI(String path) throws FileNotFoundException {
        return getProjectInfo(path).getFeatures().contains("openapi");
    }
    public static String getGradleDependencies (String cwd) throws IOException{

        String content = getGradleFileContent(cwd);
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

    public static String getMavenDependencies(String path) throws FileNotFoundException {

        String pomContent = getPomFileContent(path);


        String from  = "<dependencies>", to ="</dependencies>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        return pomContent.substring(fromIndex, toIndex);
    }

    public static String getAppNameFromMaven(String path) throws FileNotFoundException {

        String pomContent = getPomFileContent(path);


        String from  = "<artifactId>", to ="</artifactId>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        return pomContent.substring(fromIndex, toIndex);
    }

    public static String getAppNameFromGradle(String path) throws FileNotFoundException {
        String fileContent = GeneratorUtils.getFileContent(new File(path+(path.endsWith("/")? "":"/") + "settings.gradle"));

        String from = "rootProject.name=\"";
        System.out.println(fileContent);
        return fileContent.substring(from.length(),fileContent.indexOf("\"", from.length()));
    }

    //note: This is not the best way to implement this method.
    public static boolean updateMavenDependencies(String path,String newDependencies) throws IOException
    {


        String pomContent = getPomFileContent( path);


        String from  = "<dependencies>", to ="</dependencies>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        String dependencies =  pomContent.substring(fromIndex, toIndex);
        String newDep = dependencies +"\n"+ newDependencies+ "\n";
        pomContent = pomContent.replace(dependencies, newDep);

        if(path.endsWith("/")) path +="/";
        GeneratorUtils.dumpContentToFile(path +"pom.xml", pomContent);
        return true;
    }

    //note: this is not the best way to implement this method.
    public static boolean updateMavenPathAnnotation(String path, String annotation) throws IOException{


        String pomContent = getPomFileContent(path);


        String from  = "<annotationProcessorPaths>", to ="</annotationProcessorPaths>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        StringBuilder annotations =  new StringBuilder(pomContent.substring(fromIndex, toIndex)).append("\n"+ annotation+ "\n");
        pomContent = pomContent.replace(pomContent.substring(fromIndex, toIndex), annotations.toString());
        if(path.endsWith("/")) path +="/";

        GeneratorUtils.dumpContentToFile(path +"pom.xml", pomContent);
        return true;
    }
    public static boolean updateMavenPathDepndencyManagement(String path, String annotation) throws IOException{


        String pomContent = getPomFileContent(path);


        String from  = "<annotationProcessorPaths>", to ="</annotationProcessorPaths>";
        int fromIndex = pomContent.indexOf(from )+ from.length();
        int toIndex = pomContent.indexOf(to);
        StringBuilder annotations =  new StringBuilder(pomContent.substring(fromIndex, toIndex)).append("\n"+ annotation+ "\n");
        pomContent = pomContent.replace(pomContent.substring(fromIndex, toIndex), annotations.toString());
        if(path.endsWith("/")) path +="/";

        GeneratorUtils.dumpContentToFile(path+"pom.xml", pomContent);
        return true;
    }


    public static boolean updateGradlePlugin(String cwd, String plugin, int index) throws FileNotFoundException, GradleReaderException {

        GradleProjectUtils gradleProjectUtils = new GradleProjectUtils();
        String gradleContent = getGradleFileContent(cwd);
        if(gradleContent.contains(plugin.trim()))
            return true;
        LinkedList<String> gradleContentAsList = new LinkedList<>(){{
            addAll(Arrays.asList(gradleContent.split("\n")));
        }};
        Tuple3<LinkedList<String> , Integer, Integer> plugins = gradleProjectUtils.getContentBetweenBraces(gradleContentAsList, "plugins {");
        if(index < 0)
            gradleContentAsList.add(plugins.getV2() + 1, plugin.replace("\n", ""));
        else
            gradleContentAsList.add(plugins.getV3() -1, plugin.replace("\n", ""));

        String newGradleContent = gradleContentAsList.stream().reduce("", (x,y)->
                new StringBuilder().append(x).append("\n").append(y).toString());
        try {
            String kts = "";
            if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
                kts = ".kts";
            GeneratorUtils.dumpContentToFile(cwd+"build.gradle"+ kts, newGradleContent.trim());
            return true;
        }catch(Exception ex)
        {
            return false;
        }
    }

    public static boolean sortGradleDependencies(String cwd) throws FileNotFoundException, GradleReaderException {
        GradleProjectUtils gradleProjectUtils = new GradleProjectUtils();

        String gradleContent = getGradleFileContent(cwd);
        LinkedList<String> gradleContentAsList =    new LinkedList<String>()
        {{
            addAll(Arrays.asList(gradleContent.split("\n")));
        }};
        Tuple3<LinkedList<String>, Integer, Integer> dependencies = gradleProjectUtils.getDependencies(
                gradleContentAsList
        );

        String first = dependencies.getV1().removeFirst();

        Collections.sort(dependencies.getV1());
        dependencies.getV1().addFirst(first);

        AtomicInteger indexDepStr = new AtomicInteger(dependencies.getV2());
        dependencies.getV1().forEach(x->{
            gradleContentAsList.set(indexDepStr.incrementAndGet(), x);
        });



        if(!cwd.endsWith("/")) cwd = cwd + "/";
        String newGradleContent = gradleContentAsList.stream().reduce("", (x,y)->
                new StringBuilder().append(x).append("\n").append(y).toString());
        try {
            String kts = "";
            if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
                kts = ".kts";
            GeneratorUtils.dumpContentToFile(cwd+"build.gradle"+ kts, newGradleContent.trim());
            return true;
        }catch(Exception ex)
        {
            return false;
        }

    }

    public static boolean updateGradlewDependencies(String cwd, String newDependencies, int index) throws IOException, GradleReaderException {
        GradleProjectUtils gradleProjectUtils = new GradleProjectUtils();

        String gradleContent = getGradleFileContent(cwd);

        if(gradleContent.contains(newDependencies.trim()))
            return true;

        LinkedList<String> gradleContentAsList =    new LinkedList<String>()
        {{
            addAll(Arrays.asList(gradleContent.split("\n")));
        }};
        Tuple3<LinkedList<String>, Integer, Integer> dependencies = gradleProjectUtils.getDependencies(
             gradleContentAsList
        );

        if(index < 0)
            gradleContentAsList.add(dependencies.getV2() + 1, "\t\t"+newDependencies.trim());
        else
            gradleContentAsList.add(dependencies.getV3() -1, "\t\t"+newDependencies.trim());



        String newGradleContent = gradleContentAsList.stream().reduce("", (x,y)->
                new StringBuilder().append(x).append("\n").append(y).toString());

        try {
            if(!cwd.endsWith("/")) cwd = cwd+ "/";

            String kts = "";
            if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
                kts = ".kts";
            GeneratorUtils.dumpContentToFile(cwd+"build.gradle"+ kts, newGradleContent);

            return true & sortGradleDependencies(cwd);
        }catch(Exception ex)
        {
            return false;
        }
    }

    @Deprecated
    public static boolean updateGradlewDependenciesOld(String cwd, String newDependencies, int index) throws IOException
    {
        String gradleContent = getGradleFileContent(cwd);

        if(gradleContent.contains(newDependencies.trim()))
            return true;

        if(cwd.endsWith("/")) cwd +="/";


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
        String kts = "";
        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
            kts = ".kts";
        if(index<0 || !gradleContent.contains(prefix) || prefix.isEmpty())
        {
            String from  = "dependencies {", to ="application {";
            int fromIndex = gradleContent.indexOf(from )+ from.length();
            int toIndex = gradleContent.indexOf(to);
            String dependencies= gradleContent.substring(fromIndex, toIndex);
            String newDep = index <= 0? newDependencies + "\n" + dependencies :dependencies+"\n"+newDependencies;
            gradleContent = gradleContent.replace(dependencies, newDep);

            GeneratorUtils.dumpContentToFile(cwd+"build.gradle"+ kts, gradleContent);

            return true;
        }
        else if(gradleContent.contains(prefix)) {
            int lastIndexOfPrefix = gradleContent.lastIndexOf(prefix);
//            System.out.println(prefix);
//            System.out.println(lastIndexOfPrefix);
            String replaceString =      gradleContent.substring(lastIndexOfPrefix,
                    gradleContent.indexOf("\n", lastIndexOfPrefix));



            gradleContent = gradleContent.replace(replaceString, replaceString+"\n" + newDependencies);

            GeneratorUtils.dumpContentToFile(cwd+"build.gradle"+ kts, gradleContent);

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

    public static String getJavaVersion(String cwd) throws IOException, XmlPullParserException {
        String buildContent ="";
        if(isGradle(cwd)){
            buildContent = getGradleFileContent(cwd);
            String theLine =  "sourceCompatibility = JavaVersion.toVersion(\"11\")";
            String target = "JavaVersion.toVersion";
            Scanner sc = new Scanner(buildContent);
            while( (theLine = sc.nextLine()) != null){
                if(theLine.contains("JavaVersion.toVersion")){
                    String version = theLine.substring(theLine.indexOf("JavaVersion.toVersion"))
                            .replace("JavaVersion.toVersion(\"", "")
                            .replace("\")", "");
                    return version;
                }
            }
            return "11";
        }
        else {
            File  pom = new File(cwd + "/pom.xml");
            MavenXpp3Reader mavenreader = new MavenXpp3Reader();
            Model mavenPom = mavenreader.read(new FileInputStream(pom));
            return mavenPom.getProperties().getProperty("jdk.version", "11") ;
        }

    }

    private static String getGradleFileContent(String cwd) throws FileNotFoundException {
        String kts = "";
        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
            kts = ".kts";

        File  build = new File(cwd + "/build.gradle"+kts);
        return GeneratorUtils.getFileContent(build);
    }

    public static String getPomFileContent(String path) throws FileNotFoundException {
//        String cwd = System.getProperty("user.dir");
        File  pom = new File(path + "/pom.xml");
        return GeneratorUtils.getFileContent(pom);
    }
    public static ProjectInfo getProjectInfo(String cwd) throws FileNotFoundException{
        if(projectInfo != null) return projectInfo;
      //  String cwd = System.getProperty("user.dir");
        File  micronautCli = new File(cwd + "/micronaut-cli.yml");
        if(!micronautCli.exists()) return null;

        Yaml yaml = new Yaml();
        String content = GeneratorUtils.getFileContent(micronautCli);

//        return (projectInfo = yaml.loadAs(content, ProjectInfo.class));
//        projectInfo = yaml.loadAs(content, ProjectInfo.class);
//        if(projectInfo.getApplicationType().equalsIgnoreCase("default"))
//            return (projectInfo = null);
//        return projectInfo;


        return (projectInfo = yaml.loadAs(content, ProjectInfo.class))
                .getApplicationType().equalsIgnoreCase("default") || projectInfo.getApplicationType().equalsIgnoreCase("function")?projectInfo:null;
    }

    public static String getMainPackage(String path) throws IOException {
        return getProjectInfo(path).getDefaultPackage();
    }

    public static boolean addLombok(String path, ProjectInfo projectInfo) throws IOException, XmlPullParserException, GradleReaderException {
       if(projectInfo.getFeatures().contains("lombok"))
           return true;
        if(projectInfo.getSourceLanguage().equalsIgnoreCase("java"))
        {
            Feature lombok = FeaturesFactory.features(projectInfo).get("lombok");
            projectInfo.getFeatures().add("lombok");
            if(projectInfo.getBuildTool().equalsIgnoreCase("gradle"))
            {
                updateGradlewDependencies(path, lombok.getAnnotationGradle(), -2);
                updateGradlewDependencies(path, lombok.getGradle(), 3);
//                updateGradlewDependencies(lombok.getTestGradleAnnotation(), 2);
//                updateGradlewDependencies(lombok.getTestGradle(),2);

                return true;
            }
            else if(projectInfo.getBuildTool().equalsIgnoreCase("maven"))
            {
                MavenProjectUtils.addDependency(lombok, path+"pom.xml");
                MavenProjectUtils.addAnnotation(lombok, path+"pom.xml");

                //todo

                return true;
            }
        }
        return false;
    }
    public static boolean addOpenapi(String path) throws IOException, XmlPullParserException, GradleReaderException {

        if(getProjectInfo(path).getApplicationType().equalsIgnoreCase("default"))
        {
            Feature openapi = FeaturesFactory.features(projectInfo).get("openapi");
            if(getProjectInfo(path).getBuildTool().equalsIgnoreCase("gradle"))
            {
                updateGradlewDependencies(path, openapi.getAnnotationGradle(), 1);
                updateGradlewDependencies(path, openapi.getGradle(), 3);


                return true;
            }
            else if(getProjectInfo(path).getBuildTool().equalsIgnoreCase("maven"))
            {

                MavenProjectUtils.addAnnotation(openapi, "pom.xml");
                MavenProjectUtils.addDependency(openapi, "pom.xml");

                //todo
                return true;
            }


        }
        return false;
    }


    public static boolean addExposingSwaggerUI(String path) throws FileNotFoundException {
        if(getProjectInfo(path).getBuildTool().equalsIgnoreCase("gradle"))
        {
            return addExposingSwaggerUIToGradle(path);
        }
        else if(getProjectInfo(path).getBuildTool().equalsIgnoreCase("maven"))
        {
            return addExposingSwaggerUIToMaven(path);
        }

        return false;
    }
    public  static boolean addExposingSwaggerUIToMaven(String path) throws FileNotFoundException {
        if(path.endsWith("/")) path +="/";

        if(getProjectInfo(path).getSourceLanguage().equalsIgnoreCase("java")){
            String pom = getPomFileContent(path).replace("<compilerArgs>",
                    "<compilerArgs>\n" + "                    <arg>-J-Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop</arg>");

            return true;
        }
        else if(getProjectInfo(path).getSourceLanguage().equalsIgnoreCase("kotlin"))
        {
            String pom = getPomFileContent(path);


            return true;
        }
        else if(getProjectInfo(path).getSourceLanguage().equalsIgnoreCase("groovy"))
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

            String pom = getPomFileContent(path).replace(index, replace);
            GeneratorUtils.dumpContentToFile(path +"pom.xml", pom);

            return true;
        }
        return false;
    }

    public static boolean addingTaskToGradleFile(String path, String task) throws FileNotFoundException {
        if(projectInfo == null)
            projectInfo = getProjectInfo(path);
        String gradleContent = getGradleFileContent(path)+ "\n" + task;
        String kts = "";
        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
            kts = ".kts";
        if(!path.endsWith("/")) path += "/";
        return GeneratorUtils.dumpContentToFile(path+"build.gradle" + kts, gradleContent);

    }
    public  static boolean addExposingSwaggerUIToGradle(String path) throws FileNotFoundException {
        if(projectInfo == null)
            projectInfo = getProjectInfo(path);
        path = path.endsWith("/")?path:path + "/";
        if(getProjectInfo(path).getSourceLanguage().equalsIgnoreCase("java")) {
            String gradleContent = getGradleFileContent( path)+ "\n" + "tasks.withType(JavaCompile) {\n" +
                    "    options.fork = true\n" +
                    "    options.forkOptions.jvmArgs << '-Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop'\n" +
                    "}";
            String kts = "";
            if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
                kts = ".kts";
            GeneratorUtils.dumpContentToFile(path + "build.gradle" + kts, gradleContent);
            return true;
        }
        else if(getProjectInfo(path).getSourceLanguage().equalsIgnoreCase("kotlin"))
        {
            String gradleContent = getGradleFileContent(path)
                    + "\n"
                    + "kapt {\n" +
                    "    arguments {\n" +
                    "        arg(\"micronaut.openapi.views.spec\", \"redoc.enabled=true,rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop\")\n" +
                    "    }\n" +
                    "}";

            return true;
        }
        else if(getProjectInfo(path).getSourceLanguage().equalsIgnoreCase("groovy")){

            String gradleContent = getGradleFileContent(path)+ "\n"
                    +"tasks.withType(GroovyCompile) {\n" +
                    "    groovyOptions.forkOptions.jvmArgs.add('-Dgroovy.parameters=true')\n" +
                    "    groovyOptions.forkOptions.jvmArgs.add('-Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop')\n" +
                    "   \n" +
                    "}";
            String kts = "";
            if(projectInfo.getBuildTool().equalsIgnoreCase("gradle_kotlin"))
                kts = ".kts";
            GeneratorUtils.dumpContentToFile(path+"build.gradle" + kts, gradleContent);
            return true;
        }
            return false;
    }

    public static boolean addR2DBCependency(String path, Feature... feature) throws IOException, GradleReaderException {
        if(projectInfo == null)
            projectInfo = getProjectInfo(path);

        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle"))
        {

            return (
                    updateGradlewDependencies(path, Arrays.stream(feature).map(x->x.getRdbcGradle() !=null? x.getRdbcGradle():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(path, Arrays.stream(feature).map(x->x.getTestRdbcGradle() !=null? x.getTestRdbcGradle():"").reduce("", (x, y)->x+"\n"+ y),2)) ;


        }
        else if(projectInfo.getBuildTool().equalsIgnoreCase("maven"))
        {

            return Arrays.stream(feature).map(
                    x->{
                        try {
                            if(x.getRdbcMaven() == null || x.getTestRdbcMaven() == null) return false;
                            x.getMaven().clear();
                            x.getMaven().add(x.getRdbcMaven());
                            x.setTestMaven(x.getRdbcMaven());
                            // todo it is not completed
                            return MavenProjectUtils.addDependency(x, "pom.xml") &&
                                    MavenProjectUtils.addAnnotation(x, "pom.xml")
                                    && MavenProjectUtils.addDependencyToDependecyMgmt(x, "pom.xml")
                                    && MavenProjectUtils.addTestDependency(x, "pom.xml")
                                    && MavenProjectUtils.addPluginBuild(x, "pom.xml")
                                    && MavenProjectUtils.addProperties(x, "pom.xml")

                                    ;
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

    public static boolean addDependency(String path, Feature... feature) throws IOException, GradleReaderException {


        if(projectInfo == null)
            projectInfo = getProjectInfo(path);
        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle"))
        {

            return (updateGradlewDependencies(path, Arrays.stream(feature).map(x->x.getGradle() != null? x.getGradle():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(path, Arrays.stream(feature).map(x->x.getAnnotationGradle() !=null? x.getAnnotationGradle():"").reduce("", (x, y)->x+"\n"+ y),1)
                    &&
                    updateGradlewDependencies(path, Arrays.stream(feature).map(x->x.getTestGradle() !=null? x.getTestGradle():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(path, Arrays.stream(feature).map(x->x.getTestGradleAnnotation() !=null? x.getTestGradleAnnotation():"").reduce("", (x, y)->x+"\n"+ y),2)
                    &&
                    updateGradlewDependencies(path, Arrays.stream(feature).map(x->x.getTestContainerGradle() !=null? x.getTestContainerGradle():"").reduce("", (x, y)->x+"\n"+ y),2)
                   ) &&
                    updateGradlePlugin(path, Arrays.stream(feature).map(x-> !x.getGradlePlugins().isEmpty()?x.getGradlePlugins().stream().reduce("",(y,z)->y+"\n"+z):"").reduce("", (x, y)->x+"\n"+ y), 2);


        }
        else if(projectInfo.getBuildTool().equalsIgnoreCase("maven"))
        {

            return Arrays.stream(feature).map(
                    x->{
                        try {

                            // todo it is not completed
                            return MavenProjectUtils.addDependency(x, "pom.xml") &&
                                    MavenProjectUtils.addAnnotation(x, "pom.xml")
                                    && MavenProjectUtils.addDependencyToDependecyMgmt(x, "pom.xml")
                                    && MavenProjectUtils.addTestDependency(x, "pom.xml")
                                    && MavenProjectUtils.addPluginBuild(x, "pom.xml")
                                    && MavenProjectUtils.addProperties(x, "pom.xml")

                                    ;
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


    public static boolean addingOpenApiToApplicationFile(String path, String appName) throws FileNotFoundException {

        if(projectInfo == null)
            projectInfo = getProjectInfo(path);

        if(!projectInfo.getApplicationType().equalsIgnoreCase("default"))
            return false;


        String annotations = ("import io.swagger.v3.oas.annotations.*;\n" +
                "import io.swagger.v3.oas.annotations.info.*;\n" +
                "\n" +
                "@OpenAPIDefinition(\n" +
                "    info = @Info(\n" +
                "            title = \"demo\",\n" +
                "            version = \"0.0\"\n" +
                "    )\n" +
                ")"+(projectInfo.getSourceLanguage().toLowerCase().equalsIgnoreCase(KOTLIN_LANG)?"\nobject Api {\n" +
                "}":"")).replace("demo", appName);

        if(projectInfo.getSourceLanguage().equalsIgnoreCase("kotlin"))
        {
            annotations = annotations.replace("@Info", "Info");
        }
        if(!path.endsWith("/"))path = path + "/";
        String ext = projectInfo.getSourceLanguage().equalsIgnoreCase("kotlin")? ".kt": "."+ getProjectInfo(path).getSourceLanguage().toLowerCase();
        String mainFilePath = path +"src/main/"+projectInfo.getSourceLanguage()+"/"+ GeneratorUtils.packageToPath(projectInfo.getDefaultPackage())+"/Application"+ext;
        String from = projectInfo.getSourceLanguage().equalsIgnoreCase(KOTLIN_LANG)?"import io.micronaut.runtime.Micronaut.*":(projectInfo.getSourceLanguage().equalsIgnoreCase(GROOVY_LANG)?"import groovy.transform.CompileStatic":"import io.micronaut.runtime.Micronaut;");
        if(!projectInfo.getSourceLanguage().equalsIgnoreCase("java"))
        {
           annotations = annotations.replace(";","");
           from = from.replace(";", "");
        }

        String content = GeneratorUtils.getFileContent(new File(mainFilePath));
        content = content.replace(from, from+"\n"+annotations );

        GeneratorUtils.dumpContentToFile(mainFilePath, content);
        return false;
    }


    private static TemplatesService templatesService;
    static {
        templatesService = new TemplatesService();
        templatesService.loadTemplates(null);
    }
    public static boolean appendJDBCToProperties(String path, String database, boolean main, boolean testWithH2, String databaseName,String migrationTool) throws FileNotFoundException {
        //todo

        if(!path.endsWith("/") ) path = path + "/";
        String propertiesPath = path+ "src/main/resources/application"+(main?"":"-test")+".yml";

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

            String schemaGenerate = migrationTool.equalsIgnoreCase("none")?"CREATE_DROP":"none";

            String content = GeneratorUtils.generateFromTemplate(template, new HashMap<String, String>(){{
                
                put("databaseName", databaseName);
                put("schemaGenerate", schemaGenerate);
            }});

            return GeneratorUtils.dumpContentToFile(propertiesPath, new StringBuilder().append(propertiesContent).append(propertiesContent.isEmpty() ? "" : "\n---\n").append(content).toString());
            //return GeneratorUtils.appendContentToFile(propertiesPath,propertiesContent);
        }
        return false;
    }
    public static boolean appendJPAToProperties(String path, String database, boolean main, boolean testWithH2, String databaseName, String migrationTool) throws IOException {
        //todo
        if(database.contains("gorm"))
            return appendJDBCToProperties(path, database, main, testWithH2, databaseName, migrationTool);
      return appendJDBCToProperties(path, database, main, testWithH2, databaseName, migrationTool)&&
        appendToProperties(path, templatesService.loadTemplateContent(templatesService.getProperties().get(TemplatesService.JPA_yml)));
    }
    public static boolean appendToProperties(String cwd, String properties) throws IOException {
        //todo
        if(!cwd.endsWith("/")) cwd +="/";
        String propertiesPath = cwd + "src/main/resources/application.yml";
        String propertiesContent = GeneratorUtils.getFileContent(new File(propertiesPath)).replaceAll("^\\s+","");
        if(!propertiesContent.contains(properties))
        {

            boolean appending =  GeneratorUtils.appendContentToFile(propertiesPath, propertiesContent + "\n---\n"+properties) ;

            return appending;  //&& formattingYamlFile(propertiesPath);
        }
        return false;
    }
    public static boolean writeProperties(String properties) throws IOException {
        //todo
        String propertiesPath = "src/main/resources/application.yml";
        return GeneratorUtils.dumpContentToFile(propertiesPath, properties) ;
    }
    public static String getPropertiesContent(){
        String propertiesPath = "src/main/resources/application.yml";
        try {
                return GeneratorUtils.getFileContent(new File(propertiesPath)).replaceAll("^\\s+","");
        } catch (FileNotFoundException e) {
                return "";
        }

    }
    public static boolean formattingYamlFile(String path) throws IOException {
//        InputStream inputStream = new FileInputStream(new File(path));
        YamlReader reader = new YamlReader(new FileReader(path));
        Map<String, Object> data = (Map<String, Object>) reader.read();
        System.out.println(data);
//        PrintWriter writer = new PrintWriter(new File(path));
//        Yaml yaml = new Yaml();
//        yaml.dump(data, writer);
        return true;
    }

    public static boolean appendToXMLNode(String cwd, String node, String path) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

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


    public static boolean appendR2DBCToProperties(String path, String database, boolean main, boolean testWithH2, String databaseName,String migrationTool) throws FileNotFoundException {

        if(!path.endsWith("/")) path = path +"/";
        String propertiesPath = path +"src/main/resources/application"+(main?"":"-test")+".yml";

        String propertiesContent = GeneratorUtils.getFileContent(new File(propertiesPath));
        if(!propertiesContent.contains(database.replace("-test", "").toLowerCase())){



            String template = templatesService.loadTemplateContent(templatesService.getProperties().get(database));

            //this scope will be invoked if the user choose to test with H2 instead of TestContainer.
            if(testWithH2 && !main)
            {
                template = templatesService.loadTemplateContent(templatesService.getProperties().get(H2_R2DBC_yml));
            }
            if(template.isEmpty())
                return true;

            String schemaGenerate = migrationTool.equalsIgnoreCase("none")?"CREATE_DROP":"none";

            String content = GeneratorUtils.generateFromTemplate(template, new HashMap<String, String>(){{

                put("databaseName", databaseName);
                put("schemaGenerate", schemaGenerate);
            }});

            return GeneratorUtils.dumpContentToFile(propertiesPath, new StringBuilder().append(propertiesContent).append(propertiesContent.isEmpty() ? "" : "\n---\n").append(content).toString());
            //return GeneratorUtils.appendContentToFile(propertiesPath,propertiesContent);
        }
        return false;
    }

    public static String getAppName(String path) throws IOException, XmlPullParserException {
        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle")){
            return new GradleProjectUtils().getAppName(path);
        }
        else
            return MavenProjectUtils.getArtifactId("pom.xml");
    }
}





















