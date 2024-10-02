package com.clara.challenge.repository;

import com.clara.challenge.model.Artist;
import com.clara.challenge.projection.ArtistComparisonProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByName(String name);

    @Query("SELECT a.name AS name, COUNT(r) AS releaseCount " +
            "FROM Artist a LEFT JOIN a.releases r " +
            "WHERE a.name IN :names " +
            "GROUP BY a.id " +
            "ORDER BY COUNT(r) DESC")
    List<ArtistComparisonProjection> findArtistComparisonByNameInOrderByReleaseCount(List<String> names);

    @Query("SELECT a.name AS name, (MAX(r.releaseYear) - MIN(r.releaseYear)) AS activeYears " +
            "FROM Artist a LEFT JOIN a.releases r " +
            "WHERE a.name IN :names " +
            "GROUP BY a.id " +
            "ORDER BY activeYears DESC")
    List<ArtistComparisonProjection> findArtistActiveYearsByNameInOrderByActiveYears(List<String> names);

    @Query("SELECT g.name AS genreName, COUNT(g) AS usedByCount " +
            "FROM Genre g " +
            "JOIN g.release r " +
            "JOIN r.artist a " +
            "WHERE a.name IN :names " +
            "GROUP BY g.name " +
            "ORDER BY COUNT(g) DESC")
    List<ArtistComparisonProjection> findMostUsedGenresByArtistNames(List<String> names);
}
