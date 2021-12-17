package io.hashimati.domains;


import io.micronaut.core.annotation.Introspected;

import java.util.Objects;

@Introspected
public class Fruit {

    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    public Fruit() {
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return Objects.equals(name, fruit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
