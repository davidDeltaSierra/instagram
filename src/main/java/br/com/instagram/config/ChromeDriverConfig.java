package br.com.instagram.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PreDestroy;

@Component
@RequestScope
public class ChromeDriverConfig {
    private ChromeDriver chromeDriver;

    static {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
    }

    @Bean
    @RequestScope
    public ChromeDriver chromeDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
                "--headless",
                "--disable-extensions",
                "--disable-gpu",
                "--disable-dev-shm-usage"
        );
        chromeDriver = new ChromeDriver(chromeOptions);
        return chromeDriver;
    }

    @PreDestroy
    public void dispose() {
        chromeDriver.quit();
    }
}
