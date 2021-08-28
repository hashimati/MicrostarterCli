package io.hashimati.microcli;
/**
 * @author Ahmed Al Hashmi
 */
import io.hashimati.microcli.commands.*;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "MicroCli", description = "A simple tool to generate Micronaut Components",
        mixinStandardHelpOptions = true, subcommands = {
            ConfigureCommand.class,
            CreateEntityCommand.class,
            CreateEnumCommand.class,
            CreateRelationCommand.class,
            DeleteEntityCommand.class,
            DeleteAttributeCommand.class,
            AddAttributeCommand.class,
            CreateControllerCommand.class,
            CreateSingletonCommand.class,
            CreateJobCommand.class,
            CreateClientCommand.class,
            CreateKafkaClientCommand.class,
            CreateKafkaListenerCommand.class,
            CreateRabbitMQClientCommand.class,
            CreateRabbitMQListenerCommand.class,
            CreateWebsocketClientCommand.class,
            CreateWebsocketServerCommand.class,
            CreateNatsClientCommand.class,
            CreateNatsListenerCommand.class,
            EnableJWTSecurityCommand.class,
            CreateGcpPubSubClientCommand.class,
            CreateGcpPubSubListenerCommand.class,
            CreateMQTTListenerCommand.class,
            CreateMQTTClientCommand.class
})
public class MicroCliCommand implements Runnable {


    static{

        System.out.println("::::    ::::  :::::::::::  ::::::::  :::::::::   ::::::::   ::::::::  :::        :::::::::::\n" +
                "+:+:+: :+:+:+     :+:     :+:    :+: :+:    :+: :+:    :+: :+:    :+: :+:            :+:\n" +
                "+:+ +:+:+ +:+     +:+     +:+        +:+    +:+ +:+    +:+ +:+        +:+            +:+\n" +
                "+#+  +:+  +#+     +#+     +#+        +#++:++#:  +#+    +:+ +#+        +#+            +#+\n" +
                "+#+       +#+     +#+     +#+        +#+    +#+ +#+    +#+ +#+        +#+            +#+\n" +
                "#+#       #+#     #+#     #+#    #+# #+#    #+# #+#    #+# #+#    #+# #+#            #+#\n" +
                "###       ### ###########  ########  ###    ###  ########   ########  ########## ###########\n" +
                "(MicroCLI v0.0.1)\n\n" +
                "");
    }
    @Option(names = { "--verbose"}, description = "...")
    boolean verbose;

    @Option(names= {"-v"}, description = "Display MicroCli version.")
    boolean version;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(MicroCliCommand.class, args);
    }

    public void run() {
        // business logic here
        if(version)
        {
            System.out.println("MicroCLI 0.0.1");
        }
        if (verbose) {
            System.out.println("Hi!, MicroCli is a rapid development tool for Micronaut framewMicroCli is a command-line rapid development tool for Micronaut applications. It helps developers cut development time and focus on application logic by generating Micronaut components and configurations using ready-built templates.");
        }

    }
}
