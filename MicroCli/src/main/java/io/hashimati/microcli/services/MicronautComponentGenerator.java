package io.hashimati.microcli.services;


import io.hashimati.microcli.utils.GeneratorUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

@Singleton
public class MicronautComponentGenerator {


    @Inject
    private TemplatesService templatesService;


    public String generateController(String classPackage, String className,String path,String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("map", path);
        return generate(TemplatesService.COMP_CONTROLLER, map, lang);
    }

    public String generateSingleton(String classPackage, String className,String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        return generate(TemplatesService.COMP_Singleton, map, lang);
    }
//
//    public String generateRepository(String classPackage, String className, String lang){
//        HashMap<String, String> map = new HashMap<>();
//        map.put("pack", classPackage);
//        map.put("className", className);
//        return generate(TemplatesService.COMP_REPOSITORY, map, lang);
//    }

    public String generateClient(String classPackage, String className,String serviceId, String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("service-id", serviceId);
        return generate(TemplatesService.COMP_CLIENT, map, lang);

    }

    public String generateJob(String classPackage, String className,String lang){

        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        return generate(TemplatesService.COMP_JOB, map, lang);

    }

    public String generateWebSocket(String classPackage, String className, String path,String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("path", path);
        return generate(TemplatesService.COMP_WEBSOCKET, map, lang);

    }

    public String generateWebsocketClient(String classPackage, String className, String path,String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("path", path);
        return generate(TemplatesService.COMP_WEBSOCKET_CLIENT, map, lang);
    }

    public String generateKafkaClient(String classPackage,String className, String topic, String message, String messagePackage,String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("topic", topic);
        map.put("Message", message);
        map.put("importMessage", "");

        return generate(TemplatesService.KAFKA_CLIENT, map, lang);

    }

    public String generateKafkaConsumer(String classPackage, String groupId, String topic, String message, String messagePackage, String className,String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("groupId", groupId);
        map.put("topic", topic);
        map.put("Message", message);
        map.put("importMessage", "");

        return generate(TemplatesService.KAFKA_LISTENER, map, lang);

    }

    public String generateRabbitMQClient(String classPackage, String className,String queueName,String message,String messagePackage,  String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("queueName", queueName);
        map.put("Message", message);

        map.put("importMessage", "");
        return generate(TemplatesService.RABBITMQ_CLIENT, map, lang);

    }

    public String generateRabbitMQConsumer(String classPackage, String className,String queueName, String message, String lang){
        HashMap<String, String> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("queueName", queueName);
        map.put("Message", message);

        map.put("importMessage", "");
        return generate(TemplatesService.RABBITMQ_LISTENER, map, lang);
    }


    public String generate(String key,HashMap<String, String> map,  String lang)
    {
        String templatePath = "";
        switch (lang.toLowerCase())
        {
            case JAVA_LANG:
                templatePath =  templatesService.getJavaTemplates().get(key);
                break;
            case KOTLIN_LANG:
                templatePath =  templatesService.getKotlinTemplates().get(key);
                break;
            case GROOVY_LANG:
                templatePath =  templatesService.getGroovyTemplates().get(key);
                break;
            default:
                templatePath =  templatesService.getJavaTemplates().get(key);
                break;
        }
        return GeneratorUtils.generateFromTemplate(templatesService.loadTemplateContent(templatePath), map);
    }
}
