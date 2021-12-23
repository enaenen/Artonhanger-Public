package com.artonhanger.manage.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"email", "profileImg"})
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookMarks = new ArrayList<Bookmark>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<Authority>();
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();
    @OneToMany(mappedBy = "member")
    private List<Artwork> artworks = new ArrayList<Artwork>();
    @OneToMany(mappedBy = "member")
    private List<OrderMemInfo> orderMemInfos = new ArrayList<>();
    @OneToOne(mappedBy = "member")
    private Artist artist;
    @Column(unique = true, length = 50)
    private String email;
    private String name;
    @Column(unique = true)
    private String nickname;
    private String password;
    private String token;
    private String profileImg;
    private Boolean alarmAgreement = false;

    @Embedded
    @Builder.Default
    private DbMetaProperty meta = new DbMetaProperty();

    //매핑 편의 메소드
    public List<Authority> addRole(Authority authority) {
        if (this.roles == null)
            this.roles = new ArrayList<>();
        this.roles.add(authority);
        authority.setMember(this);
        return this.roles;
    }

    public Artist setArtist(Artist artist) {
        this.artist = artist;
        artist.setMember(this);
        return this.artist;
    }


    public void changePassword(String password) {
        this.password = password;
    }
    public void changeName(String name){
        if(hasText(name))
            this.name = name;
    }

    public void changeNickname(String changedNickname) {
        if(hasText(changedNickname))
            this.nickname = changedNickname;
    }

    public void changeProfileImage(String memberProfileImg) {
        if(hasText(memberProfileImg))
            this.profileImg = memberProfileImg;
    }
}
