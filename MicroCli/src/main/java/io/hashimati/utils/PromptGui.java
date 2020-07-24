package io.hashimati.utils;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;

import java.io.IOException;

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


    public static ListResult dataTypePrompt(String... enums) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        ListPromptBuilder list = promptBuilder.createListPrompt()
                .name("dataType")
                .message("Seelct the data type:")
                .newItem("String").add()
                .newItem("Char").add()
                .newItem("Byte").add()
                .newItem("Short").add()
                .newItem("Integer").add()
                .newItem("Long").add()
                .newItem("Float").add()
                .newItem("Double").add()
                .newItem("Boolean").add();
                for(String o : enums)
                    list.newItem(o).add();
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
}
