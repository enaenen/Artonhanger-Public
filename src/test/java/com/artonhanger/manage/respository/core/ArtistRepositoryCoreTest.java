package com.artonhanger.manage.respository.core;

import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.respository.ArtistRepository;
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
        "classpath:dbunit/dataset/artist.xml"
})
class ArtistRepositoryCoreTest extends RepositoryCoreTest {

    @Autowired
    ArtistRepository artistRepository;

    @Test
    public void 전체_레코드_조회_테스트() {
        List<Artist> artists = artistRepository.findAll();

        assertEquals(10, artists.size());
        assertEquals(1L, artists.get(0).getId());
        assertEquals(10L, artists.get(9).getId());
    }

    @Test
    public void 특정_레코드_조회_테스트() {
        Artist artist = artistRepository.findById(1L).orElseThrow();

        assertEquals(1L, artist.getId());
        assertEquals(1L, artist.getMember().getId());
        assertEquals(Artist.BankAccount.builder()
                .bankName("하나은행 ")
                .account("411-9111111-11117")
                .accountHolder("오*진")
                .build(), artist.getBankAccount());
        assertEquals("오일파스텔로 그림을 그리고있는, 작가입니다.", artist.getIntroduction());
        assertEquals("010-1111-1111", artist.getPhoneNumber());
        assertTrue(artist.getMeta().isEnable());
        assertEquals(LocalDateTime.of(2020,11,20,20,10,47, 0), artist.getMeta().getCreatedAt());
        assertEquals(LocalDateTime.of(2021,2,15,22,36,3, 163000000), artist.getMeta().getUpdatedAt());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtistRepositoryCoreTest/레코드_추가_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_추가_테스트() {
        Member member = Member.builder().id(11L).build();

        Artist artist = Artist.builder()
                .member(member)
                .bankAccount(Artist.BankAccount.builder()
                        .bankName("국민은행 ")
                        .account("123123123")
                        .accountHolder("내이름")
                        .build())
                .introduction("테스트소개")
                .phoneNumber("010-1234-1234")
                .build();
        artistRepository.saveAndFlush(artist);
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtistRepositoryCoreTest/레코드_수정_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_수정_테스트() {
        Artist artist = artistRepository.findById(1L).orElseThrow();
        artist.changeAccount(Artist.BankAccount.builder()
                                .bankName("국민은행 ")
                                .account("123123123")
                                .accountHolder("오*진")
                                .build());
        artistRepository.flush();
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/ArtistRepositoryCoreTest/레코드_삭제_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_삭제_테스트() {
        Artist artist = artistRepository.findById(1L).orElseThrow();
        artistRepository.delete(artist);
        artistRepository.flush();
    }
}