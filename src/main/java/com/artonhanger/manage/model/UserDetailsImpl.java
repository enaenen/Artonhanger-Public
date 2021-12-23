package com.artonhanger.manage.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsImpl implements UserDetails {
    private Member member;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserDetailsImpl(Member member){
        this.member=member;
        this.authorities=member.getRoles().stream().map(
                authority -> new SimpleGrantedAuthority(authority.getRole().getValue())
        ).collect(Collectors.toList());
    }


    //계정이 갖고있는 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    //계정이 만료되지 않았는지 확인
    @Override
    public boolean isAccountNonExpired() {
        return member.getMeta().isEnable();
    }

    //계정이 잠겨있지 않은지 확인
    @Override
    public boolean isAccountNonLocked() {
        return member.getMeta().isEnable();
    }

    //계정의 비밀번호가 만료되지 않았는지 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return member.getMeta().isEnable();
    }

    //계정이 사용가능한 계정인지 확인
    @Override
    public boolean isEnabled() {
        return member.getMeta().isEnable();
    }
}
