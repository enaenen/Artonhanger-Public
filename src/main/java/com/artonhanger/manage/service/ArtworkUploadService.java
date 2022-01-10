package com.artonhanger.manage.service;

import com.artonhanger.manage.model.Artwork;
import com.artonhanger.manage.model.ArtworkImage;
import com.artonhanger.manage.model.dto.ArtworkImageUploadDto;
import com.artonhanger.manage.model.dto.ArtworkUploadDto;
import com.artonhanger.manage.respository.ArtworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkUploadService {
    private final ArtworkRepository artworkRepository;
    private final ImwebArtworkService imwebArtworkService;
    private final ArtworkImageUploadService artworkImageUploadService;

    @Transactional
    public void artworkUpload(ArtworkUploadDto artworkUploadDto) {
        List<ArtworkImageUploadResult> uploadResults = uploadArtworkImages(artworkUploadDto);
        List<ArtworkImage> artworkImages = buildArtworkImages(uploadResults);
        Artwork artwork = saveArtwork(artworkUploadDto, artworkImages);

        String imwebId = imwebArtworkService.addArtwork(artwork, artworkUploadDto).getImwebId();
        artwork.setImwebId(imwebId);
    }

    private List<ArtworkImageUploadResult> uploadArtworkImages(ArtworkUploadDto artworkUploadDto) {
        List<ArtworkImageUploadResult> uploadResults = new ArrayList<>();
        for (int i = 0; i < artworkUploadDto.getArtworkImages().size(); i++) {
            String fileName = artworkImageUploadService.upload(ArtworkImageUploadDto.builder()
                    .member(artworkUploadDto.getMember())
                    .file(artworkUploadDto.getArtworkImages().get(i))
                    .build()).getDownloadableUrl();
            String description = artworkUploadDto.getDescriptions().get(i);

            uploadResults.add(new ArtworkImageUploadResult(fileName, description));
        }
        return uploadResults;
    }

    private List<ArtworkImage> buildArtworkImages(List<ArtworkImageUploadResult> uploadResults) {
        return uploadResults.stream()
                .map(x -> ArtworkImage.builder().name(x.downloadableUrl).description(x.description).build())
                .collect(Collectors.toList());
    }

    private Artwork saveArtwork(ArtworkUploadDto artworkUploadDto, List<ArtworkImage> artworkImages) {
        artworkUploadDto.setRepresentativeImage(artworkImages.get(0).getName());
        Artwork artwork = artworkUploadDto.toEntity(artworkImages);
        return artworkRepository.save(artwork);
    }

    @RequiredArgsConstructor
    private static class ArtworkImageUploadResult {
        private final String downloadableUrl;
        private final String description;
    }
}
