package io.hashimati.microcli.domains;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.Visitor;
import lombok.Data;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@Data
public class ProjectInfo {

    private String applicationType,
    defaultPackage,
    testFramework,
    sourceLanguage,
    srcExtension,
    artifact,
    buildTool;

    private LinkedHashSet<String> features;
    public ProjectInfo visit(Visitor<ProjectInfo> visitor)
    {
        return visitor.visit(this);
    }

    public void dumpToFile(String cwd) throws IOException {
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
        if(!cwd.endsWith("/") ) cwd = cwd + "/";
        FileWriter fileWriter = new FileWriter(cwd + "micronaut-cli.yml");

        yaml.dump(dump, fileWriter);
        fileWriter.flush();
        fileWriter.close();
        System.gc();
    }
    public static ProjectInfo fromFile(String cwd) throws FileNotFoundException {

        File micronautCli = new File(cwd + "/micronaut-cli.yml");
        if(!micronautCli.exists()) return null;
        Yaml yaml = new Yaml();
        String content = GeneratorUtils.getFileContent(micronautCli);
        return yaml.loadAs(content, ProjectInfo.class);
    }



}
