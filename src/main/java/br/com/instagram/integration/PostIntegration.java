package br.com.instagram.integration;

import br.com.instagram.config.QueryComponent;
import br.com.instagram.integration.dto.Media;
import br.com.instagram.integration.dto.MediaPage;
import br.com.instagram.integration.dto.Post;
import br.com.instagram.integration.dto.Upload;
import br.com.instagram.util.HttpHeadersUtil;
import br.com.instagram.util.RegexUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostIntegration {
    private final QueryComponent queryComponent;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public <T> Post getPost(Map<String, String> cookies, String shortcode, QueryExecutor<T> queryExecutor, String userAgent) {
        queryComponent.setQueryExecutor(queryExecutor);
        try {
            Connection connect = Jsoup
                    .connect("https://www.instagram.com/p/" + shortcode + "/")
                    .header("User-Agent", UserAgent.CHROME.getRaw());
            cookies.entrySet().forEach(it -> connect.cookie("Cookie", it.toString()));
            String sharedData = RegexUtil.getSharedData(connect.get().data())
                    .orElseThrow(() -> new RuntimeException("Shared data not found in page"));
            MediaPage mediaPage = objectMapper.readValue(sharedData, MediaPage.class);
            return mediaPage.getEntryData()
                    .getPostPage()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Register not found"))
                    .getGraphql()
                    .getShortcodeMedia();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Upload upload(byte[] file, Map<String, String> cookies, String contentLength) {
        long timeMillis = System.currentTimeMillis();
        String name = "fb_uploader_" + timeMillis;
        HttpHeaders httpHeaders = HttpHeadersUtil.headersWithCookies(cookies);
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        httpHeaders.add("User-Agent", UserAgent.CHROME.getRaw());
        httpHeaders.add("Offset", "0");
        httpHeaders.add("X-Entity-Name", name);
        httpHeaders.add("X-Entity-Length", contentLength);
        httpHeaders.add("X-Entity-Type", "image/jpeg");
        httpHeaders.add("X-Instagram-Rupload-Params", "{\"media_type\":1,\"upload_id\":\"" + timeMillis + "\",\"upload_media_height\":1080,\"upload_media_width\":1080}");
        ResponseEntity<Upload> exchange = restTemplate.exchange(
                "https://i.instagram.com/rupload_igphoto/" + name,
                HttpMethod.POST,
                new HttpEntity<>(file, httpHeaders),
                Upload.class
        );
        return exchange.getBody();
    }

    public void upload(Map<String, String> cookies, Media media) {
        log.info("Media: {}", media);
        HttpHeaders httpHeaders = HttpHeadersUtil.headersWithCookies(cookies);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add("User-Agent", UserAgent.CHROME.getRaw());
        httpHeaders.add("X-Csrftoken", cookies.get("csrftoken"));
        ResponseEntity<Void> exchange = restTemplate.exchange(
                "https://i.instagram.com/api/v1/media/configure/",
                HttpMethod.POST,
                new HttpEntity<>(media, httpHeaders),
                Void.class
        );
        log.info("Upload: {}", exchange);
    }
}
