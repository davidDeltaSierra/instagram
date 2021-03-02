package br.com.instagram.integration.pagination;

import br.com.instagram.config.ConfigProperties;
import br.com.instagram.config.QueryComponent;
import br.com.instagram.integration.QueryExecutor;

import static java.util.Objects.isNull;

public interface NextPageHandler {

    default String next(PageInfo pageInfo) {
        QueryExecutor<?> queryExecutorFromRequest = QueryComponent.getQueryExecutorFromRequest();
        if (isNotPageable(pageInfo, queryExecutorFromRequest)) {
            return null;
        }
        String url = ConfigProperties.getApiBasePath() + "/" + queryExecutorFromRequest.getEntryPoint() + "?after=" + pageInfo.getEndCursor() + "&";
        String query = String.join("&", queryExecutorFromRequest.getQueryVariables());
        return url.concat(query);
    }

    private boolean isNotPageable(PageInfo pageInfo, QueryExecutor<?> queryExecutorFromRequest) {
        return isNull(queryExecutorFromRequest) || queryExecutorFromRequest.getQueryVariables().isEmpty() || !pageInfo.getHasNextPage();
    }
}
