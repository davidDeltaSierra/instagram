package br.com.instagram.integration;

import br.com.instagram.config.VariableComponent;
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
    private final VariableComponent variableComponent;
    private final ObjectMapper objectMapper;
    private final WebDriver chromeDriver;

    public PostDto getPost(Set<Cookie> cookies, String shortcode) {
        variableComponent.setVariable(Variable.builder().shortcode(shortcode).build());
        WebDriverBridge webDriverBridge = new WebDriverBridge(chromeDriver);
        webDriverBridge.get("https://www.instagram.com");
        cookies.forEach(it -> webDriverBridge.manage().addCookie(it));
        webDriverBridge.get("https://www.instagram.com/p/" + shortcode + "/");
        Object post = webDriverBridge
                .executeScript("return window.__additionalData['/p/" + shortcode + "/']['data']['graphql']['shortcode_media']");
        return objectMapper.convertValue(post, PostDto.class);
    }
}
