package br.com.instagram.integration.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = NextHashtagDto.Builder.class)
public class NextHashtagDto {
    NextHashtagHashtagDto data;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = NextHashtagHashtagDto.Builder.class)
    public static class NextHashtagHashtagDto {
        HashtagDto hashtag;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
