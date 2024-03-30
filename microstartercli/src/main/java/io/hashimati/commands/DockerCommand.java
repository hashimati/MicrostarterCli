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
//                Runtime.getRuntime().exec("docker", "run -d -p 27017:27017 --name mongodb mongo");
                Process  process = new ProcessBuilder("docker", "run", "-d", "-p", "27017:27017", "--name", "mongodb", "mongo").start();
                process.waitFor();
                break;
            case "mysql":
                //start docker mysql container
                Process processMysql = new ProcessBuilder("docker", "run", "-d", "-p", "3306:3306", "--name", "mysql", "-e", "MYSQL_ROOT_PASSWORD=password", "mysql").start();
                processMysql.waitFor();

                  break;
            case "postgres":
                //start docker postgres container

                Process processPostgres = new ProcessBuilder("docker", "run", "-d", "-p", "5432:5432", "--name", "postgres", "-e", "POSTGRES_PASSWORD=password", "postgres").start();
                processPostgres.waitFor();

                break;
            case "nats":
                //start docker nats container

                Process processNats = new ProcessBuilder("docker", "run", "-d", "-p", "4222:4222", "--name", "nats", "nats").start();
                processNats.waitFor();

                break;
            case "rabbitmq":
                //start docker rabbitmq container

                Process processRabbitmq = new ProcessBuilder("docker", "run", "-d", "-p", "5672:5672", "-p", "15672:15672", "--name", "rabbitmq", "rabbitmq:3-management").start();
                processRabbitmq.waitFor();

                break;
            case "kafka":
                //start docker kafka container

                Process processKafka = new ProcessBuilder("docker", "run", "-d", "-p", "9092:9092", "-p", "2181:2181", "--name", "kafka", "wurstmeister/kafka").start();
                processKafka.waitFor();

                break;
            case "redis":
                //start docker redis container

                Process processRedis = new ProcessBuilder("docker", "run", "-d", "-p", "6379:6379", "--name", "redis", "redis").start();
                processRedis.waitFor();

                break;
            case "elasticsearch":
                //start docker elasticsearch container

                Process processElasticsearch = new ProcessBuilder("docker", "run", "-d", "-p", "9200:9200", "-p", "9300:9300", "--name", "elasticsearch", "elasticsearch").start();
                processElasticsearch.waitFor();

                break;
            case "cassandra":
                //start docker cassandra container

                Process processCassandra = new ProcessBuilder("docker", "run", "-d", "-p", "9042:9042", "-p", "9160:9160", "--name", "cassandra", "cassandra").start();
                processCassandra.waitFor();

                break;
            case "neo4j":
                //start docker neo4j container
//                Runtime.getRuntime().exec("docker run -d -p 7474:7474 --name neo4j -e NEO4J_AUTH=neo4j/password neo4j");
                Process processNeo4J = new ProcessBuilder("docker", "run", "-d", "-p", "7474:7474", "--name", "neo4j", "-e", "NEO4J_AUTH=neo4j/password", "neo4j").start();
                processNeo4J.waitFor();

                break;
            case "influxdb":
                //start docker influxdb container

                Process processInfluxdb = new ProcessBuilder("docker", "run", "-d", "-p", "8086:8086", "--name", "influxdb", "influxdb").start();
                processInfluxdb.waitFor();


                break;
            case "grafana":
                //start docker grafana container

                Process processGrafana = new ProcessBuilder("docker", "run", "-d", "-p", "3000:3000", "--name", "grafana", "grafana/grafana").start();
                processGrafana.waitFor();

                break;
            case "prometheus":
                //start docker prometheus container

                Process processPrometheus = new ProcessBuilder("docker", "run", "-d", "-p", "9090:9090", "--name", "prometheus", "prom/prometheus").start();
                processPrometheus.waitFor();

                break;
            case "jaeger":
                //start docker jaeger container

                Process processJaeger = new ProcessBuilder("docker", "run", "-d", "-p", "16686:16686", "-p", "14268:14268", "--name", "jaeger", "jaegertracing/all-in-one").start();
                processJaeger.waitFor();

                break;
            case "zipkin":
                //start docker zipkin container
                Process processZipkin = new ProcessBuilder("docker", "run", "-d", "-p", "9411:9411", "--name", "zipkin", "openzipkin/zipkin").start();
                processZipkin.waitFor();

                break;
            case "memcache":
                //start docker memcache container

                Process processMemcache = new ProcessBuilder("docker", "run", "-d", "-p", "11211:11211", "--name", "memcache", "memcached").start();
                processMemcache.waitFor();

                break;
            default:
                PromptGui.println("Service not found!", Ansi.Color.YELLOW);
                break;


        }
        return 1;
    }
}
