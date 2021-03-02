package br.com.instagram.integration;

import br.com.instagram.integration.dto.ProfileDto;
import br.com.instagram.integration.dto.UserDto;
import br.com.instagram.util.HttpHeadersUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Cookie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class ProfileIntegration {
    private final RestTemplate restTemplate;

    public UserDto profileInfo(Set<Cookie> cookies, String username) {
        HttpHeaders httpHeaders = HttpHeadersUtil.headersWithCookies(cookies);
        ResponseEntity<ProfileDto> exchange = restTemplate.exchange(
                "https://www.instagram.com/{username}/?__a=1",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                ParameterizedTypeReference.forType(new TypeReference<ProfileDto>(){}.getType()),
                username
        );
        return requireNonNull(exchange.getBody()).getGraphql().getUser();
    }
}
