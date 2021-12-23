package com.artonhanger.manage.service;

import com.artonhanger.manage.component.imweb.ImwebShopClient;
import com.artonhanger.manage.component.imweb.dto.ImwebProductAdd;
import com.artonhanger.manage.component.imweb.dto.ImwebProductModify;
import com.artonhanger.manage.component.imweb.dto.ImwebResponse;
import com.artonhanger.manage.component.imweb.dto.ImwebResponseData;
import com.artonhanger.manage.enums.CategoryEnum;
import com.artonhanger.manage.enums.ColorEnum;
import com.artonhanger.manage.enums.MaterialEnum;
import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.dto.ArtworkModifyDto;
import com.artonhanger.manage.model.dto.ArtworkUploadDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ImwebArtworkServiceTest {
    @InjectMocks
    private ImwebArtworkService imwebArtworkService;

    @Mock
    private ImwebShopClient imwebShopClient;

    private final ArtworkUploadDto artworkUploadDto = new ArtworkUploadDto();
    private final ArtworkModifyDto artworkModifyDto = new ArtworkModifyDto();

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

    private final ArgumentCaptor<ImwebProductAdd> productAddArgumentCaptor =
            ArgumentCaptor.forClass(ImwebProductAdd.class);

    private final ArgumentCaptor<ImwebProductModify> productModifyArgumentCaptor =
            ArgumentCaptor.forClass(ImwebProductModify.class);

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

        ImwebResponseData responseData = new ImwebResponseData();
        responseData.setImwebId("1");
        ImwebResponse response = new ImwebResponse();
        response.setMsg("SUCCESS");
        response.setCode(200);
        response.setResponseData(responseData);
        given(imwebShopClient.addProduct(productAddArgumentCaptor.capture())).willReturn(response);
        given(imwebShopClient.modifyProduct(productModifyArgumentCaptor.capture())).willReturn(response);
    }

    @Test
    public void 작품_추가_테스트() {
        imwebArtworkService.addArtwork(artwork, artworkUploadDto);

        ImwebProductAdd captured = productAddArgumentCaptor.getValue();
        assertEquals(1, captured.getStockAmount());
    }

    @Test
    public void stock_no_option값_포함_작품_추가_테스트() {
        artworkUploadDto.setStockAmount(2);

        imwebArtworkService.addArtwork(artwork, artworkUploadDto);

        ImwebProductAdd captured = productAddArgumentCaptor.getValue();
        assertEquals(2, captured.getStockAmount());
    }

    @Test
    public void 작품_수정_테스트() {
        imwebArtworkService.modifyArtwork(artwork, artworkModifyDto);
        ImwebProductModify captured = productModifyArgumentCaptor.getValue();
        assertEquals(1, captured.getStockAmount());
        assertEquals(false, captured.getSimpleContent().contains("[프린트 에디션]"));
    }

    @Test
    public void 에디션작품_수정_테스트() {
        artworkModifyDto.setIsEdition("true");
        imwebArtworkService.modifyArtwork(artwork, artworkModifyDto);
        ImwebProductModify captured = productModifyArgumentCaptor.getValue();
        assertEquals(true, captured.getSimpleContent().contains("[프린트 에디션]"));
    }
}