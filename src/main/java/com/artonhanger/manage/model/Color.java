package com.artonhanger.manage.model;


import com.artonhanger.manage.enums.ColorEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Color {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    @Setter
    private Artwork artwork;

    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    public Color(Artwork artwork, ColorEnum color) {
        this.artwork = artwork;
        this.color = color;
    }
}
