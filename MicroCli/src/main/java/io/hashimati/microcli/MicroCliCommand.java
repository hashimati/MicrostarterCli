package io.hashimati.microcli;
/**
 * @author Ahmed Al Hashmi
 */
import io.hashimati.microcli.commands.*;
import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "MicroCli", description = "A simple tool to generate Micronaut Components",
        mixinStandardHelpOptions = true, subcommands = {ConfigureCommand.class, CreateEntityCommand.class, CreateEnumCommand.class, CreateRelationCommand.class, DeleteEntityCommand.class, DeleteAttributeCommand.class,AddAttributeCommand.class, CreateControllerCommand.class, CreateSingletonCommand.class, CreateJobCommand.class, CreateClientCommand.class, CreateKafkaClientCommand.class, CreateKafkaListenerCommand.class, CreateRabbitMQClientCommand.class, CreateRabbitMQListenerCommand.class, CreateWebsocketClientCommand.class, CreateWebsocketServerCommand.class, CreateNatsClientCommand.class, CreateNatsListenerCommand.class, EnableJWTSecurityCommand.class, CreateGcpPubSubClientCommand.class, CreateGcpPubSubListenerCommand.class})
public class MicroCliCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(MicroCliCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
