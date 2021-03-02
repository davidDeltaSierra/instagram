package br.com.instagram.service;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.ProfileIntegration;
import br.com.instagram.integration.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Cookie;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileIntegration profileIntegration;
    private final AuthService authService;

    public UserDto profile(String username) {
        Set<Cookie> cookies = getDylan();
        return profileIntegration.profileInfo(cookies, username);
    }

    private Set<Cookie> getDylan() {
        return authService.auth(
                LoginDto.builder()
                        .username("instinto.reflexivo")
                        .password("***************")
                        .build()
        );
    }
}
