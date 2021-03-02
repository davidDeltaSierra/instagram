package br.com.instagram.integration.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Variable.Builder.class)
public class Variable {
    String id;
    String shortcode;
    @JsonProperty("tag_name")
    String tagName;
    String after;
    @lombok.Builder.Default
    Integer first = 30;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
