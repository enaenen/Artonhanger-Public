package com.artonhanger.manage.service;

import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.enums.UserRole;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.Authority;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.model.dto.RegisterUserDto;
import com.artonhanger.manage.respository.ArtistRepository;
import com.artonhanger.manage.respository.AuthorityRepository;
import com.artonhanger.manage.respository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MemberRegisterServiceTest {

    @InjectMocks
    MemberRegisterService memberRegisterService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    AuthorityRepository authorityRepository;

    @Mock
    ArtistRepository artistRepository;

    @Mock
    MemberProfileUploadService memberProfileUploadService;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    private RegisterUserDto registerUserDto = new RegisterUserDto(
            "test@email.com",
            "testnickname",
            "testname",
            "testpassword",
            "testbanks",
            "testaccount",
            "testphonenumber",
            "testintroduction",
            new MockMultipartFile(
                    "image",
                    "hello.txt",
                    MediaType.TEXT_PLAIN_VALUE,
                    "Hello, World!".getBytes()
            )
    );

    private ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);
    private ArgumentCaptor<Authority> authorityArgumentCaptor = ArgumentCaptor.forClass(Authority.class);
    private ArgumentCaptor<Artist> artistArgumentCaptor = ArgumentCaptor.forClass(Artist.class);

    @BeforeEach
    void setUp() throws Exception {
        given(memberRepository.existsMemberByEmail(eq("test@email.com")))
                .willReturn(false);
        given(memberRepository.existsByNickname(eq("testnickname")))
                .willReturn(false);
        given(passwordEncoder.encode(eq("testpassword")))
                .willReturn("encryptedpassword");
        Member savedMember = Member.builder()
                .id(1L)
                .name("testname")
                .nickname("testnickname")
                .email("test@email.com")
                .password("encryptedpassword")
                .alarmAgreement(true)
                .build();
        given(memberRepository.save(memberArgumentCaptor.capture())).willReturn(savedMember);
        given(memberProfileUploadService.memberProfileImageUpload(any(), eq(1L)))
                .willReturn("testprofileurl");
        Authority savedAuthority = new Authority();
        savedAuthority.setMember(savedMember);
        savedAuthority.setRole(UserRole.USER);
        given(authorityRepository.save(authorityArgumentCaptor.capture())).willReturn(savedAuthority);
        Artist savedArtist = Artist.builder().member(savedMember)

                .bankAccount(Artist.BankAccount.builder()
                        .bankName("testbanks")
                        .accountHolder("testname")
                        .account("testaccount").build())
                .phoneNumber("testphonenumber")
                .introduction("testintroduction")
                .build();
        given(artistRepository.save(artistArgumentCaptor.capture())).willReturn(savedArtist);
    }

    @Test
    void 회원가입_테스트() throws IOException {
        memberRegisterService.register(registerUserDto);

        Member argumentCapturedMember = memberArgumentCaptor.getValue();
        assertEquals("testname", argumentCapturedMember.getName());
        assertEquals("testnickname", argumentCapturedMember.getNickname());
        assertEquals("test@email.com", argumentCapturedMember.getEmail());
        assertEquals("encryptedpassword", argumentCapturedMember.getPassword());
        assertEquals(true, argumentCapturedMember.getAlarmAgreement());
//        assertEquals("testprofileurl", argumentCapturedMember.getProfileImg());
//        assertEquals(UserRole.USER, argumentCapturedMember.getRoles().get(0).getRole());
//        assertEquals("testintroduction", argumentCapturedMember.getArtist().getIntroduction());

        List<Authority> argumentCapturedAuthority = authorityArgumentCaptor.getAllValues();
        assertEquals(UserRole.USER, argumentCapturedAuthority.get(0).getRole());
        assertEquals(UserRole.ARTIST, argumentCapturedAuthority.get(1).getRole());

        Artist argumentCapturedArtist = artistArgumentCaptor.getValue();
//        assertEquals(argumentCapturedMember, argumentCapturedArtist.getMember());
//        assertEquals(argumentCapturedArtist, argumentCapturedMember.getArtist());
        assertEquals(Artist.BankAccount.builder()
                .bankName("testbanks")
                .account("testaccount")
                .accountHolder("testname").build(), argumentCapturedArtist.getBankAccount());
        assertEquals("testphonenumber", argumentCapturedArtist.getPhoneNumber());
        assertEquals("testintroduction", argumentCapturedArtist.getIntroduction());

    }

    @Test
    void 회원가입_이메일중복_테스트() {
        given(memberRepository.existsMemberByEmail(eq("test@email.com")))
                .willReturn(true);

        AOHException aohException = assertThrows(AOHException.class, () -> memberRegisterService.register(registerUserDto));
        assertEquals(ErrorEnum.MEMBER_DUPLICATED_ID, aohException.getErrorEnum());
    }

    @Test
    void 회원가입_닉네임중복_테스트() {
        given(memberRepository.existsByNickname(eq("testnickname")))
                .willReturn(true);

        AOHException aohException = assertThrows(AOHException.class, () -> memberRegisterService.register(registerUserDto));
        assertEquals(ErrorEnum.MEMBER_DUPLICATED_PROPS, aohException.getErrorEnum());
    }
}