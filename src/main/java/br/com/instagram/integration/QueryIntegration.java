package br.com.instagram.integration;

import br.com.instagram.config.Hash;
import br.com.instagram.config.VariableComponent;
import br.com.instagram.integration.pagination.Variable;
import br.com.instagram.util.HttpHeadersUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.apache.commons.text.StringEscapeUtils.unescapeJson;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryIntegration {
    private final VariableComponent variableComponent;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public <T> T executeQuery(Set<Cookie> cookies, Variable variable, TypeReference<T> typeReference, Hash hash) {
        Variable processVariable = variable.withAfter(unescapeJson(variable.getAfter()));
        variableComponent.setVariable(processVariable);
        ResponseEntity<T> exchange = restTemplate.exchange(
                "https://www.instagram.com/graphql/query/?query_hash={queryHash}&variables={variables}",
                HttpMethod.GET,
                new HttpEntity<>(HttpHeadersUtil.headersWithCookies(cookies)),
                ParameterizedTypeReference.forType(typeReference.getType()),
                hash.getHash(),
                toJson(processVariable)
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
