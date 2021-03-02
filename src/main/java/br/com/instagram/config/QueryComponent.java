package br.com.instagram.config;

import br.com.instagram.integration.QueryExecutor;
import br.com.instagram.util.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Set;

@Getter
@Setter
@Component
@RequestScope
public class QueryComponent {
    private QueryExecutor<?> queryExecutor;

    public static QueryExecutor<?> getQueryExecutorFromRequest() {
        QueryComponent queryComponent = BeanUtil.getBean(QueryComponent.class);
        return queryComponent.getQueryExecutor();
    }
}
