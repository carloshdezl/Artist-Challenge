package com.clara.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultDTO {
    private String title;
    private String country;
    private List<String> format;
    private String uri;
    private String resourceUrl;
    private String type;
    private Long id;
    private Integer year;
    private List<String> genre;
    private List<String> label;
}
