package br.com.instagram.integration.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Profile.Builder.class)
public class Profile {
    GraphqlDto graphql;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = Profile.GraphqlDto.Builder.class)
    public static class GraphqlDto {
        User user;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
