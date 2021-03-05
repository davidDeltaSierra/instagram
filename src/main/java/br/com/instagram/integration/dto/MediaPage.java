package br.com.instagram.integration.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = MediaPage.Builder.class)
public class MediaPage {
    @JsonAlias("entry_data")
    PostPage entryData;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = PostPage.Builder.class)
    public static class PostPage {
        @JsonAlias("PostPage")
        List<Graphql> postPage;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = Graphql.Builder.class)
    public static class Graphql {
        Media graphql;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }

    @Value
    @lombok.Builder(builderClassName = "Builder")
    @JsonDeserialize(builder = Media.Builder.class)
    public static class Media {
        @JsonAlias("shortcode_media")
        Post shortcodeMedia;

        @JsonPOJOBuilder(withPrefix = "")
        public static class Builder {
        }
    }
}
