package br.com.instagram.controller;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.redis.model.SessionRedis;
import br.com.instagram.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SessionRedis> auth(@RequestBody @Valid LoginDto loginDto,
                                             @RequestHeader("User-Agent") String userAgent) {
        return new ResponseEntity<>(authService.save(loginDto, userAgent), HttpStatus.OK);
    }
}
