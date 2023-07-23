package io.hashimati.microcli.commands;


import io.hashimati.lang.syntax.ServiceSyntax;
import io.hashimati.microcli.services.ServiceGenerator;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name="securityService", aliases = {"secServ"}, description = "To create security service")
public class GenerateSecurityServiceCommand implements Callable<Integer> {


    private ServiceGenerator serviceGenerator;


    @Option(names = "name", description = "A service name.")
    private String name;
    @Option(names = "package", description = "A package name.")
    private String packageName;

    @Option(defaultValue = "3030", names = "port", description = "A service port." )
    private String port;

    @Option(defaultValue = "h2", names = "Database", description = "The database name")
    private String database;


    @Option(defaultValue = "true", names = "To specify the ", description = "The database host")
    private boolean microservice;
    public GenerateSecurityServiceCommand(ServiceGenerator serviceGenerator) {

        this.serviceGenerator = serviceGenerator;
    }


    @Override
    public Integer call() throws Exception {
        AnsiConsole.systemInstall();

        if(name == null || name.trim().isEmpty())
        {
            name = PromptGui.inputText("name","Enter the service name: ","AuthService").getInput();

        }
        if(packageName == null || packageName.trim().isEmpty())
        {
            packageName = PromptGui.inputText("package","Enter the package name: ","com.demo.authservice").getInput();

        }
        if(port == null || port.trim().isEmpty())
        {
            port = PromptGui.inputText("port","Enter the port: ","3030").getInput();
            int portNumber = Integer.parseInt(port);
            if(portNumber < 1024 || portNumber > 65535)
            {
                PromptGui.printlnErr("Invalid port number!");
                return null;
            }
        }
        if(database == null || database.trim().isEmpty())
        {
            database = PromptGui.createListPrompt("Database", "Select the database: ", new String[]{"mongodb","h2", "mysql", "postgres", "mariadb", "sqlserver" }).getSelectedId();

        }


        ServiceSyntax serviceSyntax = new ServiceSyntax("");
        try {
            serviceGenerator.initiateService(serviceSyntax);
        } catch (GradleReaderException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }
}
