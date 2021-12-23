package com.artonhanger.manage.respository;

import com.artonhanger.manage.annotation.AohDataTest;
import com.artonhanger.manage.enums.UserRole;
import com.artonhanger.manage.model.Member;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@AohDataTest
@DatabaseSetup({
        "classpath:dbunit/dataset/member.xml",
        "classpath:dbunit/dataset/artist.xml",
        "classpath:dbunit/dataset/authority.xml",
})
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void is_enable이_true인_회원_이메일로_찾기() {
        Member member = memberRepository.findMemberByEmail("oh11111@gmail.com").orElseThrow();
        assertEquals("오*진", member.getName());
        assertEquals(1, member.getRoles().size());
        assertEquals(UserRole.USER, member.getRoles().get(0).getRole());
        assertEquals("오일파스텔로 그림을 그리고있는, 작가입니다.", member.getArtist().getIntroduction());
    }

    @Test
    void is_enable이_false면_회원_이메일로_못찾아야함() {
        assertThrows(NoSuchElementException.class, () -> {
            memberRepository.findMemberByEmail("kimc11111@gmail.com").orElseThrow();
        });
    }

    @Test
    void 작가_정보와_함께_회원_id로_찾기() {
        Member member = memberRepository.findMemberWithArtistById(1L);

        assertEquals("오일파스텔로 그림을 그리고있는, 작가입니다.", member.getArtist().getIntroduction());
        assertEquals(1L, member.getId());
        assertEquals("oh11111@gmail.com", member.getEmail());
        assertEquals("오*진", member.getName());
        assertEquals("오*진", member.getNickname());
        assertEquals("1111", member.getPassword());
        assertEquals("https://d3cr9r836ml7sc.cloudfront.net/profileImages/3/1111", member.getProfileImg());
        assertTrue(member.getMeta().isEnable());
        assertEquals(true, member.getAlarmAgreement());
        assertEquals(LocalDateTime.of(2020,11,20,19,51,36, 672000000), member.getMeta().getCreatedAt());
        assertEquals(LocalDateTime.of(2021,6,30,23,26,30, 344000000), member.getMeta().getUpdatedAt());
    }

    @Test
    void 작가_정보와_함께_회원_이메일로_찾기() {
        Member member = memberRepository.findMemberWithArtistByEmail("oh11111@gmail.com");

        assertEquals("오일파스텔로 그림을 그리고있는, 작가입니다.", member.getArtist().getIntroduction());
        assertEquals(1L, member.getId());
        assertEquals("oh11111@gmail.com", member.getEmail());
        assertEquals("오*진", member.getName());
        assertEquals("오*진", member.getNickname());
        assertEquals("1111", member.getPassword());
        assertEquals("https://d3cr9r836ml7sc.cloudfront.net/profileImages/3/1111", member.getProfileImg());
        assertTrue(member.getMeta().isEnable());
        assertEquals(true, member.getAlarmAgreement());
        assertEquals(LocalDateTime.of(2020,11,20,19,51,36, 672000000), member.getMeta().getCreatedAt());
        assertEquals(LocalDateTime.of(2021,6,30,23,26,30, 344000000), member.getMeta().getUpdatedAt());
    }

    @Test
    void 회원_id로_찾기() {
        Member member = memberRepository.findMemberById(1L);

        assertEquals("오일파스텔로 그림을 그리고있는, 작가입니다.", member.getArtist().getIntroduction());
        assertEquals(1L, member.getId());
        assertEquals("oh11111@gmail.com", member.getEmail());
        assertEquals("오*진", member.getName());
        assertEquals("오*진", member.getNickname());
        assertEquals("1111", member.getPassword());
        assertEquals("https://d3cr9r836ml7sc.cloudfront.net/profileImages/3/1111", member.getProfileImg());
        assertTrue(member.getMeta().isEnable());
        assertEquals(true, member.getAlarmAgreement());
        assertEquals(LocalDateTime.of(2020,11,20,19,51,36, 672000000), member.getMeta().getCreatedAt());
        assertEquals(LocalDateTime.of(2021,6,30,23,26,30, 344000000), member.getMeta().getUpdatedAt());
    }

    @Test
    void 회원_이메일로_존재하는지_확인() {
        assertTrue(memberRepository.existsMemberByEmail("oh11111@gmail.com"));
        assertFalse(memberRepository.existsMemberByEmail("not-exist@gmail.com"));
    }

    @Test
    void 회원_닉네임으로_존재하는지_확인() {
        assertTrue(memberRepository.existsByNickname("오*진"));
        assertFalse(memberRepository.existsByNickname("존재하지않는닉넴"));
    }
}