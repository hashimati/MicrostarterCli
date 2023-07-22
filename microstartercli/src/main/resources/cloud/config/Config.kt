package ${pack}.${artifact};

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
class ${appName}Application

fun main(args: Array<String>) {
    runApplication<${appName}Application>(*args)
}

