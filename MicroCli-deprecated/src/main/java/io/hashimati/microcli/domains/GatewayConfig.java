package io.hashimati.microcli.domains;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.hashimati.microcli.utils.GeneratorUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayConfig {

    private String name, build, version, javaVersion,  artifact, server, port;
    private String discovery, discoveryServer, discoveryPort;
    private String pack;
    private String language;

    private String group;
    private ArrayList<Route> routes = new ArrayList<>();


    private String routesConfiguration;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Route{


        public static final String ROUTE_CONFIG =
                        "        - id: ${id}\n" +
                        "          uri: ${uri}\n" +
                        "          predicates:\n" +
                        "            - Path=${path}\n";
        private String id, uri;
        private ArrayList<String> paths = new ArrayList<>();

        private Predicates predicates;
        ArrayList<String> filters = new ArrayList<>();

        public String toProperties(){
          return   GeneratorUtils.generateFromTemplate(ROUTE_CONFIG,  new HashMap<String, String>(){{
                put("id", id);
                put("uri", uri);
                put("path", paths.stream().collect(Collectors.joining(", ")));
            }});
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class  Predicates{
        private String path;
        private String host;
        private String method;
        private String query;
        private String header;
        private String cookie;
        private String remoteAddr;
        private String weight;
        private String after;
        private String before;
        private String between;
        private String fallback;
        private String saveSession;
        private String removeRequestHeader;
        private String removeResponseHeader;
        private String addRequestHeader;
        private String addResponseHeader;


    }

    public  boolean writeToFile(String path)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path + "/gatewayConfig.json"), this);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static GatewayConfig readFromFile(String path)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(path + "/gatewayConfig.json"), GatewayConfig.class);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


}

