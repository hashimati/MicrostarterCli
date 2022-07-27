package io.hashimati.lang.parsers.engines;

import io.hashimati.lang.syntax.AttributeDeclarationSyntax;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AttributeParsingEngineTest {




    @Test
    public void parsingAttributeDeclarationTest(){

        String sentence = "    fruit : String required notnull regex(.\\w+ .{*,+});";
        AttributeParsingEngine attributeParsingEngine  = new AttributeParsingEngine();
        AttributeDeclarationSyntax attributeDeclarationSyntax = attributeParsingEngine.parse(sentence);
        assertEquals("fruit", attributeDeclarationSyntax.getName());
        assertEquals("String", attributeDeclarationSyntax.getType());

        List<String> expectedConstraint = Arrays.asList(new String[]{"required", "notnull","regex(.\\w+ .{*,+})"});
        List<String> actualConstrains = Arrays.asList(attributeDeclarationSyntax.getConstraints().toArray(new String[]{}));
        Collections.sort(expectedConstraint);
        Collections.sort(actualConstrains);
        assertArrayEquals(expectedConstraint.toArray() , actualConstrains.toArray());
        
    }

}