package br.com.instagram.service;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.integration.AuthIntegration;
import br.com.instagram.redis.model.SessionRedis;
import br.com.instagram.redis.repository.SessionRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final SessionRedisRepository sessionRedisRepository;
    private final AuthIntegration authIntegration;

    public SessionRedis save(LoginDto loginDto, String userAgent) {
        Map<String, String> cookies = authIntegration.auth(loginDto, userAgent);
        return sessionRedisRepository
                .findByUsername(loginDto.getUsername())
                .map(redis -> saveWhitParams(redis, loginDto.getUsername(), cookies))
                .orElseGet(() -> saveWhitParams(new SessionRedis(), loginDto.getUsername(), cookies));
    }

    public SessionRedis findById(String id) {
        return sessionRedisRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found"));
    }

    private SessionRedis saveWhitParams(SessionRedis sessionRedis,
                                        String username,
                                        Map<String, String> cookies) {
        sessionRedis.setUsername(username);
        sessionRedis.setCookies(cookies);
        log.info("Save SessionRedis: {}", sessionRedis);
        return sessionRedisRepository.save(sessionRedis);
    }
}
