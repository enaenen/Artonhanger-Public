package com.artonhanger.manage.service;

import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.ArtworkFrame;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.dto.ArtworkListDto;
import com.artonhanger.manage.model.dto.ArtworkModifyDto;
import com.artonhanger.manage.model.dto.ArtworkModifyViewDto;
import com.artonhanger.manage.respository.ArtworkFrameRepository;
import com.artonhanger.manage.respository.ArtworkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArtworkServiceTest {
    @InjectMocks
    ArtworkService artworkService;

    @Mock
    ArtworkRepository artworkRepository;

    @Mock
    ArtworkFrameRepository artworkFrameRepository;

    @Mock
    ImwebArtworkService imwebArtworkService;

    private final Member member = Member.builder()
            .name("testname")
            .nickname("testnickname")
            .email("test@email.com")
            .password("encryptedPrevPassword")
            .profileImg("testprofileImg")
            .alarmAgreement(true)
            .build();
    private final ArtworkFrame artworkFrame = ArtworkFrame.builder().height(33L).width(33L).material("테스트재질")
            .build();
    private final List<Artwork> artworks = List.of(
            Artwork.builder()
                    .id(1L)
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
                    .build(),
            Artwork.builder()
                    .id(2L)
                    .member(member)
                    .title("테스트제목2")
                    .height(13L)
                    .width(14L)
                    .sizeNumber(6)
                    .price(300000)
                    .deliveryPrice(3000)
                    .photoService(true)
                    .productionYear("2021")
                    .shipping(ShippingEnum.SHIPPING)
                    .thumbnail("thumbnail-path2")
                    .build(),
            Artwork.builder()
                    .id(3L)
                    .member(member)
                    .title("테스트제목3")
                    .height(14L)
                    .width(15L)
                    .sizeNumber(7)
                    .price(400000)
                    .deliveryPrice(3000)
                    .photoService(true)
                    .productionYear("2021")
                    .shipping(ShippingEnum.SHIPPING)
                    .thumbnail("thumbnail-path")
                    .artworkFrame(artworkFrame)
                    .build()
    );

    private final Page<Artwork> artworkPage =
            new PageImpl<>(artworks, PageRequest.of(0, 2), 5);

    @Test
    void 작품리스트_회원_ID로_찾아_DTO변환() {
        given(artworkRepository.findAllByMember_IdAndMeta_EnableIsTrue(eq(1L), any()))
                .willReturn(artworkPage);

        Page<ArtworkListDto> artworkListDtoPage
                = artworkService.findArtworkListByMemberId(1L, PageRequest.of(0, 2));

        assertEquals(3, artworkListDtoPage.getNumberOfElements());
        assertEquals(5, artworkListDtoPage.getTotalElements());
        assertEquals(3, artworkListDtoPage.getTotalPages());
        List<ArtworkListDto> artworkListDtos = artworkListDtoPage.getContent();
        assertEquals(3, artworkListDtos.size());
        assertEquals(1L, artworkListDtos.get(0).getArtworkId());
        assertEquals("테스트제목", artworkListDtos.get(0).getTitle());
        assertEquals("thumbnail-path", artworkListDtos.get(0).getThumbnail());
        assertEquals(2L, artworkListDtos.get(1).getArtworkId());
    }

    @Test
    void IMWEB_NULL이_아닌_작품리스트_회원_ID로_찾아_DTO변환() {
        given(artworkRepository.findAllByMember_IdAndMeta_EnableIsTrueAndImwebIdIsNotNull(eq(1L), any()))
                .willReturn(artworkPage);

        Page<ArtworkListDto> artworkListDtoPage
                = artworkService.findArtworkListByMemberIdAndImwebId(1L, PageRequest.of(0, 2));

        assertEquals(3, artworkListDtoPage.getNumberOfElements());
        assertEquals(5, artworkListDtoPage.getTotalElements());
        assertEquals(3, artworkListDtoPage.getTotalPages());
        List<ArtworkListDto> artworkListDtos = artworkListDtoPage.getContent();
        assertEquals(3, artworkListDtos.size());
        assertEquals(1L, artworkListDtos.get(0).getArtworkId());
        assertEquals("테스트제목", artworkListDtos.get(0).getTitle());
        assertEquals("thumbnail-path", artworkListDtos.get(0).getThumbnail());
        assertEquals(2L, artworkListDtos.get(1).getArtworkId());
    }

    @Test
    void 작품_아이디로_찾기() {
        given(artworkRepository.findWithImagesById(eq(1L)))
                .willReturn(artworks.get(0));

        ArtworkModifyViewDto artworkModifyDto = artworkService.findArtworkByArtworkId(1L);

        assertEquals(1L, artworkModifyDto.getId());
        assertEquals("테스트제목", artworkModifyDto.getTitle());
        assertEquals("2021", artworkModifyDto.getProductionYear());
        assertEquals(12L, artworkModifyDto.getHeight());
        assertEquals(13L, artworkModifyDto.getWidth());
        assertNull(artworkModifyDto.getDescriptions());
        assertEquals(0, artworkModifyDto.getCategories().size());
        assertEquals(0, artworkModifyDto.getMaterials().size());
        assertEquals(0, artworkModifyDto.getColors().size());
        assertEquals(200000, artworkModifyDto.getPrice());
        assertEquals("3000", artworkModifyDto.getDeliveryPrice());
        assertEquals(ShippingEnum.SHIPPING, artworkModifyDto.getShipping());
//        assertEquals(true, artworkModifyDto.getPhotoService());
        assertEquals(false, artworkModifyDto.getPhotoService());
        assertEquals(0, artworkModifyDto.getArtworkImages().size());
        assertNull(artworkModifyDto.getImwebId());
        assertNull(artworkModifyDto.getArtworkFrame());
        assertFalse(artworkModifyDto.isFramed());
    }

    //액자가 없던 작품에서 액자없음으로 수정을 했을경우
    @Test
    void 작품_수정_테스트_PHOTOFRAME_WAS_NULL_TO_NO() {
        Artwork artwork = artworks.get(0);
        given(artworkRepository.findWithFrameById(eq(1L))).willReturn(artwork);
        ArtworkModifyDto artworkModifyDto = new ArtworkModifyDto();
        artworkModifyDto.setTitle("변경된제목");
        artworkModifyDto.setPrice(123000);
        artworkModifyDto.setProductionYear("2077");
        artworkModifyDto.setPhotoFrame(false);
        artworkModifyDto.setWidth(20D);
        artworkModifyDto.setHeight(30D);

        artworkService.artworkModify(1L, artworkModifyDto);

//        verify(artworkFrameRepository, times(1)).delete(any());
        assertEquals("변경된제목", artwork.getTitle());
        assertEquals(123000, artwork.getPrice());
        assertEquals("2077", artwork.getProductionYear());
        assertEquals(20L, artwork.getSize().getWidth());
        assertEquals(30L, artwork.getSize().getHeight());
    }

    //액자가 있던 작품에서 액자없음으로 수정을 했을경우
    @Test
    void 작품_수정_테스트_PHOTOFRAME_WAS_NOT_NULL_TO_NO() {
        Artwork artwork = artworks.get(2);
        given(artworkRepository.findWithFrameById(eq(3L))).willReturn(artwork);
        ArtworkModifyDto artworkModifyDto = new ArtworkModifyDto();
        artworkModifyDto.setTitle("변경된제목");
        artworkModifyDto.setPrice(123000);
        artworkModifyDto.setProductionYear("2077");
        artworkModifyDto.setPhotoFrame(false);
        artworkModifyDto.setWidth(20D);
        artworkModifyDto.setHeight(30D);

        artworkService.artworkModify(3L, artworkModifyDto);

        verify(artworkFrameRepository, times(1)).delete(any());
        assertEquals("변경된제목", artwork.getTitle());
        assertEquals(123000, artwork.getPrice());
        assertEquals("2077", artwork.getProductionYear());
        assertEquals(20L, artwork.getSize().getWidth());
        assertEquals(30L, artwork.getSize().getHeight());
    }

    @Test
    void 작품_수정_테스트_PHOTOFRAME_YES() {
        Artwork artwork = artworks.get(0);
        given(artworkRepository.findWithFrameById(eq(1L))).willReturn(artwork);
        ArtworkModifyDto artworkModifyDto = new ArtworkModifyDto();
        artworkModifyDto.setTitle("변경된제목");
        artworkModifyDto.setPrice(123000);
        artworkModifyDto.setProductionYear("2077");
        artworkModifyDto.setPhotoFrame(true);
        artworkModifyDto.setWidth(20D);
        artworkModifyDto.setHeight(30D);
        artworkModifyDto.setFrameHeight(40D);
        artworkModifyDto.setFrameWidth(50D);
        artworkModifyDto.setPhotoFrameMaterial("wood");

        artworkService.artworkModify(1L, artworkModifyDto);

        verify(artworkFrameRepository, times(0)).delete(any());
        assertEquals("변경된제목", artwork.getTitle());
        assertEquals(123000, artwork.getPrice());
        assertEquals("2077", artwork.getProductionYear());
        assertEquals(20L, artwork.getSize().getWidth());
        assertEquals(30L, artwork.getSize().getHeight());
        assertNotNull(artwork.getArtworkFrame());
        assertEquals("wood", artwork.getArtworkFrame().getMaterial());
        assertEquals(40L, artwork.getArtworkFrame().getSize().getHeight());
        assertEquals(50L, artwork.getArtworkFrame().getSize().getWidth());
    }
}