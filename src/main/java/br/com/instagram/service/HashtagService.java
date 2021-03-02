package br.com.instagram.service;

import br.com.instagram.config.Hash;
import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.QueryIntegration;
import br.com.instagram.integration.pagination.Variable;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Cookie;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final QueryIntegration queryIntegration;
    private final AuthService authService;

    public Object getPostsByHashtag(String tagName, String after) {
        Set<Cookie> cookies = getDylan();
        return queryIntegration.executeQuery(
                cookies,
                Variable.builder()
                        .tagName(tagName)
                        .after(after)
                        .build(),
                new TypeReference<>() {
                },
                Hash.HASHTAG
        );
    }

    private Set<Cookie> getDylan() {
        return authService.auth(
                LoginDto.builder()
                        .username("instinto.reflexivo")
                        .password("*******************")
                        .build()
        );
    }
}
