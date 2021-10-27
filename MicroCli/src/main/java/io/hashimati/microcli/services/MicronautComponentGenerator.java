package io.hashimati.microcli.services;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.utils.GeneratorUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;

@Singleton
public class MicronautComponentGenerator {


    @Inject
    private TemplatesService templatesService;


    public String generateController(String classPackage, String className,String path,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("map", path);
        map.put("micrometer", micrometers);

        return generate(TemplatesService.COMP_CONTROLLER, map, lang);
    }

    public String generateSingleton(String classPackage, String className,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("micrometer", micrometers);

        return generate(TemplatesService.COMP_Singleton, map, lang);
    }
//
//    public String generateRepository(String classPackage, String className, String lang){
//        HashMap<String, String> map = new HashMap<>();
//        map.put("pack", classPackage);
//        map.put("className", className);
//        return generate(TemplatesService.COMP_REPOSITORY, map, lang);
//    }

    public String generateClient(String classPackage, String className,String serviceId, String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("service-id", serviceId);
        map.put("micrometer", micrometers);

        return generate(TemplatesService.COMP_CLIENT, map, lang);

    }

    public String generateJob(String classPackage, String className,String lang, boolean micrometers){

        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("micrometer", micrometers);

        return generate(TemplatesService.COMP_JOB, map, lang);


    }

    public String generateWebSocket(String classPackage, String className, String path,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("path", path);
        map.put("micrometer", micrometers);

        return generate(TemplatesService.COMP_WEBSOCKET, map, lang);

    }

    public String generateWebsocketClient(String classPackage, String className, String path,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("path", path);
        map.put("micrometer", micrometers);

        return generate(TemplatesService.COMP_WEBSOCKET_CLIENT, map, lang);
    }

    public String generateKafkaClient(String classPackage,String className, String topic, Entity entity,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("topic", topic);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));
        map.put("micrometer", micrometers);

        return generate(TemplatesService.KAFKA_CLIENT, map, lang);

    }

    public String generateKafkaConsumer(String classPackage,String className, String groupId, String topic, Entity entity,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("groupId", groupId);
        map.put("topic", topic);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("micrometer", micrometers);

        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));

        return generate(TemplatesService.KAFKA_LISTENER, map, lang);

    }


    public String generateNatsClient(String classPackage,String className, String topic, Entity entity,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("topic", topic);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));
        map.putIfAbsent("micrometer",  micrometers);
        return generate(TemplatesService.NATS_CLIENT, map, lang);

    }

    public String generateNatsConsumer(String classPackage,String className, String topic, Entity entity,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("topic", topic);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("micrometer", micrometers);
        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));

        return generate(TemplatesService.NATS_LISTENER, map, lang);

    }


    public String generateGcpPubSubClient(String classPackage,String className, String topic, Entity entity,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("topic", topic);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));
        map.put("micrometer", micrometers);

        return generate(TemplatesService.GCP_PUB_SUB_CLIENT, map, lang);

    }

    public String generateGcpPubSubConsumer(String classPackage,String className, String topic, Entity entity,String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("topic", topic);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("micrometer", micrometers);

        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));

        return generate(TemplatesService.GCP_PUB_SUB_LISTENER, map, lang);

    }

    public String generateRabbitMQClient(String classPackage, String className, String queueName, Entity entity, String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("queueName", queueName);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("micrometer", micrometers);

        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));
        return generate(TemplatesService.RABBITMQ_CLIENT, map, lang);

    }

    public String generateRabbitMQConsumer(String classPackage, String className,String queueName, Entity entity, String lang, boolean micrometers){
        HashMap<String, Object> map = new HashMap<>();
        map.put("pack", classPackage);
        map.put("className", className);
        map.put("queueName", queueName);
        map.put("Message", entity == null?"String": entity.getName());
        map.put("micrometer", micrometers);
        map.put("importMessage",entity == null?"":("import " +entity.getEntityPackage() + "."+entity.getName()+(lang.equalsIgnoreCase(JAVA_LANG)?";":"")));
        return generate(TemplatesService.RABBITMQ_LISTENER, map, lang);
    }


    public String generate(String key,HashMap<String, Object> map,  String lang)
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
        return GeneratorUtils.generateFromTemplateObj(templatesService.loadTemplateContent(templatePath), map);
    }
}
