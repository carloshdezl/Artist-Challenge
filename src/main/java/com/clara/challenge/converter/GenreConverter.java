package com.clara.challenge.converter;

import com.clara.challenge.model.Genre;
import com.clara.challenge.model.Release;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenreConverter {
    public static List<Genre> stringsToEntities(List<String> genres, Release release) {
        return genres.stream()
                .map(genreName -> Genre.builder()
                        .release(release)
                        .name(genreName)
                        .build())
                .collect(Collectors.toList());
    }
}
