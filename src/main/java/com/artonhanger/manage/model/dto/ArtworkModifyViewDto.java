package com.artonhanger.manage.model.dto;

import com.artonhanger.manage.enums.MaterialEnum;
import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtworkModifyViewDto {
    private Long id;
    private Long imwebId;
    private String title;
    private String productionYear;
    private Double height;
    private Double width;
    private List<String> descriptions=new ArrayList<>();

    private List<Category> categories;
    private List<Material> materials;
    private List<Color> colors;

    private Integer price;
    private String deliveryPrice;

    private ShippingEnum shipping;
    private Boolean photoService=false;
    private List<ArtworkImage> artworkImages;

    private Boolean photoFrame;
    private boolean isFramed;
    private Double frameHeight;
    private Double frameWidth;
    private String photoFrameMaterial;
    private ArtworkFrame artworkFrame;
    private boolean isEdition;
    private Integer stockAmount;

    public ArtworkModifyViewDto(Artwork artwork){
        this.id = artwork.getId();
        this.title = artwork.getTitle();
        this.productionYear = artwork.getProductionYear();
        this.height = artwork.getSize().getHeight();
        this.width = artwork.getSize().getWidth();
        this.descriptions = null;

        this.categories = artwork.getCategories();
        this.materials = artwork.getMaterials();
        this.colors = artwork.getColors();

        this.price = artwork.getPrice();
        this.deliveryPrice = artwork.getDeliveryPrice()+"";
        this.shipping = artwork.getShipping();
        this.photoService=false;
        this.artworkImages = artwork.getArtworkImages();
        this.imwebId = artwork.getImwebId();
        this.artworkFrame = artwork.getArtworkFrame();
        if(artworkFrame != null)
            isFramed = true;
        setIsEdition(artwork);
    }
    public void verifySize(){
        if(height==null)
            height=Double.valueOf(0);
        if(width==null)
            width=Double.valueOf(0);
    }
    public void verifyFrameSize(){
        if(frameHeight==null)
            frameHeight=Double.valueOf(0);
        if(frameWidth==null)
            frameWidth=Double.valueOf(0);
    }

    private void setIsEdition(Artwork artwork){
        if(materials.contains(new Material(artwork, MaterialEnum.PRINTING)))
            isEdition = true;
        else
            isEdition = false;
    }
}
