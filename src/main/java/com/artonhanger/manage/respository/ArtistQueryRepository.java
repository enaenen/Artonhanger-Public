package com.artonhanger.manage.respository;

import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.dto.ArtistSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.artonhanger.manage.model.QArtist.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ArtistQueryRepository {
    private final JPAQueryFactory queryFactory;


    public ArtistQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<Artist> searchArtists(Pageable pageable, ArtistSearchCondition condition){
        List<Artist> content = queryFactory
                .select(artist)
                .from(artist)
                .where(
                        nicknameLike(condition.getNickname()) // TODO [hkpark] 슬로우 쿼리를 유발할 수 있음
                                .or(nameLike(condition.getName()))
                                .or(emailLike(condition.getEmail()))
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Artist> countQuery = queryFactory
                .select(artist)
                .from(artist)
                .where(
                        nicknameLike(condition.getNickname())
                                .or(nameLike(condition.getName()))
                                .or(emailLike(condition.getEmail()))
                );
        Page<Artist> page = PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
        return page;
    }

    private BooleanExpression nicknameLike(String nickname){
        return hasText(nickname) ? artist.member.nickname.contains(nickname) : Expressions.FALSE.isNull();
    }
    private BooleanExpression nameLike(String name){
        return hasText(name) ? artist.member.name.contains(name) : Expressions.FALSE.isNull();
    }
    private BooleanExpression emailLike(String email){
        return hasText(email) ? artist.member.email.contains(email) : Expressions.FALSE.isNull();
    }
}