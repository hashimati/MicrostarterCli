package io.hashimati;

import io.hashimati.commands.*;
import io.hashimati.commands.spring.SpringStarter;
import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

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
        CreateMQTTListenerCommand.class,
        CreateMQTTClientCommand.class,
        BannerCommand.class,
        SecurityCommand.class,
        SupportCommand.class,
        ReportCommand.class,
        ContactCommand.class,
        ConfigureMetricsCommand.class,
        CreateEventCommand.class,
        ListingCommand.class,
        InitCommand.class,
        GatewayCommand.class,
        EurekaCommand.class,
        ConfigServerCommand.class,
        SpringStarter.class,
        DockerCommand.class


})
public class MicrostartercliCommand implements Runnable {

    static{

//        System.out.println("  __  __ _                    _             _               _____ _ _ \n" +
//                " |  \\/  (_)                  | |           | |             / ____| (_)\n" +
//                " | \\  / |_  ___ _ __ ___  ___| |_ __ _ _ __| |_ ___ _ __  | |    | |_ \n" +
//                " | |\\/| | |/ __| '__/ _ \\/ __| __/ _` | '__| __/ _ \\ '__| | |    | | |\n" +
//                " | |  | | | (__| | | (_) \\__ \\ || (_| | |  | ||  __/ |    | |____| | |\n" +
//                " |_|  |_|_|\\___|_|  \\___/|___/\\__\\__,_|_|   \\__\\___|_|     \\_____|_|_|\n" +
//                "                                                                      \n" +
//                "                                                                      \n" +
//                "(Microstarter CLI v0.2.6)\n");


        System.out.print("\033c");

//
//        String banner = "\"'##::::'##'####:'######:'########::'#######::'######:'########:::'###:::'########:'########'########'########::\\n\" +\n" +
//                "                \" ###::'###. ##:'##... ##:##.... ##'##.... ##'##... ##... ##..:::'## ##:::##.... ##... ##..::##.....::##.... ##:\\n\" +\n" +
//                "                \" ####'####: ##::##:::..::##:::: ##:##:::: ##:##:::..:::: ##::::'##:. ##::##:::: ##::: ##::::##:::::::##:::: ##:\\n\" +\n" +
//                "                \" ## ### ##: ##::##:::::::########::##:::: ##. ######:::: ##:::'##:::. ##:########:::: ##::::######:::########::\\n\" +\n" +
//                "                \" ##. #: ##: ##::##:::::::##.. ##:::##:::: ##:..... ##::: ##::::#########:##.. ##::::: ##::::##...::::##.. ##:::\\n\" +\n" +
//                "                \" ##:.:: ##: ##::##::: ##:##::. ##::##:::: ##'##::: ##::: ##::::##.... ##:##::. ##:::: ##::::##:::::::##::. ##::\\n\" +\n" +
//                "                \" ##:::: ##'####. ######::##:::. ##. #######:. ######:::: ##::::##:::: ##:##:::. ##::: ##::::########:##:::. ##:\\n\" +\n" +
//                "                \"..:::::..:....::......::..:::::..::.......:::......:::::..::::..:::::..:..:::::..::::..::::........:..:::::..::\\n\" +\n" +
//                "                \":'######:'##::::::'####::'##::::::                                                                             \\n\" +\n" +
//                "                \"'##... ##:##::::::. ##:::. ##:::::                                                                             \\n\" +\n" +
//                "                \" ##:::..::##::::::: ##::::. ##::::                                                                             \\n\" +\n" +
//                "                \" ##:::::::##::::::: ##:::::. ##:::                                                                             \\n\" +\n" +
//                "                \" ##:::::::##::::::: ##::::: ##::::                                                                             \\n\" +\n" +
//                "                \" ##::: ##:##::::::: ##:::: ##:::::                                                                             \\n\" +\n" +
//                "                \". ######::########'####:::##::::::                                                                             \\n\" +\n" +
//                "                \":......::........:....:::..:::::::   \\n\"; "

        String banner = "   __  ____              ______           __         \n" +
                "  /  |/  (_)__________  / __/ /____ _____/ /____ ____\n" +
                " / /|_/ / / __/ __/ _ \\_\\ \\/ __/ _ `/ __/ __/ -_) __/\n" +
                "/_/  /_/_/\\__/_/  \\___/___/\\__/\\_,_/_/  \\__/\\__/_/   \n" +
                "                                                     ";
        System.out.println(banner +
                "\nMicrostarter CLI v0.2.10");
    }
    @Option(names = { "--verbose"}, description = "...")
    boolean verbose;

    @Option(names= {"-v"}, description = "Display MicrostarterCli version.")
    boolean version;


    public static void main(String[] args) throws Exception {
        PicocliRunner.run(MicrostartercliCommand.class, args);
    }

    public void run() {
        // business logic here
        if(version)
        {
            System.out.println("MicrostarterCLI 0.2.9");
        }
        if (verbose) {
            System.out.println("Hi!, MicrostarterCLI is a rapid development tool for Micronaut framework.  It helps developers to cut the development time and focus on application logic by generating Micronaut components and configurations using ready-built templates.");
        }

    }
}
