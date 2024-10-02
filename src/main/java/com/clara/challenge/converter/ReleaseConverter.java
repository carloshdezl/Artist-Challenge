package com.clara.challenge.converter;

import com.clara.challenge.dto.ArtistReleasesDTO;
import com.clara.challenge.dto.SearchResultDTO;
import com.clara.challenge.model.Artist;
import com.clara.challenge.model.Format;
import com.clara.challenge.model.Genre;
import com.clara.challenge.model.Label;
import com.clara.challenge.model.Release;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReleaseConverter {
    public static Release searchResultDtoToEntity(
            SearchResultDTO searchResultDTO, Artist artist) {
        return Release.builder()
                .artist(artist)
                .title(searchResultDTO.getTitle())
                .country(searchResultDTO.getCountry())
                .uri(searchResultDTO.getUri())
                .resourceUrl(searchResultDTO.getResourceUrl())
                .type(searchResultDTO.getType())
                .releaseYear(searchResultDTO.getYear())
                .discogsId(searchResultDTO.getId())
                .build();
    }

    public static ArtistReleasesDTO releasesToArtistReleasesDTO(
            Artist artist, List<Release> releases) {
        return ArtistReleasesDTO.builder()
                .artistId(artist.getId())
                .artistName(artist.getName())
                .results(releases.stream()
                        .map(ReleaseConverter::releasesToSearchResultDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private static SearchResultDTO releasesToSearchResultDTO(Release release) {
        return SearchResultDTO.builder()
                .title(release.getTitle())
                .country(release.getCountry())
                .format(nonNull(release.getFormats()) ? release.getFormats().stream()
                        .map(Format::getName)
                        .collect(Collectors.toList()) : null)
                .type(release.getType())
                .uri(release.getUri())
                .resourceUrl(release.getResourceUrl())
                .id(release.getDiscogsId())
                .year(release.getReleaseYear())
                .genre(nonNull(release.getGenres()) ? release.getGenres().stream()
                        .map(Genre::getName)
                        .collect(Collectors.toList()) : null)
                .label(nonNull(release.getLabels()) ? release.getLabels().stream()
                        .map(Label::getName)
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
