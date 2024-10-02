package com.clara.challenge.controller;

import com.clara.challenge.dto.ArtistReleasesDTO;
import com.clara.challenge.service.ReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artist")
public class ReleaseController {
    private final ReleaseService releaseService;

    @GetMapping("/{artistName}/releases")
    public ResponseEntity<ArtistReleasesDTO> getByName(@PathVariable String artistName) {
        return ResponseEntity.ok(releaseService.getReleasesByArtistName(artistName));
    }
}
