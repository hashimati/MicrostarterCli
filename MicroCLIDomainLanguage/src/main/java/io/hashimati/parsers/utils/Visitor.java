package io.hashimati.parsers.utils;

/**
 * @author Ahmed Al Hashmi
 */
@FunctionalInterface
public interface Visitor<T> {
    public T visit(T t);
}
