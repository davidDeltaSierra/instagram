package br.com.instagram.controller;

import br.com.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam String id,
                                      @RequestParam String after) {
        return new ResponseEntity<>(postService.getPosts(id, after), HttpStatus.OK);
    }

    @GetMapping("{shortcode}")
    public ResponseEntity<?> getPost(@PathVariable String shortcode) {
        return new ResponseEntity<>(postService.getPost(shortcode), HttpStatus.OK);
    }
}
