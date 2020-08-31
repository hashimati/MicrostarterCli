package io.hashimati.microcli.services;


import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MicronautComponentGenerator {


    @Inject
    private TemplatesService templatesService;


    public String generateController(String className,String lang){return null; }

    public String generateService(String className,String lang){return null; }

    public String generateRepository(String className, String lang){return null; }

    public String generateClient(String className,String lang){return null; }

    public String generateJob(String className,String lang){return null; }

    public String generateWebSocket(String className,String lang){return null; }

    public String generateWebsocketClient(String className,String lang){return null; }

    public String generateList(String className,String lang){return null; }

    public String generateKafkaClient(String className,String lang){return null; }

    public String generateKafkaConsumer(String className,String lang){return null; }

    public String generateRabbitMQClient(String className,String lang){return null; }

    public String generateRabbitMQConsumer(String className,String lang){return null;}

}
