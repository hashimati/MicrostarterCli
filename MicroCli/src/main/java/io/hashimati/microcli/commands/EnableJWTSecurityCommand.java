package io.hashimati.microcli.commands;


import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.services.MicronautJWTSecurityGenerator;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static io.hashimati.microcli.utils.PromptGui.*;

@Command(name = "enable-security-jwt", aliases = {"jwt", "JWT", "security-jwt"}, description = "To secure the application with JWT")
public class EnableJWTSecurityCommand implements Callable<Integer> {

    @Option(names = {"-r", "--rules"}, description = {"Security Rules ", "You can use commas without spaces to add multiple rules.", "For Example, -o ADMIN,CUSTOMER,SELLER"}, split =",")
    private HashSet<String> rules ;

    @Inject
    private MicronautJWTSecurityGenerator  micronautJWTSecurityGenerator;


    private ConfigurationInfo configurationInfo;
    @Override
    public Integer call() throws Exception {

        configurationInfo = new ConfigureCommand().call();

        AnsiConsole.systemInstall();
        if(rules == null)
            rules = new HashSet<>();
        if(rules.isEmpty()) {
            boolean read = true;
            while(read) {
                String rule = PromptGui.inputText("rule","Enter a rule:", "ADMIN" ).getInput();
                if(rules.contains(rule))
                {
                    printlnWarning(rule + " is already added!");
                }
                else
                    rules.add(rule);
                read = createConfirmResult("more", "Do you want to add another security rule?").getConfirmed() != ConfirmChoice.ConfirmationValue.NO;
            }
        }

        String rootPath = new StringBuilder().append(System.getProperty("user.dir")).append("/").append(GeneratorUtils.packageToPath(configurationInfo.getProjectInfo().getDefaultPackage())).toString();
        //todo generate  Roles


        String rolesContent = micronautJWTSecurityGenerator.generateRoles(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage(), rules.stream().collect(Collectors.toList()));
        String rolesPath = new StringBuilder().append(rootPath).append("/security/Roles.java").toString();
        GeneratorUtils.createFile(rolesPath, rolesContent);

        //todo generate User

        String userContent = micronautJWTSecurityGenerator.generateUserEntity(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage());
        String userPath = new StringBuilder().append(rootPath).append("/security/User.java").toString();
        GeneratorUtils.createFile(userPath, userContent);

        //todo generate Repository


        String repoContent = micronautJWTSecurityGenerator.generateUserRepository(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage(), configurationInfo.getDatabaseType());
        String repoPath = new StringBuilder().append(rootPath).append("/security/UserRepository.java").toString();
        GeneratorUtils.createFile(repoPath, repoContent);
        //todo generate Service

        String servContent = micronautJWTSecurityGenerator.generateUserService(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage(), configurationInfo.getDatabaseType());
        String servPath = new StringBuilder().append(rootPath).append("/security/UserService.java").toString();
        GeneratorUtils.createFile(servPath, servContent);
        //todo generate Controller

        String restContent = micronautJWTSecurityGenerator.generateUserController(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage(), configurationInfo.getDatabaseType());
        String restPath = new StringBuilder().append(rootPath).append("/security/UserController.java").toString();
        GeneratorUtils.createFile(restPath, restContent);
        //todo generate Password Encrypter

        String encrytContent = micronautJWTSecurityGenerator.generatePasswrodEncoder(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage());
        String encrytPath = new StringBuilder().append(rootPath).append("/security/PasswordEncoder.java").toString();
        GeneratorUtils.createFile(encrytPath,encrytContent);
        //todo generate Login Event
        String loginEventContent = micronautJWTSecurityGenerator.generateLoginEvent(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage());
        String loginEventPath = new StringBuilder().append(rootPath).append("/security/LoginEvent.java").toString();
        GeneratorUtils.createFile(loginEventPath,loginEventContent);
        //todo generate Login status
        String loginStatusContent = micronautJWTSecurityGenerator.generateLoginStatus(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage());
        String loginStatusPath = new StringBuilder().append(rootPath).append("/security/LoginStatus.java").toString();
        GeneratorUtils.createFile(loginStatusPath,loginStatusContent);
        //todo generate Password Provider
        String authProviderContent = micronautJWTSecurityGenerator.generateAuthenticationProvider(configurationInfo.getProjectInfo().getDefaultPackage(), configurationInfo.getProjectInfo().getSourceLanguage());
        String authProviderPath = new StringBuilder().append(rootPath).append("/security/AuthenictationProviderUserPassword.java").toString();
        GeneratorUtils.createFile(authProviderPath,authProviderContent);


        return 0;
    }
}
