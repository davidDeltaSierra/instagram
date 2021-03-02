package br.com.instagram.integration.dto;

import br.com.instagram.integration.pagination.NextPageHandler;
import br.com.instagram.integration.pagination.PageInfo;
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
@JsonDeserialize(builder = CommentDto.Builder.class)
public class CommentDto implements NextPageHandler {
    String id;

    @JsonAlias("created_at")
    Long createdAt;

    @JsonAlias("viewer_has_liked")
    Boolean viewerHasLiked;

    UserDto owner;
    String text;

    @JsonAlias("edge_liked_by")
    Pagination<NodeDto<UserDto>> edgeLikedBy;

    @JsonAlias("edge_threaded_comments")
    Pagination<NodeDto<CommentDto>> edgeThreadedComments;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
