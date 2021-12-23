package com.artonhanger.manage.component.imweb.dto;

import com.artonhanger.manage.enums.CategoryEnum;
import com.artonhanger.manage.enums.CategoryWebEnum;
import com.artonhanger.manage.enums.MaterialEnum;
import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.*;
import com.artonhanger.manage.model.dto.ArtworkUploadDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(of = {"name", "price", "prodStatus", "categories"})
@NoArgsConstructor
public class ImwebProductAdd {
    private List<String> categories = new ArrayList<String>();
    private List<String> images = new ArrayList<String>();
    private String name;
    @JsonProperty(value = "simple_content")
    private String simpleContent;
    private String content;
    @JsonProperty(value = "prod_status")
    private String prodStatus;
    @JsonProperty(value = "prod_type")
    private String prodType;
    private Float price;
    @JsonProperty(value = "minimum_purchase_quantity")
    private Integer minPurchaseQuantity;
    @JsonProperty(value = "member_maximum_purchase_quantity")
    private Integer memberMaxPurchaseQuantity;
    @JsonProperty(value = "stock_use")
    private boolean stockUse;
    @JsonProperty(value = "stock_unlimit")
    private boolean stockUnlimit;
    @JsonProperty(value = "stock_no_option")
    private int stockAmount;
    @JsonProperty(value = "weight")
    private float weight = 1;

    // TODO 매핑 로직 따로 작성하기..?
    public ImwebProductAdd(Artwork artwork) {
        categorySet(artwork.getCategories());
        materialSet(artwork.getMaterials());
        artworkImageSet(artwork.getArtworkImages());

        artDeliveryConvert(artwork.getShipping());
        this.name = artwork.getTitle() + "-" + artwork.getMember().getNickname();
        setContents(artwork.getArtworkImages());
        setSimpleDescription(artwork);
        setDeliveryPrice(artwork.getDeliveryPrice());
        this.prodStatus = "sale";
        this.prodType = "normal";
        this.price = Integer.valueOf(artwork.getPrice()).floatValue();
        this.minPurchaseQuantity = 1;
        this.memberMaxPurchaseQuantity = 1;
        this.stockAmount = 1;
        this.stockUse = true;
        this.stockUnlimit = false;
    }

    public ImwebProductAdd(Artwork artwork, ArtworkUploadDto artworkDto) {
        this.stockAmount = artworkDto.getStockAmount();
        categorySet(artwork.getCategories());
        materialSet(artwork.getMaterials());
        artworkImageSet(artwork.getArtworkImages());
        artDeliveryConvert(artwork.getShipping());
        this.name = artwork.getTitle() + "-" + artwork.getMember().getNickname();
        setContents(artwork.getArtworkImages());
        setSimpleDescription(artwork);
        setDeliveryPrice(artwork.getDeliveryPrice());
        this.prodStatus = "sale";
        this.prodType = "normal";
        this.price = Integer.valueOf(artwork.getPrice()).floatValue();
        this.minPurchaseQuantity = 1;
        this.stockUse = true;
        this.stockUnlimit = false;
    }

    private void setDeliveryPrice(int deliveryPrice) {
        if (deliveryPrice == 5000)
            this.weight = 2;
        else if (deliveryPrice == 7000)
            this.weight = 3;
        else
            this.weight = 1;
    }

