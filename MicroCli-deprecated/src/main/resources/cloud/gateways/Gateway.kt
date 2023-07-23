package ${pack}.${artifact};

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.boot.SpringApplication


@EnableDiscoveryClient
@SpringBootApplication
class $ {appName}Application

fun main(args: Array<String>) {
    runApplication<${appName}Application>(*args)
}

