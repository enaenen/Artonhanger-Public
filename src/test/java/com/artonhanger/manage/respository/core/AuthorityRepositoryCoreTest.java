package com.artonhanger.manage.respository.core;

import com.artonhanger.manage.enums.UserRole;
import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.Authority;
import com.artonhanger.manage.model.Member;
import com.artonhanger.manage.respository.ArtistRepository;
import com.artonhanger.manage.respository.AuthorityRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DatabaseSetup({
        "classpath:dbunit/dataset/member.xml",
        "classpath:dbunit/dataset/authority.xml",
})
class AuthorityRepositoryCoreTest extends RepositoryCoreTest {

    @Autowired
    AuthorityRepository authorityRepository;

    @Test
    public void 전체_레코드_조회_테스트() {
        List<Authority> authorities = authorityRepository.findAll();

        assertEquals(13, authorities.size());
        assertEquals(1L, authorities.get(0).getId());
        assertEquals(10L, authorities.get(9).getId());
    }

    @Test
    public void 특정_레코드_조회_테스트() {
        Authority authority = authorityRepository.findById(1L).orElseThrow();

        assertEquals(1L, authority.getId());
        assertEquals(1L, authority.getMember().getId());
        assertEquals(UserRole.USER, authority.getRole());
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/AuthorityRepositoryCoreTest/레코드_추가_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_추가_테스트() {
        Member member = Member.builder().id(1L).build();

        Authority authority = Authority.addAuthority(member, UserRole.ARTIST);
        authorityRepository.saveAndFlush(authority);
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/AuthorityRepositoryCoreTest/레코드_수정_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_수정_테스트() {
        Authority authority = authorityRepository.findById(1L).orElseThrow();
        authority.setRole(UserRole.ARTIST);
        authorityRepository.flush();
    }

    @Test
    @ExpectedDatabase(value = "classpath:dbunit/expected/core/AuthorityRepositoryCoreTest/레코드_삭제_테스트.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void 레코드_삭제_테스트() {
        Authority authority = authorityRepository.findById(1L).orElseThrow();
        authorityRepository.delete(authority);
        authorityRepository.flush();
    }
}