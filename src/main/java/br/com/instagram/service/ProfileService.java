package br.com.instagram.service;

import br.com.instagram.integration.ProfileIntegration;
import br.com.instagram.integration.dto.User;
import br.com.instagram.redis.model.SessionRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileIntegration profileIntegration;

    public User profile(String username, SessionRedis sessionRedis) {
        return profileIntegration.profileInfo(sessionRedis.getCookies(), username);
    }
}
