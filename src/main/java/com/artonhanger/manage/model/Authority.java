package com.artonhanger.manage.model;


import com.artonhanger.manage.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

//    @Column(name = "role_id")
//    @Convert(converter = AuthorityAttributeConverter.class)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static Authority addAuthority(Member member, UserRole userRole){
        Authority authority = new Authority();
        authority.setMember(member);
        authority.setRole(userRole);
        return authority;
    }
}

