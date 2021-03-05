package br.com.instagram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Configuration
@AllArgsConstructor
public class RestTemplateConfig implements WebMvcConfigurer {
    private final ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(getMessageConverters());
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        return List.of(
                new StringHttpMessageConverter(StandardCharsets.UTF_8),
                mappingJackson2HttpMessageConverter(),
                new ByteArrayHttpMessageConverter()
        );
    }

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
                List.of(
                        MediaType.APPLICATION_JSON,
                        MediaType.APPLICATION_FORM_URLENCODED
                )
        );
        return mappingJackson2HttpMessageConverter;
    }
}
