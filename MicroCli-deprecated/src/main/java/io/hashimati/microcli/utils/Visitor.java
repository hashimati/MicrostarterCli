package io.hashimati.microcli.utils;

/**
 * @author Ahmed Al Hashmi
 */
@FunctionalInterface
public interface Visitor<T> {
    public T visit(T t);
}
