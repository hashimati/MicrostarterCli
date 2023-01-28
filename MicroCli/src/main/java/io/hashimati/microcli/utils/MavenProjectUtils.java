package io.hashimati.microcli.utils;
/**
 * @author Ahmed Al Hashmi
 */
import io.hashimati.microcli.config.Feature;
import org.apache.maven.model.*;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MavenProjectUtils {


    public static Model readPom(String path) throws IOException, XmlPullParserException {


        MavenXpp3Reader reader  = new MavenXpp3Reader();
        Model model = reader.read(new FileReader(path+"/pom.xml"));

        return model;
    }


    public static boolean writeModelToPom(String path, Model model)
    {
        MavenXpp3Writer writer = new MavenXpp3Writer();
        try {
            writer.write(new FileWriter(path +"/pom.xml"), model);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addDependency(Feature feature, String path) throws IOException, XmlPullParserException {



        if(feature.getMaven() == null|| feature.getMaven().isEmpty()) return true;
        Model model = readPom(path);
//        System.out.println(feature.getMaven());
//        System.out.println(dependency.getGroupId());
//        System.out.println(dependency.getArtifactId());

      //  model.addDependency(dependency);
        feature.getMavenDependency().forEach(d->model.addDependency(d));
        return writeModelToPom(path, model);
    }
    public static boolean addTestDependency(Feature feature, String path) throws IOException, XmlPullParserException {
        if( feature.getTestMaven() == null|| feature.getTestMaven().isEmpty()) return true;
        Model model = readPom(path);
        Dependency dependency = feature.getMavenDependency(feature.getTestMaven());
//        System.out.println(feature.getMaven());
//        System.out.println(dependency.getGroupId());
//        System.out.println(dependency.getArtifactId());

        model.addDependency(dependency);
        return writeModelToPom(path, model);
    }

    public static boolean addDependencyToDependecyMgmt(Feature feature, String path) throws IOException, XmlPullParserException {
        if( feature.getDepndencyManagement() == null || feature.getDepndencyManagement().isEmpty() ) return true;
        Model model = readPom(path);
        DependencyManagement dependencyManagement  = model.getDependencyManagement() == null? new DependencyManagement(): model.getDependencyManagement();
        dependencyManagement.addDependency(feature.getMavenDependency(feature.getDepndencyManagement()));
        model.setDependencyManagement(dependencyManagement);
        return writeModelToPom(path, model);
    }
    public static boolean addAnnotation(Feature feature, String path) throws IOException, XmlPullParserException {
        if(feature.getAnnotationMaven() == null || feature.getAnnotationMaven().isEmpty() ) return true;
//        Model model = readPom(path);
//        Build build = model.getBuild();
//        Plugin plugin = build.getPlugins().stream().filter(x->x.getArtifactId().equalsIgnoreCase("maven-compiler-plugin")).findFirst().get();
//
//        if(plugin == null) return true;
//        plugin.setConfiguration(plugin.getConfiguration().toString().replace("</annotationProcessorPaths>", feature.getAnnotationMaven()+ "\n</annotationProcessorPaths>"));
//        Model model = readPom(path);
//
//        Build build = model.getBuild();
//        String groopId = "org.apache.maven.plugins", artifactId = "maven-compiler-plugin";
//
//        Plugin plugin = null;
//        for( Plugin p : build.getPlugins())
//        {
//            if(p.getArtifactId().equals(artifactId) && p.getGroupId().equals(groopId))
//            {
//                plugin = p ;
//                break;
//            }
//
//        }
//
//
//        plugin.setConfiguration(plugin.getConfiguration().toString().replace("</annotationProcessorPaths>", feature.getAnnotationMaven()+ "\n</annotationProcessorPaths>"));
//        System.out.println(plugin.getConfiguration());
//
//        writeModelToPom(path, model);
//        // System.out.println("is instance = "+ (plugin.getConfiguration() instanceof Xpp3Dom));


        String pomContent = MicronautProjectValidator.getPomFileContent(path);

        pomContent = pomContent.replace("</annotationProcessorPaths>", feature.getAnnotationMaven()+ "\n\t\t</annotationProcessorPaths>");
        GeneratorUtils.dumpContentToFile(path+"/pom.xml", pomContent);
        //   GeneratorUtils.dumpContentToFile(path, MicronautProjectValidator.getPomFileContent(path).replace("</annotationProcessorPaths>", feature.getAnnotationMaven()+ "\n                    </annotationProcessorPaths>"));

        return true;
    }
    public static boolean addCompileArgs(Feature feature, String path) throws IOException, XmlPullParserException {
        if(feature.getAnnotationMaven() == null || feature.getAnnotationMaven().isEmpty() ) return true;


        String pomContent = MicronautProjectValidator.getPomFileContent(path);

        pomContent = pomContent.replace("</compilerArgs>", feature.getMavenCompileArgs().stream().reduce((x,y)->x+y).orElse("")+ "\t</compilerArgs>");
        GeneratorUtils.dumpContentToFile(path+"/pom.xml", pomContent);

        return true;
    }
    public boolean addMicronautDataProperty(String path) throws IOException, XmlPullParserException {
        Model model = readPom(path);
        model.addProperty("micronaut.data.version", "3.0.0");
        model.addProperty("micronaut.openapi.version", "1.5.2");

        return  writeModelToPom(path, model);
    }
    public static boolean addProperties(Feature x, String path) throws IOException, XmlPullParserException {
        Model model = readPom(path);

        x.getMavenProperties().keySet().stream().forEach(y->{
            model.addProperty(y,x.getMavenProperties().get(y));
        });

        return  writeModelToPom(path, model);
    }

    public static boolean addPluginBuild(Feature x, String path) throws IOException, XmlPullParserException {
        Model model = readPom(path);

        if(x.getPlugin() != null)
            model.getBuild().addPlugin(x.getPlugin());


        return  writeModelToPom(path, model);
    }


    public static String getVersion(String path) throws XmlPullParserException, IOException {
        return readPom(path).getVersion();
    }

    public static String getArtifactId(String path) throws XmlPullParserException, IOException {
        return readPom(path).getArtifactId();
    }

}
