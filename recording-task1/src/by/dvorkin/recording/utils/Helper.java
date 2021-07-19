package by.dvorkin.recording.utils;

import java.util.regex.Pattern;

public class Helper {
    public static Boolean isNumber(String str) {
        return Pattern.matches("\\d+", str);
    }

    public static String extractDiskNameFromFilename(String name) {
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(0, i);
        }
        return name;
    }
}
