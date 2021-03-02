package br.com.instagram.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

@Slf4j
@Getter
@RequiredArgsConstructor
public class WebDriverBridge implements AutoCloseable {
    public static final String XPATH_CHILD = "./child::*";
    private final WebDriver driver;

    public void get(String s) {
        driver.get(s);
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public WebDriver.Options manage() {
        return driver.manage();
    }

    public WebElement awaitElementOrThrowNoSuchElement(By by, long time, TimeUnit timeUnit, int maxAttempt) {
        return awaitElementOrThrowNoSuchElement(by, time, timeUnit, maxAttempt, null);
    }

    public WebElement awaitElementOrThrowNoSuchElement(By by, long time, TimeUnit timeUnit, int maxAttempt, AwaitElement awaitElement) {
        Optional<WebElement> element = awaitElementOptional(by, time, timeUnit, maxAttempt, awaitElement);
        if (element.isEmpty()) {
            throw new NoSuchElementException("Element not found in awaitElementOptional strategy");
        }
        return element.get();
    }

    public Optional<WebElement> awaitElementOptional(By by, long time, TimeUnit timeUnit, int maxAttempt) {
        return awaitElementOptional(by, time, timeUnit, maxAttempt, null);
    }

    public Optional<WebElement> awaitElementOptional(By by, long time, TimeUnit timeUnit, int maxAttempt, AwaitElement awaitElement) {
        Optional<WebElement> element = findElementOptional(by);
        while (element.isEmpty() && maxAttempt > 0 && (isNull(awaitElement) || !awaitElement.stop())) {
            log.debug("Try find element by: {}, attempt: {}", by, maxAttempt);
            driver.manage().timeouts().implicitlyWait(time, timeUnit);
            element = findElementOptional(by);
            maxAttempt--;
        }
        return element;
    }

    public Optional<WebElement> findElementOptional(By by) {
        try {
            return Optional.of(driver.findElement(by));
        } catch (NoSuchElementException ex) {
            log.debug("NoSuchElementException in by: {}", by);
            return Optional.empty();
        }
    }

    @Override
    public void close() {
        driver.quit();
    }

    public static boolean isDisplayedSuppressStaleElementReferenceException(WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (StaleElementReferenceException ex) {
            log.debug("StaleElementReferenceException: {}", webElement);
            return false;
        }
    }

    public Object executeScript(String s, Object... objects) {
        return ((JavascriptExecutor) driver).executeScript(s, objects);
    }
}
