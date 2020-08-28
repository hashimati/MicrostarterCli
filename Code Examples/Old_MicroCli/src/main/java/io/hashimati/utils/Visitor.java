package io.hashimati.utils;


@FunctionalInterface
public interface Visitor<T> {
    public T visit(T t);
}
