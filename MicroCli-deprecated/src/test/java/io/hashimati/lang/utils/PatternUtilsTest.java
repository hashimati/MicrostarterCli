package io.hashimati.lang.utils;

import io.hashimati.lang.parsers.patterns.GrammarPatterns;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatternUtilsTest {



    @Test
    void getPatternsFromText() {

        String content = "service Fruit{\n" +
                "\tport 8080; \n" +
                "\treactive reactor; \n" +
                "\tbuild gradle; \n" +
                "\tdatabase h2; \n" +
                "\tlanguage java; \n" +
                "\tdao jdbc; \n" +
                "\tmigrationTool liquibase; \n" +
                "\tannotation micronaut; \n" +
                "\ttracing jaeger; \n" +
                "\ttestFramework junit; \n" +
                "\n" +
                "\tentity Fruit {\n" +
                "\t\tname:String; \n" +
                "\t\tquantity: Integer; \n" +
                "\t}\n" +
                "}";

        List<String> ps = PatternUtils.getPatternsFromText("\\s*[^\\w]*entity\\s+\\w+\\s*\\{[\\s*\\w*\\:\\;\\-\\(\\)]*}\\s*", content);

        assertTrue(ps.size()>0);


        assertTrue("OneToOne A(sf) to B(lksf);".matches(GrammarPatterns.relationShipSyntax));
        assertTrue("OneToMany A(ff) to C(we);".matches(GrammarPatterns.relationShipSyntax));
        assertTrue("ManyToMany A(ff) to C(we);".matches(GrammarPatterns.relationShipSyntax));
    }
}