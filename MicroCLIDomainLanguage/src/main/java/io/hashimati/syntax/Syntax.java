package io.hashimati.syntax;

import java.util.ArrayList;

public abstract class Syntax {

    private String sentence;
    private ArrayList<String> errors = new ArrayList<>();
    public Syntax(final String sentence)
    {
        this.sentence = sentence;
    }
    public String getSentence() {
        return sentence;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }
}
