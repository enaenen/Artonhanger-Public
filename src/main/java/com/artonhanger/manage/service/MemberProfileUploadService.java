package com.artonhanger.manage.service;

import com.artonhanger.manage.service.dto.MultipartFileUploadRequest;
import com.artonhanger.manage.utils.S3UploadPrefix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberProfileUploadService {
    private final ObjectStorageService s3Service;

    public String memberProfileImageUpload(MultipartFile file, Long memberId) {
        if(file==null || file.isEmpty())
            return "";
        MultipartFileUploadRequest request = MultipartFileUploadRequest.builderWithFile(file)
                .dirName(S3UploadPrefix.PROFILE + memberId.toString())
                .build();
        return s3Service.upload(request).getDownloadableUrl();
    }

    public void memberProfileImageDelete(String fileName){
        s3Service.remove(fileName);
    }
}
