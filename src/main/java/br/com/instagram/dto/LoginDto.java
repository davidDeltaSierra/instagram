package br.com.instagram.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.NotEmpty;

@With
@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = LoginDto.Builder.class)
public class LoginDto {
    @NotEmpty
    String username;
    @NotEmpty
    String password;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
