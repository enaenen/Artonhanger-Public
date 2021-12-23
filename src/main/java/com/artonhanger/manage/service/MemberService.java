package com.artonhanger.manage.service;

import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.UserDetailsImpl;
import com.artonhanger.manage.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("memberService")
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberWithArtistByEmail(username);
        if(member==null){
            throw new UsernameNotFoundException(username);
        }
        return UserDetailsImpl
                .builder()
                .member(member)
                .build();
    }
}
