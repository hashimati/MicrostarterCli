# MicroCli
MicroCli is a command-line rapid development tool for Micronaut applications. It helps developers to cut the development time and focus on the application logic by generating Micronaut components and configurations using ready-built templates. This tool is a [Micronaut](https://github.com/micronaut-projects)/[PicoCLI](https://github.com/remkop/picocli) application powered with [ConsoleUI](https://github.com/awegmann/consoleui)



# Table Of Contents
1. [Getting Started](#started)
2. [Configure Command](#configure)
3. [Enum Command](#enum)
4. [Entity Command](#entity)
5. [Relationship Command](#relationship)
6. [Messaging](#messaging)
    1. [Kafka Commmands](#kafka)
    2. [RabbitMQ Commands](#rabbitmq)
    3. [NAT Commands](#nat). 
7. [Security Command](#security)
    1. [JWT](#jwt)
    2. [Baiscs](#basicsecurity)
    3. [Session](#session)
8. [Banner Command](#banner)








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



6. MicroCli application will launch. And, it will start with configuration process. 

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

The "entity" command helps the developers to bootstarp the code of the basic CRUD operations for the application's domains. The files are including Entity Class file, Database Repository file, Service file, REST Endpoint file, and GraphQL configuration files. The "entity" command generates the files based on the information that the developer provides in "configure" command. The "entity" command runs the "configure" command implicitly if the developer didn't run it.. 

#### Command Options: 
| Option |  Aliases | Description |
| :---: | :---: | :---: |
| --entity-name| -e , -n | To specify the entity's name |
| --collection-name | -c | to specify the entity's table/collection name |
| --no-endpoint | :---: | to prevent generating the entity's controller class | 
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

<a name="messaging"></a>
## Messaging

<a name="kafka"></a>
### Kafka Commands

<a name="rabbitmq"></a>
### RabbitMQ Commands

<a name="nat"></a>
### NAT Commands

<a name="security"></a>
## Security Command
<a name="jwt"></a>
### JWT
<a name="basicsecurity"></a>
### Basic

<a name="Session"></a>
### Session

<a name="banner"></a>
## Banner Command
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/P5P411AKC)
<a href="https://www.buymeacoffee.com/hashimati"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=hashimati&button_colour=BD5FFF&font_colour=ffffff&font_family=Cookie&outline_colour=000000&coffee_colour=FFDD00"></a>

