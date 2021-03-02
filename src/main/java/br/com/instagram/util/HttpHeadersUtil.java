package br.com.instagram.util;

import org.openqa.selenium.Cookie;
import org.springframework.http.HttpHeaders;

import java.util.Set;

public class HttpHeadersUtil {
    public static HttpHeaders headersWithCookies(Set<Cookie> cookies) {
        HttpHeaders httpHeaders = new HttpHeaders();
        cookies.forEach(cookie -> httpHeaders.add(HttpHeaders.COOKIE, cookie.toString()));
        return httpHeaders;
    }
}
