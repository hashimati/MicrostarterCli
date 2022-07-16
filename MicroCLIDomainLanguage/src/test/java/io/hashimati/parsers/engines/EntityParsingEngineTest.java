package io.hashimati.parsers.engines;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityParsingEngineTest {

    @Test
    void parsingEntityTest() {

        String statement = "    entity fruit {  name : String required min(2) max(10) size(12-100) email regex(22lkasd \\.{2,44}); letter : String; type : String; pagination; records;}";
        EntityParsingEngine entityParsingEngine = new EntityParsingEngine();

        var entitySyntax = entityParsingEngine.parse(statement);
        assertEquals("fruit", entitySyntax.getName());
        System.out.println(entitySyntax.getAttributesDeclarationsStr().get(0));
        assertEquals("name : String required min(2) max(10) size(12-100) email regex(22lkasd \\.{2,44});", entitySyntax.getAttributesDeclarationsStr().get(0));
        assertEquals("letter : String;", entitySyntax.getAttributesDeclarationsStr().get(1));
        assertEquals("type : String;", entitySyntax.getAttributesDeclarationsStr().get(2));
        assertTrue(entitySyntax.isPagination());
        assertTrue(entitySyntax.isRecords());

        var attributeDeclarationSyntax = entitySyntax.getAttributesDeclarations().get(0);
        List<String> expectedConstraint = Arrays.asList(new String[]{"required","min(2)","max(10)","size(12-100)","email","regex(22lkasd \\.{2,44})"});
        Collections.sort(expectedConstraint);
        System.out.println("expected Constraints: "+ expectedConstraint);
        List<String> actualConstrains = Arrays.asList(attributeDeclarationSyntax.getConstraints().toArray(new String[]{}));


        Collections.sort(actualConstrains);
        System.out.println("Actual Constraints: "+ actualConstrains);
        assertArrayEquals(expectedConstraint.toArray() , actualConstrains.toArray());


    }


}