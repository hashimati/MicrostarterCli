package io.hashimati.microcli.client;


import io.hashimati.microcli.utils.PromptGui;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import org.fusesource.jansi.Ansi;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.List;


@Singleton
public class MicronautLaunchClient {

    @Inject
    @Client("https://launch.micronaut.io")
    private HttpClient micronautLaunchClient;

    //https://launch.micronaut.io/preview/default/com.example.demo?lang=JAVA&build=GRADLE&test=JUNIT&javaVersion=JDK_11&features=data-mongodb

    public String getOptions()
    {
        try{

            HttpRequest request= HttpRequest.GET("/select-options");
            return micronautLaunchClient.toBlocking().retrieve(request, String.class);
        }
        catch (Exception ex)
        {
            return null;
        }
    }






    ///https://launch.micronaut.io/create/default/com.example.demo?lang=JAVA&build=GRADLE&test=JUNIT&javaVersion=JDK_11&features=data-mongodb
    public  byte[] generateProject(@PathVariable("type") String type,
                                                @PathVariable("pack") String pack,
                                                @QueryValue("lang") String lang,
                                                @QueryValue("build") String build,
                                                @QueryValue("test") String test,
                                                @QueryValue("javaVersion") String javaVersion,
                                                @QueryValue("features") List<String> features){


        try{

            new Socket().connect(new InetSocketAddress("www.micronaut.io", 80), 6000);

        }
        catch (Exception ex)
        {
            PromptGui.printlnErr("https://launch.micronaut.io is not reachable. Please, check your internet connection.");
            return null;
        }
      try {
          HttpRequest request = HttpRequest.GET(MessageFormat.format("/create/{0}/{1}?lang={2}&build={3}&test={4}&javaVersion={5}{6}", type, pack, lang, build, test, javaVersion, features.isEmpty() ? "" : features.stream().map(x -> "&features=" + x).reduce((x, y) -> x + y)));
          PromptGui.println("Downloading "+ pack.substring(pack.lastIndexOf(".")).replace(".", "") + ".zip:\nGET: https://launch.micronaut.io"+request.getUri(), Ansi.Color.WHITE);

          return micronautLaunchClient.toBlocking().retrieve(request, new byte[]{}.getClass());
      }
      catch(Exception ex)
      {
          return null;
      }
    }


}
