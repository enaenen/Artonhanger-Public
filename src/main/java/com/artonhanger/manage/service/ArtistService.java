package com.artonhanger.manage.service;

import com.artonhanger.manage.model.Artist;
import com.artonhanger.manage.model.dto.ArtistSearchCondition;
import com.artonhanger.manage.respository.ArtistQueryRepository;
import com.artonhanger.manage.respository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("artistService")
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistQueryRepository artistQueryRepository;
    private final ArtistRepository artistRepository;

    public Page<Artist> loadArtistList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 100);
        return artistRepository.findAllArtistWithMember(pageable);
    }
    public Page<Artist> searchArtist(Pageable pageable,ArtistSearchCondition condition){
        return artistQueryRepository.searchArtists(pageable, condition);

    }
}
