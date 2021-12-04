package io.hashimati;

import io.hashimati.parsers.EntityParser;
import io.hashimati.parsers.utils.Validator;

public class Main {


    public static void main(String... args)
    {
        System.out.println(new Validator().isBracketBalanced("{[{}]}"));
    }
}
