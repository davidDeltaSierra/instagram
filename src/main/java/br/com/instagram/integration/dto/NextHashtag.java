package br.com.instagram.integration.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = NextHashtag.Builder.class)
public class NextHashtag {
    NextHashtagHashtagDto data;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = NextHashtagHashtagDto.Builder.class)
    public static class NextHashtagHashtagDto {
        Hashtag hashtag;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
