package br.com.instagram.service;

import br.com.instagram.config.Hash;
import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.QueryExecutor;
import br.com.instagram.integration.QueryIntegration;
import br.com.instagram.integration.dto.NextHashtagDto;
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

    public NextHashtagDto getPostsByHashtag(String tagName, String after) {
        Set<Cookie> cookies = getDylan();
        return queryIntegration.executeQuery(
                QueryExecutor.<NextHashtagDto>builder()
                        .cookies(cookies)
                        .variable(Variable.builder()
                                .tagName(tagName)
                                .after(after)
                                .first(10)
                                .build())
                        .typeReference(new TypeReference<>() {
                        })
                        .hash(Hash.HASHTAG)
                        .queryVariables(Set.of("tagName=" + tagName))
                        .entryPoint("hashtag")
                        .build()
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
