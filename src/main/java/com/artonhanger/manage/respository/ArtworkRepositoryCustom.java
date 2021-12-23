package com.artonhanger.manage.respository;

import com.artonhanger.manage.model.dto.ArtworkListDto;
import com.artonhanger.manage.model.dto.ArtworkSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

public interface ArtworkRepositoryCustom {

    Page<ArtworkListDto> load(Pageable pageable);
    Page<ArtworkListDto> search(ArtworkSearchCondition artworkSearchCondition, Pageable pageable);
}
