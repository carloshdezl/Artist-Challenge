package com.clara.challenge.service;

import com.clara.challenge.client.DiscogsClient;
import com.clara.challenge.configuration.DiscogsConfig;
import com.clara.challenge.dto.SearchResultResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscogsService {
    private final DiscogsClient discogsClient;
    private final DiscogsConfig discogsConfig;

    public SearchResultResponseDTO searchReleasesByArtistName(String artistName) {
        return discogsClient.searchArtist(
                artistName, discogsConfig.getConsumerKey(),
                discogsConfig.getConsumerSecret());
    }
}
