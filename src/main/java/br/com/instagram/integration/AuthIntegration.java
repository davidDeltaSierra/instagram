package br.com.instagram.integration;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.util.WebDriverBridge;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthIntegration {
    private final WebDriver chromeDriver;

    public Set<Cookie> auth(LoginDto loginDto) {
        WebDriverBridge webDriverBridge = new WebDriverBridge(chromeDriver);
        webDriverBridge.get("https://www.instagram.com");
        WebElement loginForm = webDriverBridge
                .awaitElementOrThrowNoSuchElement(By.id("loginForm"), 2, TimeUnit.SECONDS, 5);
        WebElement username = loginForm.findElement(By.name("username"));
        WebElement password = loginForm.findElement(By.name("password"));
        WebElement loginButton = loginForm
                .findElements(By.tagName("button"))
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Login button not found"));

        username.sendKeys(loginDto.getUsername());
        password.sendKeys(loginDto.getPassword());
        loginButton.click();
        Optional<WebElement> slfErrorAlert = webDriverBridge.awaitElementOptional(
                By.id("slfErrorAlert"),
                1,
                TimeUnit.SECONDS,
                10,
                () -> !WebDriverBridge.isDisplayedSuppressStaleElementReferenceException(loginForm)
        );
        if (slfErrorAlert.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, slfErrorAlert.get().getText());
        }
        return webDriverBridge.manage().getCookies();
    }
}
