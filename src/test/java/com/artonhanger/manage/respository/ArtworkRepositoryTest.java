package com.artonhanger.manage.respository;

import com.artonhanger.manage.annotation.AohDataTest;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.dto.ArtworkListDto;
import com.artonhanger.manage.model.dto.ArtworkSearchCondition;
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
        "classpath:dbunit/dataset/artwork_frame.xml",
        "classpath:dbunit/dataset/artwork.xml",
        "classpath:dbunit/dataset/artwork_image.xml",
})
class ArtworkRepositoryTest {

    @Autowired
    ArtworkRepository artworkRepository;

    @Test
    void 작품을_image와_함께_id로_찾는다() {
        Artwork artwork = artworkRepository.findWithImagesById(1L);
        assertEquals("머무는 시간", artwork.getTitle());
        assertEquals(2, artwork.getArtworkImages().size());
        assertEquals("함께 지낼 수 있는 시간들이 점점 줄어드는 요즘, 소중한 사람들과 즐거웠던 시간들.",
                artwork.getArtworkImages().get(0).getDescription());
        assertEquals("판넬에 아크릴 물감 그림입니다.",
                artwork.getArtworkImages().get(1).getDescription());
    }

    @Test
    void 작품을_frame과_함께_id로_찾는다() {
        Artwork artwork = artworkRepository.findWithFrameById(1L);
        assertEquals("머무는 시간", artwork.getTitle());
        assertEquals("스테인리스", artwork.getArtworkFrame().getMaterial());
    }

    @Test
    void is_enable이_true인_작품들을_member_id로_가져온다_페이징() {
        Page<Artwork> artworkPage = artworkRepository.findAllByMember_IdAndMeta_EnableIsTrue(
                1L, PageRequest.of(0, 2));

        assertEquals(2, artworkPage.getNumberOfElements());
        assertEquals(3, artworkPage.getTotalElements());
        assertEquals(2, artworkPage.getTotalPages());
        List<Artwork> artworks = artworkPage.getContent();
        assertEquals(2, artworks.size());
        assertEquals("머무는 시간", artworks.get(0).getTitle());
        assertEquals("아무도 모르는 들 2 ", artworks.get(1).getTitle());
    }

    @Test
    void is_enable이_true이고_imweb_id가_비어있지_않은_작품들을_member_id로_가져온다_페이징() {
        Page<Artwork> artworkPage = artworkRepository.findAllByMember_IdAndMeta_EnableIsTrueAndImwebIdIsNotNull(
                1L, PageRequest.of(0, 2));

        assertEquals(1, artworkPage.getNumberOfElements());
        assertEquals(1, artworkPage.getTotalElements());
        assertEquals(1, artworkPage.getTotalPages());
        List<Artwork> artworks = artworkPage.getContent();
        assertEquals(1, artworks.size());
        assertEquals("머무는 시간", artworks.get(0).getTitle());
    }

    @Test
    void 모든_작품_불러오기_페이징_id_내림차순() {
        Page<ArtworkListDto> artworkListDtoPage = artworkRepository.load(PageRequest.of(0, 2));

        assertEquals(2, artworkListDtoPage.getNumberOfElements());
        assertEquals(10, artworkListDtoPage.getTotalElements());
        assertEquals(5, artworkListDtoPage.getTotalPages());
        List<ArtworkListDto> artworkListDtos = artworkListDtoPage.getContent();
        assertEquals(2, artworkListDtos.size());
        assertEquals(10L, artworkListDtos.get(0).getArtworkId());
        assertNull(artworkListDtos.get(0).getImwebId());
        assertEquals(390000, artworkListDtos.get(0).getPrice());
        assertEquals("FAUST", artworkListDtos.get(0).getTitle());
        assertEquals("박*현", artworkListDtos.get(0).getArtistName());
        assertEquals("https://d3cr9r836ml7sc.cloudfront.net/artworkImages/15/1000", artworkListDtos.get(0).getThumbnail());
        assertEquals("PEACE SHARK", artworkListDtos.get(1).getTitle());
    }

    @Test
    void 작품_제목으로_검색_페이징() {
        Page<ArtworkListDto> artworkListDtoPage = artworkRepository.search(ArtworkSearchCondition.builder()
                        .title("아무도 모르는 들")
                        .nickname("아무도 모르는 들")
                        .price(0)
                        .build(),
                PageRequest.of(0, 2));

        assertEquals(2, artworkListDtoPage.getNumberOfElements());
        assertEquals(2, artworkListDtoPage.getTotalElements());
        assertEquals(1, artworkListDtoPage.getTotalPages());
        List<ArtworkListDto> artworkListDtos = artworkListDtoPage.getContent();
        assertEquals(2, artworkListDtos.size());
        assertEquals(3L, artworkListDtos.get(0).getArtworkId());
        assertNull(artworkListDtos.get(0).getImwebId());
        assertEquals(120000, artworkListDtos.get(0).getPrice());
        assertEquals("아무도 모르는 들 2 ", artworkListDtos.get(0).getTitle());
        assertEquals("오*진", artworkListDtos.get(0).getArtistName());
        assertEquals("https://d3cr9r836ml7sc.cloudfront.net/artworkImages/17/333", artworkListDtos.get(0).getThumbnail());
        assertEquals("아무도 모르는 들", artworkListDtos.get(1).getTitle());
    }

    @Test
    void 작품_닉네임으로_검색_페이징() {
        Page<ArtworkListDto> artworkListDtoPage = artworkRepository.search(ArtworkSearchCondition.builder()
                        .title("오*진")
                        .nickname("오*진")
                        .price(0)
                        .build(),
                PageRequest.of(0, 2));

        assertEquals(2, artworkListDtoPage.getNumberOfElements());
        assertEquals(5, artworkListDtoPage.getTotalElements());
        assertEquals(3, artworkListDtoPage.getTotalPages());
        List<ArtworkListDto> artworkListDtos = artworkListDtoPage.getContent();
        assertEquals(2, artworkListDtos.size());
        assertEquals(1L, artworkListDtos.get(0).getArtworkId());
        assertEquals(1L, artworkListDtos.get(0).getImwebId());
        assertEquals(90000, artworkListDtos.get(0).getPrice());
        assertEquals("머무는 시간", artworkListDtos.get(0).getTitle());
        assertEquals("오*진", artworkListDtos.get(0).getArtistName());
        assertEquals("https://d3cr9r836ml7sc.cloudfront.net/artworkImages/17/111", artworkListDtos.get(0).getThumbnail());
        assertEquals("들판", artworkListDtos.get(1).getTitle());
    }
}