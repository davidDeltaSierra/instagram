package br.com.instagram.service;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.AuthIntegration;
import br.com.instagram.util.CookieBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthIntegration authIntegration;
    private final ObjectMapper objectMapper;

    public Set<Cookie> auth(LoginDto loginDto) {
        Optional<Set<Cookie>> cookieFile = findCookieFileAndValidateSessionId(loginDto.getUsername());
        return cookieFile.orElseGet(() -> getCookiesFromInstagramAndSaveToFile(loginDto));
    }

    private Set<Cookie> getCookiesFromInstagramAndSaveToFile(LoginDto loginDto) {
        Set<Cookie> cookies = authIntegration.auth(loginDto);
        return saveCookieFile(loginDto.getUsername(), cookies);
    }

    private Set<Cookie> saveCookieFile(String username, Set<Cookie> cookies) {
        try (var writer = new BufferedWriter(new FileWriter(generateCookieFileName(username)))) {
            writer.write(objectMapper.writeValueAsString(cookies));
            return cookies;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Optional<Set<Cookie>> findCookieFileAndValidateSessionId(String username) {
        Optional<Set<Cookie>> cookieFile = findCookieFile(username);
        if (cookieFile.isEmpty()) {
            return Optional.empty();
        }
        Optional<Cookie> sessionId = cookieFile.get()
                .stream()
                .filter(it -> it.getName().equals("sessionid"))
                .findFirst();
        if (sessionId.isEmpty()) {
            return Optional.empty();
        }
        LocalDateTime expiry = sessionId.get().getExpiry().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (LocalDateTime.now().isAfter(expiry)) {
            return Optional.empty();
        }
        return cookieFile;
    }

    private Optional<Set<Cookie>> findCookieFile(String username) {
        try {
            Set<Cookie> cookies = objectMapper.readValue(
                    new File(generateCookieFileName(username)),
                    new TypeReference<Set<CookieBuilder>>() {
                    }
            ).stream().map(CookieBuilder::build).collect(Collectors.toSet());
            return Optional.of(cookies);
        } catch (IOException e) {
            log.debug("Cookie file not found for user: {}", username);
            return Optional.empty();
        }
    }

    private String generateCookieFileName(String username) {
        return "src/main/resources/cookies/" + username + ".json";
    }
}
