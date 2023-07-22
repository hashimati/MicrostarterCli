package io.hashimati.microcli.exceptions;

public class NotImplementedException extends IncompatibleClassChangeError{
    public NotImplementedException(String message)
    {

        super(message);
    }
}
