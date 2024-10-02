package com.clara.challenge.service;

import com.clara.challenge.converter.FormatConverter;
import com.clara.challenge.converter.GenreConverter;
import com.clara.challenge.converter.LabelConverter;
import com.clara.challenge.converter.ReleaseConverter;
import com.clara.challenge.dto.ArtistReleasesDTO;
import com.clara.challenge.dto.SearchResultDTO;
import com.clara.challenge.dto.SearchResultResponseDTO;
import com.clara.challenge.model.Artist;
import com.clara.challenge.model.Release;
import com.clara.challenge.repository.ReleaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ReleaseService {
    private final ArtistService artistService;
    private final DiscogsService discogsService;
    private final ReleaseRepository releaseRepository;

    @Transactional
    public ArtistReleasesDTO getReleasesByArtistName(String artistName) {
        final Optional<Artist> artistOptional = artistService.findByName(artistName);
        Artist artist;
        SearchResultResponseDTO searchResultResponseDTO =
                discogsService.searchReleasesByArtistName(artistName);

        artist = getArtist(artistName, artistOptional, searchResultResponseDTO);

        updateReleases(artist, searchResultResponseDTO);

        return ReleaseConverter.releasesToArtistReleasesDTO(
                artist, releaseRepository.findByArtistIdOrderByReleaseYear(artist.getId()));
    }

    private Artist getArtist(
            String artistName, Optional<Artist> artistOptional, SearchResultResponseDTO searchResultResponseDTO) {
        Artist artist;

        if (artistOptional.isEmpty()) {
            if (CollectionUtils.isEmpty(searchResultResponseDTO.getResults())) {
                throw new RuntimeException("");
            }
            artist = artistService.saveArtistByName(artistName);
        }
        else {
            artist = artistOptional.get();
        }

        return artist;
    }

    private void updateReleases(Artist artist,
            SearchResultResponseDTO searchResultResponseDTO) {
        final Long artistId = artist.getId();
        List<Release> releases = releaseRepository.findByArtistId(artistId);

        if(!CollectionUtils.isEmpty(releases)) {
            releaseRepository.deleteByArtistId(artistId);
        }

        List<SearchResultDTO > resultDTOs = nonNull(searchResultResponseDTO) ?
                searchResultResponseDTO.getResults() : Collections.emptyList();
        releases = resultDTOs.stream().map(
                        searchResultDTO -> getReleaseByArtistAndResultDTO(artist, searchResultDTO))
                .collect(Collectors.toList());
        releaseRepository.saveAll(releases);
    }

    private Release getReleaseByArtistAndResultDTO(Artist artist, SearchResultDTO searchResultDTO) {
        Release release = ReleaseConverter.searchResultDtoToEntity(searchResultDTO, artist);
        release.setGenres(
                GenreConverter.stringsToEntities(searchResultDTO.getGenre(), release));
        release.setLabels(
                LabelConverter.stringsToEntities(searchResultDTO.getLabel(), release));
        release.setFormats(
                FormatConverter.stringsToEntities(searchResultDTO.getLabel(), release));

        return release;
    }
}
