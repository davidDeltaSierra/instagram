package br.com.instagram.controller;

import br.com.instagram.redis.model.SessionRedis;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractController {

    protected SessionRedis getSessionRedis() {
        return (SessionRedis) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
