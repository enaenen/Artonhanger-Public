package com.artonhanger.manage.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArtworkImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Embedded
    private DbMetaProperty meta = new DbMetaProperty();

    @Builder
    public ArtworkImage(String name, String description){
        this.name = name;
        this.description = description;
        this.meta = new DbMetaProperty();
    }
}
