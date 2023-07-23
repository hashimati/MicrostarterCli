package ${pack}.${artifact}

iimport org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.client.discovery.EnableDiscoveryClient


@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
class ${appName}Application {


    static void main(String[] args) {
        SpringApplication.run(${appName}Application.class, args)
    }

}
