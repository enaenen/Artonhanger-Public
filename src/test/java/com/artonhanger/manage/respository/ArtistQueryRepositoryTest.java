package com.artonhanger.manage.respository;

import com.artonhanger.manage.annotation.AohDataTest;
import com.artonhanger.manage.config.DbUnitConfig;
import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.dto.ArtistSearchCondition;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(ArtistQueryRepository.class)
@AohDataTest
@DatabaseSetup({
        "classpath:dbunit/dataset/member.xml",
        "classpath:dbunit/dataset/artist.xml"
})
class ArtistQueryRepositoryTest {

    @Autowired
    ArtistQueryRepository artistQueryRepository;

    @Test
    public void 작가_정보_검색_기본_테스트() {
        Page<Artist> artistPage = artistQueryRepository.searchArtists(PageRequest.of(0, 2),
                ArtistSearchCondition.builder()
                        .nickname("김")
                        .name("김")
                        .email("김")
                        .build());

        assertEquals(2, artistPage.getNumberOfElements());
        assertEquals(2, artistPage.getTotalElements());
        assertEquals(1, artistPage.getTotalPages());
        List<Artist> artists = artistPage.getContent();
        assertEquals(2, artists.size());
        assertEquals("김*원", artists.get(0).getMember().getName());
        assertEquals("김*별", artists.get(1).getMember().getName());
    }

    @Test
    public void 작가_정보_검색_페이징_테스트() {
        Page<Artist> artistPage = artistQueryRepository.searchArtists(PageRequest.of(0, 5),
                ArtistSearchCondition.builder()
                        .nickname("*")
                        .name("*")
                        .email("*")
                        .build());

        assertEquals(5, artistPage.getNumberOfElements());
        assertEquals(10, artistPage.getTotalElements());
        assertEquals(2, artistPage.getTotalPages());
        List<Artist> artists = artistPage.getContent();
        assertEquals(5, artists.size());
        assertEquals("오*진", artists.get(0).getMember().getName());
        assertEquals("김*원", artists.get(1).getMember().getName());
        assertEquals("박*현", artists.get(2).getMember().getName());
        assertEquals("김*별", artists.get(3).getMember().getName());
        assertEquals("황*정", artists.get(4).getMember().getName());
    }

    @Test
    public void 작가_정보_검색_이름만검색_테스트() {
        Page<Artist> artistPage = artistQueryRepository.searchArtists(PageRequest.of(0, 2),
                ArtistSearchCondition.builder()
                        .nickname("")
                        .name("김")
                        .email("")
                        .build());
        assertEquals(2, artistPage.getNumberOfElements());
        assertEquals(2, artistPage.getTotalElements());
        assertEquals(1, artistPage.getTotalPages());
        List<Artist> artists = artistPage.getContent();
        assertEquals(2, artists.size());
        assertEquals("김*원", artists.get(0).getMember().getName());
        assertEquals("김*별", artists.get(1).getMember().getName());
    }
}