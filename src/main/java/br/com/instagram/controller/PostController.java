package br.com.instagram.controller;

import br.com.instagram.integration.dto.Media;
import br.com.instagram.integration.dto.NextPost;
import br.com.instagram.integration.dto.Post;
import br.com.instagram.integration.dto.Upload;
import br.com.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController extends AbstractController {
    private final PostService postService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<NextPost> getPosts(@RequestParam String id,
                                             @RequestParam String after) {
        return new ResponseEntity<>(postService.getPosts(id, after, getSessionRedis()), HttpStatus.OK);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Upload> upload(@RequestBody byte[] file,
                                         @RequestHeader("Content-Type") String contentType,
                                         @RequestHeader("Content-Length") String contentLength) {
        return new ResponseEntity<>(postService.upload(file, getSessionRedis(), contentLength), HttpStatus.CREATED);
    }

    @PostMapping(value = "{uploadId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> upload(@PathVariable String uploadId,
                                       @RequestBody @Valid Media media) {
        postService.upload(getSessionRedis(), media.withUploadId(uploadId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "{shortcode}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getPost(@PathVariable String shortcode,
                                        @RequestHeader("User-Agent") String userAgent) {
        return new ResponseEntity<>(postService.getPost(shortcode, getSessionRedis(), userAgent), HttpStatus.OK);
    }
}
