package io.hashimati.parsers.engines;

import io.hashimati.syntax.Syntax;

public abstract class ParsingEngine<T extends Syntax> {
    public abstract T parse(String stentence);

}
