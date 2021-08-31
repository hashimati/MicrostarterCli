package io.hashimati.microcli.utils;

import io.leego.banana.BananaUtils;
import io.leego.banana.Font;

import java.util.List;
import java.util.stream.Collectors;

public class AsciiUtils {
    public static List<String> getFontNames(){

        return BananaUtils.fonts()
                .stream()
                .map(x-> x.getName())
                .collect(Collectors.toList());
    }
    public static String getBanner(String name, String fontName)
    {
        return BananaUtils.bananaify(name, Font.get(fontName));
    }
    public void foo(){
        BananaUtils.bananaify("Hello, Banana!", Font.ALLIGATOR2);
    }
}
