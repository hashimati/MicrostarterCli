package io.hashimati.microcli.commands;


import de.codeshelf.consoleui.prompt.ListResult;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.domains.ConfigurationInfo;
import io.hashimati.microcli.domains.URL;
import io.hashimati.microcli.services.TemplatesService;
import io.hashimati.microcli.utils.GeneratorUtils;
import io.hashimati.microcli.utils.MicronautProjectValidator;
import io.hashimati.microcli.utils.PromptGui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.hashimati.microcli.services.TemplatesService.SECURITY_INTERCEPT_URL;
import static io.hashimati.microcli.services.TemplatesService.SECURITY_INTERCEPT_URL_PATTERN;
import static io.hashimati.microcli.utils.PromptGui.println;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.Color.YELLOW;

@Command(name= "urls", description = "To configure Intercept URL Map.")
public class InterceptURLCommand implements Callable<Integer>
{

    @Inject
    private TemplatesService templatesService;
    @CommandLine.Option(names = "--path", description = "To specify the working directory.")
    private String path;
    @Override
    public Integer call() throws Exception {
        if(path == null || path.trim().isEmpty())
        {
            path = GeneratorUtils.getCurrentWorkingPath();

        }
        AnsiConsole.systemInstall();
        File configurationFile =new File(ConfigurationInfo.getConfigurationFileName(path));
        ConfigurationInfo  configurationInfo = null;
        List<Ansi.Color> colorList = Arrays.asList(MAGENTA, CYAN, GREEN, BLUE, WHITE, YELLOW);
        String interceptURLTemplate = templatesService.loadTemplateContent(templatesService.getSecurityPropertiesTemplates().get(SECURITY_INTERCEPT_URL));
        String interceptURLPatternTemplate = templatesService.loadTemplateContent(templatesService.getSecurityPropertiesTemplates().get(SECURITY_INTERCEPT_URL_PATTERN));
        String patterns = "";
        LinkedHashSet<URL> applicationURLs = new LinkedHashSet<>();
        if(!configurationFile.exists()){

            println("The project is not configured. Please, run \"configure\" command", YELLOW);
            return 0;
        }
        else {
            configurationInfo = ConfigurationInfo.fromFile(configurationFile);
        }

        if(!configurationInfo.isSecurityEnable())
        {
            PromptGui.printlnWarning("Please, configure security first.");
            return 0;
        }
        Set<String> urlsScope = configurationInfo.getEntities().stream().filter(x -> !x.isNoEndpoints()).map(x -> x.getName()).collect(Collectors.toSet());
        urlsScope.addAll(configurationInfo.getUrls().stream().map(x->x.getScope()).collect(Collectors.toList()));
       String entityName =  PromptGui.createListPrompt("entity", "Select the scope of Urls: ", urlsScope.toArray(new String[]{})).getSelectedId();




         var roles =  new ArrayList<String>(){{
         }};
         roles.addAll(configurationInfo.getSecurityRoles().stream().filter(x->!x.equalsIgnoreCase("ADMIN_ROLE")).collect(Collectors.toList()));
        ArrayList<URL> urls = new ArrayList<>();
         if(Arrays.asList("/GraphQL", "/OpenAPI", "/Security", "/metrics").contains(entityName)){
             //todo securing graphql
             urls.addAll( configurationInfo.getUrls().stream().filter(x->x.getScope().equalsIgnoreCase(entityName  )).collect(Collectors.toList()));
         }
        else {
             urls = configurationInfo.getEntities().stream().filter(x -> x.getName().equals(entityName)).findFirst()
                     .get().getUrls();
         }
        urls.stream().forEach(x -> {
            try {
                String securedRule = PromptGui.createListPrompt("Type", "Choose secured rules for: " + x.getUrl(), "isAnonymous()", "isAuthenticated()", "Choose Roles").getSelectedId();


                if (securedRule.equals("Choose Roles")) {
                    var selectedRoles = PromptGui.createChoiceResult("roles", "Choose roles for: " + x.getUrl(), roles.toArray(new String[]{})).getSelectedIds();
                    selectedRoles.add("ADMIN_ROLE");
                    x.setRoles(selectedRoles);
                } else {
                    x.setRoles(new HashSet<String>() {{
                        add(securedRule);
                    }});
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        configurationInfo.writeToFile(path);

         applicationURLs.addAll(configurationInfo.getEntities().stream()
                .filter(x -> !x.getUrls().isEmpty())
                .map(x -> x.getUrls())
                .flatMap(x -> x.stream())
                .collect(Collectors.toList()));
         applicationURLs.addAll(configurationInfo.getUrls());
        patterns = applicationURLs.stream().filter(x-> !x.getRoles().isEmpty())
                .map(x->{
                    HashMap<String, Object> binder = new HashMap<String, Object>();
                    binder.put("pattern", "\""+x.getUrl()+"\"");
                    binder.put("method", "\""+x.getMethod().name().toUpperCase(Locale.ROOT)+"\"");
                    binder.put("roles","["+x.getRoles().stream().map(y-> "\"" + y+"\"") .reduce((a,b)->a + ", " + b).orElse("") + "]");
                    try {
                        return new SimpleTemplateEngine().createTemplate(interceptURLPatternTemplate).make(binder).toString();
                    } catch (ClassNotFoundException e) {
                        return "";
                    } catch (IOException e) {
                        return "";
                    }
                }).reduce((a,b) ->a+"\n"+b).orElse("");

        if(patterns.trim().isEmpty())
            return 0;
        String finalPatterns = patterns;
        String intercepURLMap = new SimpleTemplateEngine()
                .createTemplate(interceptURLTemplate)
                .make(new HashMap<String, String>(){{
                    put("patterns", finalPatterns);
                }}).toString();

       String oldProperties=  MicronautProjectValidator.getPropertiesContent();
       if(oldProperties.contains("--- #Intercept URL Map") && oldProperties.contains("--- #End Intercept URL Map")){
            String oldURLIntercep = oldProperties.substring(oldProperties.indexOf("--- #Intercept URL Map"), oldProperties.indexOf("--- #End Intercept URL Map")+"--- #End Intercept URL Map".length()+1);
            MicronautProjectValidator.writeProperties(oldProperties.replace(oldURLIntercep, intercepURLMap));
       }
       else {
           MicronautProjectValidator.appendToProperties(intercepURLMap);
       }

       return 100;
    }
}
