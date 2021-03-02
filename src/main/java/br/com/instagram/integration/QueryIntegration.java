package br.com.instagram.integration;

import br.com.instagram.config.QueryComponent;
import br.com.instagram.util.HttpHeadersUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.apache.commons.text.StringEscapeUtils.unescapeJson;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryIntegration {
    private final QueryComponent queryComponent;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public <T> T executeQuery(QueryExecutor<T> queryExecutor) {
        queryComponent.setQueryExecutor(queryExecutor);
        ResponseEntity<T> exchange = restTemplate.exchange(
                "https://www.instagram.com/graphql/query/?query_hash={queryHash}&variables={variables}",
                HttpMethod.GET,
                new HttpEntity<>(HttpHeadersUtil.headersWithCookies(queryExecutor.getCookies())),
                ParameterizedTypeReference.forType(queryExecutor.getTypeReference().getType()),
                queryExecutor.getHash().getHash(),
                toJson(queryExecutor.getVariable().withAfter(unescapeJson(queryExecutor.getVariable().getAfter())))
        );
        return exchange.getBody();
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
