package io.hashimati.microcli;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
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
           // EnableJWTSecurityCommand.class,
            CreateGcpPubSubClientCommand.class,
            CreateGcpPubSubListenerCommand.class,
//            CreateMQTTListenerCommand.class,
//            CreateMQTTClientCommand.class,
            BannerCommand.class,
            SecurityCommand.class,
            SupportCommand.class,
        ReportCommand.class,
        ContactCommand.class,
        ConfigureMetricsCommand.class

})
public class MicroCliCommand implements Runnable {


    static{

        System.out.println("  __  __ _                    _             _               _____ _ _ \n" +
                " |  \\/  (_)                  | |           | |             / ____| (_)\n" +
                " | \\  / |_  ___ _ __ ___  ___| |_ __ _ _ __| |_ ___ _ __  | |    | |_ \n" +
                " | |\\/| | |/ __| '__/ _ \\/ __| __/ _` | '__| __/ _ \\ '__| | |    | | |\n" +
                " | |  | | | (__| | | (_) \\__ \\ || (_| | |  | ||  __/ |    | |____| | |\n" +
                " |_|  |_|_|\\___|_|  \\___/|___/\\__\\__,_|_|   \\__\\___|_|     \\_____|_|_|\n" +
                "                                                                      \n" +
                "                                                                      \n" +
                "(Microstarter CLI v0.0.3)\n");
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
