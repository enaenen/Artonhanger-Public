package com.artonhanger.manage.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;

    @Setter
    private String introduction;
    private String phoneNumber;

    @Embedded
    private BankAccount bankAccount;

    @Embedded
    @Builder.Default
    private DbMetaProperty meta = new DbMetaProperty();

    public void changeAccount(@NonNull Artist.BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Builder
    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class BankAccount {
        private String bankName;
        private String account;
        private String accountHolder;
    }
}