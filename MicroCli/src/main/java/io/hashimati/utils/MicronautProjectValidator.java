package io.hashimati.utils;

import io.hashimati.config.Feature;
import io.hashimati.config.FeaturesFactory;
import io.hashimati.domains.ProjectInfo;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

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
    public static boolean updateGradlewDependencies(String newDependencies, int index) throws IOException
    {
        String gradleContent = getGradleFileContent();

        if(gradleContent.contains(newDependencies.trim()))
            return true;
        String from  = "dependencies {", to ="}\n" +
                "\n" +
                "test.classpath";
        int fromIndex = gradleContent.indexOf(from )+ from.length();
        int toIndex = gradleContent.indexOf(to);
        String dependencies= gradleContent.substring(fromIndex, toIndex);
        String newDep = index <= 0? newDependencies + "\n" + dependencies + "\n" :dependencies+"\n"+newDependencies+"\n";
        gradleContent = gradleContent.replace(dependencies, newDep);
        GeneratorUtils.dumpContentToFile("build.gradle", gradleContent);
        return true;
    }


    private static String getGradleFileContent() throws FileNotFoundException {
        String cwd = System.getProperty("user.dir");
        File  build = new File(cwd + "/build.gradle");
        return GeneratorUtils.getFileContent(build);
    }

    private static String getPomFileContent() throws FileNotFoundException {
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

    public static boolean addLombok() throws IOException {
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
                return true;
            }
        }
        return false;
    }
    public static boolean addDependency(Feature... feature) throws IOException {
        if(projectInfo.getBuildTool().equalsIgnoreCase("gradle"))
        {
            return updateGradlewDependencies(Arrays.stream(feature).map(x->x.getGradle()).reduce("", (x, y)->x+"\n"+ y),2);
        }
        else if(projectInfo.getBuildTool().equalsIgnoreCase("maven"))
        {
            return updateMavenDependencies(Arrays.stream(feature).map(x->x.getMaven()).reduce("", (x, y)->x+"\n"+ y));
        }
        return false;
    }
}





















