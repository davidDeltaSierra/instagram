package br.com.instagram.integration.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = LoginPage.Builder.class)
public class LoginPage {
    ConfigDto config;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = ConfigDto.Builder.class)
    public static class ConfigDto {
        @JsonAlias("csrf_token")
        String csrfToken;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
