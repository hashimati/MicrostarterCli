package io.hashimati.parsers.utils;

import java.util.HashMap;
import java.util.Stack;

public class Validator {
    public static boolean isBracketBalanced(String myString){
        Stack<Character> b = new Stack<>();
        HashMap<Character, Character> brackets = new HashMap<>(){{
            put('[',']');
            put('{','}');
            put('(',')');
        }};
        for(char i : myString.toCharArray())
        {
            if(brackets.keySet().contains(i))
            {
                b.push(i);
            }
            else
            {
                if(brackets.get(b.peek()).equals(i)){
                    b.pop();
                }
                else {
                    return false;
                }
            }
        }
        return b.isEmpty();
    }
}
