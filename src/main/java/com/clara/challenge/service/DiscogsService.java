package com.clara.challenge.service;

import com.clara.challenge.client.DiscogsClient;
import com.clara.challenge.configuration.DiscogsKeys;
import com.clara.challenge.dto.SearchResultResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscogsService {
    private final DiscogsClient discogsClient;
    private final DiscogsKeys discogsKeys;

    public SearchResultResponseDTO searchReleasesByArtistName(String artistName) {
        return discogsClient.searchArtist(
                artistName, discogsKeys.getConsumerKey(),
                discogsKeys.getConsumerSecret());
    }
}
