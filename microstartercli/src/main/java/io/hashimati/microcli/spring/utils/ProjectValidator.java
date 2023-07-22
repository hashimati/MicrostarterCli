package io.hashimati.microcli.spring.utils;

import io.hashimati.microcli.config.Feature;
import io.hashimati.microcli.utils.MavenProjectUtils;
import org.apache.maven.model.Model;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.spring.utils.GeneratorUtils.getWorkingDirectory;
import static io.hashimati.microcli.utils.MavenProjectUtils.readPom;

public class ProjectValidator {

    public boolean isMavenOrGradle() throws FileNotFoundException {
        return isMaven() || isGradle();
    }
    public boolean isMaven() throws FileNotFoundException {

        return new File(getWorkingDirectory()+ "/pom.xml").exists();
    }
    public boolean isGradle() throws FileNotFoundException {
        return new File(getWorkingDirectory()+ "/build.gradle").exists();
    }
    public boolean isGradleKotlin() throws FileNotFoundException {
        return new File(getWorkingDirectory()+ "/build.gradle.kts").exists();
    }
    public String getProjectLanguage() throws FileNotFoundException {
        String buildFile = getWorkingDirectory() + (isMaven()?"/pom.xml":(isGradleKotlin()? "/build.gradle.ktl":"/build.gradle") );
        String language = JAVA_LANG;
        String fileContent = GeneratorUtils.getFileContent(new File(buildFile));
        if(fileContent.contains("org.jetbrains.kotlin"))
        {
            language = KOTLIN_LANG;
        }
        else if(fileContent.contains("org.codehaus.groovy"))
        {
            language = GROOVY_LANG;
        }
        return language;
    }

    public String getDefaultPackage() throws IOException, XmlPullParserException {
        String defaultPackage = "com.example";
        String filePath = getWorkingDirectory() + (isMaven()?"/pom.xml":(isGradleKotlin()? "/build.gradle.ktl":"/build.gradle") );
        File buildFile = new File(filePath);
        String fileContent = GeneratorUtils.getFileContent(buildFile);
        if(isMaven())
        {
            Model model = readPom(filePath);
            return model.getGroupId()+"."+getArtifact();
        }
        else if(isGradle())
        {
            return fileContent.substring(fileContent.indexOf("group ="),
                            fileContent.indexOf("\n", fileContent.indexOf("group =")
                            )).replace("group =", "")
                    .replace("\"", "").replace("\u0027","").trim()+"."+getArtifact();
        }
        return defaultPackage;
    }

    public String getJdkVersion() throws IOException, XmlPullParserException {
        String filePath = getWorkingDirectory() + (isMaven()?"/pom.xml":"/build.gradle" );
        File buildFile = new File(filePath);

        String fileContent = GeneratorUtils.getFileContent(buildFile);
        if(isMaven())
        {
            return fileContent.substring(fileContent.indexOf("<java.version>"),
                            fileContent.indexOf("</java.version>"))
                    .replace("<java.version>", "")
                    .replace("</java.version>", "").trim();
        }
        else if(isGradle())
        {
            return fileContent.substring(fileContent.indexOf("sourceCompatibility ="),
                            fileContent.indexOf("\n", fileContent.indexOf("sourceCompatibility =")))
                    .replace("sourceCompatibility =", "")
                    .replace("'", "").trim();
        }
        return "11";
    }

    public String getArtifact() throws IOException, XmlPullParserException {
        String filePath = getWorkingDirectory() + (isMaven()?"/pom.xml":"/settings.gradle" );
        File buildFile = new File(filePath);
        String fileContent = GeneratorUtils.getFileContent(buildFile);
        if(isMaven())
        {
            Model model = readPom(filePath);
            return model.getArtifactId();
        }
        else
        {
            return fileContent.substring(fileContent.indexOf("rootProject.name =")).
                    replace("rootProject.name =", "")
                    .replace("'", "").trim();
        }
    }
    public  boolean addDependency(Feature feature) throws IOException, XmlPullParserException {
        if(isMaven()){
            return MavenProjectUtils.addDependency(feature,getWorkingDirectory()+ "/pom.xml");
        }
        else if (isGradle()){
            return GradleProjectUtils.updateGradlewDependencies(feature, isGradleKotlin());
        }
        return false;
    }
}
