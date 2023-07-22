package io.hashimati.microcli.test;
/**
 * @author Ahmed Al Hashmi
 *
 * @github: @Hashimati
 * @twitter: @hashimati
 * @email: hashimati.ahmed@gmail.com
 */
//
//
//import de.codeshelf.consoleui.elements.ConfirmChoice;
//import de.codeshelf.consoleui.prompt.ConfirmResult;
//import de.codeshelf.consoleui.prompt.ConsolePrompt;
//import de.codeshelf.consoleui.prompt.InputResult;
//import de.codeshelf.consoleui.prompt.PromtResultItemIF;
//import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
//import io.hashimati.utils.ProjectValidator;
//import io.hashimati.utils.PromptGui;
//import lombok.SneakyThrows;
//import org.fusesource.jansi.AnsiConsole;
//import org.jline.reader.LineReader;
//import org.jline.terminal.Terminal;
//import picocli.CommandLine.Command;
//import picocli.CommandLine.Option;
//import picocli.CommandLine.Parameters;
//
//import jakarta.inject.Inject;
//import java.io.IOException;
//import java.util.HashMap;
//
//
//@Command(name = "test", subcommandsRepeatable = true)
//public class TestCommand implements Runnable
//{
//    @Command(name = "add")
//    public void add(@Option(names = {"-d"}) String ahmed){
//        System.out.println("Hahaha: " + ahmed);
//    }
//
//    @Option(names = "-o", converter = MyOptionConverter.class)
//    private MyOptions myOptions;
//
//
//    @Option(names = {"-a", "--age"})
//    private int age;
//
//
//    @Option(names = {"-n", "--name"})
//    private String name;
//    public TestCommand(){}
//
//    @Parameters(index = "0")
//    private String family;
//
//    @Option(names = "-as")
//    private boolean ask;
////    private String hohoh;
//
//    @Inject
//    private LineReader lineReader;
//
//
//
//
//
//
//    @SneakyThrows
//    @Override
//    public void run()  {
//        System.out.println("Name: "+ name);
//        System.out.println("Age: " + age);
//        System.out.println("Options: " + myOptions.getName() + " " + myOptions.getAge());
//        System.out.println("Family: " + family);
//        if(ask)
//        {
//
//
//        //    AnsiConsole.systemInstall();;
////
////            PromptGui.inputText("ahmed", "What is your name?", "hashimi").getInput();
////
////
////            PromptGui.dataTypePrompt("HelloEnum");
////            ConsolePrompt prompt = new ConsolePrompt();
////            PromptBuilder promptBuilder = prompt.getPromptBuilder();
////
////            promptBuilder.createInputPrompt()
////                    .name("entityName")
////                    .message("Please enter entity name:")
////                    .defaultValue("Person")
////                    //.mask('*')
////                    ///.addCompleter(new StringsCompleter("Jim", "Jack", "John"))
////                    .addPrompt();
////
////            promptBuilder.createListPrompt()
////                    .name("pizzatype")
////                    .message("Which pizza do you want?")
////                    .newItem().text("Margherita").add()  // without name (name defaults to text)
////                    .newItem("veneziana").text("Veneziana").add()
////                    .newItem("hawai").text("Hawai").add()
////                    .newItem("quattro").text("Quattro Stagioni").add()
////                    .addPrompt();
////
////            promptBuilder.createCheckboxPrompt()
////                    .name("topping")
////                    .message("Please select additional toppings:")
////
////                    .newSeparator("standard toppings")
////                    .add()
////
////                    .newItem().name("cheese").text("Cheese").add()
////                    .newItem("bacon").text("Bacon").add()
////                    .newItem("onions").text("Onions").disabledText("Sorry. Out of stock.").add()
////
////                    .newSeparator().text("special toppings").add()
////
////                    .newItem("salami").text("Very hot salami").check().add()
////                    .newItem("salmon").text("Smoked Salmon").add()
////
////                    .newSeparator("and our speciality...").add()
////
////                    .newItem("special").text("Anchovies, and olives").checked(true).add()
////                    .addPrompt();
////
////            promptBuilder.createChoicePrompt()
////                    .name("payment")
////                    .message("How do you want to pay?")
////
////                    .newItem().name("cash").message("Cash").key('c').asDefault().add()
////                    .newItem("visa").message("Visa Card").key('v').add()
////                    .newItem("master").message("Master Card").key('m').add()
////                    .newSeparator("online payment").add()
////                    .newItem("paypal").message("Paypal").key('p').add()
////                    .addPrompt().toString();
////
////            promptBuilder.createConfirmPromp()
////                    .name("delivery")
////                    .message("Is this pizza for delivery?")
////                    .defaultValue(ConfirmChoice.ConfirmationValue.YES)
////                    .addPrompt();
////
////
////            HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());
////
////            System.out.println("result = " + result);
////
////            ConfirmResult delivery = (ConfirmResult) result.get("delivery");
////            if (delivery.getConfirmed()== ConfirmChoice.ConfirmationValue.YES) {
////                System.out.println("We will deliver the pizza in 5 minutes");
////            }
//        }
//
//
//
//        System.out.println(ProjectValidator.getProjectInfo());
//        micronautEntityGenerator.test();;
//    }
//    @Inject
//    private io.hashimati.microcli.MicronautEntityGenerator micronautEntityGenerator;
//
//}
