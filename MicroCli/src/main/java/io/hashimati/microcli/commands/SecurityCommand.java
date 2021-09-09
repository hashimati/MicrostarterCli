package io.hashimati.microcli.commands;

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Callable;

import static org.fusesource.jansi.Ansi.ansi;

@Command(name = "security", description = "Enabling Security")
public class SecurityCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        AnsiConsole.systemInstall();
        ansi().eraseScreen();
        File configurationFile = new File(ConfigurationInfo.getConfigurationFileName());
        if(!configurationFile.exists()){
            PromptGui.printlnWarning("run \"configure\" command first!");
            return 0;
        }

        ConfigurationInfo configurationInfo  = ConfigurationInfo.fromFile(configurationFile);
        if(configurationInfo.isSecurityEnable())
        {
            PromptGui.println("The security is enabled", Ansi.Color.WHITE);
            return 0;
        }

        ListResult strategyResult = PromptGui.createListPrompt("strategy", "Select authentication strategy:", "Basic", "Session", "JWT");
        String strategy = strategyResult.getSelectedId();

        ArrayList<String> roles = new ArrayList<>();

        for(;;){
            ConfirmResult addRoleConfirm = PromptGui.createConfirmResult("addRole", "Do you want to add Role?");
            if(addRoleConfirm.getConfirmed()== ConfirmChoice.ConfirmationValue.NO)
                break;
            else{
                InputResult roleInput = PromptGui.inputText("role", "Enter Role Name:", ("Role"));
                String role = roleInput.getInput().toUpperCase()
                        .trim().replace(" ", "_");
                roles.add(role);
            }
        }

        String lang = configurationInfo.getProjectInfo().getSourceLanguage();

        switch (lang.toLowerCase())
        {
            case "java":
                break;
            case "kotlin":
                break;
            case "groovy":
                break;
            default:
                break;
        }

        return 0;
    }
}
