package com.artonhanger.manage.component.imweb;

import com.artonhanger.manage.component.imweb.dto.ImwebProductAdd;
import com.artonhanger.manage.component.imweb.dto.ImwebProductModify;
import com.artonhanger.manage.component.imweb.dto.ImwebRequest;
import com.artonhanger.manage.component.imweb.dto.ImwebResponse;
import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.exception.AOHException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImwebShopClient {
    private static final String URL_SHOP_PRODUCTS = "/v2/shop/products";

    private final ImwebClient imwebClient;

    public ImwebResponse addProduct(ImwebProductAdd product) {
        ImwebResponse response = imwebClient.post(ImwebRequest.builder()
                .uri(URL_SHOP_PRODUCTS)
                .body(product)
                .build());
        verifyResponse(response.getMsg());
        return response;
    }

    public ImwebResponse modifyProduct(ImwebProductModify product) {
        ImwebResponse response = imwebClient.patch(ImwebRequest.builder()
                .uri(URL_SHOP_PRODUCTS + "/" + product.getImwebId())
                .body(product)
                .build());
        verifyResponse(response.getMsg());
        return response;
    }

    public ImwebResponse deleteProduct(Long imwebId) {
        return imwebClient.delete(ImwebRequest.builder()
                .uri(URL_SHOP_PRODUCTS + "/" + imwebId)
                .build());
    }
    public ImwebResponse findProduct(Long imwebId){
        ImwebResponse response = imwebClient.get(ImwebRequest.builder()
                .uri(URL_SHOP_PRODUCTS + "/" + imwebId)
                .build());
        verifyResponse(response.getMsg());
        return response;
    }

    private void verifyResponse(String msg) {
        if ("상품이 존재하지 않습니다.".equals(msg))
            throw new AOHException(ErrorEnum.IMWEB_404_ERROR);
        if (!"SUCCESS".equals(msg))
            throw new AOHException(ErrorEnum.ETC, "API Error");
    }
}
