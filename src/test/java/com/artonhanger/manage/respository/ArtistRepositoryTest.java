package com.artonhanger.manage.respository;

import com.artonhanger.manage.annotation.AohDataTest;
import com.artonhanger.manage.model.Artist;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AohDataTest
@DatabaseSetup({
        "classpath:dbunit/dataset/member.xml",
        "classpath:dbunit/dataset/artist.xml"
})
class ArtistRepositoryTest {

    @Autowired
    ArtistRepository artistRepository;

    @Test
    void is_enable이_true인_레코드의_개수를_가져온다() {
        assertEquals(9, artistRepository.countAll());
    }

    @Test
    void 모든_작가를_회원정보와_함께_가져온다_정렬_및_페이징_포함() {
        Page<Artist> artistPage = artistRepository.findAllArtistWithMember(PageRequest.of(0, 2));

        assertEquals(2, artistPage.getNumberOfElements());
        assertEquals(10, artistPage.getTotalElements());
        assertEquals(5, artistPage.getTotalPages());
        List<Artist> artists = artistPage.getContent();
        assertEquals(2, artists.size());
        assertEquals("Ki***ys", artists.get(0).getMember().getName());
        assertEquals("김*별", artists.get(1).getMember().getName());
    }
}