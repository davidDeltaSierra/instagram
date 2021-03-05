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
@JsonDeserialize(builder = NextPost.Builder.class)
public class NextPost {
    NextPostDataDto data;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = NextPostDataDto.Builder.class)
    public static class NextPostDataDto {
        NextPostUserDto user;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = NextPostUserDto.Builder.class)
    public static class NextPostUserDto {
        @JsonAlias("edge_owner_to_timeline_media")
        Pagination<Node<Post>> edgeOwnerToTimelineMedia;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
