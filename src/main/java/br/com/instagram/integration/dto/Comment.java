package br.com.instagram.integration.dto;

import br.com.instagram.integration.pagination.NextPageHandler;
import br.com.instagram.integration.pagination.Pagination;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Comment.Builder.class)
public class Comment implements NextPageHandler {
    String id;

    @JsonAlias("created_at")
    Long createdAt;

    @JsonAlias("viewer_has_liked")
    Boolean viewerHasLiked;

    User owner;
    String text;

    @JsonAlias("edge_liked_by")
    Pagination<Node<User>> edgeLikedBy;

    @JsonAlias("edge_threaded_comments")
    Pagination<Node<Comment>> edgeThreadedComments;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
