package io.hashimati.parsers.engines;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityParsingEngineTest {

    @Test
    void parse() {

        String statement = "    entity fruit {name : String required min(2) max(10) size(12-100) email regex[22lkasd \\.{2,44}]; letter : String; type : String;}";
        EntityParsingEngine entityParsingEngine = new EntityParsingEngine();

        var entitySyntax = entityParsingEngine.parse(statement);
        assertEquals("fruit", entitySyntax.getName());
        System.out.println(entitySyntax.getAttributesDeclarationsStr().get(0));
        assertEquals("name : String required min(2) max(10) size(12-100) email regex[22lkasd \\.{2,44}];", entitySyntax.getAttributesDeclarationsStr().get(0));
        assertEquals("letter : String;", entitySyntax.getAttributesDeclarationsStr().get(1));
        assertEquals("type : String;", entitySyntax.getAttributesDeclarationsStr().get(2));

    }


}