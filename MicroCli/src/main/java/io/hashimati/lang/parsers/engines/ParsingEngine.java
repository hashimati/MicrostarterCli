package io.hashimati.lang.parsers.engines;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import io.hashimati.lang.syntax.Syntax;

public abstract class ParsingEngine<T extends Syntax> {
    public abstract T parse(String sentence);

}
