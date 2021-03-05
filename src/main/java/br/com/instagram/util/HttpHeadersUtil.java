package br.com.instagram.util;

import org.springframework.http.HttpHeaders;

import java.util.Map;

public class HttpHeadersUtil {
    public static HttpHeaders headersWithCookies(Map<String, String> cookies) {
        HttpHeaders httpHeaders = new HttpHeaders();
        cookies.entrySet().forEach(cookie -> httpHeaders.add(HttpHeaders.COOKIE, cookie.toString()));
        return httpHeaders;
    }
}
