package com.artonhanger.manage.model;

import com.artonhanger.manage.model.dto.ArtworkUploadDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ArtworkFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    private ArtworkSize size;

    private String material;
    @OneToOne(mappedBy = "artworkFrame")
    private Artwork artwork;

    @Builder
    public ArtworkFrame(double height, double width, String material){
        this.size = ArtworkSize.builder().height(height).width(width).build();
        this.material = material;
    }
    public void changeHeight(double height){
        if (height <= 0)
            return;
        this.size.setHeight(height);
    }
    public void changeWidth(double width){
        if (width <= 0)
            return;
        this.size.setWidth(width);
    }
    public void changeMaterial(String material){
        if (material == null || material.equals(""))
            return;
        this.material = material;
    }

}
