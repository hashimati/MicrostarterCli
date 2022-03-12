package io.hashimati.microcli.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.EnumClass;
import io.hashimati.microcli.services.MicronautEntityGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.GROOVY_LANG;
import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.KOTLIN_LANG;
import static io.hashimati.microcli.constants.ProjectConstants.PathsTemplate.ENUMS;
import static io.hashimati.microcli.utils.PromptGui.printlnWarning;
import static io.hashimati.microcli.utils.PromptGui.setToDefault;


@Command(name = "create-enum",aliases = {"enum"}, description = {"To create Enumeration Class", "You can use the created enumeration as an attribute for an entity."})
public class CreateEnumCommand implements Callable<Integer> {
   
    @Option(names={"-n", "--name"}, description = "Enumeration class name")
    private String name;
    @Option(names = "--path", description = "To specify the working directory.")
    private String path;

    @Option(names = {"-o", "--options"}, description = {"Enumeration values ", "You can use commas without spaces to add multiple values.", "For Example, -o FOO,BAR,BOO"}, split =",")
    private HashSet<String> values ;


    @Inject
    MicronautEntityGenerator micronautEntityGenerator;
    @Override
    public Integer call() throws Exception {
        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        else {
            File directory = new File(path);
            if(!directory.exists()) {
                directory = new File(GeneratorUtils.getCurrentWorkingPath()+"/"+ path);
                if(!directory.exists()){

                    PromptGui.printlnErr("Cannot find the working path!");
                    return null;
                }
            }
        }
        micronautEntityGenerator.setPath(path);
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName(path));
        ConfigurationInfo  configurationInfo;
        if(!configurationFile.exists()){
           configurationInfo =  new ConfigureCommand().call();
        }
        else {
            configurationInfo = ConfigurationInfo.fromFile(configurationFile);
        }
        if(configurationInfo !=null)
        {
            EnumClass enumClass = new EnumClass();
            enumClass.setName(name);
            enumClass.setEnumPackage(configurationInfo.getProjectInfo().getDefaultPackage() + ".enums");
            enumClass.setValues(values);
             boolean isExist = configurationInfo.getEnums().stream().anyMatch(x->x.getName().equals(enumClass.getName()));
            if(isExist)
            {
                printlnWarning("Warning: "+ name + " is already exist. ");
                setToDefault();
                for(EnumClass e : configurationInfo.getEnums())
                {
                    if(e.getName().equals(name)) {
                        e.getValues().addAll(values);
                        values.addAll(e.getValues());
                        break;
                    }
                }
            }
            String enumFilePath = GeneratorUtils.generateFromTemplate(ENUMS, new HashMap<String, String>(){{
                put("lang", configurationInfo.getProjectInfo().getSourceLanguage());
                put("defaultPackage", GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage()));
            }});

            String extension = ".java";
            switch (configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase())
            {
                case GROOVY_LANG:
                    extension= ".groovy";
                    break;
                case KOTLIN_LANG:
                    extension = ".kt";
                    break;
                default:
                    extension = ".java";
                    break;
            }
            String outPutPath = path+enumFilePath+"/"+name+extension;


            GeneratorUtils.createFile(outPutPath.replace("\\", "/"), micronautEntityGenerator.generateEnum(enumClass, configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase()));

            //                System.out.println(micronautEntityGenerator.generateEnum(enumClass, configurationInfo.getProjectInfo().getSourceLanguage()));
//                System.out.println(micronautEntityGenerator.generateEnum(enumClass, "groovy"));
//                System.out.println(micronautEntityGenerator.generateEnum(enumClass, "kotlin"));
                if(!isExist)
                    configurationInfo.getEnums().add(enumClass);
                configurationInfo.writeToFile(path);


                //if graphql is supported
            if(configurationInfo.isGraphQlSupport())
            {
                String enumGraphQlFilename = new StringBuilder().append(path).append("/src/main/resources/").append(name).append(".graphqls").toString();
                String enumContent =micronautEntityGenerator.generateEnumGraphQL(enumClass);
                GeneratorUtils.createFile(enumGraphQlFilename, enumContent);
            }
        }
        else {
            System.out.println(":- micronaut-cli.yml file is not found in the current directory. Please, try to create the enum class from the project's root directory");
        }




//        Example
//        EnumClass enumClass = new EnumClass();
//        enumClass.setEnumPackage("io.hashimati.ahmed");
//        enumClass.setName(name);
//        enumClass.setValues(values);
//
//
//        System.out.println(micronautEntityGenerator.generateEnum(enumClass, "java"));
//        System.out.println(micronautEntityGenerator.generateEnum(enumClass, "groovy"));
//        System.out.println(micronautEntityGenerator.generateEnum(enumClass, "kotlin"));
        System.gc();
        return 0;
    }
}
