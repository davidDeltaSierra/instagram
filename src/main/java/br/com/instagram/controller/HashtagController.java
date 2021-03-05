package br.com.instagram.controller;

import br.com.instagram.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hashtag")
@RequiredArgsConstructor
public class HashtagController extends AbstractController {
    private final HashtagService hashTagService;

    @GetMapping
    public ResponseEntity<?> getPostsByHashtag(@RequestParam String tagName,
                                               @RequestParam(required = false) String after) {
        return new ResponseEntity<>(hashTagService.getPostsByHashtag(tagName, after, getSessionRedis()), HttpStatus.OK);
    }
}
