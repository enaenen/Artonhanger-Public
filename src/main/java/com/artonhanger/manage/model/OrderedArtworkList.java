package com.artonhanger.manage.model;


import com.artonhanger.manage.enums.DeliveryStateEnum;
import com.artonhanger.manage.enums.ShippingEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderedArtworkList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStateEnum deliveryState;

    @Enumerated(EnumType.STRING)
    private ShippingEnum shipping;

    @Embedded
    private Delivery delivery;

    private int price;
    private int deliveryPrice;

    @Embeddable @Getter @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode
    public static class Delivery {
        @Column(name = "delivery_company_id") private String companyId;
        @Column(name = "delivery_company") private String company;
        @Column(name = "tracking_number") private String trackingNumber;
    }
}
