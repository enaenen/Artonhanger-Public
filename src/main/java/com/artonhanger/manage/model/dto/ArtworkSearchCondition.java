package com.artonhanger.manage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtworkSearchCondition {
    //작품명, 작가명
    private String title;
    private String nickname;
    private Integer price;
}
