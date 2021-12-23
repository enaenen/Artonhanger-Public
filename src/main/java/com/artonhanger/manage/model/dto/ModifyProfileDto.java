package com.artonhanger.manage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModifyProfileDto {
    private String email;
    private String nickname;
    private String name;
    private String introduction;
    private String bankName;
    private String account;
    private MultipartFile profileImg;
}
