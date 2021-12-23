package com.artonhanger.manage.model;

import com.artonhanger.manage.enums.MaterialEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Material {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    @Setter
    private Artwork artwork;

    @Enumerated(EnumType.STRING)
    private MaterialEnum material;

    public Material(Artwork artwork, MaterialEnum material) {
        this.artwork = artwork;
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material1 = (Material) o;
        return material.name().equals(material1.material.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(material);
    }
}
