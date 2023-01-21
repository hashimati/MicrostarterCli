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
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.GradleReaderException;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Callable;

import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.NO;
import static de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue.YES;
import static org.fusesource.jansi.Ansi.ansi;

@Command(name = "security", description = {"To enable Security", "This command will generate the security files based the configuration, and selected security mechanism."} , subcommands = {InterceptURLCommand.class})//,subcommands = SSLCommand.class)
public class SecurityCommand implements Callable<Integer> {

    @Inject
    private SecurityGenerator securityGenerator;
    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
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
                path = GeneratorUtils.getCurrentWorkingPath()+"/" +path ;

            }
           path = path +"/";
        }

        AnsiConsole.systemInstall();
        ansi().eraseScreen();
        File configurationFile = new File(ConfigurationInfo.getConfigurationFileName(path));
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


        HashSet<String> roles = new HashSet<>();

        roles.add("ADMIN_ROLE");
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
        HashSet<String> servcieIds = new HashSet<>();
        boolean monolithic  = configurationInfo.isMonolithic();
        boolean propagate = false;
      if(strategy.equalsIgnoreCase("jwt")){

          if(monolithic) {
              ConfirmResult monolithicConfirm = PromptGui.createConfirmResult("microservice", "Is this a monolithic service?", YES);
              monolithic = monolithicConfirm.getConfirmed() == ConfirmChoice.ConfirmationValue.YES;
          }
          if(!monolithic)
          {
              ConfirmResult propagte = PromptGui.createConfirmResult("addServiceID", "Is this Token Propagation service?", NO);
              propagate = propagte.getConfirmed() == ConfirmChoice.ConfirmationValue.YES;
              if (propagate)
                  for (; ; ) {
                      ConfirmResult addServiceIDConfirm = PromptGui.createConfirmResult("addServiceID", "Do you want to add Service ID?", NO);
                      if (addServiceIDConfirm.getConfirmed() == ConfirmChoice.ConfirmationValue.NO)
                          break;
                      else {
                          InputResult serviceIDnput = PromptGui.inputText("serviceIDnput", "Enter Service id (hint: Use Kebab formatting, like \"my-service-id\":", ("service-id"));
                          servcieIds.add(serviceIDnput.getInput());
                      }
                  }
          }
      }
        String lang = configurationInfo.getProjectInfo().getSourceLanguage();
        switch (lang.toLowerCase())
        {
            case "java":
                try {

                    MicronautProjectValidator.addLombok(path,configurationInfo.getProjectInfo());
                } catch (GradleReaderException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "kotlin":
                break;
            case "groovy":
                break;
            default:
                break;
        }
        try {
            securityGenerator.generateSecurityFiles(path, strategy.toLowerCase(), roles, persistRefreshToken, propagate, servcieIds, monolithic);
        } catch (GradleReaderException e) {

        }
        return 0;
    }
}
