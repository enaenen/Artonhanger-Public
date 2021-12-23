package com.artonhanger.manage.respository.core;

import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.respository.MemberRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DatabaseSetup({
        "classpath:dbunit/dataset/member.xml",
})
class MemberRepositoryCoreTest extends RepositoryCoreTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 전체_레코드_조회_테스트() {
        List<Member> members = memberRepository.findAll();

        assertEquals(13, members.size());
        assertEquals(1L, members.get(0).getId());
        assertEquals(10L, members.get(9).getId());
    }

    @Test
    public void 특정_레코드_조회_테스트() {
        Member member = memberRepository.findById(1L).orElseThrow();

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
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/MemberRepositoryCoreTest/레코드_추가_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_추가_테스트() {
        Member member = Member.builder()
                .email("test@email.com")
                .name("테스트")
                .nickname("닉테스트")
                .password("1234")
                .profileImg("img-path")
                .alarmAgreement(true)
                .build();
        member = memberRepository.saveAndFlush(member);

        assertEquals(LocalDateTime.now().getYear(), member.getMeta().getCreatedAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), member.getMeta().getCreatedAt().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), member.getMeta().getCreatedAt().getDayOfMonth());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/MemberRepositoryCoreTest/레코드_수정_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_수정_테스트() {
        Member member = memberRepository.findById(1L).orElseThrow();
        member.changeName("이름변경");
        memberRepository.flush();

        assertEquals(LocalDateTime.now().getYear(), member.getMeta().getUpdatedAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), member.getMeta().getUpdatedAt().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), member.getMeta().getUpdatedAt().getDayOfMonth());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/MemberRepositoryCoreTest/레코드_삭제_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_삭제_테스트() {
        Member member = memberRepository.findById(1L).orElseThrow();
        memberRepository.delete(member);
        memberRepository.flush();
    }
}