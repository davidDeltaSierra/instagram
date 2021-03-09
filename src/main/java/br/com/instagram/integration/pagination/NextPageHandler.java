package br.com.instagram.integration.pagination;

import br.com.instagram.config.ConfigProperties;
import br.com.instagram.config.QueryComponent;
import br.com.instagram.integration.QueryExecutor;

import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public interface NextPageHandler {

    default String baseUrl(PageInfo pageInfo, String entryPoint) {
        return ConfigProperties.getApiBasePath() + "/" + entryPoint + "?after=" + pageInfo.getEndCursor() + "&";
    }

    default String next(PageInfo pageInfo) {
        QueryExecutor<?> queryExecutorFromRequest = QueryComponent.getQueryExecutorFromRequest();
        if (isNotPageable(pageInfo, queryExecutorFromRequest)) {
            return null;
        }
        String baseUrl = baseUrl(pageInfo, queryExecutorFromRequest.getEntryPoint());
        String query = queryExecutorFromRequest
                .getQueryVariables()
                .entrySet()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.joining("&"));
        return baseUrl.concat(query);
    }

    private boolean isNotPageable(PageInfo pageInfo, QueryExecutor<?> queryExecutorFromRequest) {
        return isNull(queryExecutorFromRequest) || queryExecutorFromRequest.getQueryVariables().isEmpty() || !pageInfo.getHasNextPage();
    }
}
