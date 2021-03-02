package br.com.instagram.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = ResponseEntityException.Builder.class)
public class ResponseEntityException {
    Long timestamp;
    String message;
    int status;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
