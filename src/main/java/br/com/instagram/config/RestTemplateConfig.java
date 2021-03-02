package br.com.instagram.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        RestTemplate restTemplate = new RestTemplate(getMessageConverters());
        restTemplate.setInterceptors(List.of(
                (httpRequest, bytes, clientHttpRequestExecution) -> {
                    log.info("Rest Template send new request to: {}", httpRequest.getURI());
                    return clientHttpRequestExecution.execute(httpRequest, bytes);
                }
        ));
        return restTemplate;
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        return List.of(
                new StringHttpMessageConverter(StandardCharsets.UTF_8),
                new MappingJackson2HttpMessageConverter(objectMapper)
        );
    }
}
