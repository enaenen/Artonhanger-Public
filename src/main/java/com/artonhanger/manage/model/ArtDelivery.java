package com.artonhanger.manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private String impUid;

    //customerUid = "email@email.com_2020-11-20 20:55:55";
    private String customerUid;
    //merchantUid = "artdelivery_1605873500863"     artdelivery_"+new Date().getTime();
    private String merchantUid;

    private String nextCustomerUid;

    @Embedded
    private DbMetaProperty meta = new DbMetaProperty();
}
