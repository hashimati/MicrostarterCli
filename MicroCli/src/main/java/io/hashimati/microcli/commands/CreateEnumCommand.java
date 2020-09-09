package io.hashimati.microcli.commands;

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


@Command(name = "create-enum",aliases = {"enum"}, description = "Create Enumuration Class")
public class CreateEnumCommand implements Callable<Integer> {
   
    @Option(names={"-n", "--name"}, description = "Enumuration class name")
    private String name;


    @Option(names = {"-o", "--options"}, description = {"Enumration values ", "You can use commas without spaces to add multiple values.", "For Example, -o FOO,BAR,BOO"}, split =",")
    private HashSet<String> values ;


    @Inject
    MicronautEntityGenerator micronautEntityGenerator;
    @Override
    public Integer call() throws Exception {
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName());
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
            String outPutPath = System.getProperty("user.dir")+enumFilePath+"/"+name+extension;


            GeneratorUtils.createFile(outPutPath.replace("\\", "/"), micronautEntityGenerator.generateEnum(enumClass, configurationInfo.getProjectInfo().getSourceLanguage().toLowerCase()));

            //                System.out.println(micronautEntityGenerator.generateEnum(enumClass, configurationInfo.getProjectInfo().getSourceLanguage()));
//                System.out.println(micronautEntityGenerator.generateEnum(enumClass, "groovy"));
//                System.out.println(micronautEntityGenerator.generateEnum(enumClass, "kotlin"));
                if(!isExist)
                    configurationInfo.getEnums().add(enumClass);
                configurationInfo.writeToFile();
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
        return 0;
    }
}
