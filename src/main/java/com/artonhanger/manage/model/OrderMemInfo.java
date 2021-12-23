package com.artonhanger.manage.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMemInfo {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "orderMemInfo")
    private Order order;

    private String name;
    private String phone;
    private String address;
    private String addressDetails;
    private String postNum;
}
