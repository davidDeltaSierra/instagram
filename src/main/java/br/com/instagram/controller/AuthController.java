package br.com.instagram.controller;

import br.com.instagram.dto.LoginDto;
import br.com.instagram.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid LoginDto loginDto,
                                  @RequestHeader("User-Agent") String userAgent) {
        return new ResponseEntity<>(authService.save(loginDto, userAgent), HttpStatus.OK);
    }
}
