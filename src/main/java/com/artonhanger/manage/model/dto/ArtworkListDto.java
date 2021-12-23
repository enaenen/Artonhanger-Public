package com.artonhanger.manage.model.dto;

import com.artonhanger.manage.model.Artwork;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkListDto { // TODO ArtworkListDto 네이밍을 ArtworkDto 로 변경 ??
    private Long artworkId;
    private Long imwebId;
    private int price;
    private String title;
    private String artistName;
    private String thumbnail;

    public ArtworkListDto(Artwork artwork){
        this.artworkId = artwork.getId();
        this.title = artwork.getTitle();
        this.thumbnail = artwork.getThumbnail();
    }

    @QueryProjection
    public ArtworkListDto(Long artworkId, Long imwebId, String title, String thumbnail, String artistName, int price){
        this.artworkId = artworkId;
        this.imwebId = imwebId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.artistName = artistName;
        this.price = price;
    }
}
