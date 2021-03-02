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
@JsonDeserialize(builder = UserDto.Builder.class)
public class UserDto implements NextPageHandler {
    String id;

    @JsonAlias("full_name")
    String fullName;

    String biography;

    @JsonAlias("blocked_by_viewer")
    Boolean blockedByViewer;

    @JsonAlias("restricted_by_viewer")
    Boolean restrictedByViewer;

    @JsonAlias("country_block")
    Boolean countryBlock;

    @JsonAlias("external_url")
    String externalUrl;

    @JsonAlias("external_url_linkshimmed")
    String externalUrlLinkShimmed;

    @JsonAlias("follows_viewer")
    Boolean followsViewer;

    @JsonAlias("is_private")
    Boolean isPrivate;

    @JsonAlias("is_verified")
    Boolean isVerified;

    @JsonAlias("profile_pic_url")
    String profilePicUrl;

    String username;

    @JsonAlias("edge_followed_by")
    Pagination<NodeDto<UserDto>> edgeFollowedBy;

    @JsonAlias("edge_follow")
    Pagination<NodeDto<UserDto>> edgeFollow;

    @JsonAlias("edge_owner_to_timeline_media")
    Pagination<NodeDto<PostDto>> timeLineMedia;

    @Override
    public String next(PageInfo pageInfo) {
        return null;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
