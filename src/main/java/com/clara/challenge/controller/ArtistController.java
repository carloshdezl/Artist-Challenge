package com.clara.challenge.controller;

import com.clara.challenge.dto.ArtistComparisonDTO;
import com.clara.challenge.dto.ArtistRequestDTO;
import com.clara.challenge.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists")
public class ArtistController {
    private final ArtistService artistService;

    @PostMapping("/compare")
    public ResponseEntity<List<ArtistComparisonDTO>> getByName(
            @RequestBody ArtistRequestDTO artistRequestDTO) {
        return ResponseEntity.ok(artistService.getArtistsByArtistRequestDTO(artistRequestDTO));
    }
}
