package io.hashimati.microcli.utils;
/**
 * @author Ahmed Al Hashmi
 */

import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.*;
import de.codeshelf.consoleui.prompt.builder.CheckboxPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import fr.jcgay.notification.*;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import org.apache.commons.lang3.math.NumberUtils;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class PromptGui {



    public static InputResult inputText(String name, String message, String defaultValue) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createInputPrompt()
                .name(name)
                .message(message)
                .defaultValue(defaultValue)
                //.mask('*')
                ///.addCompleter(new StringsCompleter("Jim", "Jack", "John"))
                .addPrompt();
        InputResult inputValue = (InputResult)prompt.prompt(promptBuilder.build()).get(name);
        return inputValue;
    }
    public static InputResult readNumber(String name, String message, String defaultValue)throws IOException
    {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createInputPrompt()
                .name(name)
                .message(message)
                .defaultValue(defaultValue)
                //.mask('*')
                ///.addCompleter(new StringsCompleter("Jim", "Jack", "John"))
                .addPrompt();
        InputResult inputValue = (InputResult)prompt.prompt(promptBuilder.build()).get(name);

        if(NumberUtils.isCreatable(inputValue.getInput()))
        {
            return inputValue;
        }
        else {
            System.out.println("Please, enter number.");
            return readNumber(name, message, defaultValue);
        }
    }



    public static ListResult dataTypePrompt(List<String> enums) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        ListPromptBuilder list = promptBuilder.createListPrompt()
                .name("dataType")
                .message("Select the data type:")

                .newItem("String").add()
                .newItem("boolean").add()
                .newItem("char").add()
                .newItem("byte").add()
                .newItem("short").add()
                .newItem("int").add()
                .newItem("long").add()
                .newItem("float").add()
                .newItem("double").add()
                .newItem("BigInteger").add()
                .newItem("BigDecimal").add()
                .newItem("Date").add()
                .newItem("file").add();
               if(!enums.isEmpty()) {

                   for (String o : enums)
                       list.newItem(o).add();
               }
              //  .addPrompt();
        list.addPrompt();
        ListResult  result = (ListResult) prompt.prompt(promptBuilder.build()).get("dataType");
        return result;

    }



    
    
    public static ListResult createListPrompt(String name, String message,  String... enums) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        ListPromptBuilder list = promptBuilder.createListPrompt()
                .name(name)
                .message(message);
        for(String o : enums)
            list.newItem(o).add();
        //  .addPrompt();
        list.addPrompt();
        ListResult  result = (ListResult) prompt.prompt(promptBuilder.build()).get(name);
        return result;

    }
//    public
    public static ConfirmResult createConfirmResult(String name, String message,ConfirmChoice.ConfirmationValue defaultValue  ) throws IOException {

        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder builder = prompt.getPromptBuilder();
        builder.createConfirmPromp()
                .name(name)
                .message(message)
                .defaultValue(defaultValue)
                .addPrompt();
        return (ConfirmResult)prompt.prompt(builder.build()).get(name);

    }


    public static CheckboxResult createChoiceResult(String name, String message, String... choices) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder builder = prompt.getPromptBuilder();
        CheckboxPromptBuilder list = builder.createCheckboxPrompt().name(name)
                .message(message);
        for(String c: choices)
            list.newItem(c).add();
        list.addPrompt();

        return (CheckboxResult)prompt.prompt(builder.build()).get(name);

    }



    public static void println(String message, Color color)
    {
        AnsiConsole.systemInstall();
        System.out.println(ansi().bold().fg(color).a(message));
       setToDefault();

    }
    public static void print(String message, Color color)
    {
        AnsiConsole.systemInstall();
        System.out.print(ansi().bold().fg(color).a(message));
       setToDefault();

    }
    public static void printlnErr(String message)
    {
        AnsiConsole.systemInstall();
        System.out.println(ansi().bold().fgBrightRed().a(message));
       // setToDefault();


    }
    public static void printlnSuccess(String message)
    {
        AnsiConsole.systemInstall();
        System.out.println(ansi().bold().fgBrightGreen().a(message));
        setToDefault();

    }
    public static void setToDefault()
    {
        AnsiConsole.systemInstall();
        System.out.print(ansi().fgDefault().boldOff());
    }

    public static void printlnWarning(String message) {
        AnsiConsole.systemInstall();
        System.out.println(ansi().bold().fgBrightYellow().a(message));
        setToDefault();

    }

    public static void Notify(String title, String message)
    {
        ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();

        var icon = loader.getResource("classpath:images/MicrostarterLogo.png").get();
        Notifier notifier = new SendNotification()
             //   .setApplication(Application.builder().name("MicrostarterCLI").id("MicrostarterCLI").icon(Icon.create(icon, "MicrostarterCli")).build())
                .initNotifier();
        try
        {
            notifier.send(Notification.builder().title(title).icon(Icon.create(icon, "MicromstarterCli")).message(message).build());
            notifier.close();
        }
        finally {
            notifier.close();

        }
    }
}
