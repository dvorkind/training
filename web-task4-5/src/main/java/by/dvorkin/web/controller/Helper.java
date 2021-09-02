package by.dvorkin.web.controller;

public class Helper {
    public static String extractPath(String url, String context) {
        int postfixIndex = url.lastIndexOf(".html");
        if (postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        return url;
    }
}
