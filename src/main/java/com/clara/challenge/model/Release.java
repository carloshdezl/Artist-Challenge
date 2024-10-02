package com.clara.challenge.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "release")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private String title;

    @Column
    private String country;

    @Column
    private String type;

    @Column
    private String uri;

    @Column
    private String resourceUrl;

    @Column
    private Integer releaseYear;

    @Column
    private Long discogsId;

    @OneToMany(mappedBy = "release", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Label> labels;

    @OneToMany(mappedBy = "release", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Genre> genres;

    @OneToMany(mappedBy = "release", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Format> formats;
}
