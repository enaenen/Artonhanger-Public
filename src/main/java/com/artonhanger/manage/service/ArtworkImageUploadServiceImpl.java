package com.artonhanger.manage.service;

import com.artonhanger.manage.model.dto.ArtworkImageUploadDto;
import com.artonhanger.manage.service.dto.MultipartFileUploadRequest;
import com.artonhanger.manage.service.dto.UploadResult;
import com.artonhanger.manage.utils.S3UploadPrefix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtworkImageUploadServiceImpl implements ArtworkImageUploadService {
    private final ObjectStorageService s3Service;

    @Override
    @Transactional
    public UploadResult upload(ArtworkImageUploadDto artworkImageUploadDto) {
        MultipartFile file = artworkImageUploadDto.getFile();
        MultipartFileUploadRequest request = MultipartFileUploadRequest.builderWithFile(file)
                .fileName(generateS3FileName(artworkImageUploadDto.getMember().getId()))
                .build();
        return s3Service.upload(request);
    }

    private String generateS3FileName(Long memberId){
        String uuid = UUID.randomUUID().toString();
        return S3UploadPrefix.ARTWORK+memberId.toString()+"/"+uuid;
    }
}
