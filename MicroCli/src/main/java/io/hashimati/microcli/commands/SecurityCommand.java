package io.hashimati.microcli.commands;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.services.SecurityGenerator;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static org.fusesource.jansi.Ansi.ansi;

@Command(name = "security", description = "Enabling Security")
public class SecurityCommand implements Callable<Integer> {


    @Inject
    private SecurityGenerator securityGenerator;

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


        boolean persistRefreshToken = true; //todo: to add this option in the future; strategy.equalsIgnoreCase("jwt")? PromptGui.createConfirmResult("refreshToken","Do you want to persist Refresh Token" ).getConfirmed() == ConfirmChoice.ConfirmationValue.YES:false;


        ArrayList<String> roles = new ArrayList<>();

        for(;;){
            ConfirmResult addRoleConfirm = PromptGui.createConfirmResult("addRole", "Do you want to add Role?", NO);
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
        try {
            securityGenerator.generateSecurityFiles(strategy.toLowerCase(), roles, persistRefreshToken );
        } catch (GradleReaderException e) {

        }
        return 0;
    }
}
