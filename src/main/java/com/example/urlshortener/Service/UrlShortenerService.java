package com.example.urlshortener.Service;

import com.example.urlshortener.dto.UrlStatsResponse;
import com.example.urlshortener.exception.AliasAlreadyExistsException;
import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class UrlShortenerService {
    private static final String BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final UrlMappingRepository urlMappingRepository;

    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @Transactional
    public String shortenUrl(String originalUrl, String customAlias,Integer hoursToExpire) {
        if (StringUtils.hasText(customAlias)) {
            if (urlMappingRepository.findByShortCode(customAlias).isPresent()) {

                throw new AliasAlreadyExistsException("Alias '" + customAlias + "' is already in use.");
            }
            UrlMapping newUrlMapping = new UrlMapping();
            newUrlMapping.setOriginalUrl(originalUrl);
            newUrlMapping.setCreationDate(LocalDateTime.now());
            newUrlMapping.setShortCode(customAlias);
            if (hoursToExpire != null) {
                newUrlMapping.setExpirationDate(LocalDateTime.now().plusHours(hoursToExpire));
            }
            urlMappingRepository.save(newUrlMapping);
            return customAlias;


        } else {


            UrlMapping urlMapping = new UrlMapping();
            urlMapping.setOriginalUrl(originalUrl);
            urlMapping.setCreationDate(LocalDateTime.now());

            UrlMapping savedEntity = urlMappingRepository.save(urlMapping);

            String shortCode = encodeBase62(savedEntity.getId());
            savedEntity.setShortCode(shortCode);

            urlMappingRepository.save(savedEntity);

            return shortCode;
        }
    }
    public String encodeBase62(Long number){
        if(number == 0){
            return String.valueOf(BASE62_CHARS.charAt(0));
        }
        StringBuilder sb=new StringBuilder();
        long num=number;
        while(num>0){
            int remainder=(int) (num%62);
            sb.append(BASE62_CHARS.charAt(remainder));
            num/=62;
        }
        return sb.reverse().toString();
    }
    public String getOriginalUrlAndIncrementClicks(String shortCode) {

        UrlMapping urlMapping= urlMappingRepository.
                findByShortCode(shortCode).orElseThrow(()
                        ->new UrlNotFoundException("URL not found for short code: "+shortCode));

        if (urlMapping.getExpirationDate() != null && urlMapping.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new UrlNotFoundException("This link has expired and is no longer active.");
        }
            urlMapping.setClickCount(urlMapping.getClickCount() + 1);
        urlMappingRepository.save(urlMapping);

        return urlMapping.getOriginalUrl();
    }
    public UrlStatsResponse getStats(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("No statistics found for short code: " + shortCode));


        String fullShortUrl = "http://localhost:8080/" + urlMapping.getShortCode();


        return new UrlStatsResponse(
                urlMapping.getOriginalUrl(),
                fullShortUrl,
                urlMapping.getCreationDate(),
                urlMapping.getClickCount()
        );
    }

}
