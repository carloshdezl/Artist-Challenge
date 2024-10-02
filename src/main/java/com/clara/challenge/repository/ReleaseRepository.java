package com.clara.challenge.repository;

import com.clara.challenge.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
    List<Release> findByArtistId(Long artistId);

    List<Release> findByArtistIdOrderByReleaseYear(Long artistId);

    @Modifying
    void deleteByArtistId(Long artistId);
}
