package com.clara.challenge.converter;

import com.clara.challenge.model.Label;
import com.clara.challenge.model.Release;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LabelConverter {
    public static List<Label> stringsToEntities (List<String> labels, Release release) {
        return labels.stream()
                .map(labelName -> Label.builder()
                .release(release)
                .name(labelName)
                .build())
                .collect(Collectors.toList());
    }
}
