package io.hashimati.commands;


import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "docker", description = "To start services.")
public class DockerCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-s", "--start"}, description = "Start a docker container", required = true )
    String service;
    @Override
    public Integer call() throws Exception {

        //check if the service is valid
        if(service==null)
        {
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Cannot run: "+service).reset());
            return 0;

        }
        else if(!Arrays.asList("mongodb", "mysql", "postgres", "kafka", "redis", "nats","elasticsearch",
        "rabbitmq", "neo4j", "jaegre", "zipkin", "influxdb", "grafana", "cassandra", "prometheus").contains(service.toLowerCase()))
        {
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Invalid service: "+service).reset());
            return 1;
        }


        switch (service)
        {
            case "mongodb":
                //start docker mongodb container
                Runtime.getRuntime().exec("docker run -d -p 27017:27017 --name mongodb mongo");
                break;
            case "mysql":
                //start docker mysql container
                Runtime.getRuntime().exec("docker run -d -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=password mysql");
                break;
            case "postgres":
                //start docker postgres container
                Runtime.getRuntime().exec("docker run -d -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=password postgres");
                break;
            case "nats":
                //start docker nats container
                Runtime.getRuntime().exec("docker run -d -p 4222:4222 --name nats nats");
                break;
            case "rabbitmq":
                //start docker rabbitmq container
                Runtime.getRuntime().exec("docker run -d -p 5672:5672 --name rabbitmq rabbitmq");
                break;
            case "kafka":
                //start docker kafka container
                Runtime.getRuntime().exec("docker run -d -p 9092:9092 --name kafka -e KAFKA_ADVERTISED_HOST_NAME=localhost");
                break;
            case "redis":
                //start docker redis container
                Runtime.getRuntime().exec("docker run -d -p 6379:6379 --name redis redis");
                break;
            case "elasticsearch":
                //start docker elasticsearch container
                Runtime.getRuntime().exec("docker run -d -p 9200:9200 --name elasticsearch -e \"discovery.type=single-node\" elasticsearch");
                break;
            case "cassandra":
                //start docker cassandra container
                Runtime.getRuntime().exec("docker run -d -p 9042:9042 --name cassandra cassandra");
                break;
            case "neo4j":
                //start docker neo4j container
                Runtime.getRuntime().exec("docker run -d -p 7474:7474 --name neo4j -e NEO4J_AUTH=neo4j/password neo4j");
                break;
            case "influxdb":
                //start docker influxdb container
                Runtime.getRuntime().exec("docker run -d -p 8086:8086 --name influxdb influxdb");
                break;
            case "grafana":
                //start docker grafana container
                Runtime.getRuntime().exec("docker run -d -p 3000:3000 --name grafana grafana/grafana");
                break;
            case "prometheus":
                //start docker prometheus container
                Runtime.getRuntime().exec("docker run -d -p 9090:9090 --name prometheus prom/prometheus");
                break;
            case "jaeger":
                //start docker jaeger container
                Runtime.getRuntime().exec("docker run -d -p 16686:16686 --name jaeger jaegertracing/all-in-one");
                break;
            case "zipkin":
                //start docker zipkin container
                Runtime.getRuntime().exec("docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin");
                break;
            case "memcache":
                //start docker memcache container
                Runtime.getRuntime().exec("docker run -d -p 11211:11211 --name memcache memcached");
                break;
            default:
                PromptGui.println("Service not found!", Ansi.Color.YELLOW);
                break;


        }
        return 1;
    }
}
