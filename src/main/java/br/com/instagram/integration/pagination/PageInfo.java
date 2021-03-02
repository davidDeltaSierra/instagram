package br.com.instagram.integration.pagination;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class PageInfo {
    @JsonAlias("has_next_page")
    Boolean hasNextPage;
    @JsonAlias("end_cursor")
    String endCursor;

    @JsonCreator
    public static PageInfo jsonCreator(@JsonProperty("has_next_page") Boolean hasNextPage,
                                       @JsonProperty("end_cursor") String endCursor) {
        return new PageInfo(hasNextPage, endCursor);
    }
}
