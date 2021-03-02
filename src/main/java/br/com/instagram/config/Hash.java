package br.com.instagram.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Hash {
    POST("003056d32c2554def87228bc3fd9668a"),
    COMMENT("bc3296d1ce80a24b1b6e40b1e72903f5"),
    HASHTAG("9b498c08113f1e09617a1703c22b2f32");

    private final String hash;
}
