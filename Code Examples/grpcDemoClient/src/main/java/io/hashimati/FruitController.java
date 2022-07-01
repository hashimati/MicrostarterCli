package io.hashimati;


import io.hashimati.clients.FruitClient;
import io.hashimati.domains.Fruit;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller
public class FruitController {

    @Inject
    FruitServiceGrpc.FruitServiceStub fruitServiceStub;
    @Get
    public Fruit save()
    {

    }
}
