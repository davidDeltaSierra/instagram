package br.com.instagram.integration.dto;

import br.com.instagram.config.ConfigProperties;
import br.com.instagram.config.VariableComponent;
import br.com.instagram.integration.pagination.NextPageHandler;
import br.com.instagram.integration.pagination.PageInfo;
import br.com.instagram.integration.pagination.Pagination;
import br.com.instagram.integration.pagination.Variable;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import static java.util.Objects.isNull;

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

    @Override
    public String next(PageInfo pageInfo) {
        Variable variable = VariableComponent.getVariableFromRequest();
        if (isNull(variable)) {
            return null;
        }
        return String.format("%s/comment?shortcode=%s&after=%s", ConfigProperties.getApiBasePath(), variable.getShortcode(), pageInfo.getEndCursor());
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
