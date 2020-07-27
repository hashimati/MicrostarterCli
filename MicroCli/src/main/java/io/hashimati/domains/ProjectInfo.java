package io.hashimati.domains;

import io.hashimati.utils.GeneratorUtils;
import io.hashimati.utils.ProjectValidator;
import io.hashimati.utils.Visitor;
import lombok.Data;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

@Data
public class ProjectInfo {

    private String applicationType,
    defaultPackage,
    testFramework,
    sourceLanguage,
    buildTool;

    private HashSet<String> features;
    public ProjectInfo visit(Visitor<ProjectInfo> visitor)
    {
        return visitor.visit(this);
    }

    public void dumpToFile() throws IOException {
        LinkedHashMap<String, Object> dump = new LinkedHashMap<>();
        dump.put("applicationType", this.applicationType);
        dump.put("defaultPackage", this.defaultPackage);
        dump.put("testFramework", this.testFramework);
        dump.put("sourceLanguage", this.sourceLanguage);
        dump.put("buildTool" ,buildTool);
        dump.put("features", features.toArray());

        DumperOptions dumperOptions = new DumperOptions();
     //   dumperOptions.setDefaultFlowStyle(DumperOptionsfileWriter.);
        Yaml yaml = new Yaml();
        FileWriter fileWriter = new FileWriter("micronaut-cli.yml");

        yaml.dump(dump, fileWriter);
        fileWriter.flush();
        fileWriter.close();
        System.gc();
    }
    public static ProjectInfo fromFile() throws FileNotFoundException {
        String cwd = System.getProperty("user.dir");
        File micronautCli = new File(cwd + "/micronaut-cli.yml");
        if(!micronautCli.exists()) return null;
        Yaml yaml = new Yaml();
        String content = GeneratorUtils.getFileContent(micronautCli);
        return yaml.loadAs(content, ProjectInfo.class);
    }



}
