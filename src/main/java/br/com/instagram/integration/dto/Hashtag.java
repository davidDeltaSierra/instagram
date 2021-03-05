package br.com.instagram.integration.dto;

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
@JsonDeserialize(builder = Hashtag.Builder.class)
public class Hashtag {
    String id;
    String name;

    @JsonAlias("allow_following")
    Boolean allowFollowing;

    @JsonAlias("is_following")
    Boolean isFollowing;

    @JsonAlias("is_top_media_only")
    Boolean isTopMediaOnly;

    @JsonAlias("profile_pic_url")
    String profilePicUrl;

    @JsonAlias("edge_hashtag_to_media")
    Pagination<Node<Post>> edgeHashtagToMedia;

    @JsonAlias("edge_hashtag_to_top_posts")
    Pagination<Node<Post>> edgeHashtagToTopPosts;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