    private void setSimpleDescription(Artwork artwork) {
        if (artwork == null ||
                artwork.getProductionYear() == null ||
                artwork.getSize().getHeight() == 0 ||
                artwork.getSize().getWidth() == 0 ||
                artwork.getMaterials() == null)
            return;
        StringBuilder sb = new StringBuilder();

        // 프린트에디션이 아닐경우 stockAmount 가 0으로 초기화된상태,
        // 프린트에디션일경우 이 메소드에 들어오기전에 1이상으로 미리 값을 설정한다
        if (this.stockAmount != 0)
            sb.append("[프린트 에디션]<br>");
        sb.append(artwork.getProductionYear());
        sb.append("<br>");
        sb.append(artwork.getSize().getHeight());
        sb.append(" X ");
        sb.append(artwork.getSize().getWidth());
        sb.append(" cm <br>");
        List<Material> materials = artwork.getMaterials();
        for (Material material : materials) {
            sb.append(material.getMaterial().name());
            sb.append(" ");
        }
        ArtworkFrame artworkFrame = artwork.getArtworkFrame();
        if (artworkFrame!=null) {
            sb.append("<br>(액자포함 크기)<br>");
            sb.append(artworkFrame.getSize().getHeight());
            sb.append(" X ");
            sb.append(artworkFrame.getSize().getWidth());
            sb.append(" cm <br>");
            sb.append("<br> 액자재질 : " + artworkFrame.getMaterial());
        }
        this.simpleContent = sb.toString();
    }
    private void setContents(List<ArtworkImage> artworkImage) {
        if (artworkImage == null)
            return;
        StringBuilder sb = new StringBuilder();

        for (ArtworkImage image : artworkImage) {
            sb.append("<img src='" + image.getName() + "' style='width: 700px; height: auto;'>" + "<br>");
            sb.append(convertSpecialStringAtDescription(image.getDescription()));
            sb.append("<br>");
            sb.append("<br>");
        }
        this.content = sb.toString();
    }

    private String convertSpecialStringAtDescription(String description) {
        description = description.replaceAll(">", "&gt");
        description = description.replaceAll("<", "&lt");
        description = description.replaceAll("\n", "<br>");
        return description;
    }

    private void artDeliveryConvert(ShippingEnum shippingEnum) {
        if (shippingEnum.equals(ShippingEnum.ARTDELIVERY_SHIPPING))
            categories.add(CategoryWebEnum.ARTONHANGER_PASS.getCategoryCode());
    }

    private void categorySet(List<Category> categoryList) {
        if (categories == null)
            return;
        categories.add(CategoryWebEnum.ALL_PRODUCT.getCategoryCode());
        for (int i = 0; i < categoryList.size(); i++) {
            categoryConvert(categoryList.get(i).getCategoryEnum());
        }
    }

    private void categoryConvert(CategoryEnum categoryEnum) {
        if (categoryEnum.equals(CategoryEnum.LANDSCAPE))
            categories.add(CategoryWebEnum.LANDSCAPE.getCategoryCode());
        if (categoryEnum.equals(CategoryEnum.PERSON))
            categories.add(CategoryWebEnum.PERSON.getCategoryCode());
        if (categoryEnum.equals(CategoryEnum.OBJECT))
            categories.add(CategoryWebEnum.OBJECT.getCategoryCode());
        if (categoryEnum.equals(CategoryEnum.ABSTRACT))
            categories.add(CategoryWebEnum.ABSTRACT.getCategoryCode());
        if (categoryEnum.equals(CategoryEnum.CHARACTER))
            categories.add(CategoryWebEnum.CHARACTER.getCategoryCode());
        if (categoryEnum.equals(CategoryEnum.SCULPTURE))
            categories.add(CategoryWebEnum.SCULPTURE_M.getCategoryCode());
    }

    private void materialSet(List<Material> materials) {
        if (materials == null)
            return;
        categories.add(CategoryWebEnum.ALL_MATERIALS.getCategoryCode());
        for (int i = 0; i < materials.size(); i++) {
            materialConvert(materials.get(i).getMaterial());
        }
    }

    private void materialConvert(MaterialEnum material) {
        if (material.equals(MaterialEnum.WATERCOLORS))
            categories.add(CategoryWebEnum.WATERCOLOR.getCategoryCode());
        if (material.equals(MaterialEnum.PENCILS))
            categories.add(CategoryWebEnum.PENCIL.getCategoryCode());
        if (material.equals(MaterialEnum.ACRYLICPAINT))
            categories.add(CategoryWebEnum.ARCRYLE.getCategoryCode());
        if (material.equals(MaterialEnum.OILCOLORS))
            categories.add(CategoryWebEnum.OIL_COLOR.getCategoryCode());
        if (material.equals(MaterialEnum.OILPASTEL))
            categories.add(CategoryWebEnum.OIL_PASTEL.getCategoryCode());
        if (material.equals(MaterialEnum.SCULPTURE))
            categories.add(CategoryWebEnum.SCULPTURE_M.getCategoryCode());
    }

    private void artworkImageSet(List<ArtworkImage> artworkImages) {
        if (artworkImages == null)
            return;
        for (ArtworkImage artworkImage : artworkImages) {
            images.add(artworkImage.getName());
        }
    }
}

