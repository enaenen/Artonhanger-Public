package com.artonhanger.manage.service;

import com.artonhanger.manage.component.imweb.ImwebShopClient;
import com.artonhanger.manage.component.imweb.dto.ImwebArtworkResult;
import com.artonhanger.manage.component.imweb.dto.ImwebProductAdd;
import com.artonhanger.manage.component.imweb.dto.ImwebProductModify;
import com.artonhanger.manage.component.imweb.dto.ImwebResponse;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImwebArtworkService {
    private final ImwebShopClient imwebShopClient;

    public ImwebArtworkResult addArtwork(Artwork artwork, ArtworkUploadDto artworkUploadDto){
        ImwebProductAdd imwebProductAdd = buildImwebProductAdd(artwork, artworkUploadDto);
        String imwebId = imwebShopClient.addProduct(imwebProductAdd)
                .getResponseData()
                .getImwebId();
        return ImwebArtworkResult.builder()
                .imwebId(imwebId).build();
    }

    private ImwebProductAdd buildImwebProductAdd(Artwork artwork, ArtworkUploadDto artworkUploadDto) {
        // 아임웹 페이지에서는 사용되는 필드가 있지만
        // 아트온행거DB의 필드에는 필요가 없을경우
        if (artworkUploadDto != null && artworkUploadDto.getStockAmount() != null)
            return new ImwebProductAdd(artwork, artworkUploadDto);
        return new ImwebProductAdd(artwork);
    }

    public ImwebArtworkResult modifyArtwork(Artwork artwork, ArtworkModifyDto artworkModifyDto){
        ImwebProductModify imwebProductModify = buildImwebProductModify(artwork, artworkModifyDto);
        String imwebId = imwebShopClient.modifyProduct(imwebProductModify)
                .getResponseData()
                .getImwebId();
        return ImwebArtworkResult.builder()
                .imwebId(imwebId).build();
    }

    private ImwebProductModify buildImwebProductModify(Artwork artwork, ArtworkModifyDto artworkModifyDto){
        if (artworkModifyDto != null && artworkModifyDto.isEdition())
            return new ImwebProductModify(artwork, artworkModifyDto);
        return new ImwebProductModify(artwork);
    }

    public void loadStockAmountFromImweb(ArtworkModifyViewDto artworkModifyViewDto){
        ImwebResponse product = imwebShopClient.findProduct(artworkModifyViewDto.getImwebId());
        artworkModifyViewDto.setStockAmount(product.getResponseData().getStock().getStockAmount());
    }

    public void deleteArtwork(Long imwebId) {
        imwebShopClient.deleteProduct(imwebId);
    }
}
