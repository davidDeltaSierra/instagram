package br.com.instagram.integration;

import br.com.instagram.config.QueryComponent;
import br.com.instagram.integration.dto.PostDto;
import br.com.instagram.integration.pagination.Variable;
import br.com.instagram.util.WebDriverBridge;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostIntegration {
    private final QueryComponent queryComponent;
    private final ObjectMapper objectMapper;
    private final WebDriver chromeDriver;

    public <T> PostDto getPost(Set<Cookie> cookies, String shortcode, QueryExecutor<T> queryExecutor) {
        queryComponent.setQueryExecutor(queryExecutor);
        WebDriverBridge webDriverBridge = new WebDriverBridge(chromeDriver);
        webDriverBridge.get("https://www.instagram.com");
        cookies.forEach(it -> webDriverBridge.manage().addCookie(it));
        webDriverBridge.get("https://www.instagram.com/p/" + shortcode + "/");
        Object post = webDriverBridge
                .executeScript("return window.__additionalData['/p/" + shortcode + "/']['data']['graphql']['shortcode_media']");
        return objectMapper.convertValue(post, PostDto.class);
    }
}
