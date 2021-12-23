package com.artonhanger.manage.model;

import com.artonhanger.manage.enums.DeliveryStateEnum;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<OrderedArtworkList> orderedArtworkLists;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_mem_info_id")
    private OrderMemInfo orderMemInfo;

    @Enumerated(EnumType.STRING)
    private DeliveryStateEnum deliveryState;

    private String impUid;
    private String merchantUid;

    private String customerName;
    @Embedded
    private Payment payment;

    private boolean isRemoteArea;
    private int remoteAreaPrice;

    @Embedded
    @Builder.Default
    private DbMetaProperty meta = new DbMetaProperty();

    @Embeddable @Getter @NoArgsConstructor @AllArgsConstructor @Builder @EqualsAndHashCode
    public static class Payment {
        @Column(name = "payment_phone") private String phone;
        @Column(name = "payment_method") private String method;
        @Column(name = "payment_price") private int price;
    }
}
