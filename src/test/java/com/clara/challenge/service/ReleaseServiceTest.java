package com.clara.challenge.service;

import com.clara.challenge.dto.ArtistReleasesDTO;
import com.clara.challenge.dto.SearchResultDTO;
import com.clara.challenge.dto.SearchResultResponseDTO;
import com.clara.challenge.exception.NotFoundException;
import com.clara.challenge.model.Artist;
import com.clara.challenge.model.Genre;
import com.clara.challenge.model.Release;
import com.clara.challenge.repository.ReleaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReleaseServiceTest {
    @Mock
    private ArtistService artistService;
    @Mock
    private DiscogsService discogsService;
    @Mock
    private ReleaseRepository releaseRepository;
    @InjectMocks
    private ReleaseService releaseService;

    private String artistName;
    private Long artistId;
    private Optional<Artist> artistOptional;
    private SearchResultResponseDTO searchResultResponseDTO;
    private List<Release> releases;

    @BeforeEach
    public void init() {
        artistName = "testArtist";
        artistId = 1L;
        artistOptional = Optional.of(Artist.builder()
                .id(artistId)
                .name(artistName)
                .releases(List.of(Release.builder().id(2L)
                        .build()))
                .build());
        searchResultResponseDTO = SearchResultResponseDTO
                .builder()
                .results(List.of(SearchResultDTO.builder()
                        .id(3L)
                        .year(2024)
                        .genre(List.of("Rock"))
                        .label(List.of("Sony Music"))
                        .build()))
                .build();
        releases = List.of(Release.builder().id(4L).title("Release")
                .genres(List.of(Genre.builder().name("Rock").build()))
                .build());
    }

    @Test
    void getReleasesByArtistName_recordsFoundByArtist_success() {
        when(artistService.findByName(artistName)).thenReturn(artistOptional);
        when(discogsService.searchReleasesByArtistName(artistName)).thenReturn(searchResultResponseDTO);
        when(releaseRepository.findByArtistId(artistId)).thenReturn(releases);
        when(releaseRepository.findByArtistIdOrderByReleaseYear(artistId)).thenReturn(releases);

        ArtistReleasesDTO artistReleasesDTO = releaseService.getReleasesByArtistName(artistName);

        assertEquals(artistId, artistReleasesDTO.getArtistId());
        assertEquals(artistName, artistReleasesDTO.getArtistName());
        assertEquals(releases.size(), artistReleasesDTO.getResults().size());
        verify(artistService).findByName(artistName);
        verify(discogsService).searchReleasesByArtistName(artistName);
        verify(releaseRepository).findByArtistId(artistId);
        verify(releaseRepository).findByArtistIdOrderByReleaseYear(artistId);
        verify(releaseRepository).saveAll(anyList());
        verify(artistService, never()).saveArtistByName(artistName);
    }

    @Test
    void getReleasesByArtistName_recordsNotFoundInDbByArtist_success() {
        when(artistService.findByName(artistName)).thenReturn(Optional.empty());
        when(artistService.saveArtistByName(artistName)).thenReturn(artistOptional.get());
        when(discogsService.searchReleasesByArtistName(artistName)).thenReturn(searchResultResponseDTO);
        when(releaseRepository.findByArtistIdOrderByReleaseYear(artistId)).thenReturn(releases);

        ArtistReleasesDTO artistReleasesDTO = releaseService.getReleasesByArtistName(artistName);

        assertEquals(artistId, artistReleasesDTO.getArtistId());
        assertEquals(artistName, artistReleasesDTO.getArtistName());
        assertEquals(releases.size(), artistReleasesDTO.getResults().size());
        verify(artistService).saveArtistByName(artistName);
        verify(artistService).findByName(artistName);
        verify(discogsService).searchReleasesByArtistName(artistName);
        verify(releaseRepository).findByArtistId(artistId);
        verify(releaseRepository).findByArtistIdOrderByReleaseYear(artistId);
        verify(releaseRepository).saveAll(anyList());
        verify(releaseRepository, never()).deleteByArtistId(artistId);
    }

    @Test
    void getReleasesByArtistName_recordsNotFoundInApiByArtist_success() {
        SearchResultResponseDTO searchResultResponseDTO = SearchResultResponseDTO
                .builder()
                .results(List.of())
                .build();
        when(artistService.findByName(artistName)).thenReturn(Optional.empty());
        when(discogsService.searchReleasesByArtistName(artistName)).thenReturn(searchResultResponseDTO);

        NotFoundException result = assertThrows(NotFoundException.class,
                () -> releaseService.getReleasesByArtistName(artistName));

        assertEquals(String.format("No records found for the artist %s", artistName), result.getMessage());
        verify(artistService).findByName(artistName);
        verify(discogsService).searchReleasesByArtistName(artistName);
        verifyNoInteractions(releaseRepository);
        verifyNoMoreInteractions(artistService);
    }
}