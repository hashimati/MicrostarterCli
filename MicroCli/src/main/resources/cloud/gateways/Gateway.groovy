package ${pack}.${artifact}

import com.netflix.discovery.EurekaClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Lazy


@EnableDiscoveryClient
@SpringBootApplication
public class ${appName}Application {

    @Lazy
    EurekaClient eurekaClient
    static void main(String[] args) {
        SpringApplication.run(${appName}Application, args)
    }

}

