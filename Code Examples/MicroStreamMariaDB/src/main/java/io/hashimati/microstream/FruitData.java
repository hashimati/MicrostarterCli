package io.hashimati.microstream;

import io.hashimati.domains.Fruit;
import io.micronaut.core.annotation.Introspected;

import java.util.HashMap;
import io.micronaut.core.annotation.NonNull;

@Introspected
public class FruitData {
    HashMap<String, Fruit> fruits = new HashMap<String, Fruit>();
    @NonNull
    public HashMap<String, Fruit> getFruits(){
        return this.fruits;
    }
}

