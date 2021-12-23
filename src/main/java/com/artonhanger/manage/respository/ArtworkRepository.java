package com.artonhanger.manage.respository;

import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.enums.DeliveryStateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long>, JpaSpecificationExecutor<Artwork>, ArtworkRepositoryCustom {
    @Query(value = "SELECT a FROM Artwork  a JOIN FETCH a.artworkImages WHERE a.id= :id")
    Artwork findWithImagesById(@Param("id") Long id);

    @Query("SELECT a FROM Artwork  a LEFT JOIN FETCH a.artworkFrame WHERE a.id = :id")
    Artwork findWithFrameById(@Param("id")Long id);

    Page<Artwork> findAllByMember_IdAndMeta_EnableIsTrue(Long memberId, Pageable pageable);
    Page<Artwork> findAllByMember_IdAndMeta_EnableIsTrueAndImwebIdIsNotNull(Long memberId, Pageable pageable);
}