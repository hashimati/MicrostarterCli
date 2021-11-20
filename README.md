# MicroCli
MicroCli is a command-line rapid development tool for Micronaut applications. It helps developers to cut the development time and focus on the application logic by generating Micronaut components and configurations using ready-built templates. This tool is a [Micronaut](https://github.com/micronaut-projects)/[PicoCLI](https://github.com/remkop/picocli) application powered with [ConsoleUI](https://github.com/awegmann/consoleui)



# Table Of Contents
1. [Technologies Stack](#stack)
2. [Getting Started](#started)
3. [Configure Command](#configure)
4. [Enum Command](#enum)
5. [Entity Command](#entity)
6. [Relationship Command](#relationship)
7. [Messaging Commands](#messaging)
    1. [Kafka Commmands](#kafka)
    2. [RabbitMQ Commands](#rabbitmq)
    3. [NAT Commands](#nat)
    4. [GCP Commands](#gcp). 
8. [Security Command](#security)
    1. [JWT](#jwt)
    2. [Baiscs](#basicsecurity)
    3. [Session](#session)
9. [Metrics Command](#metrics)
10. [Banner Command](#banner)






<a name="stack"></a>
## Technologies Stack

#### 0. Reactive Framework

| Framework | Notes |
| :--: | :-- |
| Reactor | It is the recommended to use Reactor. |
|Rxjava2 | It's not supported in security command. |
|Rxjava3 | It's jnt supported in security command. |
#### 1. Languages 
| Language | Notes |
| :--: | :--: | 
| java | MicroCli will detected the language from micronaut-cli.yml file | 
| groovy | MicroCli will detected the language from micronaut-cli.yml file |
| kotlin | MicroCli will detected the language from micronaut-cli.yml file | 

#### 2. Database
| Database | Type | Features | Notes |
| :--: | :--: | :--: | :-- | 
| Mongodb | NoSQL | ReactiveMongo, GORM | GORM is in <b>Preview</b> stage and it is supported with Groovy Language Only.<  |
| H2 | RDBM | JPA , JDBC, R2DBC , GROM | 1. GORM is in <b>Preview</b> stage and it is supported with Groovy Language Only.<br /> 2. R2DBC is in <b>Preview</b> stage. |
| MySQL | RDBM | JPA , JDBC , R2DBC ,  GROM | 1. GORM is in <b>Preview</b> stage and it is supported with Groovy Language Only.<br /> 2. R2DBC is in <b>Preview</b> stage . |
| MariaDB | RDBM | JPA , JDBC, R2DBC , GROM | 1. GORM is in <b>Preview</b> stage and it is supported with Groovy Language Only.<br /> 2. R2DBC is in <b>Preview</b> stage . |
| PostgreSQL | RDBM | JPA , JDBC, R2DBC , GROM | 1. GORM in <b>Preview</b> stage and it is supported with Groovy Language Only.<br /> 2. R2DBC is in <b>Preview</b> stage . |
| Oracle | RDBM | JPA , JDBC, R2DBC , GROM | 1. GORM is in <b>Preview</b> stage and it is supported with Groovy Language Only.<br /> 2. R2DBC is in <b>Preview</b> stage . |
| SqlServer | RDBM | JPA , JDBC, R2DBC , GROM | 1. GORM is in <b>Preview</b> stage and it is supported with Groovy Language Only.<br /> 2. R2DBC is in <b>Preview</b> stage . |

#### 3. SQL Data Migrations Tools
| Framework/Tools | notes |
| :--: | :--: | 
| Liquibase | recommended | 
| Flyway | It's not yet implemented in "security" command. |

#### 4. GraphQL

| Framework/Tools | Notes | 
| :--: | :--: | 
| graphql-java-tools | |

#### 5. Caching
| Framework/Tools | Notes |
| :--: | :--: |
| Caffeine | | 

#### 6. Observability 
| Framework/Tools | Notes |
|:--: | :--: | 
| Micrometers | | 
| InfluxDB | |
| prometheus | |

#### 7. Messeging 
| Messaging | Notes |
| :--: | :--: | 
| Kafka | | 
| RabbitMQ | | 
| NAT | | 
| GCP | | 

#### 8. Security
| Mechanism | Notes | 
| :--: | :--: | 
| JWT | | 
| Sessions | | 
| Basics | | 

#### 9. Banners. 
The MicroCli uses [Banana](https://github.com/yihleego/banana) to generate banners. 

####

<a name="started"> </a>
## Getting Started

As a getting started steps, we will generate an application using The Micronaut Launch. Then, we will generate a Fruit entity, repository, service, REST API, and GraphQL endpoints.

1. Download the MicroCli. 
2. Generate a Micronaut Application using [Micronaut Launch](https://launch.micronaut.io) or [Micronaut CLI](https://micronaut.io/download/). 
3. Unzip MicroCli in the Micronaut Application or configure it in you environment.  
4. Open the Terminal/Command Prompt. And, navigate to the project's directory. 
5. Run this command to start generating the Fruit entity
```shell
mc entity -e fruit --graphql
```
6. MicroCli application will launch. 
7. Select the instructions as in this GIF: 
![Alt Tutorial](https://github.com/hashimati/MicroCli/blob/master/MicroCli%20Demo.gif)


<a name="configure"></a>
## Configure Command

#### Command Syntax:
```shell
> mc configure
```
The "configure" command prepares a Micronaut application to be used by MicroCli's commands. The command should be run once. The first action that "configure" command does is reading "micronaut-cli.yml" file and collects the application's information. Based on the infomration in the "miconaut-cli.yml", MicroCli application will check if the application type is supported by Microcli and determines generating Micronaut components flow. 

The "Configure" command adds the necessary features and configurations that are required by other command to the Micronaut Application. When a user runs the command it will ask the user to configure the below: 

1. Reactive framework: Reactor Project, RxJava2 , RxJava3. 
2. Database Name. 
3. Database Type. 
4. Messaging Framework
5. Caching
6. Metrics Observibiltiy
7. GraphQL

In the other hand, the "configure" does the below configurations by default: 

1. Adding the OpenAPI features and adding the necessary YAML and Java annotations configurations. 
2. Adding "OpenWriter" features. 
3. Updating the "logback.xml" file. It will add the "FILE" appender to the file, which will let the Miconaut application to write/append the logs to "logs.log" file. Also, it will add "io.micronaut.data.query" logger to trace the Micronaut data events.  

* File Appender Configuration: 
```xml
<appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs.log</file>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
</appender>
...
    <root level="info">
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE" />
    </root>
```

"io.micronaut.data.query" Logger Configuration: 
```xml
    <logger name="io.micronaut.data.query" level="trace" />

```
5. Adding Lombok to the Micornaut-java application if the Micronaut application doesn't contain Lombok. 
6. Creates "MicoCliConfig.json" file. The "MicroCliConfig.json" file contains the application informations from "micronaut-cli.yml" file and tarcks all the actions that users do using the MicroCli tool.

The "configure" command runs implicitly if the user runs [Entity Command](#entity). 


<a name="enum"></a>
## Enum Command
#### Command Syntax:
```shell
> mc create-enum --name <Enum Name> --options <OPTION1,OPTION2,OPTION3, ...> 
```

The users can use"enum" command declare and configure an Enum data type in the applicaiton. The defined enum data type will appear in the attributes data type selection list in [Enum Command](#enum).    

#### Example:
```shell
> mc create-enum --name WHETHER --options SUNNY,CLOUDY,RAINY 
```


<a name="entity"></a>
## Entity Command

#### Command Syntax:
```shell
mc entity --entity-name <EntityName> --collection-name <collection name> --graphql --cache --no-endpoint
```

The "entity" command helps the developers to bootstarp the code of the basic CRUD operations for the application's domains. The files are including Entity Class file, Database Repository file, Service file, REST Endpoint file, and GraphQL configuration files. The "entity" command generates the files based on the information that the developer provides in "configure" command. The "entity" command runs the "configure" command implicitly if the developer didn't run it. 

#### Command Options: 
| Option |  Aliases | Description |
| :---: | :---: | :--- |
| --entity-name| -e , -n | To specify the entity's name |
| --collection-name | -c | to specify the entity's table/collection name |
| --no-endpoint |  | to generate the entity class only. | 
| --graphql | -gl | to generate entity's graphql configuration and files including QueryFactory, QueryResolver, schema, data, query,and mutation files | 
| --cache | --caffine | to add caching annotations in the entity's service file |


### Example
```shell
> mc entity -n Fruit
```
#### Generated Files: 
##### 1. Fruit Class
```java 
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Schema(name="Fruit", description="Fruit Description")
@MappedEntity(value = "fruits", namingStrategy = Raw.class)
public class Fruit{
    
    @Id 
    @GeneratedValue(GeneratedValue.Type.AUTO) 
    @EqualsAndHashCode.Exclude 
    private Long id;
    
    private String name;
    @DateCreated 
    private Date dateCreated;

    @DateUpdated 
    private Date dateUpdated;    
}
```

##### 2. Fruit Repository

```java
@Repository
@JdbcRepository(dialect = Dialect.H2)
public interface FruitRepository extends CrudRepository<Fruit, Long> {
    
}
```

##### 3. Fruit Service
```java
@Singleton
@Transactional
public class FruitService {

    private static final Logger log = LoggerFactory.getLogger(FruitService.class);
    @Inject private FruitRepository fruitRepository;

    @Timed(value = "io.hashimati.services.fruitService.save", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for saving fruit object")
    public Fruit save(Fruit fruit){
        log.info("Saving  Fruit : {}", fruit);
        //TODO insert your logic here!
        //saving Object
        fruitRepository.save(fruit);
        return fruit;
    }

    
    @Timed(value = "io.hashimati.services.fruitService.findById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    public Fruit findById(long id){
        log.info("Finding Fruit By Id: {}", id);
        return fruitRepository.findById(id).orElse(null);
    }

    @Timed(value = "io.hashimati.services.fruitService.deleteById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for deleting a fruit object by id")
    public boolean deleteById(long id){
        log.info("Deleting Fruit by Id: {}", id);
        try{
            fruitRepository.deleteById(id);
            log.info("Deleted Fruit by Id: {}", id);
            return true;
        }
        catch(Exception ex)
        {
            log.info("Failed to delete Fruit by Id: {}", id);
            ex.printStackTrace();
            return false;
        }
    }

    @Timed(value = "io.hashimati.services.fruitService.findAll", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects")
    public Iterable<Fruit> findAll() {
        log.info("Find All");
      return  fruitRepository.findAll();
    }

    public boolean existsById(Long id)
    {
        log.info("Check if id exists: {}", id);
        return  fruitRepository.existsById(id);

    }

    @Timed(value = "io.hashimati.services.fruitService.update", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    public Fruit update(Fruit fruit)
    {
        log.info("update {}", fruit);
        return fruitRepository.update(fruit);

    }

}
```
##### 4. Fruit Controller
```java
@Controller("/api/fruit")
public class FruitController {

    private static final Logger log = LoggerFactory.getLogger(FruitController.class);

    @Inject private FruitService fruitService;


    @Post("/save")
    @Version("1")
    @Timed(value = "io.hashimati.controllers.fruitController.save", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for saving fruit object")
    @Operation(summary = "Creating a fruit and Storing in the database",
            description = "A REST service, which saves Fruit objects to the database.",
            operationId = "SaveFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Object Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not stored")
    public Fruit save(@Body Fruit fruit){
        log.info("Saving  Fruit : {}", fruit);
        //TODO insert your logic here!

        //saving Object
        return fruitService.save(fruit);
    }


    @Get("/get")
    @Version("1") 
    @Timed(value = "io.hashimati.controllers.fruitController.findById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    @Operation(summary = "Getting a fruit by Id",
        description = "A REST service, which retrieves a Fruit object by Id.",
        operationId = "FindByIdFruit"
    )
    @ApiResponse(
        content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public Fruit findById(@Parameter("id") long id){
        return fruitService.findById(id);
    }

    @Delete("/delete/{id}")
    @Version("1")  
    @Timed(value = "io.hashimati.controllers.fruitController.deleteById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for deleting a fruit object by id")
    @Operation(summary = "Deleting a fruit by ID",
            description = "A REST service, which deletes Fruit object from the database.",
            operationId = "DeleteByIdFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "boolean")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public boolean deleteById(@PathVariable("id") long id){
        log.info("Deleting Fruit by Id: {}", id);
        return  fruitService.deleteById(id);
    }

    @Get("/findAll")
    @Version("1")
    @Timed(value = "io.hashimati.controllers.fruitController.findAll", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects")
    @Operation(summary = "Retrieving all fruit objects as Json",
            description = "A REST service, which returns all Fruit objects from the database.",
            operationId = "FindAllFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    public Iterable<Fruit> findAll(){
        log.info("find All");
        return fruitService.findAll();
    }

    @Put("/update")
    @Version("1") 
    @Timed(value = "io.hashimati.controllers.fruitController.update", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    @Operation(summary = "Updating a fruit.",
            description = "A REST service, which update a Fruit objects to the database.",
            operationId = "UpdateFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public Fruit update(@Body Fruit fruit)
    {
        log.info("update {}", fruit);
        return fruitService.update(fruit);

    }


}
```

##### 5. Fruit Client
```java
@Client("/api/fruit")
public interface FruitClient {

    @Post("/save")
    public Fruit save(Fruit fruit);

    @Get("/get")
    public Fruit findById(@Parameter("id") long id);

    @Delete("/delete/{id}")
    public boolean deleteById(@PathVariable("id") long id);

    @Get("/findAll")
    public Iterable<Fruit> findAll();

    @Put("/update")
    public Fruit update(@Body Fruit fruit);
}
```


<a name="relationship"></a>
## Relationship Command

The developers can add relationship between two generated entities. The command asks the use the following: 
1. First entity. 
2. Second entity. 
3. The relationship type: One-to-One or One-to-Many. 

#### Command Syntax:
```shell
> mc create-relation
```

##### Alias: relation

<a name="messaging"></a>
## Messaging

The developers can use Messaging commands to generated producer/consumer components for the entities generated using "entity" command. MicroCli supports the following messaging systems: 
1. Kafka. 
2. RabbitMQ. 
3. Nats
4. GCP-PubSub. 

Each messaging system has two commands. The first command is for generating the Listener Component. The second command is for generating the Client Component. The messaging commands ask the developers to provide the following information: 
1. The class/interface's package. 
2. The class/interface's name. 
3. The GroupID if required by the system. 
4. The "Subject", "Topic", or "Queue" based on the messaging system. 


##### Nats Listener Example: 
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.micronaut.messaging.annotation.MessageBody;
import io.micronaut.nats.annotation.NatsListener;
import io.micronaut.nats.annotation.Subject;
import io.hashimati.domains.Fruit;


@NatsListener
public class FruitsListener {

    private static final Logger log= LoggerFactory.getLogger(FruitsListener.class);

    
    @Subject("fruits")
    public void receive(@MessageBody Fruit message)
    {
        log.info("Received {}", message);
    }
}


```
##### Nats Client Example: 
```java 
import io.micronaut.nats.annotation.NatsClient;
import io.micronaut.nats.annotation.Subject;
import io.micronaut.messaging.annotation.MessageBody;
import io.hashimati.domains.Fruit;

@NatsClient
public interface FruitClient {

    
    @Subject("fruit")
    public void send(Fruit message);

}
```

#### Micronaut Messaging with Nats Demo: 
![Alt Tutorial](https://github.com/hashimati/MicroCli/blob/master/Nats%20Messaging%20Demo.gif)
<a name="kafka"></a>
### Kafka Commands
#### Kafka Listener 

##### Command: 
```shell
> create-kafka-listener <-e <entityName>>
```

##### Aliases: 
kafka-listener - kafkaListener

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name. |

#### Kafka Client

##### Command: 
```shell
> create-kafka-client <-e <entityName>> 
```

##### Aliases: 
kafka-client - kafkaClient

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name. |


<a name="rabbitmq"></a>
### RabbitMQ Commands
#### RabbitMQ Listener

##### Command: 
```shell
> create-rabbitmq-listener <-e <entityName>>
```

##### Aliases: 
rabbitmqListener - rabbitmq-Listener

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name |



#### RabbitMQ Client

##### Command: 
```shell
> create-rabbitmq-listener <-e <entityName>>
```

##### Aliases: 
rabbitmq-client - rabbitmqClient

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name. |


<a name="nat"></a>
### NATS Commands
#### NATS Listener

##### Command: 
```shell
> create-nat-listener <-e <entityName>>
```

##### Aliases: 
nats-Listener - natsListener

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name. |

#### NATS Client

##### Command: 
```shell
> create-nat-client <-e <entityName>>

```

##### Aliases: 
nats-client - natsClient

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name. |


<a name="gcp"></a>
### GCP Commands
#### GCP Listener

##### Command: 
```shell
> create-gcp-pubsub-listener <-e <entityName>>

```

##### Aliases: 
pubsub-listener - pubSubListener

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name. |

#### GCP Client

##### Command: 
```shell
> create-gcp-pubsub-client <-e <entityName>>
```
##### Aliases: 
pubsub-client - pubsubClient - PubSubClient

##### Options: 
| Option| Alias | Mandatory | Description | 
| :--: | :--: | :--: | :-- | 
| -e | --entity | No | To pass the entity name. |


<a name="security"></a>
## Security Command

MicroCli helps to bootstrap the security authentication mechanisim using the "security" command. The MicroCli supports the following Mechanisim: 
1. Basic. 
2. Session.
3. JWT. 

The "security" command requires the "configure" to be run first and it will boostrap the security files accordingly. Please, ensure to configure according to the supported technologies in the below table: 

| Mechanisim | MongoDB | JDBC | JPA | GORM | Liquibase | Flyway | Reactor | RxJava2 | RxJava3 | 
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
| Basic | <b color="green">Yes</b> | <b color="green">Yes</b> | - | - | <b color="green">Yes</b> | - | <b color="green">Yes</b> | - | - |
| Session | <b color="green">Yes</b> | <b color="green">Yes</b> | - | - | <b color="green">Yes</b> | - | <b color="green">Yes</b> | - | - |
| JWT | <b color="green">Yes</b> | <b color="green">Yes</b> | - | - | <b color="green">Yes</b> | - | <b color="green">Yes</b> | - | - |

### Command Syntax 
```shell
> mc security
```
#### JWT Authentication Demo
![Alt Tutorial](https://github.com/hashimati/MicroCli/blob/master/JWT%20Security%20Demo.gif)

<a name="metrics"></a> 
## Metrics Command
The "metrics" command configurs the metircs registry in the micronaut application. 

### Micronaut + Prometheus + Grafana 
![Grafana](https://github.com/hashimati/MicroCli/blob/master/Prometheus.gif)


<a name="banner"></a>

## Banner Command
The banner command allows to the user to customize the displayed banner at launch time. Please refer to quick start section for the demo. 

##### Command: 
```shell
> mc banner
```


## Support

If you like this work and you would like to see it growing, your support will help: 

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/P5P411AKC)
<a href="https://www.buymeacoffee.com/hashimati"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=hashimati&button_colour=BD5FFF&font_colour=ffffff&font_family=Cookie&outline_colour=000000&coffee_colour=FFDD00"></a> <a href="https://www.patreon.com/bePatron?u=10819450"><img src="https://camo.githubusercontent.com/2b7105015397da52617ce6775a339b0b99d689d6f644c2ce911c5d472362bcbd/68747470733a2f2f63352e70617472656f6e2e636f6d2f65787465726e616c2f6c6f676f2f6265636f6d655f615f706174726f6e5f627574746f6e2e706e67" alt="" data-canonical-src="https://c5.patreon.com/external/logo/become_a_patron_button.png" style="max-width: 100%;">

