package br.com.instagram.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    private static final String SHARED_DATA_REGEX = "(\\bwindow\\._sharedData\\b)(.*?=.*?)(\\{.+})";

    public static Optional<String> getSharedData(String data) {
        Matcher matcher = Pattern.compile(SHARED_DATA_REGEX).matcher(data);
        if (matcher.find()) {
            return Optional.of(matcher.group(3));
        }
        return Optional.empty();
    }
}
