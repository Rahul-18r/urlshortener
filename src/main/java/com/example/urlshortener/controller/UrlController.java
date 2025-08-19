package com.example.urlshortener.controller;

import com.example.urlshortener.Service.UrlShortenerService;
import com.example.urlshortener.dto.ShortenUrlRequest;
import com.example.urlshortener.dto.ShortenUrlResponse;
import com.example.urlshortener.dto.UrlStatsResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {
    @Value("${app.base-url}")
    private String baseUrl;

    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/api/v1/url/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@Valid @RequestBody ShortenUrlRequest request){
        String shortCode = urlShortenerService.
                shortenUrl(  request.url(),
                        request.customAlias(),
                        request.hoursToExpire());
         String fullShortUrl=baseUrl+ shortCode;

         ShortenUrlResponse response=new ShortenUrlResponse(fullShortUrl);

         return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){

            String originalUrl= urlShortenerService.getOriginalUrlAndIncrementClicks(shortCode);

        HttpHeaders headers=new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));

        return  new ResponseEntity<>(headers,HttpStatus.FOUND);
    }

    @GetMapping("/api/v1/url/stats/{shortCode}")
    public ResponseEntity<UrlStatsResponse> getUrlStats(@PathVariable String shortCode) {
        return null;
    }
}
