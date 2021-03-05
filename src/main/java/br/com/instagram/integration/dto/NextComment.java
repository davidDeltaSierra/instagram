package br.com.instagram.integration.dto;

import br.com.instagram.integration.pagination.Pagination;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = NextComment.Builder.class)
public class NextComment {
    NextCommentDataDto data;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @With
    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = NextCommentDataDto.Builder.class)
    public static class NextCommentDataDto {
        @JsonAlias("shortcode_media")
        NextCommentMediaDto shortcodeMedia;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }

    @With
    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = NextCommentMediaDto.Builder.class)
    public static class NextCommentMediaDto {
        @JsonAlias("edge_media_to_parent_comment")
        Pagination<Node<Comment>> edgeMediaToParentComment;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
