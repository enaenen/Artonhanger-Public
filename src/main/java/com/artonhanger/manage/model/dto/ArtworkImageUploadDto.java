package com.artonhanger.manage.model.dto;

import com.artonhanger.manage.model.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class ArtworkImageUploadDto {
    private Member member;
    private MultipartFile file;
}
