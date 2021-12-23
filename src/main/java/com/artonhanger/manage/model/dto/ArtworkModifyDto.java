package com.artonhanger.manage.model.dto;

import com.artonhanger.manage.enums.ShippingEnum;
import com.artonhanger.manage.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArtworkModifyDto {
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
    private String isEdition;
    @NotNull
    private Integer stockAmount=1;

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
    public boolean isEdition(){
        if(StringUtils.hasText(isEdition) && isEdition.equals("true"))
            return true;
        else
            return false;
    }
}
