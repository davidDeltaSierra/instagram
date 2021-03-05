package br.com.instagram.controller;

import br.com.instagram.redis.model.SessionRedis;
import br.com.instagram.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {
    private AuthService authService;

    protected SessionRedis getSessionRedis() {
        SessionRedis sessionRedis = (SessionRedis) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authService.findById(sessionRedis.getId());
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
