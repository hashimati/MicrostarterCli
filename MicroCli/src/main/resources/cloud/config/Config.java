package ${pack}.${artifact};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ${appName}Application {

    public static void main(String[] args) {
        SpringApplication.run(${appName}Application.class, args);
    }

}
