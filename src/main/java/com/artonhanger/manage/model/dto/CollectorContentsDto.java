package com.artonhanger.manage.model.dto;

import com.artonhanger.manage.model.CollectorContents;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectorContentsDto {
    private String collectorName;
    private String artworkTitle;
    private String contentTitle;

    private String introduce;
    private MultipartFile introduceUrl;


    private String taste;
    private MultipartFile tasteUrl;

    private String location;
    private MultipartFile locationUrl;

    private String choiceStandard;
    private MultipartFile choiceStandardUrl;

    private String choiceReason;
    private MultipartFile choiceReasonUrl;

    private String charmingPoint;
    private MultipartFile charmingPointUrl;

    private String locationAfter;
    private MultipartFile locationAfterUrl;

    private String tip;
    private String recommend;
    private String etc;

    public CollectorContents toEntity(Map<String, String> urls){
        return CollectorContents.builder()
                .collectorName(this.collectorName)
                .artworkTitle(this.artworkTitle)
                .contentTitle(this.contentTitle)
                .introduce(CollectorContents.Introduce.builder()
                        .content(this.introduce)
                        .url(urls.getOrDefault("introduceUrl",null)).build())
                .taste(CollectorContents.Taste.builder()
                        .content(this.taste)
                        .url(urls.getOrDefault("tasteUrl",null)).build())
                .location(CollectorContents.Location.builder()
                        .content(this.location)
                        .url(urls.getOrDefault("locationUrl",null)).build())
                .choiceStandard(CollectorContents.ChoiceStandard.builder()
                        .content(this.choiceStandard)
                        .url(urls.getOrDefault("choiceStandardUrl",null)).build())
                .choiceReason(CollectorContents.ChoiceReason.builder()
                        .content(this.choiceReason)
                        .url(urls.getOrDefault("choiceReason", null)).build())
                .charmingPoint(CollectorContents.CharmingPoint.builder()
                        .content(this.charmingPoint)
                        .url(urls.getOrDefault("charmingPointUrl", null))
                        .build())
                .locationAfter(CollectorContents.LocationAfter.builder()
                        .content(this.locationAfter)
                        .url(urls.getOrDefault("locationAfterUrl",null))
                        .build())
                .tip(this.tip)
                .recommend(this.recommend)
                .etc(this.etc)
                .build();
    }

    public Map<String, MultipartFile> collectorsContentsImageToHashMap() {
        Map<String, MultipartFile> imageMap = new HashMap<String, MultipartFile>();
        if (this.introduceUrl != null)
            imageMap.put("introduceUrl",this.introduceUrl);
        if (this.tasteUrl != null)
            imageMap.put("tasteUrl",this.tasteUrl);
        if (this.locationAfterUrl != null)
            imageMap.put("locationUrl",this.locationUrl);
        if (this.choiceStandardUrl != null)
            imageMap.put("choiceStandardUrl",this.choiceStandardUrl);
        if (this.choiceReasonUrl != null)
            imageMap.put("choiceReasonUrl",this.choiceReasonUrl);
        if (this.charmingPointUrl != null)
            imageMap.put("charmingPointUrl",this.charmingPointUrl);
        if (this.locationAfterUrl != null)
            imageMap.put("locationAfterUrl",this.locationAfterUrl);
        return imageMap;
    }

}
