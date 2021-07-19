package by.dvorkin.recording.utils;

import java.util.regex.Pattern;

public class Helper {
    public static Boolean isNumber(String str) {
        if (Pattern.matches("\\d+", str)) {
            return true;
        } else {
            return false;
        }
    }

    public static String setDiskNameFromFilename(String name) {
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(0, i);
        }
        return name;
    }
}
