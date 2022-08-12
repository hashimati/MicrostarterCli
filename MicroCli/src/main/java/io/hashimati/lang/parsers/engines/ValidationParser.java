package io.hashimati.lang.parsers.engines;

import io.vavr.Tuple2;

public class ValidationParser{

    public static String getMinMax(String sentence)
    {
        return sentence.toLowerCase()
                .replace("min", "")
                .replace("max", "")
                .replaceFirst("\\(", "")
                .replaceFirst("\\)", "")
                .trim();
    }

    public static Tuple2<String, String> parseSize(String sentence)
    {
        var minMax =  sentence.toLowerCase()
                .replace("size", "")
                .replaceFirst("\\(", "")
                .replaceFirst("\\)", "")
                .trim()
                .split("-");

        return new Tuple2<String, String>(minMax[0].trim(), minMax[1].trim());

    }

    public static String getRegex(String sentence)
    {
        return sentence.substring(0, sentence.length()-1)
                .trim()
                .replaceFirst("regex", "")
                .replaceFirst("\\(", "");

    }

}
