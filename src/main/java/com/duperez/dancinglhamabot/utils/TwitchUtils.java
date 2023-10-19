package com.duperez.dancinglhamabot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwitchUtils {

    public static int extractTotalPotato(String text) {
        String padrao = "\\[(?:\\+|-)?\\d+ ⇒ ([-\\d,]+)\\]";

        Pattern pattern = Pattern.compile(padrao);

        Matcher matcher = pattern.matcher(text);

        if(matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }


}
