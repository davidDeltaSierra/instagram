package br.com.instagram.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.NotEmpty;

@With
@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Media.Builder.class)
public class Media {
    @lombok.Builder.Default
    @JsonProperty("source_type")
    String sourceType = "library";

    @NotEmpty
    String caption;

    @JsonProperty("upload_id")
    String uploadId;

    @JsonProperty("usertags")
    String userTags;

    @JsonProperty("custom_accessibility_caption")
    String customAccessibilityCaption;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
