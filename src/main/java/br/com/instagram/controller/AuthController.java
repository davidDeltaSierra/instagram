package br.com.instagram.controller;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid LoginDto loginDto) {
        return new ResponseEntity<>(authService.auth(loginDto), HttpStatus.OK);
    }
}
