package br.com.instagram.integration;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.dto.LoginPage;
import br.com.instagram.util.RegexUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthIntegration {
    private final ObjectMapper objectMapper;

    public Map<String, String> auth(LoginDto loginDto, String userAgent) {
        try {
            Document document = Jsoup.connect("https://www.instagram.com/accounts/login/").get();
            String sharedData = RegexUtil.getSharedData(document.data())
                    .orElseThrow(() -> new RuntimeException("Shared data not found in page"));
            LoginPage loginPage = objectMapper.readValue(sharedData, LoginPage.class);
            Connection.Response loginResponse = Jsoup.connect("https://www.instagram.com/accounts/login/ajax/")
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .header("User-Agent", UserAgent.CHROME.getRaw())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("x-csrftoken", loginPage.getConfig().getCsrfToken())
                    .data("username", loginDto.getUsername())
                    .data("enc_password", "#PWD_INSTAGRAM_BROWSER:0:" + System.currentTimeMillis() + ":" + loginDto.getPassword())
                    .execute();
            Map<String, String> loginCookies = loginResponse.cookies();
            if (!loginCookies.containsKey("sessionid")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
            }

            Connection.Response homeResponse = Jsoup.connect("https://www.instagram.com/")
                    .header("User-Agent", UserAgent.CHROME.getRaw())
                    .cookies(loginCookies)
                    .execute();
            Map<String, String> extraCookies = homeResponse.cookies();
            loginCookies.putAll(extraCookies);
            return loginCookies;
        } catch (HttpStatusException ex) {
            HttpStatus resolve = ofNullable(HttpStatus.resolve(ex.getStatusCode()))
                    .orElse(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new ResponseStatusException(resolve, ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
