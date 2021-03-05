package br.com.instagram.integration.dto;

import br.com.instagram.integration.pagination.NextPageHandler;
import br.com.instagram.integration.pagination.PageInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.With;

@With
@Value
public class Node<T extends NextPageHandler> implements NextPageHandler {
    T node;

    @Override
    public String next(PageInfo pageInfo) {
        return node.next(pageInfo);
    }

    @JsonCreator
    public static <T extends NextPageHandler> Node<T> jsonCreator(@JsonProperty("node") T node) {
        return new Node<>(node);
    }
}
