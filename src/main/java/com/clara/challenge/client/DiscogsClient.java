package com.clara.challenge.client;

import com.clara.challenge.dto.SearchResultResponseDTO;
import com.clara.challenge.exception.feign.DiscogsClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "discogsClient", url = "https://api.discogs.com", configuration = DiscogsClientConfig.class)
public interface DiscogsClient {
    @GetMapping("/database/search")
    SearchResultResponseDTO searchArtist(
            @RequestParam("artist") String artistName,
            @RequestParam("key") String key,
            @RequestParam("secret") String secret
    );
}