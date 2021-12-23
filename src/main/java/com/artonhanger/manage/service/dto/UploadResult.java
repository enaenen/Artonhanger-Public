package com.artonhanger.manage.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class UploadResult {
    private final String downloadableUrl;
}
