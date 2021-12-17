package io.hashimati.handlers;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import io.hashimati.domains.Fruit;
import io.micronaut.function.aws.MicronautRequestHandler;

public class FruitRequestHandler extends MicronautRequestHandler<Fruit, String> {


    @Override
    public String execute(Fruit input) {
        return "Saving fruit " + input.getName();
    }
}
