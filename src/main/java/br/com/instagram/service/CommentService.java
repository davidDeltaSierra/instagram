package br.com.instagram.service;

import br.com.instagram.config.Hash;
import br.com.instagram.integration.QueryExecutor;
import br.com.instagram.integration.QueryIntegration;
import br.com.instagram.integration.dto.NextComment;
import br.com.instagram.integration.pagination.Variable;
import br.com.instagram.redis.model.SessionRedis;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final QueryIntegration queryIntegration;

    public NextComment getComments(String shortcode, String after, SessionRedis sessionRedis) {
        return queryIntegration.executeQuery(
                QueryExecutor.<NextComment>builder()
                        .cookies(sessionRedis.getCookies())
                        .variable(Variable.builder()
                                .shortcode(shortcode)
                                .after(after)
                                .build())
                        .typeReference(new TypeReference<>() {
                        })
                        .hash(Hash.COMMENT)
                        .queryVariables(Map.of("shortcode", shortcode))
                        .entryPoint("comment")
                        .build()
        );
    }
}
