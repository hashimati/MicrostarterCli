package io.hashimati.microcli.utils;

import io.leego.banana.Ansi;
import io.leego.banana.BananaUtils;
import io.leego.banana.Font;

import java.util.*;
import java.util.stream.Collectors;

public class AsciiUtils {
    private static HashMap<String, String> ansiCode = new HashMap<>();
    private static ArrayList<String> ansiSet = new ArrayList<>();
    static {

        ansiSet.add("Black");
        ansiSet.add("Red" );
        ansiSet.add("Green");
        ansiSet.add("Yellow" );
        ansiSet.add("Blue" );
        ansiSet.add("Purple" );
        ansiSet.add("Cyan" );
        ansiSet.add("White");
        ansiSet.add("Black Background" );
        ansiSet.add("Red Background" );
        ansiSet.add("Green Background" );
        ansiSet.add("Yellow Background");
        ansiSet.add("Blue Background" );
        ansiSet.add("Purple Background");
        ansiSet.add("Cyan Background" );
        ansiSet.add("White Background");
        ansiSet.add("Normal");
        ansiSet.add("Bold");
        ansiSet.add("Faint");
        ansiSet.add("Italic");
        ansiSet.add("Underline");
        ansiSet.add("Slow Blink");
        ansiSet.add("Rapid Blink");
        ansiSet.add("Reverse Video");
        ansiSet.add("Conceal");
        ansiSet.add("Crossed Out");
        ansiSet.add("Primary");

        ansiCode.put("Black" , "30");
        ansiCode.put("Red" , "31");
        ansiCode.put("Green" , "32");
        ansiCode.put("Yellow" , "33");
        ansiCode.put("Blue" , "34");
        ansiCode.put("Purple" , "35");
        ansiCode.put("Cyan" , "36");
        ansiCode.put("White" , "37");
        ansiCode.put("Black Background" , "40");
        ansiCode.put("Red Background" , "41");
        ansiCode.put("Green Background" , "42");
        ansiCode.put("Yellow Background" , "43");
        ansiCode.put("Blue Background" , "44");
        ansiCode.put("Purple Background" , "45");
        ansiCode.put("Cyan Background" , "46");
        ansiCode.put("White Background" , "47");
        ansiCode.put("Normal" , "0");
        ansiCode.put("Bold" , "1");
        ansiCode.put("Faint" , "2");
        ansiCode.put("Italic" , "3");
        ansiCode.put("Underline" , "4");
        ansiCode.put("Slow Blink" , "5");
        ansiCode.put("Rapid Blink" , "6");
        ansiCode.put("Reverse Video" , "7");
        ansiCode.put("Conceal" , "8");
        ansiCode.put("Crossed Out" , "9");
        ansiCode.put("Primary" , "10");
    }
    public static List<String> getFontNames(){

        return BananaUtils.fonts()
                .stream()
                .map(x-> x.getName())
                .collect(Collectors.toList());
    }
    public static ArrayList<String> getAnsiStyle(){
        return ansiSet;
    }

    public static String getAnsiCode(String name)
    {

        return ansiCode.get(name);
    }

    public static String getBanner(String name, String fontName, String... ansiStyle)
    {

        Ansi ansi[]  = new Ansi[ansiStyle.length];
        int i = 0;
        for(String x : ansiStyle)
            ansi[i++] =Ansi.get( ansiCode.get(x));
        return BananaUtils.bananaify(name, Font.get(fontName));
      //  return BananaUtils.bananansi(name, Font.get(fontName),ansi);
    }

    public void foo(){


        BananaUtils.bananansi("Hello Bannana", Font.DANCING_FONT, Ansi.BLUE);
        BananaUtils.bananaify("Hello, Banana!", Font.ALLIGATOR2);
    }
}
