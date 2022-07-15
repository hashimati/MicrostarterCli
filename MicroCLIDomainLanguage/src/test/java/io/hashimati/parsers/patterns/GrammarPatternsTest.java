package io.hashimati.parsers.patterns;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.hashimati.parsers.patterns.GrammarPatterns.ATTRIBUTE_DECLARATION;
import static org.junit.jupiter.api.Assertions.*;



class GrammarPatternsTest {


    @Test
    public void testPatterns(){


        System.out.println("name :String required  min(1) max(21) size(1-22)  ;".matches(ATTRIBUTE_DECLARATION));
    }
}