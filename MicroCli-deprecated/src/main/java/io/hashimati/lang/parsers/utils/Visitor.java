package io.hashimati.lang.parsers.utils;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
@FunctionalInterface
public interface Visitor<T> {
    public T visit(T t);
}
