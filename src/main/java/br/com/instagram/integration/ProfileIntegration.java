package br.com.instagram.integration;

import br.com.instagram.integration.dto.Profile;
import br.com.instagram.integration.dto.User;
import br.com.instagram.util.HttpHeadersUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class ProfileIntegration {
    private final RestTemplate restTemplate;

    public User profileInfo(Map<String, String> cookies, String username) {
        ResponseEntity<Profile> exchange = restTemplate.exchange(
                "https://www.instagram.com/{username}/?__a=1",
                HttpMethod.GET,
                new HttpEntity<>(HttpHeadersUtil.headersWithCookies(cookies)),
                Profile.class,
                username
        );
        return requireNonNull(exchange.getBody()).getGraphql().getUser();
    }
}
