package io.hashimati;

import io.hashimati.domains.Fruit;
import io.hashimati.handlers.FruitRequestHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FruitRequestHandlerTest {

    private static FruitRequestHandler fruitRequestHandler;
    @BeforeAll
    public static void setupServer() {
        fruitRequestHandler = new FruitRequestHandler();
    }

    @AfterAll
    public static void stopServer() {
        if(fruitRequestHandler != null)
        {
            fruitRequestHandler.getApplicationContext().close();
        }
    }
    @Test
    public void testFruitHandler() {
        Fruit fruit = new Fruit("Grape");
        String fruitResponse = fruitRequestHandler.execute(fruit);
        assertEquals(fruitResponse.contains(fruit.getName()), true);
    }
}
