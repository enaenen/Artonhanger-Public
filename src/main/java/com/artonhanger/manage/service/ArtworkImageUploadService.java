package com.artonhanger.manage.service;

import com.artonhanger.manage.model.dto.ArtworkImageUploadDto;
import com.artonhanger.manage.service.dto.UploadResult;

public interface ArtworkImageUploadService {
    UploadResult upload(ArtworkImageUploadDto artworkImageUploadDto);
}
