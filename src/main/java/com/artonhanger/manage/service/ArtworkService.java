package com.artonhanger.manage.service;

import com.artonhanger.manage.enums.ErrorEnum;
import com.artonhanger.manage.exception.AOHException;
import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.dto.ArtworkListDto;
import com.artonhanger.manage.model.dto.ArtworkModifyDto;
import com.artonhanger.manage.model.dto.ArtworkModifyViewDto;
import com.artonhanger.manage.model.dto.ArtworkSearchCondition;
import com.artonhanger.manage.respository.ArtworkFrameRepository;
import com.artonhanger.manage.respository.ArtworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtworkService {
    private final ArtworkRepository artworkRepository;
    private final ArtworkFrameRepository artworkFrameRepository;
    private final ImwebArtworkService imwebArtworkService;

    public Page<ArtworkListDto> findArtworkListByMemberId(Long memberId, Pageable pageable) {
        return artworkRepository.findAllByMember_IdAndMeta_EnableIsTrue(memberId, pageable).map(artwork -> new ArtworkListDto(artwork));
    }

    public Page<ArtworkListDto> findArtworkListByMemberIdAndImwebId(Long memberId, Pageable pageable) {
        return artworkRepository.findAllByMember_IdAndMeta_EnableIsTrueAndImwebIdIsNotNull(memberId, pageable).map(artwork -> new ArtworkListDto(artwork));
    }

    public ArtworkModifyViewDto findArtworkByArtworkId(Long artworkId) {
        Artwork artwork = artworkRepository.findWithImagesById(artworkId);
        ArtworkModifyViewDto artworkModifyViewDto = new ArtworkModifyViewDto(artwork);
        if(artworkModifyViewDto.isEdition())
            imwebArtworkService.loadStockAmountFromImweb(artworkModifyViewDto);
        return artworkModifyViewDto;
    }

    public Page<ArtworkListDto> loadAllArtworks(Pageable pageable){
        return artworkRepository.load(pageable);
    }

    public Page<ArtworkListDto> searchArtworks(ArtworkSearchCondition condition,Pageable pageable){
        return artworkRepository.search(condition, pageable);
    }

    @Transactional
    public void artworkModify(Long artworkId, ArtworkModifyDto artworkModifyDto) {
        Artwork artwork = artworkRepository.findWithFrameById(artworkId);
        if (artwork == null)
            throw new AOHException(ErrorEnum.ARTWORK_NOT_FOUND);
        if (isArtworkFrameRemoved(artwork, artworkModifyDto))
            artworkFrameDeleteProcess(artwork);
        artwork.modifyArtworkByMember(artworkModifyDto);
        imwebArtworkService.modifyArtwork(artwork, artworkModifyDto);
    }
    private boolean isArtworkFrameRemoved(Artwork artwork, ArtworkModifyDto dto){
        if (artwork.getArtworkFrame() != null && !dto.getPhotoFrame())
            return true;
        return false;
    }
    private void artworkFrameDeleteProcess(Artwork artwork){
        artworkFrameRepository.delete(artwork.getArtworkFrame());
        artwork.deleteArtworkFrame();
    }

    @Transactional
    public void artworkDelete(Long artworkId){
        Optional<Artwork> artwork = artworkRepository.findById(artworkId);
        Long imwebId = artwork.get().getImwebId();
        if (imwebId!=null)
            imwebArtworkService.deleteArtwork(imwebId);
        artworkRepository.deleteById(artworkId);
    }
}
