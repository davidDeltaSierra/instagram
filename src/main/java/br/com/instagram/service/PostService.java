package br.com.instagram.service;

import br.com.instagram.config.Hash;
import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.PostIntegration;
import br.com.instagram.integration.QueryExecutor;
import br.com.instagram.integration.QueryIntegration;
import br.com.instagram.integration.dto.NextPostDto;
import br.com.instagram.integration.dto.PostDto;
import br.com.instagram.integration.pagination.Variable;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Cookie;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {
    private final QueryIntegration queryIntegration;
    private final PostIntegration postIntegration;
    private final AuthService authService;

    public NextPostDto getPosts(String id, String after) {
        Set<Cookie> cookies = getDylan();
        return queryIntegration.executeQuery(
                QueryExecutor.<NextPostDto>builder()
                        .cookies(cookies)
                        .variable(Variable.builder()
                                .id(id)
                                .after(after)
                                .build())
                        .typeReference(new TypeReference<>() {
                        })
                        .hash(Hash.POST)
                        .queryVariables(Set.of("id=" + id))
                        .entryPoint("post")
                        .build()
        );
    }

    public PostDto getPost(String shortcode) {
        Set<Cookie> cookies = getDylan();
        return postIntegration.getPost(cookies, shortcode, executorFactory(shortcode));
    }

    private QueryExecutor<PostDto> executorFactory(String shortcode) {
        return QueryExecutor.<PostDto>builder()
                .queryVariables(Set.of("shortcode=" + shortcode))
                .entryPoint("comment")
                .build();
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
