package com.artonhanger.manage.service;

import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.dto.ModifyProfileDto;
import com.artonhanger.manage.model.dto.PasswordModifyDto;
import com.artonhanger.manage.respository.MemberRepository;
import com.artonhanger.manage.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberModifyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberRepository memberRepository;
    private final MailSendService mailSendService;
    private final MemberProfileUploadService memberProfileUploadService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordUtil passwordUtil;

    @Transactional
    public void modifyPassword(PasswordModifyDto passwordModifyDto) {
        verifyInput(passwordModifyDto.getEmail()); // TODO [hkpark] javax.validation 사용 추천
        verifyInput(passwordModifyDto.getChangedPassword());
        Member findMember = memberRepository.findMemberByEmail(passwordModifyDto.getEmail())
                .orElseThrow(()->new AOHException(ErrorEnum.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(passwordModifyDto.getPrevPassword(), findMember.getPassword()))
            throw new AOHException(ErrorEnum.MEMBER_NOT_FOUND);
        String encodedPassword = passwordEncoder.encode(passwordModifyDto.getChangedPassword());
        findMember.changePassword(encodedPassword);
    }

    private void verifyInput(String input) {
        if (!StringUtils.hasText(input))
            throw new AOHException(ErrorEnum.MEMBER_FORMAT_ERROR);
    }

    @Transactional
    public void resetPassword(String email) {
        Member member = memberRepository.findMemberByEmail(email).orElseThrow(()
                -> new AOHException(ErrorEnum.MEMBER_NOT_FOUND));
        String tempPassword = passwordUtil.generateRandomPassword(12);
        member.changePassword(passwordEncoder.encode(tempPassword));
        logger.info(email + " ======= password changed : " + tempPassword);
        mailSendService.sendNewPassword(email,tempPassword);
    }

    @Transactional
    public void modifyProfile(ModifyProfileDto modifyProfileDto) throws IOException {
        Member member = memberRepository.findMemberWithArtistByEmail(modifyProfileDto.getEmail());
        member.changeNickname(modifyProfileDto.getNickname());
        member.changeName(modifyProfileDto.getName());
        member.getArtist().setIntroduction(modifyProfileDto.getIntroduction());
        if (isBankAccountValid(modifyProfileDto)) {
            member.getArtist().changeAccount(Artist.BankAccount.builder()
                            .accountHolder(modifyProfileDto.getName())
                            .bankName(modifyProfileDto.getBankName())
                            .account(modifyProfileDto.getAccount())
                            .build());
        }

        String profileImgUrl = memberProfileUploadService.memberProfileImageUpload(modifyProfileDto.getProfileImg(), member.getId());
        member.changeProfileImage(profileImgUrl);
    }

    private boolean isBankAccountValid(ModifyProfileDto modifyProfileDto) {
        return StringUtils.hasText(modifyProfileDto.getAccount()) &&
                StringUtils.hasText(modifyProfileDto.getBankName()) &&
                StringUtils.hasText(modifyProfileDto.getName());
    }

    //TODO 기존에 프로필사진이 존재할때 삭제하는것을 구현해야함 21-06-28
    // [hkpark] 배치로 돌면서 사용되지 않은 프로필사진은 삭제하는 방법도 있음
    private void modifyProfileImage(Member member){
        if(member.getProfileImg() != null)
            memberProfileUploadService.memberProfileImageDelete("profileImages/"+member.getId().toString());

    }
}
