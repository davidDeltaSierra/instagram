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
@JsonDeserialize(builder = Post.Builder.class)
public class Post implements NextPageHandler {
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

    User owner;

    @JsonAlias({"edge_media_to_comment", "edge_media_to_parent_comment"})
    Pagination<Node<User>> edgeMediaToComment;

    @JsonAlias({"edge_liked_by", "edge_media_preview_like"})
    Pagination<Node<User>> edgeLikedBy;

    @JsonAlias("edge_media_to_caption")
    Pagination<Node<Comment>> edgeMediaToCaption;

    @JsonAlias("edge_media_to_parent_comment")
    Pagination<Node<Comment>> parentComment;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
