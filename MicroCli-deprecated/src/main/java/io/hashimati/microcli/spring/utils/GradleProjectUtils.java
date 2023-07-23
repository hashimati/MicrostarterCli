package io.hashimati.microcli.spring.utils;

/**
 * @author Ahmed Al Hashmi
 */

import groovy.lang.Tuple;
import groovy.lang.Tuple3;
import io.hashimati.microcli.config.Feature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static io.hashimati.microcli.spring.utils.GeneratorUtils.getWorkingDirectory;


public class GradleProjectUtils{


    public static boolean updateGradlewDependencies(Feature feature, boolean kotlin) {

        AtomicBoolean result = new AtomicBoolean(true);


        try {
            return updateGradlewDependencies(kotlin?feature.getGradle().replaceFirst("'", "(\"").replaceFirst("'", "\")"):feature.getGradle(), 2, kotlin);
        } catch (IOException e) {
          //  throw new RuntimeException(e);
            return false;

        } catch (GradleReaderException e) {
           // throw new RuntimeException(e);
            return false;
        }


    }

    /**
     *
     * @param build: build.gradle file
     * @return file content in a linked list LinkedList<String>
     * @throws FileNotFoundException
     */
    public LinkedList<String> readGradleFile(File build) throws FileNotFoundException {

        LinkedList<String> content = new LinkedList<>();
//        String cwd = System.getProperty("user.dir");
//        File build = new File(cwd + "/build.gradle");
        if(!build.exists()) return new LinkedList<>();
        Scanner sc = new Scanner(build);

        while (sc.hasNextLine()){
            content.add(sc.nextLine());
        }
        sc.close();
        return content;
    }

    /**
     *
     * @param fileContent: The gradle file as linked list of String
     * @return a Tuple3 object. V1 = dependencies as linked list, V2. The index of the "dependencies {", and V3 is the closing bracket index
     * @throws FileNotFoundException
     * @throws GradleReaderException
     */
    public Tuple3<LinkedList<String>, Integer, Integer> getDependencies(LinkedList<String> fileContent) throws FileNotFoundException, GradleReaderException {
        LinkedList<String> result = new LinkedList<String>();


        int indexOfDependencies = -1;
        int indexOfBound = -1;
        int braceCounter = 0;
        String dependenciesIndex = "dependencies {";
        String boundIndex = "}";

        findingDependencies:
        for(int i = 0; i < fileContent.size(); i++){
            if(fileContent.get(i).contains(dependenciesIndex)){
                indexOfDependencies = i;

                braceCounter += 1;
                break findingDependencies;
            }
        }
        findingBound:
        for(int i = indexOfDependencies+1;i < fileContent.size() ; i++)
        {
            String line = fileContent.get(i);
            if(!line.equalsIgnoreCase(boundIndex))
            {
                if(!line.trim().isEmpty()) result.add(line);
                if(line.contains("{")) braceCounter++;
                if(line.contains("}")) braceCounter--;
            }
            else if (line.equalsIgnoreCase(boundIndex)){
                braceCounter--;
                if(braceCounter == 0)
                {
                    indexOfBound = i;
                    break findingBound;
                }
            }
        }
        if(braceCounter != 0)
            throw new GradleReaderException("Couldn't read dependencies!");
       return Tuple.tuple(result, indexOfDependencies, indexOfBound);
    }

    /**
     *
     * @param fileContent
     * @param dependenciesIndex: Example: dependencies {, plugins {
     * @return
     * @throws FileNotFoundException
     * @throws GradleReaderException
     */
    public Tuple3<LinkedList<String>, Integer, Integer> getContentBetweenBraces(LinkedList<String> fileContent, String dependenciesIndex) throws FileNotFoundException, GradleReaderException {
        LinkedList<String> result = new LinkedList<String>();


        int indexOfDependencies = -1;
        int indexOfBound = -1;
        int braceCounter = 0;
        String boundIndex = "}";

        findingDependencies:
        for(int i = 0; i < fileContent.size(); i++){
            if(fileContent.get(i).contains(dependenciesIndex)){
                indexOfDependencies = i;

                braceCounter += 1;
                break findingDependencies;
            }
        }
        findingBound:
        for(int i = indexOfDependencies+1;i < fileContent.size() ; i++)
        {
            String line = fileContent.get(i);
            if(!line.equalsIgnoreCase(boundIndex))
            {
                if(!line.trim().isEmpty()) result.add(line);
                if(line.contains("{")) braceCounter++;
                if(line.contains("}")) braceCounter--;
            }
            else if (line.equalsIgnoreCase(boundIndex)){
                braceCounter--;
                if(braceCounter == 0)
                {
                    indexOfBound = i;
                    break findingBound;
                }
            }
        }
        if(braceCounter != 0)
            throw new GradleReaderException("Couldn't read dependencies!");
        return Tuple.tuple(result, indexOfDependencies, indexOfBound);
    }
    public void appendDependencies(LinkedList<String> list, String dependency)
    {
        list.addLast(dependency);
    }
    public void appendLombokAnnotation(LinkedList<String> list , String dependcy){
        list.addFirst(dependcy);
    }

    public String getPorjectVersion(LinkedList<String> gradleContent){
        String versionLine = "";
        for(String x: gradleContent){
            if(x.startsWith("version ="))
            {
                versionLine = x;
                break;
            }
        }
        return versionLine
                .replace("version =", "")
                .replaceAll("\"", "");
    }

    public String getAppName() throws FileNotFoundException {
        String settings = new StringBuilder(System.getProperty("user.dir")).append("/settings.gradle").toString();
       String content =  GeneratorUtils.getFileContent(new File(settings));
        Scanner scanner = new Scanner(content);
        String line = "";
        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            if(line.startsWith("rootProject.name=\"")){
                break;
            }
        }


        return line.replace("rootProject.name=\"", "")
                .replace("\"", "");
    }

    private static String getGradleFileContent() throws FileNotFoundException {
        String cwd = getWorkingDirectory();
        File  build = new File(cwd + "/build.gradle");
        return GeneratorUtils.getFileContent(build);
    }

    public static boolean updateGradlewDependencies(String newDependencies, int index, boolean kotlin) throws IOException, GradleReaderException {
        GradleProjectUtils gradleProjectUtils = new GradleProjectUtils();

        String gradleContent = getGradleFileContent();

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
            gradleContentAsList.add(dependencies.getV2() + 1, "\t"+newDependencies.trim());
        else
            gradleContentAsList.add(dependencies.getV3() -1, "\t"+newDependencies.trim());



        String newGradleContent = gradleContentAsList.stream().reduce("", (x,y)->
                new StringBuilder().append(x).append("\n").append(y).toString());
        try {


            GeneratorUtils.dumpContentToFile("build.gradle"+(kotlin?".kts":""), newGradleContent.trim());

            return true & sortGradleDependencies();
        }catch(Exception ex)
        {
            return false;
        }
    }
    public static boolean sortGradleDependencies() throws FileNotFoundException, GradleReaderException {
        GradleProjectUtils gradleProjectUtils = new GradleProjectUtils();

        String gradleContent = getGradleFileContent();
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



        String newGradleContent = gradleContentAsList.stream().reduce("", (x,y)->
                new StringBuilder().append(x).append("\n").append(y).toString());
        try {

            GeneratorUtils.dumpContentToFile("build.gradle", newGradleContent.trim());
            return true;
        }catch(Exception ex)
        {
            return false;
        }

    }



}
