package com.artonhanger.manage.service;

import com.artonhanger.manage.component.imweb.dto.ImwebArtworkResult;
import com.artonhanger.manage.enums.CategoryEnum;
import com.artonhanger.manage.enums.ColorEnum;
import com.artonhanger.manage.enums.MaterialEnum;
import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.dto.ArtworkUploadDto;
import com.artonhanger.manage.respository.ArtworkRepository;
import com.artonhanger.manage.service.dto.UploadResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ArtworkUploadServiceTest {
    @InjectMocks
    private ArtworkUploadService artworkUploadService;

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private ImwebArtworkService imwebArtworkService;

    @Mock
    private ArtworkImageUploadService artworkImageUploadService;

    private final Member member = Member.builder()
            .name("testname")
            .nickname("testnickname")
            .email("test@email.com")
            .password("encryptedPrevPassword")
            .profileImg("testprofileImg")
            .alarmAgreement(true)
            .build();

    private final Artwork artwork = Artwork.builder()
            .id(1L)
            .member(member)
            .title("테스트제목")
            .height(12L)
            .width(13L)
            .sizeNumber(0)
            .price(200000)
            .deliveryPrice(3000)
            .photoService(true)
            .productionYear("2021")
            .shipping(ShippingEnum.SHIPPING)
            .thumbnail("thumbnail-path")
            .build();

    private final ArtworkUploadDto artworkUploadDto = new ArtworkUploadDto();
    private final ArgumentCaptor<Artwork> artworkArgumentCaptor = ArgumentCaptor.forClass(Artwork.class);

    @BeforeEach
    public void setUp() {
        artworkUploadDto.setMember(member);
        artworkUploadDto.setCategories(List.of(CategoryEnum.ABSTRACT));
        artworkUploadDto.setMaterials(List.of(MaterialEnum.SCULPTURE));
        artworkUploadDto.setColors(List.of(ColorEnum.BLUE));
        artworkUploadDto.setTitle("테스트제목");
        artworkUploadDto.setPrice(200000);
        artworkUploadDto.setDeliveryPrice("3000");
        artworkUploadDto.setProductionYear("2021");
        artworkUploadDto.setHeight(12);
        artworkUploadDto.setWidth(13);
        artworkUploadDto.setPhotoService(true);
        artworkUploadDto.setStockAmount(1);
        artworkUploadDto.setArtworkImages(List.of(new MockMultipartFile(
                "image",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()),
                new MockMultipartFile(
                        "image2",
                        "hello2.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "Hello, World2!".getBytes())));
        artworkUploadDto.setDescriptions(List.of("description1", "description2"));

        given(artworkImageUploadService.upload(any()))
                .willReturn(UploadResult.builder().downloadableUrl("thumbnail-path").build(),
                        UploadResult.builder().downloadableUrl("thumbnail-path2").build());
        given(artworkRepository.save(artworkArgumentCaptor.capture())).willReturn(artwork);
        given(imwebArtworkService.addArtwork(any(), any()))
                .willReturn(ImwebArtworkResult.builder().imwebId("1").build());
    }

    @Test
    public void 작품_업로드_서비스_테스트() {
        artworkUploadService.artworkUpload(artworkUploadDto);

        verify(artworkImageUploadService, times(2)).upload(any());
        Artwork capturedArtwork = artworkArgumentCaptor.getValue();
        assertEquals(artwork.getTitle(), capturedArtwork.getTitle());
        assertEquals(artwork.getPrice(), capturedArtwork.getPrice());
        assertEquals(artwork.getDeliveryPrice(), capturedArtwork.getDeliveryPrice());
        assertEquals(artwork.getSize().getHeight(), capturedArtwork.getSize().getHeight());
        assertEquals(artwork.getSize().getWidth(), capturedArtwork.getSize().getWidth());
        assertEquals(artwork.getSizeNumber(), capturedArtwork.getSizeNumber());
    }
}