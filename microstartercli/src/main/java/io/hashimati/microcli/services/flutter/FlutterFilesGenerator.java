package io.hashimati.microcli.services.flutter;


import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.DataTypeMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.DART_LANG;

@Singleton
public class FlutterFilesGenerator {



    @Inject
    private TemplatesService templatesService;
    public String generateEntity(String workDir, Entity entity) throws IOException, ClassNotFoundException {
        //get the entity template from the resources
        String entityTemplate = templatesService.loadTemplateContent(TemplatesService.FLUTTER_ENTITY);
        //replace the entity name in the template
        HashMap<String, String> binder = new HashMap<>();

        binder.put("entityName", entity.getName());
        StringBuilder instances = new StringBuilder();
        StringBuilder fromMap = new StringBuilder();
        StringBuilder toMap = new StringBuilder();

        StringBuilder fromJson = new StringBuilder();
        if(!entity.getAttributes().isEmpty())
        {
            for (int i = 0; i < entity.getAttributes().size(); i++) {
                var attribute = entity.getAttributes().get(i);
                instances.append(attribute.getDeclaration(DART_LANG, false)).append("\n");
                fromMap.append("\t\t\t\tmap[").append(attribute.getName()).append("],\n");
                toMap.append("\t\t\t\t'").append(attribute.getName()).append("' : ").append(attribute.getName()).append(",\n");
                fromJson.append("\t\t\t\tjson[").append(entity.getAttributes().get(i).getName()).append("] as ").append(DataTypeMapper.dartMapper.getOrDefault(attribute.getType().toLowerCase(), attribute.getType())).append(",\n");
            }


        //    instances = instances.substring(0, instances.length() - 2);
        }
        binder.put("instances", instances.toString());
        binder.put("fromMap", fromMap.toString());
        binder.put("toMap", toMap.toString());
        binder.put("fromJson", fromJson.toString());
        return new SimpleTemplateEngine().createTemplate(entityTemplate).make(binder).toString();
    }


    
    public String generateClient(String workDir, Entity entity) throws IOException, ClassNotFoundException {
        //get the entity template from the resources
        String entityTemplate = templatesService.loadTemplateContent(TemplatesService.FLUTTER_CLIENT);
        //replace the entity name in the template
        HashMap<String, String> binder = new HashMap<>();

        binder.put("entityName", entity.getName());
        return new SimpleTemplateEngine().createTemplate(entityTemplate).make(binder).toString();
    }

}
