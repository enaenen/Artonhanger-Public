package com.artonhanger.manage.respository;

import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.dto.ArtworkListDto;
import com.artonhanger.manage.model.dto.ArtworkSearchCondition;
import com.artonhanger.manage.model.dto.QArtworkListDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.artonhanger.manage.model.QArtwork.artwork;
import static com.artonhanger.manage.model.QMember.member;
import static org.springframework.util.StringUtils.hasText;


public class ArtworkRepositoryCustomImpl implements ArtworkRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ArtworkRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ArtworkListDto> load(Pageable pageable) {
        List<ArtworkListDto> content = queryFactory
                .select(new QArtworkListDto(
                        artwork.id.as("artworkId"),
                        artwork.imwebId.as("imwebId"),
                        artwork.title.as("title"),
                        artwork.thumbnail.as("thumbnail"),
                        artwork.member.name.as("artistName"),
                        artwork.price.as("price")
                ))
                .from(artwork)
                .leftJoin(artwork.member, member)
                .orderBy(artwork.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Artwork> countQuery = queryFactory
                .select(artwork)
                .from(artwork)
                .leftJoin(artwork.member, member);
        return PageableExecutionUtils.getPage(content,pageable, countQuery::fetchCount);
    }

    @Override
    public Page<ArtworkListDto> search(ArtworkSearchCondition condition, Pageable pageable){
        List<ArtworkListDto> content = queryFactory
                .select(new QArtworkListDto(
                        artwork.id.as("artworkId"),
                        artwork.imwebId.as("imwebId"),
                        artwork.title.as("title"),
                        artwork.thumbnail.as("thumbnail"),
                        artwork.member.name.as("artistName"),
                        artwork.price.as("price")
                ))
                .from(artwork)
                .leftJoin(artwork.member, member)
                .where(
                        titleLike(condition.getTitle())
                        .or(nicknameLike(condition.getNickname()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Artwork> countQuery = queryFactory
                .select(artwork)
                .from(artwork)
                .leftJoin(artwork.member, member)
                .where(titleLike(condition.getTitle())
                        .or(nicknameLike(condition.getNickname()))
                );
        return PageableExecutionUtils.getPage(content,pageable, countQuery::fetchCount);
    }
    private BooleanExpression nicknameLike(String nickname){
        return hasText(nickname) ? artwork.member.nickname.contains(nickname) : Expressions.FALSE.isNull();
    }

    private BooleanExpression titleLike(String title) {
        return hasText(title) ? artwork.title.contains(title) : Expressions.FALSE.isNull();
    }

    private BooleanExpression nicknameEq(String nickname) {
        return hasText(nickname) ? artwork.member.nickname.eq(nickname) : Expressions.FALSE.isNull();
    }

    private BooleanExpression priceLoe(Integer priceLoe) {
        return priceLoe != null ? artwork.price.loe(priceLoe) : null;
    }

    private BooleanExpression titleEq(String title) {
        return hasText(title) ? artwork.title.eq(title) : null;
    }
}
