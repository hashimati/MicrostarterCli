package io.hashimati.microcli.client;


import io.hashimati.microcli.utils.PromptGui;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import org.fusesource.jansi.Ansi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.MessageFormat;

@Singleton
public class StartSpringClient {

    //https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=2.7.0&baseDir=demo&groupId=com.example&artifactId=demo&name=demo&description=Demo project for Spring Boot&packageName=com.example.demo&packaging=jar&javaVersion=17&dependencies=cloud-eureka-server

    @Inject
    @Client("https://start.spring.io")
    private HttpClient startSpringClient;

    public byte[] generateEurekaServer(String build, String lang, String version, String group, String javaVersion ){
        try{

            new Socket().connect(new InetSocketAddress("start.spring.io", 80), 6000);

        }
        catch (Exception ex)
        {
            PromptGui.printlnErr("https://start.spring.io is not reachable. Please, check your internet connection.");
            return null;
        }

        HttpRequest request = HttpRequest.GET(MessageFormat.format("/starter.zip?type={0}-project&language={1}&bootVersion={2}&baseDir=eurekaService&groupId={3}&artifactId=eurekaService&name=eurekaService&description=Eureka Service&packageName={3}.eurekaService&packaging=jar&javaVersion={4}&dependencies=cloud-eureka-server",
                build, lang, version, group, javaVersion));
        PromptGui.println("Downloading eurekaService.zip:\nGET: https://start.spring.io"+request.getUri(), Ansi.Color.WHITE);

        return startSpringClient.toBlocking().retrieve(request, new byte[]{}.getClass());


    }

}
