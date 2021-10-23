# MicroCli
MicroCli is a command-line rapid development tool for Micronaut applications. It helps developers to cut the development time and focus on the application logic by generating Micronaut components and configurations using ready-built templates. This tool is a [Micronaut](https://github.com/micronaut-projects)/[PicoCLI](https://github.com/remkop/picocli) application powered with [ConsoleUI](https://github.com/awegmann/consoleui)



# Table Of Contents
1. [Getting Started](#started)
2. [Configure Command](#configure)
3. [Enum Command](#enum)
4. [Entity Command](#entity)
5. [Relationship Command](#relationship)
6. [Messaging](#messaging)
    1. [Kafka](#kafka)
    2. [RabbitMQ](#rabbitmq)
    3. [NAT](#nat). 
7. [Security](#security)
    1. [JWT](#jwt)
    2. [Baiscs](#basicsecurity)
    3. [Session](#session)
8. [Banner](#banners)








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

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/P5P411AKC)
<a href="https://www.buymeacoffee.com/hashimati"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=hashimati&button_colour=BD5FFF&font_colour=ffffff&font_family=Cookie&outline_colour=000000&coffee_colour=FFDD00"></a>

