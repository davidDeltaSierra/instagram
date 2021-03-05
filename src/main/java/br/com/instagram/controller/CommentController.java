package br.com.instagram.controller;

import br.com.instagram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
public class CommentController extends AbstractController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getComments(@RequestParam String shortcode,
                                         @RequestParam String after) {
        return new ResponseEntity<>(commentService.getComments(shortcode, after, getSessionRedis()), HttpStatus.OK);
    }
}
