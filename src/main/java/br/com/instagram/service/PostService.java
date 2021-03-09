package br.com.instagram.service;

import br.com.instagram.config.Hash;
import br.com.instagram.integration.PostIntegration;
import br.com.instagram.integration.QueryExecutor;
import br.com.instagram.integration.QueryIntegration;
import br.com.instagram.integration.dto.Media;
import br.com.instagram.integration.dto.NextPost;
import br.com.instagram.integration.dto.Post;
import br.com.instagram.integration.dto.Upload;
import br.com.instagram.integration.pagination.Variable;
import br.com.instagram.redis.model.SessionRedis;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final QueryIntegration queryIntegration;
    private final PostIntegration postIntegration;

    public NextPost getPosts(String id, String after, SessionRedis sessionRedis) {
        return queryIntegration.executeQuery(
                QueryExecutor.<NextPost>builder()
                        .cookies(sessionRedis.getCookies())
                        .variable(Variable.builder()
                                .id(id)
                                .after(after)
                                .build())
                        .typeReference(new TypeReference<>() {
                        })
                        .hash(Hash.POST)
                        .queryVariables(Map.of("id", id))
                        .entryPoint("post")
                        .build()
        );
    }

    public Post getPost(String shortcode, SessionRedis sessionRedis, String userAgent) {
        return postIntegration.getPost(sessionRedis.getCookies(), shortcode, executorFactory(shortcode), userAgent);
    }

    public Upload upload(byte[] file, SessionRedis sessionRedis, String contentLength) {
        return postIntegration.upload(file, sessionRedis.getCookies(), contentLength);
    }

    public void upload(SessionRedis sessionRedis, Media media) {
        postIntegration.upload(sessionRedis.getCookies(), media);
    }

    private QueryExecutor<Post> executorFactory(String shortcode) {
        return QueryExecutor.<Post>builder()
                .queryVariables(Map.of("shortcode", shortcode))
                .entryPoint("comment")
                .build();
    }
}
