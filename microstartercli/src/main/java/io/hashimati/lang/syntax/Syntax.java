package io.hashimati.lang.syntax;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import java.util.ArrayList;

public abstract class Syntax {
    private String sentence;
    private ArrayList<String> errors = new ArrayList<>();
    private boolean isValid;
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

    @Override
    public String toString() {
        return sentence;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
