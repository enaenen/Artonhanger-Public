package com.artonhanger.manage.respository;
import com.artonhanger.manage.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT m FROM Member m LEFT JOIN FETCH m.roles LEFT JOIN FETCH m.artist" +
            " WHERE m.email= :email and m.meta.enable = true")
    Optional<Member> findMemberByEmail(@Param("email")String email);
    @Query(value = "SELECT m FROM Member AS m LEFT JOIN FETCH m.artist where m.id = :id")
    Member findMemberWithArtistById(@Param("id") Long id);
    @Query(value = "SELECT m FROM Member AS m LEFT JOIN FETCH m.artist  LEFT JOIN FETCH m.roles WHERE m.email= :email")
    Member findMemberWithArtistByEmail(@Param("email") String email);
    Member findMemberById(Long memberId);
    boolean existsMemberByEmail(String email);
    boolean existsByNickname (String nickname);
}