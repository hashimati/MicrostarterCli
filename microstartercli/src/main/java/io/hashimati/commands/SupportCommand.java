package io.hashimati.commands;

import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;

@Command(name = "support", aliases = {"donate"}, description = {"To sponsor or support the MicroCli project.", "Thanks in advance!"})
public class SupportCommand implements Callable<Integer> {
    @CommandLine.Option(names = "--path")
    private String path;
    @Override
    public Integer call() throws Exception {
        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        else {
            File directory = new File(path);
            if(!directory.exists()) {
                directory = new File(GeneratorUtils.getCurrentWorkingPath()+"/"+ path);
                if(!directory.exists()){

                    PromptGui.printlnErr("Cannot find the working path!");
                    return null;
                }
            }
            path = path + "/";
        }
        AnsiConsole.systemInstall();
        ansi().eraseScreen();
        File configurationFile = new File(ConfigurationInfo.getConfigurationFileName(path));
        ListResult supportResult = PromptGui.createListPrompt("support", "Support us on:", "Ko-fi", "BuyMeACoffee","Patreon",  "Paypal");
        String support = supportResult.getSelectedId();

        switch (support)
        {
            case "Ko-fi":
                Desktop.getDesktop().browse(new URI("https://ko-fi.com/hashimati"));
                break;
            case "BuyMeACoffee":
                Desktop.getDesktop().browse(new URI("https://www.buymeacoffee.com/hashimati"));
                break;
            case "Patreon":
                Desktop.getDesktop().browse(new URI("https://www.patreon.com/hashimati"));
                break;
            case "Paypal":
            default:
                Desktop.getDesktop().browse(new URI("https://www.paypal.com/paypalme/microstarterio"));
                break;


        }
        return 0;

    }
}
