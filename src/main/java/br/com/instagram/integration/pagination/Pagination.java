package br.com.instagram.integration.pagination;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@With
@Value
@Builder(builderClassName = "Builder")
public class Pagination<T extends NextPageHandler> {
    Long count;
    List<T> edges;
    @JsonAlias("page_info")
    PageInfo pageInfo;

    public String getNextPage() {
        Optional<T> first = isNull(edges) ? Optional.empty() : edges.stream().findFirst();
        if (isNull(pageInfo) || Boolean.FALSE.equals(pageInfo.getHasNextPage()) || first.isEmpty()) {
            return null;
        }
        return first.get().next(pageInfo);
    }

    @JsonCreator
    public static <T extends NextPageHandler> Pagination<T> jsonCreator(@JsonProperty("count") Long count,
                                                                        @JsonProperty("edges") List<T> edges,
                                                                        @JsonProperty("page_info") PageInfo pageInfo) {
        return new Pagination<>(count, edges, pageInfo);
    }
}
