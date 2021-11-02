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

#### Command:
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
```shell
> mc create-enum --name WHETHER --options SUNNY,CLOUDY,RAINY 
```


<a name="entity"></a>
## Entity Command

```shell
mc entity -entity-name Fruit
```
options
--collection-name
--no-endpoint
--graphql
--cache


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

