package com.artonhanger.manage.component.imweb;

import com.artonhanger.manage.component.imweb.dto.ImwebProductAdd;
import com.artonhanger.manage.component.imweb.dto.ImwebProductModify;
import com.artonhanger.manage.component.imweb.dto.ImwebResponse;
import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.ArtworkImage;
import com.artonhanger.manage.model.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "prod")
@ActiveProfiles("prod")
@SpringBootTest
class ImwebShopClientTest {
    @Autowired
    private ImwebShopClient imwebShopClient;

    private final Member member = Member.builder()
            .name("testname")
            .nickname("testnickname")
            .email("test@email.com")
            .password("encryptedPrevPassword")
            .profileImg("testprofileImg")
            .alarmAgreement(true)
            .build();

    private final List<ArtworkImage> artworkImages = List.of(
            ArtworkImage.builder()
                    .name("image-name")
                    .description("image-description")
                    .build()
    );

    private final Artwork artwork = Artwork.builder()
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
            .artworkImages(artworkImages)
            .build();

    private String imwebId;

    @Test
    public void imweb_작품_추가_및_수정_테스트() {
        ImwebResponse response = imwebShopClient.addProduct(new ImwebProductAdd(artwork));
        assertEquals("SUCCESS", response.getMsg());

        imwebId = response.getResponseData().getImwebId();
        artwork.setImwebId(imwebId);
        response = imwebShopClient.modifyProduct(new ImwebProductModify(artwork));
        assertEquals("SUCCESS", response.getMsg());
    }

    @Test
    public void imweb_작품_찾기_테스트(){
        ImwebResponse responseFromAdd = imwebShopClient.addProduct(new ImwebProductAdd(artwork));
        imwebId = responseFromAdd.getResponseData().getImwebId();

        ImwebResponse responseFromFind = imwebShopClient.findProduct(Long.parseLong(imwebId));
        assertEquals("SUCCESS", responseFromFind.getMsg());
        assertEquals(1, responseFromFind.getResponseData().getStock().getStockAmount());
    }

    @AfterEach // 테스트 후 추가한 파일 삭제
    public void tearDown() {
        ImwebResponse response = imwebShopClient.deleteProduct(Long.parseLong(imwebId));
        assertEquals("SUCCESS", response.getMsg());
    }
}