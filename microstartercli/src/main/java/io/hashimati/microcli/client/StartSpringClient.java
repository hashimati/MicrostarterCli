package io.hashimati.microcli.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hashimati.microcli.domains.spring.SpringDependencies;
import io.hashimati.microcli.utils.PromptGui;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import org.fusesource.jansi.Ansi;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class StartSpringClient {

    //https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=2.7.0&baseDir=demo&groupId=com.example&artifactId=demo&name=demo&description=Demo project for Spring Boot&packageName=com.example.demo&packaging=jar&javaVersion=17&dependencies=cloud-eureka-server

    @Inject
    @Client("https://start.spring.io")
    private HttpClient startSpringClient;

    public byte[] generateEurekaServer(String build, String lang, String version, String group, String javaVersion, String artifact){
        try{

            new Socket().connect(new InetSocketAddress("start.spring.io", 80), 6000);

        }
        catch (Exception ex)
        {
            PromptGui.printlnErr("https://start.spring.io is not reachable. Please, check your internet connection.");
            return null;
        }

        HttpRequest request = HttpRequest.GET(MessageFormat.format("/starter.zip?type={0}-project" +
                        "&language={1}" +
                        "&bootVersion={2}" +
                        "&baseDir=eureka" +
                        "&groupId={3}" +
                        "&artifactId=eureka" +
                        "&name=eureka" +
                        "&description=Eureka%20Server%20for%20Spring%20Boot" +
                        "&packageName={3}.eureka" +
                        "&packaging=jar" +
                        "&javaVersion={4}" +
                        "&dependencies=cloud-eureka-server" //+
//                        ",native"
                ,
                build, lang, version, group, javaVersion));
        PromptGui.println("Downloading eurekaService.zip:\nGET: https://start.spring.io"+request.getUri(), Ansi.Color.WHITE);

        return startSpringClient.toBlocking().retrieve(request, new byte[]{}.getClass());


    }

    // get all available versions
    public ArrayList<String> getVersions()
    {
        HttpRequest request = HttpRequest.GET("/starter/info");
        String response = startSpringClient.toBlocking().retrieve(request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            HashMap<String, Object> map = mapper.readValue(response, new TypeReference<HashMap<String, Object>>() {});
            return (ArrayList<String>) map.get("bootVersions");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public byte[] generateGatewayServer(String build, String lang, String version, String group, String javaVersion , String artifact, String discovery, String config){

        //https://start.spring.io/starter.zip?type=gradle-project&language=java&bootVersion=3.0.1&baseDir=demo&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&packaging=jar&javaVersion=17&dependencies=cloud-gateway,webflux,oauth2-client,security,native
        try{

            new Socket().connect(new InetSocketAddress("start.spring.io", 80), 6000);

        }
        catch (Exception ex)
        {
            PromptGui.printlnErr("https://start.spring.io is not reachable. Please, check your internet connection.");
            return null;
        }

        String discoveryStarter = discovery.equalsIgnoreCase("eureka")? "cloud-eureka":"cloud-starter-consul-discovery";
       String configStarter;
        if (config.equalsIgnoreCase("spring")) configStarter = "cloud-config-client";
        else if (config.equalsIgnoreCase("consul")) configStarter = "cloud-starter-consul-config";
        else configStarter = "";
        HttpRequest request = HttpRequest.GET(MessageFormat.format("/starter.zip?" +
                        "type={0}-project" +
                        "&language={1}" +
                        "&bootVersion={2}" +
                        "&baseDir=gateway" +
                        "&groupId={3}" +
                        "&artifactId=gateway" +
                        "&name=gateway" +
                        "&description=Gateway" +
                        "&packageName={3}.gateway" +
                        "&packaging=jar" +
                        "&javaVersion={4}" +
                        "&dependencies=webflux,cloud-gateway" +
//                        ",oauth2-client,security" +
//                        ",native" +
                        "," +discoveryStarter +
                        (!configStarter.isEmpty()?"," + configStarter:""),
                build, lang, version, group, javaVersion));
        PromptGui.println("Downloading gateway.zip:\nGET: https://start.spring.io"+request.getUri(), Ansi.Color.WHITE);


        return startSpringClient.toBlocking().retrieve(request, new byte[]{}.getClass());


    }

    //generate Spring Cloud Config Server
    public byte[] generateConfigServer(String build, String lang, String version, String group, String javaVersion , String artifact, String discovery) {

        //https://start.spring.io/starter.zip?type=gradle-project&language=java&bootVersion=3.0.1&baseDir=demo&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&packaging=jar&javaVersion=17&dependencies=cloud-gateway,webflux,oauth2-client,security,native
        try {

            new Socket().connect(new InetSocketAddress("start.spring.io", 80), 6000);

        } catch (Exception ex) {
            PromptGui.printlnErr("https://start.spring.io is not reachable. Please, check your internet connection.");
            return null;
        }

        String discoveryStarter = discovery.equalsIgnoreCase("eureka") ? "cloud-eureka" : "cloud-starter-consul-discovery";
        HttpRequest request = HttpRequest.GET(MessageFormat.format("/starter.zip?" +
                "type={0}-project" +
                "&language={1}" +
                "&bootVersion={2}" +
                "&baseDir=config" +
                "&groupId={3}" +
                "&artifactId=config" +
                "&name=config" +
                "&description=Config" +
                "&packageName={3}.config" +
                "&packaging=jar" +
                "&javaVersion={4}" +
                "&dependencies=webflux,cloud-config-server",
//                        ",oauth2-client,security" +
//                        ",native" +
//                        "," +discoveryStarter,
        build, lang, version, group, javaVersion));
       // cloud-config-client,cloud-config-server,cloud-starter-consul-config
        PromptGui.println("Downloading config.zip:\nGET: https://start.spring.io" + request.getUri(), Ansi.Color.WHITE);




        return startSpringClient.toBlocking().retrieve(request, new byte[]{}.getClass());
    }

    public SpringDependencies getSpringDependencies() {
        try {

            new Socket().connect(new InetSocketAddress("start.spring.io", 80), 6000);

        } catch (Exception ex) {
            PromptGui.printlnErr("https://start.spring.io is not reachable. Please, check your internet connection.");
            return null;
        }

        HttpRequest request = HttpRequest.GET("/dependencies");
        PromptGui.println("Downloading dependencies:\nGET: https://start.spring.io" + request.getUri(), Ansi.Color.WHITE);
        SpringDependencies response = startSpringClient.toBlocking().retrieve(request, SpringDependencies.class);
        return response;

    }
        //get spring boot dependencies from start.spring.io
//    public ArrayList<String> getDependencies()
//    {
//        try{
//
//            new Socket().connect(new InetSocketAddress("start.spring.io", 80), 6000);
//
//        }
//        catch (Exception ex)
//        {
//            PromptGui.printlnErr("https://start.spring.io is not reachable. Please, check your internet connection.");
//            return null;
//        }
//
//        HttpRequest request = HttpRequest.GET("/dependencies");
//        PromptGui.println("Downloading dependencies:\nGET: https://start.spring.io"+request.getUri(), Ansi.Color.WHITE);
//
//        HashMap<String, ?> response = startSpringClient.toBlocking().retrieve(request, HashMap.class);
//        System.out.println(response.get("dependencies"));
//
//        // convert response.get("dependencies").toString().replace("=", ":") to HashMap<String, String> using Gson
//
//        Gson gson = new Gson();
//        HashMap<String, String> map = gson.fromJson(response.get("dependencies").toString().replace("=", ":"), HashMap.class);
//        System.out.println(map);
//
//        // return map keys as ArrayList<String>
//        return new ArrayList<>(map.keySet());
//
//
//
//
//
//    }



    //generate Spring App
    public byte[] generateApp(String build, String lang, String version, String group, String javaVersion , String artifact, String... dependencies){

        //https://start.spring.io/starter.zip?type=gradle-project&language=java&bootVersion=3.0.1&baseDir=demo&groupId=com.example&artifactId=demo&name=demo&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.demo&packaging=jar&javaVersion=17&dependencies=cloud-gateway,webflux,oauth2-client,security,native
        try {

            new Socket().connect(new InetSocketAddress("start.spring.io", 80), 6000);

        } catch (Exception ex) {
            PromptGui.printlnErr("https://start.spring.io is not reachable. Please, check your internet connection.");
            return null;
        }

        String dependenciesText = Stream.of(dependencies).distinct().collect(Collectors.joining(","));
        if(dependenciesText.isBlank())
            dependenciesText = "webflux";

        HttpRequest request = HttpRequest.GET(MessageFormat.format("/starter.zip?" +
                        "type={0}-project" +
                        "&language={1}" +
                        "&bootVersion={2}" +
                        "&baseDir={5}" +
                        "&groupId={3}" +
                        "&artifactId={5}" +
                        "&name={5}" +
                        "&description={5}" +
                        "&packageName={3}.{5}" +
                        "&packaging=jar" +
                        "&javaVersion={4}" +
                        "&dependencies={6}",
//                        ",oauth2-client,security" +
//                        ",native" +
//                        "," +discoveryStarter +
//                        "," + configStarter,
                build, lang, version, group, javaVersion, artifact, dependenciesText));
        // cloud-config-client,cloud-config-server,cloud-starter-consul-config
        PromptGui.println("Downloading " + artifact + ".zip:\nGET: https://start.spring.io" + request.getUri(), Ansi.Color.WHITE);

        return startSpringClient.toBlocking().retrieve(request, new byte[]{}.getClass());
    }

}
