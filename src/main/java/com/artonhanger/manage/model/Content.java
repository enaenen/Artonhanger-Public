package com.artonhanger.manage.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String thumbnail;
    private String image;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<ContentArtworkPivot> contentArtworkPivots = new ArrayList<>();

    @Embedded
    private DbMetaProperty meta;
}
