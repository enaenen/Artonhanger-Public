package com.artonhanger.manage.service;

import com.artonhanger.manage.service.dto.FileUploadRequest;
import com.artonhanger.manage.service.dto.MultipartFileUploadRequest;
import com.artonhanger.manage.service.dto.UploadResult;

public interface ObjectStorageService {
    UploadResult upload(FileUploadRequest request);
    UploadResult upload(MultipartFileUploadRequest request);
    void remove(String fileName);
}
