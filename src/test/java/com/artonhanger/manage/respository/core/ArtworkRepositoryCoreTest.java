package com.artonhanger.manage.respository.core;

import com.artonhanger.manage.enums.DeliveryStateEnum;
import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.respository.ArtworkRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DatabaseSetup({
        "classpath:dbunit/dataset/member.xml",
        "classpath:dbunit/dataset/artwork_frame.xml",
        "classpath:dbunit/dataset/artwork.xml"
})
class ArtworkRepositoryCoreTest extends RepositoryCoreTest {

    @Autowired
    ArtworkRepository artworkRepository;

    @Test
    public void 전체_레코드_조회_테스트() {
        List<Artwork> artworks = artworkRepository.findAll();

        assertEquals(10, artworks.size());
        assertEquals(1L, artworks.get(0).getId());
        assertEquals(10L, artworks.get(9).getId());
    }

    @Test
    public void 특정_레코드_조회_테스트() {
       Artwork artwork = artworkRepository.findById(1L).orElseThrow();

        assertEquals(1L, artwork.getId());
        assertEquals(1L, artwork.getMember().getId());
        assertEquals("머무는 시간", artwork.getTitle());
        assertEquals(27.3, artwork.getSize().getHeight());
        assertEquals(22.0, artwork.getSize().getWidth());
        assertEquals(4, artwork.getSizeNumber());
        assertEquals(90000, artwork.getPrice());
        assertEquals(3000, artwork.getDeliveryPrice());
        assertEquals(DeliveryStateEnum.ONSALE, artwork.getDeliveryState());
        assertEquals(true, artwork.getPhotoService());
        assertEquals("2020", artwork.getProductionYear());
        assertEquals(ShippingEnum.SHIPPING, artwork.getShipping());
        assertFalse(artwork.isSoldOut());
        assertEquals("https://d3cr9r836ml7sc.cloudfront.net/artworkImages/17/111", artwork.getThumbnail());
        assertTrue(artwork.getMeta().isEnable());
        assertEquals(LocalDateTime.of(2021,1,6,18,4,55, 765000000), artwork.getMeta().getCreatedAt());
        assertEquals(LocalDateTime.of(2021,1,6,18,4,55, 765000000), artwork.getMeta().getUpdatedAt());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtworkRepositoryCoreTest/레코드_추가_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_추가_테스트() {
        Member member = Member.builder().id(11L).build();

        Artwork artwork = Artwork.builder()
                .member(member)
                .title("테스트제목")
                .height(12L)
                .width(13L)
                .sizeNumber(5)
                .price(200000)
                .deliveryPrice(3000)
                .photoService(true)
                .productionYear("2021")
                .shipping(ShippingEnum.SHIPPING)
                .thumbnail("thumbnail-path")
                .build();
        artworkRepository.saveAndFlush(artwork);

        assertEquals(LocalDateTime.now().getYear(), artwork.getMeta().getCreatedAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), artwork.getMeta().getCreatedAt().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), artwork.getMeta().getCreatedAt().getDayOfMonth());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtworkRepositoryCoreTest/레코드_수정_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_수정_테스트() {
        Artwork artwork = artworkRepository.findById(1L).orElseThrow();
        artwork.soldout();
        artworkRepository.flush();

        assertEquals(LocalDateTime.now().getYear(), artwork.getMeta().getUpdatedAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), artwork.getMeta().getUpdatedAt().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), artwork.getMeta().getUpdatedAt().getDayOfMonth());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtworkRepositoryCoreTest/레코드_삭제_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_삭제_테스트() {
        Artwork artwork = artworkRepository.findById(1L).orElseThrow();
        artworkRepository.delete(artwork);
        artworkRepository.flush();
    }
}