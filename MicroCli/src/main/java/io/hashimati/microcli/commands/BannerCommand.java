package io.hashimati.microcli.commands;
/*
 * @author Ahmed Al Hashmi
 */
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.ProjectInfo;
import io.hashimati.microcli.utils.AsciiUtils;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.helpers.NOPLogger;

import java.io.File;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

@Command(name = "banner")
public class BannerCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {


        String defaultBanner = "Micronaut";
        try{
            File configurationFile =new File(ConfigurationInfo.getConfigurationFileName());
            if(configurationFile.exists()){
               defaultBanner =  ConfigurationInfo.fromFile(configurationFile).getAppName();

            }
        }
        catch(Exception ex){

        }

        AnsiConsole.systemInstall();
        org.fusesource.jansi.AnsiConsole.systemInstall();
        InputResult bannerTextResult = PromptGui.inputText("banner", "Enter Banner Text", defaultBanner);
        String bannerText = bannerTextResult.getInput();
        String[] fonts = AsciiUtils.getFontNames().toArray(new String[AsciiUtils.getFontNames().size()]);
        ListResult fontResult = PromptGui.createListPrompt("Font", "Select the font:",fonts );
        InputResult version = PromptGui.inputText("version", "Enter release version:", "0.0.1");

        String banner = AsciiUtils.getBanner(bannerText, fontResult.getSelectedId());


         try{
            String finalBanner =  new StringBuilder(banner).append("\n").append("(").append(bannerText).append(" v").append(version.getInput()).append(")").toString();
            System.out.println("Preview: ");
            System.out.println(finalBanner);
            ListResult confirmBanner = PromptGui.createListPrompt("confirmBanner", "Generate banner?", "yes", "no", "cancel");
            switch (confirmBanner.getSelectedId())
            {
                case "yes":

                    GeneratorUtils.createFile( new StringBuilder().append(System.getProperty("user.dir")).append("/src/main/resources/").append("micronaut-banner.txt").toString(),finalBanner);
                    return 1;
                case "no":
                    return call();
                case "exit":
                    return 0;
                default:
                    return 0;

            }

        }
        catch(Exception ex)
        {
            return 0;

        }
    }
}
