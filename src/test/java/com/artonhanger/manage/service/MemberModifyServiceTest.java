package com.artonhanger.manage.service;

import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.dto.ModifyProfileDto;
import com.artonhanger.manage.model.dto.PasswordModifyDto;
import com.artonhanger.manage.respository.MemberRepository;
import com.artonhanger.manage.utils.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MemberModifyServiceTest {
    @InjectMocks
    MemberModifyService memberModifyService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    MailSendService mailSendService;

    @Mock
    MemberProfileUploadService memberProfileUploadService;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    PasswordUtil passwordUtil;

    private final Member member = Member.builder()
            .name("testname")
            .nickname("testnickname")
            .email("test@email.com")
            .password("encryptedPrevPassword")
            .profileImg("testprofileImg")
            .alarmAgreement(true)
            .build();

    private final Artist artist = Artist.builder()
            .member(member)
            .bankAccount(Artist.BankAccount.builder()
                    .account("123123123")
                    .bankName("국민은행 ")
                    .accountHolder("testname")
                    .build())
            .introduction("testintroduction")
            .phoneNumber("010-1234-1234")
            .build();

    @Test
    void 회원_비밀번호_변경_테스트() {
        PasswordModifyDto passwordModifyDto = new PasswordModifyDto(
                "test@email.com",
                "changedPassword",
                "prevPassword"
        );

        given(memberRepository.findMemberByEmail(eq("test@email.com"))).willReturn(Optional.of(member));
        given(passwordEncoder.matches(eq("prevPassword"), eq("encryptedPrevPassword"))).willReturn(true);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        given(passwordEncoder.encode(passwordCaptor.capture())).willReturn("encryptedChangedPassword");

        memberModifyService.modifyPassword(passwordModifyDto);

        assertEquals("changedPassword", passwordCaptor.getValue());
        assertEquals("encryptedChangedPassword", member.getPassword());
    }

    @Test
    void 회원_비밀번호_초기화_테스트() {
        given(memberRepository.findMemberByEmail(eq("test@email.com"))).willReturn(Optional.of(member));
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        given(passwordEncoder.encode(passwordCaptor.capture())).willReturn("encryptedRandomPassword");
        doNothing().when(mailSendService).sendNewPassword(any(), any());
        given(passwordUtil.generateRandomPassword(12)).willReturn("12345678abcd");
        memberModifyService.resetPassword("test@email.com");

        assertEquals(12, passwordCaptor.getValue().length());
        assertEquals("encryptedRandomPassword", member.getPassword());
    }

    @Test
    void 회원_프로필_수정_테스트() throws IOException {
        ModifyProfileDto modifyProfileDto = new ModifyProfileDto(
                "test@email.com",
                "modifiednickname",
                "modifiedname",
                "modifiedintroduction",
                "modifiedbanks",
                "modifiedaccount",
                new MockMultipartFile(
                        "image",
                        "hello.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "Hello, World!".getBytes()
                )
        );

        member.setArtist(artist);
        given(memberRepository.findMemberWithArtistByEmail(eq("test@email.com"))).willReturn(member);
        given(memberProfileUploadService.memberProfileImageUpload(any(), any()))
                .willReturn("modifiedprofileurl");

        memberModifyService.modifyProfile(modifyProfileDto);

        assertEquals("modifiednickname", member.getNickname());
        assertEquals("modifiedname", member.getName());
        assertEquals("modifiedintroduction", artist.getIntroduction());
        assertEquals(Artist.BankAccount.builder()
                .account("modifiedaccount")
                .bankName("modifiedbanks")
                .accountHolder("modifiedname").build(), artist.getBankAccount());
        assertEquals("modifiedprofileurl", member.getProfileImg());
    }
}