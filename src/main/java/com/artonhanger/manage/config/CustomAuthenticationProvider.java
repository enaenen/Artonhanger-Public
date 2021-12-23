package com.artonhanger.manage.config;

import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.model.UserDetailsImpl;
import com.artonhanger.manage.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;


@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Resource(name = "memberService")
    private MemberService memberService;
    private BCryptPasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String userEmail = token.getName();
        String userPw = (String)token.getCredentials();
        // UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        try {
            UserDetailsImpl userDetails = memberService.loadUserByUsername(userEmail);
            if (!passwordEncoder.matches(userPw, userDetails.getPassword())) {
                throw new AOHException(ErrorEnum.MEMBER_PASSWORD_NOT_CORRECT);
            }
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        catch(UsernameNotFoundException e){
            throw new AOHException(ErrorEnum.MEMBER_NOT_FOUND);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}