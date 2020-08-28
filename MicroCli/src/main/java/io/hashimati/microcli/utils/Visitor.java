package io.hashimati.microcli.utils;


@FunctionalInterface
public interface Visitor<T> {
    public T visit(T t);
}
