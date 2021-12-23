package com.artonhanger.manage.service;

import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.enums.UserRole;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.Authority;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.dto.RegDupCheckDto;
import com.artonhanger.manage.model.dto.RegisterUserDto;
import com.artonhanger.manage.respository.ArtistRepository;
import com.artonhanger.manage.respository.AuthorityRepository;
import com.artonhanger.manage.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberRegisterService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final ArtistRepository artistRepository;
    private final MemberProfileUploadService memberProfileUploadService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterUserDto registerUserDto) throws IOException {
        duplicationCheck(new RegDupCheckDto(registerUserDto));

        Member member = Member.builder()
                .name(registerUserDto.getName())
                .nickname(registerUserDto.getNickname())
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .alarmAgreement(true).build();
        Member savedMember = memberRepository.save(member);

        String profileUrl = memberProfileUploadService.memberProfileImageUpload(registerUserDto.getProfileImage(), savedMember.getId());
        savedMember.changeProfileImage(profileUrl);

        Authority authority = Authority.addAuthority(savedMember, UserRole.USER);
        Authority savedAuthority = authorityRepository.save(authority);
        savedMember.addRole(savedAuthority);

        Artist artist = Artist.builder().member(savedMember)
                .bankAccount(Artist.BankAccount.builder()
                        .account(registerUserDto.getAccount())
                        .bankName(registerUserDto.getBanks())
                        .accountHolder(registerUserDto.getName())
                        .build())
                .phoneNumber(registerUserDto.getPhoneNumber())
                .introduction(registerUserDto.getIntroduction())
                .member(savedMember)
                .build();
        Artist savedArtist = artistRepository.save(artist);
        authorityRepository.save(Authority.addAuthority(savedMember,UserRole.ARTIST));
        savedMember.setArtist(savedArtist);
    }

    public void duplicationCheck(RegDupCheckDto regDupCheckDto){
        if(memberRepository.existsMemberByEmail(regDupCheckDto.getEmail()))
            throw new AOHException(ErrorEnum.MEMBER_DUPLICATED_ID);
        else if(memberRepository.existsByNickname(regDupCheckDto.getNickname()))
            throw new AOHException(ErrorEnum.MEMBER_DUPLICATED_PROPS);
    }
}
