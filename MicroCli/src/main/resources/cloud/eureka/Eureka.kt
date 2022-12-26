package $import

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@EnableEurekaServer
@SpringBootApplication
class $ {appName}Application

fun main(args: Array<String>) {
    runApplication<${appName}Application>(*args)
}

