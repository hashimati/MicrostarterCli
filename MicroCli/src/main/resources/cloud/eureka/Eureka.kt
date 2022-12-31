package ${pack}.${artifact}

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class $ {appName}Application

fun main(args: Array<String>) {
    runApplication<${appName}Application>(*args)
}

