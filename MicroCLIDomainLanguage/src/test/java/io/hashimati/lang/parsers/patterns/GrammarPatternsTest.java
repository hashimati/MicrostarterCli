package io.hashimati.lang.parsers.patterns;

import org.junit.jupiter.api.Test;

import static io.hashimati.lang.parsers.patterns.GrammarPatterns.FULL_ATTRIBUTE_DECLARATION;


class GrammarPatternsTest {


    @Test
    public void testPatterns(){
        System.out.println("name :String required  min(1) max(21) size(1-22);".matches(FULL_ATTRIBUTE_DECLARATION));

    }

}