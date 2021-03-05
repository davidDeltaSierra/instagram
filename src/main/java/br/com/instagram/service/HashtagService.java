package br.com.instagram.service;

import br.com.instagram.config.Hash;
import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.QueryExecutor;
import br.com.instagram.integration.QueryIntegration;
import br.com.instagram.integration.dto.NextHashtag;
import br.com.instagram.integration.pagination.Variable;
import br.com.instagram.redis.model.SessionRedis;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final QueryIntegration queryIntegration;
    private final AuthService authService;

    public NextHashtag getPostsByHashtag(String tagName, String after, SessionRedis sessionRedis) {
        return queryIntegration.executeQuery(
                QueryExecutor.<NextHashtag>builder()
                        .cookies(sessionRedis.getCookies())
                        .variable(Variable.builder()
                                .tagName(tagName)
                                .after(after)
                                .first(10)
                                .build())
                        .typeReference(new TypeReference<>() {
                        })
                        .hash(Hash.HASHTAG)
                        .queryVariables(Map.of("tagName", tagName))
                        .entryPoint("hashtag")
                        .build()
        );
    }
}
