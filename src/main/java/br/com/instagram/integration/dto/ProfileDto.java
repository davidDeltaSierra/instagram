package br.com.instagram.integration.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = ProfileDto.Builder.class)
public class ProfileDto {
    GraphqlDto graphql;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = ProfileDto.GraphqlDto.Builder.class)
    public static class GraphqlDto {
        UserDto user;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
