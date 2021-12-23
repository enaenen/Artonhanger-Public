package com.artonhanger.manage.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"member_id","artwork_id"}
                )
        }
)
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    @Embedded
    private DbMetaProperty meta = new DbMetaProperty();

    @Override
    public boolean equals(Object o) {
        if(o instanceof Long) {
            Long memberId = (Long) o;
            return getMember() != null && getMember().getId().equals(memberId);
        }
        else
            return false;
    }

}
