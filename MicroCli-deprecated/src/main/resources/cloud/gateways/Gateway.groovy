package ${pack}.${artifact}

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient


@EnableDiscoveryClient
@SpringBootApplication
public class ${appName}Application {

    static void main(String[] args) {
        SpringApplication.run(${appName}Application, args)
    }

}

