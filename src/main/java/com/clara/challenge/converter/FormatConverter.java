package com.clara.challenge.converter;

import com.clara.challenge.model.Format;
import com.clara.challenge.model.Release;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatConverter {
    public static List<Format> stringsToEntities(List<String> formats, Release release) {
        return formats.stream()
                .map(formatName -> Format.builder()
                        .release(release)
                        .name(formatName)
                        .build())
                .collect(Collectors.toList());
    }
}
