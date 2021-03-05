package br.com.instagram.controller;

import br.com.instagram.integration.dto.User;
import br.com.instagram.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController extends AbstractController {
    private final ProfileService profileService;

    @GetMapping(value = "{username}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> profileView(@PathVariable String username) {
        return new ResponseEntity<>(profileService.profile(username, getSessionRedis()), HttpStatus.OK);
    }
}
