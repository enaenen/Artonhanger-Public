package com.artonhanger.manage.model.dto;

import com.artonhanger.manage.enums.CategoryEnum;
import com.artonhanger.manage.enums.ColorEnum;
import com.artonhanger.manage.enums.MaterialEnum;
import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.ArtworkFrame;
import com.artonhanger.manage.model.ArtworkImage;
import com.artonhanger.manage.model.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString()
@Getter
@Setter
public class ArtworkUploadDto {
    private Member member;
    private String title;
    private String productionYear;

    private double height;
    private double width;

    private String description;

    private List<CategoryEnum> categories;
    private List<MaterialEnum> materials;
    private List<ColorEnum> colors;

    private int price;
    private String deliveryPrice;

    private ShippingEnum shipping=ShippingEnum.SHIPPING;
    private Boolean photoService=false;

    // TODO artworkImages Multipart랑 description이 한 세트로 보이는데, 프론트에서 DTO로 묶어줄 수 있음
    // https://stackoverflow.com/questions/33015654/how-to-bind-data-to-list-in-spring-form
    private List<MultipartFile> artworkImages;
    private List<String> descriptions=new ArrayList<>();

    private MultipartFile representativeImage;
    private String representativeImageName;

    private boolean photoFrame;
    private double frameHeight;
    private double frameWidth;
    private String photoFrameMaterial;
    private Integer stockAmount;

    private int sizeCalculate(){
        double area = height * width;
        int size = 0;
        if(area <= 252)
            size = 0;
        else if(area <=352)
            size = 1;
        else if(area <=418)
            size = 2;
        else if(area <=594)
            size = 3;
        else if(area <=792)
            size = 4;
        else if(area <=945)
            size = 5;
        else if(area <=1353)
            size = 6;
        else if(area <=1748)
            size = 8;
        else if(area <=2530)
            size = 10;
        else if(area <=3050)
            size = 12;
        else if(area <=3510)
            size = 15;
        else if(area <=4380)
            size = 20;
        else if(area <=5265)
            size = 25;
        else if(area <=6716)
            size = 30;
        else if(area <=8100)
            size = 40;
        else if(area <=10324)
            size = 50;
        else if(area <=12610)
            size = 60;
        else if(area <=16352)
            size = 80;
        else if(area <=21060)
            size = 100;
        return size;
    }

    public Artwork toEntity(List<ArtworkImage> artworkImages){
        Artwork artwork = Artwork.builder()
                .member(member)
                .thumbnail(representativeImageName)
                .artworkImages(artworkImages)
                .categories(categories)
                .materials(materials)
                .colors(colors)
                .title(title)
                .price(price)
                .deliveryPrice(Integer.parseInt(deliveryPrice))
                .productionYear(productionYear)
                .height(height)
                .width(width)
                .sizeNumber(sizeCalculate())
                .shipping(shipping)
                .photoService(photoService)
                .build();
        if (this.photoFrame) {
            artwork.changeArtworkFrame(ArtworkFrame.builder()
                    .width(this.frameWidth)
                    .height(this.frameHeight)
                    .material(this.photoFrameMaterial)
                    .build());
        }
        return artwork;
    }

    public void setRepresentativeImage(String imagePath){
        this.representativeImageName=imagePath;
    }
}
