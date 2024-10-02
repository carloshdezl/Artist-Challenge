package com.clara.challenge.service;

import com.clara.challenge.dto.ArtistComparisonDTO;
import com.clara.challenge.dto.ArtistRequestDTO;
import com.clara.challenge.model.Artist;
import com.clara.challenge.projection.ArtistComparisonProjection;
import com.clara.challenge.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    public Optional<Artist> findByName(String name) {
        return artistRepository.findByName(name);
    }

    public Artist saveArtistByName(String name) {
        return artistRepository.save(
                Artist.builder()
                .name(name)
                .build());
    }

    public List<ArtistComparisonDTO> getArtistsByArtistRequestDTO(ArtistRequestDTO artistRequestDTO) {
        List<ArtistComparisonProjection> artistComparisonProjections = null;
        if ("releaseCount".equalsIgnoreCase(artistRequestDTO.getCriteria())) {
            artistComparisonProjections =
                    artistRepository.findArtistComparisonByNameInOrderByReleaseCount(
                            artistRequestDTO.getArtistNames());
        }

        if ("activeYears".equalsIgnoreCase(artistRequestDTO.getCriteria())) {
            artistComparisonProjections =
                    artistRepository.findArtistActiveYearsByNameInOrderByActiveYears(
                            artistRequestDTO.getArtistNames());
        }

        return CollectionUtils.isEmpty(artistComparisonProjections) ? null : artistComparisonProjections
                .stream()
                .map(projection -> new ArtistComparisonDTO(
                        projection.getName(), projection.getReleaseCount(), projection.getActiveYears()))
                .collect(Collectors.toList());
    }
}
