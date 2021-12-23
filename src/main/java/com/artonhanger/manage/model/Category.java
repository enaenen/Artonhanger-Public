package com.artonhanger.manage.model;


import com.artonhanger.manage.enums.CategoryEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    @Setter
    private Artwork artwork;

    public Category(Artwork artwork, CategoryEnum categoryEnum){
        this.categoryEnum=categoryEnum;
        this.artwork = artwork;
    }
}
