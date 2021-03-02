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
@JsonDeserialize(builder = PostDto.Builder.class)
public class PostDto implements NextPageHandler {
    String id;

    String shortcode;

    @JsonAlias("display_url")
    String displayUrl;

    @JsonAlias("video_url")
    String videoUrl;

    @JsonAlias("is_video")
    Boolean isVideo;

    @JsonAlias("video_play_count")
    Long videoPlayCount;

    @JsonAlias("video_duration")
    Long videoDuration;

    @JsonAlias("accessibility_caption")
    String accessibilityCaption;

    @JsonAlias("comments_disabled")
    Boolean commentsDisabled;

    UserDto owner;

    @JsonAlias({"edge_media_to_comment", "edge_media_to_parent_comment"})
    Pagination<NodeDto<UserDto>> edgeMediaToComment;

    @JsonAlias({"edge_liked_by", "edge_media_preview_like"})
    Pagination<NodeDto<UserDto>> edgeLikedBy;

    @JsonAlias("edge_media_to_caption")
    Pagination<NodeDto<CommentDto>> edgeMediaToCaption;

    @JsonAlias("edge_media_to_parent_comment")
    Pagination<NodeDto<CommentDto>> parentComment;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
