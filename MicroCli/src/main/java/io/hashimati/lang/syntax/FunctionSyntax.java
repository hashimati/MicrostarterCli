package io.hashimati.lang.syntax;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import java.util.ArrayList;

public class FunctionSyntax extends Syntax{

    private String name;
    private ArrayList<String> parameters = new ArrayList<>();

    public FunctionSyntax(String sentence) {
        super(sentence);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }
}
