package com.artonhanger.manage.respository;

import com.artonhanger.manage.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query("SELECT COUNT(a) FROM Artist AS a where a.meta.enable = true")
    Long countAll();

    @Query(value = "SELECT a FROM Artist AS a JOIN FETCH a.member AS m ORDER BY m.name",
    countQuery = "SELECT COUNT (a) FROM Artist AS a JOIN a.member")
    Page<Artist> findAllArtistWithMember(Pageable pageable);
}
