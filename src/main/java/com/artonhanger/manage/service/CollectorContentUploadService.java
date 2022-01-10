package com.artonhanger.manage.service;

import com.artonhanger.manage.model.dto.CollectorContentsDto;
import com.artonhanger.manage.respository.CollectorContentsRepository;
import com.artonhanger.manage.service.dto.MultipartFileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectorContentUploadService {
    private final ObjectStorageService s3Service;
    private final CollectorContentsRepository collectorContentsRepository;

    public void upload(CollectorContentsDto contentsDto) {
        Map<String, String> contentImageUrlMap = uploadContents(contentsDto.collectorsContentsImageToHashMap());
        collectorContentsRepository.save(contentsDto.toEntity(contentImageUrlMap));
    }

    private Map<String, String> uploadContents(Map<String, MultipartFile> contentImageFileMap) {
        Map<String, String> contentImageUrlMap = new HashMap<>();
        for (Map.Entry<String, MultipartFile> contentDtoEntry : contentImageFileMap.entrySet()) {
            MultipartFileUploadRequest request = MultipartFileUploadRequest.builderWithFile(contentDtoEntry.getValue())
                    .fileName(generateS3FileNamePrefixOnly(contentDtoEntry.getKey()))
                    .build();
            String downloadableUrl = s3Service.upload(request).getDownloadableUrl();
            contentImageUrlMap.put(contentDtoEntry.getKey(), downloadableUrl);
        }
        return contentImageUrlMap;
    }

    private String generateS3FileNamePrefixOnly(String prefix){
        String uuid = UUID.randomUUID().toString();
        return prefix+"/"+uuid;
    }
}
