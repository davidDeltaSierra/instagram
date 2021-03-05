package br.com.instagram.integration.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = Upload.Builder.class)
public class Upload {
    @JsonAlias("upload_id")
    String uploadId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
