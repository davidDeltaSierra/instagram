package br.com.instagram.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import org.openqa.selenium.Cookie;

import java.util.Date;

@ToString
public class CookieBuilder {
    private String name;
    private String value;
    private String path;
    private String domain;
    private Date expiry;
    private Boolean secure;
    private Boolean httpOnly;

    private CookieBuilder() {

    }

    private CookieBuilder(String name, String value, String path, String domain, Date expiry, Boolean secure, Boolean httpOnly) {
        this.name = name;
        this.value = value;
        this.path = path;
        this.domain = domain;
        this.expiry = expiry;
        this.secure = secure;
        this.httpOnly = httpOnly;
    }

    public CookieBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CookieBuilder value(String value) {
        this.value = value;
        return this;
    }

    public CookieBuilder path(String path) {
        this.path = path;
        return this;
    }

    public CookieBuilder domain(String domain) {
        this.domain = domain;
        return this;
    }

    public CookieBuilder expiry(Date expiry) {
        this.expiry = expiry;
        return this;
    }

    public CookieBuilder secure(Boolean secure) {
        this.secure = secure;
        return this;
    }

    public CookieBuilder name(Boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    public Cookie build() {
        return new Cookie(name, value, domain, path, expiry, secure, httpOnly);
    }

    @JsonCreator
    public static CookieBuilder jsonCreator(@JsonProperty("name") String name,
                                            @JsonProperty("value") String value,
                                            @JsonProperty("path") String path,
                                            @JsonProperty("domain") String domain,
                                            @JsonProperty("expiry") Date expiry,
                                            @JsonProperty("secure") Boolean secure,
                                            @JsonProperty("httpOnly") Boolean httpOnly) {
        return new CookieBuilder(name, value, path, domain, expiry, secure, httpOnly);
    }
}
