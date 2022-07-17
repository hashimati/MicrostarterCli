package io.hashimati.parsers.engines;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceParsingEngineTest {

    @Test
    void parse() {

        ServiceParsingEngine serviceParsingEngine = new ServiceParsingEngine();

        var serviceSyntax = serviceParsingEngine.parse("service\t\tFruitService{\n" +
                "\tport 8080;\n\treactive reactor;\n" +
                "\tdatabase microstream;\n"+
                "\tdatabaseName fruitDb;\n"+
                "\t entity fruit { " +
                        "\n\tname:String;\n"+
                        "}\n"+
                        "\t entity Veg { " +
                        "\n\tname:String;\n"+
                        "}\n"+

                "}");


        assertEquals("FruitService", serviceSyntax.getName());
        assertEquals("8080", serviceSyntax.getPort());
        assertEquals("reactor", serviceSyntax.getReactive());
        assertEquals("microstream", serviceSyntax.getDatabase());
        assertEquals("fruitDb", serviceSyntax.getDatabaseName());





    }
}