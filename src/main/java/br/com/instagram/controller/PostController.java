package br.com.instagram.controller;

import br.com.instagram.integration.dto.Media;
import br.com.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController extends AbstractController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam String id,
                                      @RequestParam String after) {
        return new ResponseEntity<>(postService.getPosts(id, after, getSessionRedis()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> upload(@RequestBody byte[] file,
                                    @RequestHeader("Content-Type") String contentType,
                                    @RequestHeader("Content-Length") String contentLength) {
        return new ResponseEntity<>(postService.upload(file, getSessionRedis(), contentType, contentLength), HttpStatus.CREATED);
    }

    @PostMapping("{uploadId}")
    public ResponseEntity<?> upload(@PathVariable String uploadId,
                                    @RequestBody @Valid Media media) {
        postService.upload(getSessionRedis(), media.withUploadId(uploadId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{shortcode}")
    public ResponseEntity<?> getPost(@PathVariable String shortcode,
                                     @RequestHeader("User-Agent") String userAgent) {
        return new ResponseEntity<>(postService.getPost(shortcode, getSessionRedis(), userAgent), HttpStatus.OK);
    }
}
